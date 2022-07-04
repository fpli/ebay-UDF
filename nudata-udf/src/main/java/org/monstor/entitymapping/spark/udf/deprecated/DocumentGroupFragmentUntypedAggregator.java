package org.monstor.entitymapping.spark.udf.deprecated;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.spark.sql.Encoder;
import org.apache.spark.sql.Encoders;
import org.apache.spark.sql.expressions.Aggregator;
import org.monstor.entitymapping.model.DocumentGroup;
import org.monstor.entitymapping.model.DocumentGroupFragment;
import org.monstor.entitymapping.transform.DocumentGroupTransforms;
import org.monstor.entitymapping.utils.DocumentGroupDecoders;
import org.monstor.entitymapping.utils.DocumentGroupMerger;

public class DocumentGroupFragmentUntypedAggregator extends Aggregator<byte[], DocumentGroup, String> {
    private final String transformerCls;
    private final String transformName;

    public DocumentGroupFragmentUntypedAggregator(String transformerCls, String transformName) {
        this.transformerCls = transformerCls;
        this.transformName = transformName;
    }

    @Override
    public DocumentGroup zero() {
        return new DocumentGroup();
    }

    @Override
    public DocumentGroup reduce(DocumentGroup b, byte[] a) {
        DocumentGroupFragment f = DocumentGroupDecoders.decodeAsDocumentGroupFragment(a);
        return DocumentGroupMerger.merge(b, f.getDocumentGroup());
    }

    @Override
    public DocumentGroup merge(DocumentGroup b1, DocumentGroup b2) {
        return DocumentGroupMerger.merge(b1, b2);
    }

    @Override
    public String finish(DocumentGroup reduction) {
        try {
            Object ppf = DocumentGroupTransforms.transform(reduction, transformerCls, transformName);
            ObjectMapper mapper = new ObjectMapper();
            return mapper.writeValueAsString(ppf);
        }catch (Exception ex){
            throw new RuntimeException(ex);
        }
    }

    @Override
    public Encoder<DocumentGroup> bufferEncoder() {
        return Encoders.bean(DocumentGroup.class);
    }

    @Override
    public Encoder<String> outputEncoder() {
        return Encoders.STRING();
    }
}
