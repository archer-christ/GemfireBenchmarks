#!/bin/bash

# Issue commands to gfsh to start locator
echo "Starting locator..."
/Users/nchandrappa/Documents/Gemfire/PRESENTATION/Pivotal_GemFire_820_b17919_Linux/bin/gfsh -e "start locator --name=${LOCATOR_NAME} --port=10334 --properties-file=config/locator.properties --initial-heap=100m --max-heap=100m --enable-cluster-configuration=true"