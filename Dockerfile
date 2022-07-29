FROM hub.tess.io/adihadoop/maven:3.8-jdk-8-slim AS builder
RUN mvn clean package -f /workspace -DskipTests -U

# exclude search-udf module because of CVE issue
RUN rm -rf /workspace/*/target/search-qu-udf-*-jar-with-dependencies.jar

FROM scratch
COPY --from=builder /workspace/*/target/*-jar-with-dependencies.jar ./

LABEL com.ebay.adi.adlc.include="*.jar"
LABEL com.ebay.adi.adlc.tag="1.0.0-SNAPSHOT"
