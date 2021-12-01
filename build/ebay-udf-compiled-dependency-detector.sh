#!/usr/bin/env bash

# Usage:
# To detect dependencies of a compiled UDF: ./build/ebay-udf-compiled-dependency-detector.sh

export LC_ALL=C

set -o pipefail
set -e
set -x

PWD=$(cd "$(dirname "$0")"/.. || exit; pwd)
MVN="mvn"
SUBMOD_PATH_TMP_DATA="${PWD}"/build/all-submod-path.tmp

# get all submodule path
function get_all_submod_path() {
  rm -rf "${SUBMOD_PATH_TMP_DATA}"
  $MVN -q exec:exec -Dexec.executable="pwd" | sort >> "${SUBMOD_PATH_TMP_DATA}"
  # remove the first line because it is the path of the current directory
  text=$(sed '1d' "${SUBMOD_PATH_TMP_DATA}")
  > "${SUBMOD_PATH_TMP_DATA}"
  echo "$text" >> "${SUBMOD_PATH_TMP_DATA}"
}

get_all_submod_path

detected_compiled_mods=""

while IFS= read -r line
do
  cd "$line"

  set +e
  detected_mod=$($MVN dependency:tree |\
    grep 'org.apache.hive\|org.apache.hadoop\|org.apache.spark' |\
    grep compile)

  if [[ -n $detected_mod ]]; then
    detected_compiled_mods="${detected_compiled_mods}\n$line\n$detected_mod"
  fi
done < "$SUBMOD_PATH_TMP_DATA"

rm -rf "${SUBMOD_PATH_TMP_DATA}"

if [[ -n $detected_compiled_mods ]]; then
  echo "Detect compiled dependency submods:"
  echo "${detected_compiled_mods}\n"
  exit 1
fi
