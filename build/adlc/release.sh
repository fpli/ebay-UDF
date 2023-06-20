#!/usr/bin/env bash

WORKSPACE="$(cd "`dirname "$0"`/../.."; pwd)"

# excluding search-qu-udf temporarily because of building issue
mvn -s $WORKSPACE/build/adlc/maven-settings.xml clean package -f $WORKSPACE -DskipTests -U -pl bx-udf,common-udf,core-udf,dapgap-udf,datelib-udf,deploy,dss-shpmt-udf,ebaylib-udf,ecg-udf,ep-udf,hadoop-udf,nudata-udf,risk-udf,soj-udf,sys-udf,udf-tags,tokenizer-udf
