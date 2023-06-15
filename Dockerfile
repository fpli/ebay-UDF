FROM hub.tess.io/adihadoop/maven:3.8-jdk-8-slim AS builder
RUN mvn -s /workspace/build/maven-settings.xml clean package -f /workspace -DskipTests -U \
    # remove search-qu-udf because of building issue
    -pl bx-udf,common-udf,core-udf,dapgap-udf,datelib-udf,deploy,dss-shpmt-udf,ebaylib-udf,ecg-udf,ep-udf,hadoop-udf,nudata-udf,risk-udf,soj-udf,sys-udf,tags,tokenizer-udf

FROM scratch
COPY --from=builder /workspace/*/target/*-jar-with-dependencies.jar ./
COPY --from=builder /workspace/*/*.yaml ./

LABEL com.ebay.adi.adlc.include="*.jar,*.yaml"
LABEL com.ebay.adi.adlc.tag="1.0.0-SNAPSHOT"
