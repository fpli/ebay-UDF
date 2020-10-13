package com.ebay.hadoop.udf.soj;

import com.ebay.hadoop.udf.utils.SOJParseClientInfo;
import org.apache.hadoop.hive.ql.exec.UDF;
import org.apache.hadoop.io.Text;

public final class ClientInfoParser extends UDF {

    public Text evaluate(final Text clientInfoTexT, String clientField) {
        if (clientInfoTexT == null || clientField == null) {
            return null;
        } 
        
        String clientInfo = clientInfoTexT.toString();
        String value = SOJParseClientInfo.getClientInfo(clientInfo, clientField);
        if (value == null) {
            return null;
        } else {
            return new Text(value);
        }
    }
}
