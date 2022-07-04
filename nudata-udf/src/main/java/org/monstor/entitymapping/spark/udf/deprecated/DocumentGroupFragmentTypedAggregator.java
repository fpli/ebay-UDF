package org.monstor.entitymapping.spark.udf.deprecated;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.spark.sql.Encoder;
import org.apache.spark.sql.Encoders;
import org.apache.spark.sql.catalyst.expressions.GenericRowWithSchema;
import org.apache.spark.sql.expressions.Aggregator;
import org.monstor.entitymapping.model.DocumentGroup;
import org.monstor.entitymapping.model.DocumentGroupFragment;
import org.monstor.entitymapping.transform.DocumentGroupTransforms;
import org.monstor.entitymapping.utils.DocumentGroupDecoders;
import org.monstor.entitymapping.utils.DocumentGroupMerger;

public class DocumentGroupFragmentTypedAggregator extends Aggregator<GenericRowWithSchema, DocumentGroup, TransformedJsonWrapper> {
    private final String transformerCls;
    private final String transformName;
    public DocumentGroupFragmentTypedAggregator(String transformerCls, String transformName){
        this.transformerCls = transformerCls;
        this.transformName = transformName;
    }

    @Override
    public DocumentGroup zero() {
        return new DocumentGroup();
    }

    @Override
    public DocumentGroup reduce(DocumentGroup b, GenericRowWithSchema a) {
        // decode to DocumentGroupFragmentP
        DocumentGroupFragment f = DocumentGroupDecoders.decodeAsDocumentGroupFragment((byte[]) a.get(2));
        // validate groupKey?
        // merge
        return DocumentGroupMerger.merge(b, f.getDocumentGroup());
    }

    @Override
    public DocumentGroup merge(DocumentGroup b1, DocumentGroup b2) {
        return DocumentGroupMerger.merge(b1, b2);
    }

    @Override
    public TransformedJsonWrapper finish(DocumentGroup reduction) {
        try {
            Object ppf = DocumentGroupTransforms.transform(reduction, transformerCls, transformName);
            ObjectMapper mapper = new ObjectMapper();
            String json = mapper.writeValueAsString(ppf);
            TransformedJsonWrapper jsonWrapper = new TransformedJsonWrapper();
            jsonWrapper.setJson(json);
            return jsonWrapper;
        }catch (Exception ex){
            throw new RuntimeException(ex);
        }
    }

    @Override
    public Encoder<DocumentGroup> bufferEncoder() {
        return Encoders.bean(DocumentGroup.class);
    }

    @Override
    public Encoder<TransformedJsonWrapper> outputEncoder() {
        return Encoders.bean(TransformedJsonWrapper.class);
    }
}
