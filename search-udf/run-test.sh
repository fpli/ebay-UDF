#! /bin/sh

CP=/Users/zwu1/.m2/raptor2/com/ebay/searchscience/vnfnormalizer/1.0.4/vnfnormalizer-1.0.4.jar:/Users/zwu1/.m2/raptor2/org/apache/commons/commons-lang3/3.6/commons-lang3-3.6.jar:/Users/zwu1/.m2/raptor2/org/json/json/20180813/json-20180813.jar:/Users/zwu1/.m2/raptor2/commons-io/commons-io/2.6/commons-io-2.6.jar:/Users/zwu1/.m2/raptor2/com/google/guava/guava/20.0/guava-20.0.jar:/Users/zwu1/.m2/raptor2/commons-lang/commons-lang/2.5/commons-lang-2.5.jar:/Users/zwu1/.m2/raptor2/org/json4s/json4s-jackson_2.11/3.2.11/json4s-jackson_2.11-3.2.11.jar:/Users/zwu1/.m2/raptor2/org/json4s/json4s-core_2.11/3.2.11/json4s-core_2.11-3.2.11.jar:/Users/zwu1/.m2/raptor2/org/json4s/json4s-ast_2.11/3.2.11/json4s-ast_2.11-3.2.11.jar:/Users/zwu1/.m2/raptor2/org/scala-lang/scalap/2.11.0/scalap-2.11.0.jar:/Users/zwu1/.m2/raptor2/com/fasterxml/jackson/core/jackson-databind/2.3.1/jackson-databind-2.3.1.jar:/Users/zwu1/.m2/raptor2/com/fasterxml/jackson/core/jackson-annotations/2.3.0/jackson-annotations-2.3.0.jar:/Users/zwu1/.m2/raptor2/com/fasterxml/jackson/core/jackson-core/2.3.1/jackson-core-2.3.1.jar:/Users/zwu1/.m2/raptor2/org/scala-lang/scala-library/2.11.8/scala-library-2.11.8.jar:/Users/zwu1/.m2/raptor2/org/scala-lang/scala-reflect/2.11.8/scala-reflect-2.11.8.jar:/Users/zwu1/.m2/raptor2/com/ebay/tracking/tracking-schemas_2.11/1.0-SNAPSHOT/tracking-schemas_2.11-1.0-SNAPSHOT.jar:/Users/zwu1/.m2/raptor2/org/apache/avro/avro/1.8.2/avro-1.8.2.jar:/Users/zwu1/.m2/raptor2/org/codehaus/jackson/jackson-core-asl/1.9.13/jackson-core-asl-1.9.13.jar:/Users/zwu1/.m2/raptor2/org/codehaus/jackson/jackson-mapper-asl/1.9.13/jackson-mapper-asl-1.9.13.jar:/Users/zwu1/.m2/raptor2/com/thoughtworks/paranamer/paranamer/2.7/paranamer-2.7.jar:/Users/zwu1/.m2/raptor2/org/xerial/snappy/snappy-java/1.1.1.3/snappy-java-1.1.1.3.jar:/Users/zwu1/.m2/raptor2/org/tukaani/xz/1.5/xz-1.5.jar:/Users/zwu1/.m2/raptor2/com/ebay/scalaplatform/platform-spark_2.11/3.0-SNAPSHOT/platform-spark_2.11-3.0-SNAPSHOT.jar:/Users/zwu1/.m2/raptor2/com/ebay/scalaplatform/platform_2.11/3.0-SNAPSHOT/platform_2.11-3.0-SNAPSHOT.jar:/Users/zwu1/.m2/raptor2/com/ebay/v3jars/TrackingMetadata/1.30.1010-E1061_DEV_BASE/TrackingMetadata-1.30.1010-E1061_DEV_BASE.jar:/Users/zwu1/.m2/raptor2/com/ebay/v3jars/TrackingPropertiesCache/2.3.6.1001-E1059_DEV_BASE/TrackingPropertiesCache-2.3.6.1001-E1059_DEV_BASE.jar:/Users/zwu1/.m2/raptor2/com/ebay/v3jars/AppCache/2.3.0.1001-E1059_DEV_BASE/AppCache-2.3.0.1001-E1059_DEV_BASE.jar:/Users/zwu1/.m2/raptor2/com/ebay/v3jars/beaumont/beaumont-core/2.0.0.1001-E1059_DEV_BASE/beaumont-core-2.0.0.1001-E1059_DEV_BASE.jar:/Users/zwu1/.m2/raptor2/com/ebay/v3jars/beaumont/beaumont-ukernel/2.0.0.1001-E1059_DEV_BASE/beaumont-ukernel-2.0.0.1001-E1059_DEV_BASE.jar:/Users/zwu1/.m2/raptor2/com/ebay/v3jars/PMSVCGingerClient/2.2.1.1001-E1059_DEV_BASE/PMSVCGingerClient-2.2.1.1001-E1059_DEV_BASE.jar:/Users/zwu1/.m2/raptor2/com/github/nscala-time/nscala-time_2.11/2.16.0/nscala-time_2.11-2.16.0.jar:/Users/zwu1/.m2/raptor2/joda-time/joda-time/2.9.7/joda-time-2.9.7.jar:/Users/zwu1/.m2/raptor2/org/joda/joda-convert/1.2/joda-convert-1.2.jar:/Users/zwu1/.m2/raptor2/io/spray/spray-json_2.11/1.3.5/spray-json_2.11-1.3.5.jar:/Users/zwu1/.m2/raptor2/com/ebay/cassini/tokenizer/tokenizer-api/2.6.12-RELEASE/tokenizer-api-2.6.12-RELEASE.jar:/Users/zwu1/.m2/raptor2/com/ebay/cassini/maven/maven-configuration/1.0.3-RELEASE/maven-configuration-1.0.3-RELEASE.jar:/Users/zwu1/.m2/raptor2/com/ebay/scalaplatform/platform-macros_2.11/3.0-SNAPSHOT/platform-macros_2.11-3.0-SNAPSHOT.jar:/Users/zwu1/.m2/raptor2/org/scala-lang/modules/scala-xml_2.11/1.0.6/scala-xml_2.11-1.0.6.jar:/Users/zwu1/.m2/raptor2/org/scalactic/scalactic_2.11/3.0.5/scalactic_2.11-3.0.5.jar:/Users/zwu1/.m2/raptor2/org/apache/hive/hive-exec/1.2.1/hive-exec-1.2.1.jar:/Users/zwu1/.m2/raptor2/org/apache/hive/hive-ant/1.2.1/hive-ant-1.2.1.jar:/Users/zwu1/.m2/raptor2/org/apache/velocity/velocity/1.5/velocity-1.5.jar:/Users/zwu1/.m2/raptor2/commons-collections/commons-collections/3.1/commons-collections-3.1.jar:/Users/zwu1/.m2/raptor2/oro/oro/2.0.8/oro-2.0.8.jar:/Users/zwu1/.m2/raptor2/org/apache/hive/hive-metastore/1.2.1/hive-metastore-1.2.1.jar:/Users/zwu1/.m2/raptor2/org/apache/hive/hive-serde/1.2.1/hive-serde-1.2.1.jar:/Users/zwu1/.m2/raptor2/org/apache/hive/hive-common/1.2.1/hive-common-1.2.1.jar:/Users/zwu1/.m2/raptor2/net/sf/opencsv/opencsv/2.3/opencsv-2.3.jar:/Users/zwu1/.m2/raptor2/com/twitter/parquet-hadoop-bundle/1.6.0/parquet-hadoop-bundle-1.6.0.jar:/Users/zwu1/.m2/raptor2/com/jolbox/bonecp/0.8.0.RELEASE/bonecp-0.8.0.RELEASE.jar:/Users/zwu1/.m2/raptor2/commons-cli/commons-cli/1.2/commons-cli-1.2.jar:/Users/zwu1/.m2/raptor2/org/apache/derby/derby/10.10.2.0/derby-10.10.2.0.jar:/Users/zwu1/.m2/raptor2/org/datanucleus/datanucleus-api-jdo/3.2.6/datanucleus-api-jdo-3.2.6.jar:/Users/zwu1/.m2/raptor2/org/datanucleus/datanucleus-rdbms/3.2.9/datanucleus-rdbms-3.2.9.jar:/Users/zwu1/.m2/raptor2/commons-pool/commons-pool/1.5.4/commons-pool-1.5.4.jar:/Users/zwu1/.m2/raptor2/commons-dbcp/commons-dbcp/1.4/commons-dbcp-1.4.jar:/Users/zwu1/.m2/raptor2/javax/jdo/jdo-api/3.0.1/jdo-api-3.0.1.jar:/Users/zwu1/.m2/raptor2/javax/transaction/jta/1.1/jta-1.1.jar:/Users/zwu1/.m2/raptor2/org/apache/thrift/libthrift/0.9.2/libthrift-0.9.2.jar:/Users/zwu1/.m2/raptor2/org/apache/httpcomponents/httpclient/4.2.5/httpclient-4.2.5.jar:/Users/zwu1/.m2/raptor2/org/apache/httpcomponents/httpcore/4.2.4/httpcore-4.2.4.jar:/Users/zwu1/.m2/raptor2/org/apache/hive/hive-shims/1.2.1/hive-shims-1.2.1.jar:/Users/zwu1/.m2/raptor2/org/apache/hive/shims/hive-shims-common/1.2.1/hive-shims-common-1.2.1.jar:/Users/zwu1/.m2/raptor2/org/apache/hive/shims/hive-shims-0.20S/1.2.1/hive-shims-0.20S-1.2.1.jar:/Users/zwu1/.m2/raptor2/org/apache/hive/shims/hive-shims-0.23/1.2.1/hive-shims-0.23-1.2.1.jar:/Users/zwu1/.m2/raptor2/org/apache/hadoop/hadoop-yarn-server-resourcemanager/2.6.0/hadoop-yarn-server-resourcemanager-2.6.0.jar:/Users/zwu1/.m2/raptor2/org/apache/hadoop/hadoop-annotations/2.6.0/hadoop-annotations-2.6.0.jar:/Users/zwu1/.m2/raptor2/com/google/inject/extensions/guice-servlet/3.0/guice-servlet-3.0.jar:/Users/zwu1/.m2/raptor2/com/google/protobuf/protobuf-java/2.5.0/protobuf-java-2.5.0.jar:/Users/zwu1/.m2/raptor2/com/google/inject/guice/3.0/guice-3.0.jar:/Users/zwu1/.m2/raptor2/javax/inject/javax.inject/1/javax.inject-1.jar:/Users/zwu1/.m2/raptor2/aopalliance/aopalliance/1.0/aopalliance-1.0.jar:/Users/zwu1/.m2/raptor2/com/sun/jersey/jersey-json/1.9/jersey-json-1.9.jar:/Users/zwu1/.m2/raptor2/com/sun/xml/bind/jaxb-impl/2.2.3-1/jaxb-impl-2.2.3-1.jar:/Users/zwu1/.m2/raptor2/org/codehaus/jackson/jackson-jaxrs/1.8.3/jackson-jaxrs-1.8.3.jar:/Users/zwu1/.m2/raptor2/org/codehaus/jackson/jackson-xc/1.8.3/jackson-xc-1.8.3.jar:/Users/zwu1/.m2/raptor2/com/sun/jersey/contribs/jersey-guice/1.9/jersey-guice-1.9.jar:/Users/zwu1/.m2/raptor2/com/sun/jersey/jersey-server/1.9/jersey-server-1.9.jar:/Users/zwu1/.m2/raptor2/asm/asm/3.1/asm-3.1.jar:/Users/zwu1/.m2/raptor2/org/apache/hadoop/hadoop-yarn-common/2.6.0/hadoop-yarn-common-2.6.0.jar:/Users/zwu1/.m2/raptor2/org/apache/hadoop/hadoop-yarn-api/2.6.0/hadoop-yarn-api-2.6.0.jar:/Users/zwu1/.m2/raptor2/javax/xml/bind/jaxb-api/2.2.2/jaxb-api-2.2.2.jar:/Users/zwu1/.m2/raptor2/javax/xml/stream/stax-api/1.0-2/stax-api-1.0-2.jar:/Users/zwu1/.m2/raptor2/javax/activation/activation/1.1/activation-1.1.jar:/Users/zwu1/.m2/raptor2/org/codehaus/jettison/jettison/1.1/jettison-1.1.jar:/Users/zwu1/.m2/raptor2/com/sun/jersey/jersey-core/1.9/jersey-core-1.9.jar:/Users/zwu1/.m2/raptor2/com/sun/jersey/jersey-client/1.9/jersey-client-1.9.jar:/Users/zwu1/.m2/raptor2/org/mortbay/jetty/jetty-util/6.1.26/jetty-util-6.1.26.jar:/Users/zwu1/.m2/raptor2/org/apache/hadoop/hadoop-yarn-server-common/2.6.0/hadoop-yarn-server-common-2.6.0.jar:/Users/zwu1/.m2/raptor2/org/fusesource/leveldbjni/leveldbjni-all/1.8/leveldbjni-all-1.8.jar:/Users/zwu1/.m2/raptor2/org/apache/hadoop/hadoop-yarn-server-applicationhistoryservice/2.6.0/hadoop-yarn-server-applicationhistoryservice-2.6.0.jar:/Users/zwu1/.m2/raptor2/org/apache/hadoop/hadoop-yarn-server-web-proxy/2.6.0/hadoop-yarn-server-web-proxy-2.6.0.jar:/Users/zwu1/.m2/raptor2/org/mortbay/jetty/jetty/6.1.26/jetty-6.1.26.jar:/Users/zwu1/.m2/raptor2/org/apache/hive/shims/hive-shims-scheduler/1.2.1/hive-shims-scheduler-1.2.1.jar:/Users/zwu1/.m2/raptor2/commons-codec/commons-codec/1.4/commons-codec-1.4.jar:/Users/zwu1/.m2/raptor2/commons-httpclient/commons-httpclient/3.0.1/commons-httpclient-3.0.1.jar:/Users/zwu1/.m2/raptor2/commons-logging/commons-logging/1.1.3/commons-logging-1.1.3.jar:/Users/zwu1/.m2/raptor2/log4j/log4j/1.2.16/log4j-1.2.16.jar:/Users/zwu1/.m2/raptor2/log4j/apache-log4j-extras/1.2.17/apache-log4j-extras-1.2.17.jar:/Users/zwu1/.m2/raptor2/org/antlr/antlr-runtime/3.4/antlr-runtime-3.4.jar:/Users/zwu1/.m2/raptor2/org/antlr/stringtemplate/3.2.1/stringtemplate-3.2.1.jar:/Users/zwu1/.m2/raptor2/antlr/antlr/2.7.7/antlr-2.7.7.jar:/Users/zwu1/.m2/raptor2/org/antlr/ST4/4.0.4/ST4-4.0.4.jar:/Users/zwu1/.m2/raptor2/org/apache/ant/ant/1.9.1/ant-1.9.1.jar:/Users/zwu1/.m2/raptor2/org/apache/ant/ant-launcher/1.9.1/ant-launcher-1.9.1.jar:/Users/zwu1/.m2/raptor2/org/apache/commons/commons-compress/1.4.1/commons-compress-1.4.1.jar:/Users/zwu1/.m2/raptor2/org/apache/thrift/libfb303/0.9.2/libfb303-0.9.2.jar:/Users/zwu1/.m2/raptor2/org/apache/ivy/ivy/2.4.0/ivy-2.4.0.jar:/Users/zwu1/.m2/raptor2/org/apache/zookeeper/zookeeper/3.4.6/zookeeper-3.4.6.jar:/Users/zwu1/.m2/raptor2/io/netty/netty/3.7.0.Final/netty-3.7.0.Final.jar:/Users/zwu1/.m2/raptor2/org/apache/curator/curator-framework/2.6.0/curator-framework-2.6.0.jar:/Users/zwu1/.m2/raptor2/org/apache/curator/curator-client/2.6.0/curator-client-2.6.0.jar:/Users/zwu1/.m2/raptor2/org/apache/curator/apache-curator/2.6.0/apache-curator-2.6.0.pom:/Users/zwu1/.m2/raptor2/org/codehaus/groovy/groovy-all/2.1.6/groovy-all-2.1.6.jar:/Users/zwu1/.m2/raptor2/org/datanucleus/datanucleus-core/3.2.10/datanucleus-core-3.2.10.jar:/Users/zwu1/.m2/raptor2/org/apache/calcite/calcite-core/1.2.0-incubating/calcite-core-1.2.0-incubating.jar:/Users/zwu1/.m2/raptor2/org/apache/calcite/calcite-linq4j/1.2.0-incubating/calcite-linq4j-1.2.0-incubating.jar:/Users/zwu1/.m2/raptor2/com/google/code/findbugs/jsr305/1.3.9/jsr305-1.3.9.jar:/Users/zwu1/.m2/raptor2/net/hydromatic/eigenbase-properties/1.1.5/eigenbase-properties-1.1.5.jar:/Users/zwu1/.m2/raptor2/org/codehaus/janino/janino/2.7.6/janino-2.7.6.jar:/Users/zwu1/.m2/raptor2/org/codehaus/janino/commons-compiler/2.7.6/commons-compiler-2.7.6.jar:/Users/zwu1/.m2/raptor2/org/pentaho/pentaho-aggdesigner-algorithm/5.1.5-jhyde/pentaho-aggdesigner-algorithm-5.1.5-jhyde.jar:/Users/zwu1/.m2/raptor2/org/apache/calcite/calcite-avatica/1.2.0-incubating/calcite-avatica-1.2.0-incubating.jar:/Users/zwu1/.m2/raptor2/stax/stax-api/1.0.1/stax-api-1.0.1.jar:/Users/zwu1/.m2/raptor2/jline/jline/2.12/jline-2.12.jar:/Users/zwu1/.m2/raptor2/org/slf4j/slf4j-api/1.7.5/slf4j-api-1.7.5.jar:/Users/zwu1/.m2/raptor2/org/slf4j/slf4j-log4j12/1.7.5/slf4j-log4j12-1.7.5.jar:/Users/zwu1/.m2/raptor2/junit/junit/4.12/junit-4.12.jar:/Users/zwu1/.m2/raptor2/org/hamcrest/hamcrest-core/1.3/hamcrest-core-1.3.jar

java -cp /home/zwu1/src/ebay-UDF/search-udf/target/classes:./target/search-qu-udf-1.1-SNAPSHOT-jar-with-dependencies.jar:${CP} $*