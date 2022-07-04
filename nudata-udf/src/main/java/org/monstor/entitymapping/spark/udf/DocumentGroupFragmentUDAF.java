package org.monstor.entitymapping.spark.udf;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.spark.sql.Row;
import org.apache.spark.sql.catalyst.expressions.GenericRowWithSchema;
import org.apache.spark.sql.expressions.MutableAggregationBuffer;
import org.apache.spark.sql.expressions.UserDefinedAggregateFunction;
import org.apache.spark.sql.types.DataType;
import org.apache.spark.sql.types.DataTypes;
import org.apache.spark.sql.types.StructField;
import org.apache.spark.sql.types.StructType;
import org.monstor.entitymapping.model.DocumentGroup;
import org.monstor.entitymapping.model.DocumentGroupFragment;
import org.monstor.entitymapping.model.DocumentMetadata;
import org.monstor.entitymapping.model.DocumentWithMetadata;
import org.monstor.entitymapping.transform.DocumentGroupTransforms;
import org.monstor.entitymapping.utils.DocumentGroupDecoders;
import org.monstor.entitymapping.utils.DocumentGroupMerger;
import scala.Tuple2;
import scala.Tuple4;
import scala.collection.Seq;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * `input` has below schema (Note: transformer class and transform method are passed as input arguments)
 * root
 *  |-- value: binary (nullable = true)
 *  |-- transformerCls: string (nullable = true)
 *  |-- transformName: string (nullable = true)
 *
 *  `buffer` has below schema
 *  root
 *  |-- root: struct (nullable = true)
 *  |    |-- metadata: struct (nullable = true)
 *  |    |    |-- docset: string (nullable = true)
 *  |    |    |-- key: binary (nullable = true)
 *  |    |    |-- creationTime: long (nullable = true)
 *  |    |    |-- lastModified: long (nullable = true)
 *  |    |-- docAsBson: binary (nullable = true)
 *  |-- children: map (nullable = true)
 *  |    |-- key: string
 *  |    |-- value: array (valueContainsNull = true)
 *  |    |    |-- element: struct (containsNull = true)
 *  |    |    |    |-- metadata: struct (nullable = true)
 *  |    |    |    |    |-- docset: string (nullable = true)
 *  |    |    |    |    |-- key: binary (nullable = true)
 *  |    |    |    |    |-- creationTime: long (nullable = true)
 *  |    |    |    |    |-- lastModified: long (nullable = true)
 *  |    |    |    |-- docAsBson: binary (nullable = true)
 *  |-- transformerCls: string (nullable = true)
 *  |-- transformName: string (nullable = true)
 */
public class DocumentGroupFragmentUDAF extends UserDefinedAggregateFunction {
    private final StructType inputSchema;
    private final StructType bufferSchema;

    public DocumentGroupFragmentUDAF(){
        List<StructField> inputFields = new ArrayList<>();
        inputFields.add(DataTypes.createStructField("value", DataTypes.BinaryType, true));
        inputFields.add(DataTypes.createStructField("transformerCls", DataTypes.StringType, true));
        inputFields.add(DataTypes.createStructField("transformName", DataTypes.StringType, true));
        inputSchema = DataTypes.createStructType(inputFields);

        List<StructField> documentMetadataFields = new ArrayList<>();
        documentMetadataFields.add(DataTypes.createStructField("docset", DataTypes.StringType, true));
        documentMetadataFields.add(DataTypes.createStructField("key", DataTypes.BinaryType, true));
        documentMetadataFields.add(DataTypes.createStructField("creationTime", DataTypes.LongType, true));
        documentMetadataFields.add(DataTypes.createStructField("lastModified", DataTypes.LongType, true));
        StructType documentMetadataType = DataTypes.createStructType(documentMetadataFields);

        List<StructField> documentWithMetadataFields = new ArrayList<>();
        documentWithMetadataFields.add(DataTypes.createStructField("metadata", documentMetadataType, true));
        documentWithMetadataFields.add(DataTypes.createStructField("docAsBson", DataTypes.BinaryType, true));
        StructType documentWithMetadataType = DataTypes.createStructType(documentWithMetadataFields);

        List<StructField> documentGroupFields = new ArrayList<>();
        documentGroupFields.add(DataTypes.createStructField("root", documentWithMetadataType, true));
        documentGroupFields.add(DataTypes.createStructField("children",
                DataTypes.createMapType(DataTypes.StringType, DataTypes.createArrayType(documentWithMetadataType)), true));
        documentGroupFields.add(DataTypes.createStructField("transformerCls", DataTypes.StringType, true));
        documentGroupFields.add(DataTypes.createStructField("transformName", DataTypes.StringType, true));
        bufferSchema = DataTypes.createStructType(documentGroupFields);
    }

    @Override
    public StructType inputSchema() {
        return inputSchema;
    }

    @Override
    public StructType bufferSchema() {
        return bufferSchema;
    }

    @Override
    public DataType dataType() {
        return DataTypes.StringType;
    }

    @Override
    public boolean deterministic() {
        return true;
    }

    /**
     * @param buffer, 4 elements: root, children, transformerCls, transformName
     */
    @Override
    public void initialize(MutableAggregationBuffer buffer) {
        buffer.update(0, new Tuple2<>(null, null)); // root document
        buffer.update(1, new HashMap<String, List<Tuple2>>()); // children documents
        buffer.update(2, ""); // transformerCls
        buffer.update(3, ""); // transformName
    }

    private Tuple2<Tuple4<String, byte[], Long, Long>, byte[]> encodeDocumentWithMetadata(DocumentWithMetadata dwm){
        return new Tuple2<>(
                new Tuple4<>(dwm.getMetadata().getDocset(),
                        dwm.getMetadata().getKey(),
                        dwm.getMetadata().getCreationDate(),
                        dwm.getMetadata().getLastModified()),
                dwm.getDocAsBson());
    }

    private Map<String, List<Tuple2>> encodeChildren(Map<String, List<DocumentWithMetadata>> children) {
        Map<String, List<Tuple2>> convertedChildren = new HashMap<>();
        for(Map.Entry<String, List<DocumentWithMetadata>> entry : children.entrySet()){
            List<DocumentWithMetadata> docs = entry.getValue();
            List<Tuple2> convertedDocs = new ArrayList<>();
            for(DocumentWithMetadata doc : docs){
                convertedDocs.add(encodeDocumentWithMetadata(doc));
            }
            convertedChildren.put(entry.getKey(), convertedDocs);
        }
        return convertedChildren;
    }

    private DocumentWithMetadata decodeRowAsDocumentWithMetadata(GenericRowWithSchema docWithMRow){
        DocumentWithMetadata dwm = new DocumentWithMetadata();
        dwm.setDocAsBson((byte[]) docWithMRow.get(1));

        GenericRowWithSchema docMRow = (GenericRowWithSchema) docWithMRow.get(0);
        DocumentMetadata dm = new DocumentMetadata();
        if(docMRow != null) {
            dm.setDocset(docMRow.getString(0));
            dm.setKey((byte[]) docMRow.get(1));
            dm.setCreationDate(docMRow.getLong(2));
            dm.setLastModified(docMRow.getLong(3));
        }
        dwm.setMetadata(dm);

        return dwm;
    }

    /**
     * According to buffer schema, first element is root document, second element is children documents
     */
    private DocumentGroup buffer2DocumentGroup(Row buffer){
        // create DocumentGroup from buffer
        return buffer2DocumentGroup((GenericRowWithSchema)buffer.get(0), buffer.getJavaMap(1));
    }

    private DocumentGroup buffer2DocumentGroup(GenericRowWithSchema rootDoc, Map<String, Seq> childDocs){
        DocumentGroup dg = new DocumentGroup();

        // DocumentWithMetadata
        DocumentWithMetadata docWithM = decodeRowAsDocumentWithMetadata(rootDoc);
        dg.setRoot(docWithM);

        // children
        Map<String, List<DocumentWithMetadata>> bufferChildren = new HashMap<>();
        for(Map.Entry<String, Seq> entry : childDocs.entrySet()){
            // convert GenericRowWithSchema(tuple) back to DocumentWithMetadata
            List<DocumentWithMetadata> list = new ArrayList<>();
            scala.collection.Iterator<GenericRowWithSchema> iterator = entry.getValue().iterator();
            while (iterator.hasNext()) {
                list.add(decodeRowAsDocumentWithMetadata(iterator.next()));
            }
            bufferChildren.put(entry.getKey(), list);
        }
        dg.setChildren(bufferChildren);

        return dg;
    }

    /**
     *
     * @param buffer, 4 elements: root, children, transformerCls, transformName
     * @param input, 3 elements: encoded fragment,  transformerCls, transformName
     */
    @Override
    public void update(MutableAggregationBuffer buffer, Row input) {
        // create DocumentGroup from buffer
        DocumentGroup dg1;
        if (!buffer.isNullAt(0)) {
            // create DocumentGroup from buffer
            dg1 = buffer2DocumentGroup(buffer);
        }else {
            dg1 = new DocumentGroup();
        }

        // get encoded DocumentGroupFragmentP from input
        byte[] encoded = (byte[]) input.get(0);
        DocumentGroupFragment f = DocumentGroupDecoders.decodeAsDocumentGroupFragment(encoded);
        DocumentGroup dg2 = f.getDocumentGroup();

        // merge DocumentGroup(s)
        DocumentGroup merged = DocumentGroupMerger.merge(dg1, dg2);

        // update buffer
        // update DocumentWithMetadata
        buffer.update(0, encodeDocumentWithMetadata(merged.getRoot()));

        // update children
        buffer.update(1, encodeChildren(merged.getChildren()));

        // update transformerCls and transformName
        buffer.update(2, input.getString(1));
        buffer.update(3, input.getString(2));
    }

    /**
     *
     * @param buffer1, 4 elements: root, children, transformerCls, transformName
     * @param buffer2, 4 elements: root, children, transformerCls, transformName
     */
    @Override
    public void merge(MutableAggregationBuffer buffer1, Row buffer2) {
        // create DocumentGroup from buffer
        DocumentGroup dg1;
        if (!buffer1.isNullAt(0)) {
            // create DocumentGroup from buffer
            dg1 = buffer2DocumentGroup(buffer1);
        }else {
            dg1 = new DocumentGroup();
        }

        // create DocumentGroup from input
        DocumentGroup dg2 = buffer2DocumentGroup(buffer2);

        // merge DocumentGroup(s)
        DocumentGroup result = DocumentGroupMerger.merge(dg1, dg2);
        buffer1.update(0, encodeDocumentWithMetadata(result.getRoot()));
        buffer1.update(1, encodeChildren(result.getChildren()));

        // update transformerCls and transformName
        buffer1.update(2, buffer2.getString(2));
        buffer1.update(3, buffer2.getString(3));
    }

    @Override
    public String evaluate(Row buffer) {
        try {
            DocumentGroup dg = buffer2DocumentGroup(buffer);
            Object ppf = DocumentGroupTransforms.transform(dg, buffer.getString(2), buffer.getString(3));
            ObjectMapper mapper = new ObjectMapper();
            return mapper.writeValueAsString(ppf);
        }catch (Exception ex){
            throw new RuntimeException(ex);
        }
    }
}
