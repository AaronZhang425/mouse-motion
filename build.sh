#!/bin/bash

SCRIPT_DIR=$(dirname "$(readlink -f "$0")")
cd ${SCRIPT_DIR}

javac -d ./build/ ./src/*.java ./src/inputanalysis/*.java ./src/eventclassification/*.java ./src/eventclassification/eventcodes/*.java ./src/devicemanagement/*.java 
