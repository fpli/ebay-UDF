FROM hub.tess.io/adihadoop/maven:3.8-jdk-8-slim AS builder

RUN /workspace/build/adlc/release.sh

FROM scratch
COPY --from=builder /workspace/*/target/*-jar-with-dependencies.jar ./
COPY --from=builder /workspace/*/*.yaml ./
COPY --from=builder /workspace/ebay-udf.yaml ./

LABEL com.ebay.adi.adlc.include="*.jar,*.yaml"
LABEL com.ebay.adi.adlc.tag="1.0.0-SNAPSHOT"
