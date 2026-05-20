#!/bin/bash

SCRIPT_DIR=$(dirname "$(readlink -f "$0")")
cd ${SCRIPT_DIR}

javac -Xlint -d ./build/ $(find . -name "*.java")
