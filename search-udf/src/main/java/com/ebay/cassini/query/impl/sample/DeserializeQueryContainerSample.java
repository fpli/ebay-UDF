// Copyright (c) 2011 to 2013 eBay inc. All rights reserved.
package com.ebay.cassini.query.impl.sample;

import org.apache.commons.codec.binary.Base64;
import java.io.*;

import com.ebay.cassini.common.exceptions.SBEException;
import com.ebay.cassini.query.api.core.QueryInputContainer;
import com.ebay.cassini.query.api.service.QueryApi;
import com.ebay.cassini.query.api.service.QueryRequest;
import com.ebay.cassini.query.api.service.QueryResponse;
import com.ebay.cassini.query.api.service.QueryService;
import com.ebay.cassini.query.api.service.RequestProcessor;
import com.ebay.cassini.query.impl.serviceimpl.QueryApiManager;

/**
 * This is the sample code for deserializing base64 or blob query containers
 * without sending the requests to Cassini.
 */
public class DeserializeQueryContainerSample {

    /**
     * Private constructor for a utility class.
     */
    private DeserializeQueryContainerSample() {
    }

    /**
     * The main program.
     */
    public static void main(final String[] args) throws SBEException {

        // ********************************************************************
        // The QueryApi object could be constructed from a customized api configuration file,
        // in which
        // - host information could be neglected, as it is not needed for this use case
        // - path to local metadata files could be specified
        //
        // The advantage of using local metadata files is that the api does not need to
        // connect to any Cassini endpoints, since it does not need to send any requests,
        // nor does it need to pull metadata files from it. I.e. the api could be ran
        // completely offline.
        //
        // Example of no_mds_api_config.json could be found at
        // https://github.scm.corp.ebay.com/SBE/cassini-java-query/raw/master/api/src/main/resources/no_mds_api_config.json
        //
        // ********************************************************************

        String configFilePath = "no_mds_api_config.json";

        // configFilePath is the full file path to the test_config.json file.
        InputStream is = com.ebay.search.util.CommonUtils.accessFile(configFilePath);

        // Create QueryApi from the input stream.
        QueryApi api = QueryApiManager.createQueryApi(is);

        // QueryApi api = QueryApiManager.createQueryApi(configFilePath);

        // The base64 representation of a query container.
        String base64Container = "DkNTV0ZWMQAGAgAYY2Fzc2luaV9kZXYAAhRRdWVyeVRpbWUAFDE0MDMzMTMwOTMCAgACAAIAAAACACZpdGVtX2FjdGl2ZStzZWxsZXIAAAIQc2l0ZV9pZAACMAAAAgwicXVlcnlfZXhwcmVzc2lvbgAIYW5kAAQMQHF1ZXJ5X2V4cHJlc3Npb25fcmF3X3VzZXJfcXVlcnkAHnJhd191c2VyX3F1ZXJ5AAAAIGFwcGxlIGlwb2QgbmFubwAadGh1bmRlcnN0b25lAA4AHmhhc3ZhbHVlX2ludDY0AAQMPHF1ZXJ5X2V4cHJlc3Npb25fdGVybV9wYXlsb2FkACR0ZXJtX212YWx1ZV9pbnQ2NAAAACZpdGVtX2FjdGl2ZStzZWxsZXIAFGF0dHJpYnV0ZQAOggHYAdgBhgHCAegB5gEMNHF1ZXJ5X2V4cHJlc3Npb25fY29uc3RhbnQAGGNvbnN0X2ludDY0AAAACgIA3oEJAAAAAAAAAgIAAgACAADIAR5kZWZhdWx0X291dHB1dAAmaXRlbV9hY3RpdmUrc2VsbGVyAAAAAAICAAIACqgB0gHoAdgBygEOBBR0ZXJtX2Jsb2IAAAAmaXRlbV9hY3RpdmUrc2VsbGVyABRhdHRyaWJ1dGUACqgB0gHoAdgBygEAAAAAAAAC";

        try {
            RequestProcessor p = new Base64RequestProcessor(base64Container);

            // This calls the "populateRequest" method of p only.
            // The request is not sent to Cassini.
            api.populateRequest(p);



        } finally {
            api.close();
        }
    }

    /**
     * The implementation of the RequestProcessor which deserializes a base64 string
     * into a query container.
     */
    public static class Base64RequestProcessor implements RequestProcessor {

        private String base64Container;

        public Base64RequestProcessor(final String base64Container) {
            this.base64Container = base64Container;
        }

        @Override
        public void populateRequest(QueryRequest req) throws SBEException {

            QueryInputContainer container = req.getQueryContainer();
            QueryService svc = req.getService();

            byte[] containerBlob = Base64.decodeBase64(base64Container);
            svc.deserializeQueryContainer(container, containerBlob);
            System.out.println("\ncomputeQueryStatistics\n" + svc.computeQueryStatistics(container));

            System.out.println("\n====\ngetUsecaseName " + req.getUsecaseName());
            System.out.println("getClientName " + req.getClientName());
            System.out.println("getQueryContainer " + req.getQueryContainer());
            System.out.println("getParams " + req.getParams());
            System.out.println("\n=====\n" + container);

            try {
                for (int idx = 0; idx > -1; idx++) {
                    System.out.println("idx " + idx);
                    System.out.println("container: query and flags " + idx + " = " +
                            container.getQueryAndFlags(idx));
                    System.out.println("container: query at " + idx + " = " +
                            container.getQueryAndFlags(idx).getQuery());
                }
            }
            catch (Throwable t) {
            }
        }

        @Override
        public void processResponse(QueryRequest req, QueryResponse res) throws SBEException {
            // Do nothing. This method won't be called in api.processRequestUnexecuted.
        }
    }
}
