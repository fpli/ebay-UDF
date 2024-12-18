# ebay-UDF

## Several requirements

1. No external dependency 
2. No system.out
3. Unit test
4. Write with java language

## Release process(For users)

1. Create the PR and get initial approval
2. Release in corresponding test db and hermes will do the integration test
3. Make self-service udf testing on prod env, https://wiki.vip.corp.ebay.com/display/~fwang12/Self+service+UDF+testing
4. Merge the PR and release in production

## Known issues:

1. The `org.apache.hadoop.hive.ql.udf.generic.GenericUDF` based UDF might not work for spark-3.
For spark-3, it is built with hive-2.3.* but ebay managed hive service version is 1.2.1.
And there might be some incompatiblities between spark-2.3 and spark-3.
So, if your GenericUDF based UDF does not work with spark-3, please try to use `org.apache.hive.ql.exec.UDF`.

## ADLC Related Information

1. ADLC application address: https://cloud.ebay.com/object/detail/Application:ebayudf
2. Pipeline address: https://cloud.ebay.com/object/detail/Application:ebayudf/Pipeline
3. Hdfs folder for latest udf jars: /apps/b_adlc/b_hive_dba/ebayudf/1.0.0-SNAPSHOT/latest
4. While the developer pr to git, this action will auto trigger the build system to build application.

## Deploy and Upgrade UDF jar(for administer)

1. copy the udf jar from ADLC release folder to `/apps/udf/${ORG}/`, please check the detail directory structure in hdfs
2. UDF release preview - http://hmc.vip.ebay.com/swagger-ui/#/udf/previewUDFReleaseUsingPOST
```text
{
  "clusterName": "apollorno",
  "env": "QA",
  "org": "${ORG}"
}
```
3. UDF release - http://hmc.vip.ebay.com/swagger-ui/#/udf/submitUDFOperatorUsingPOST
```text
{
  "clusterName": "apollorno",
  "env": "QA",
  "org": "${ORG}"
}
```
