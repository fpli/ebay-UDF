#!/usr/bin/env bash

WORKSPACE="$(cd "`dirname "$0"`/../.."; pwd)"

mvn -s $WORKSPACE/build/adlc/maven-settings.xml clean package -f $WORKSPACE -DskipTests -U
