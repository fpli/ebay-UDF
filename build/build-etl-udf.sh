#!/usr/bin/env bash

mvn clean package -Petl-udf-assembly -DskipTests -pl :udf-tags,:common-udf,:soj-udf,:etl-udf-assembly
