#!/usr/bin/env bash

# Usage:
# To generate dependency list:      ./build/etl-udf-dependency.sh --replace
# To check the dependency change:   ./build/etl-udf-dependency.sh

set -o pipefail
set -e
set -x

export LC_ALL=C

PWD=$(cd "$(dirname "$0")"/.. || exit; pwd)

MVN="mvn"


DEP_PR="${PWD}"/build/etl-udf-dependencyList.tmp
DEP="${PWD}"/build/etl-udf-dependencyList
PROFILES="-Petl-udf-assembly -pl :udf-tags,:common-udf,soj-udf,:etl-udf-assembly"


function build_classpath() {
  $MVN dependency:build-classpath $PROFILES |\
    grep -v "INFO" | tail -1 |\
    tr ":" "\n" | \
    awk -F '/' '{
      artifact_id=$(NF-2);
      version=$(NF-1);
      jar_name=$NF;
      classifier_start_index=length(artifact_id"-"version"-") + 1;
      classifier_end_index=index(jar_name, ".jar") - 1;
      classifier=substr(jar_name, classifier_start_index, classifier_end_index - classifier_start_index + 1);
      print artifact_id"/"version"/"classifier"/"jar_name
    }' | sort >> "${DEP_PR}"
}

function check_diff() {
    set +e
    the_diff=$(diff ${DEP} ${DEP_PR})
    set -e
    rm -rf "${DEP_PR}"
    if [[ -n $the_diff ]]; then
        echo "Dependency List Changed Detected: "
        echo ${the_diff}
        echo "To update the dependency file, run './build/etl-udf-dependency.sh --replace'."
        exit 1
    fi
}

rm -rf "${DEP_PR}"

build_classpath

if [[ "$1" == "--replace" ]]; then
    rm -rf "${DEP}"
    mv "${DEP_PR}" "${DEP}"
    exit 0
fi

check_diff
