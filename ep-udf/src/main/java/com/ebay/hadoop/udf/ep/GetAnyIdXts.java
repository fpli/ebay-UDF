package com.ebay.hadoop.udf.ep;

import com.ebay.ep.impl.tracking.avro.EpAvroDeserializer;
import com.ebay.ep.impl.tracking.avro.EpAvroException;
import com.ebay.tracking.ep.AnyId;
import com.ebay.tracking.ep.EpContext;
import org.apache.hadoop.hive.ql.exec.UDF;
import org.apache.hadoop.hive.ql.exec.UDFArgumentException;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Get AnyID xt and id from epcontext.
 *
 * @author minding
 */
public class GetAnyIdXts extends UDF {
    private static final String ANYID_TO_STR_TEMPLATE = "%s:%s";

    public String evaluate(String epContextTag) throws UDFArgumentException {

        EpContext epContext = null;
        try {
            epContext = EpAvroDeserializer.deserialize(epContextTag);
        } catch (EpAvroException e) {
            throw new UDFArgumentException("Illegal epContextTag");
        }
        if (epContext == null) {
            return null;
        }
        List<AnyId> anyIds = epContext.getAnyId();
        if (anyIds != null && !anyIds.isEmpty()) {
            return anyIds.stream().map(GetAnyIdXts::anyId2Str).collect(Collectors.joining(";"));
        } else {
            return null;
        }

    }

    private static String anyId2Str(AnyId anyId) {
        String id = anyId.getVal().toString();
        String xts = anyId.getXt().stream().map(String::valueOf).collect(Collectors.joining(","));
        return String.format(ANYID_TO_STR_TEMPLATE,id, xts);
    }
}
