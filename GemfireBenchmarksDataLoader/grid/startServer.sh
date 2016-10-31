#!/bin/bash

# Issue commands to gfsh to launch a server
echo "Starting cache server..."
/Users/nchandrappa/Documents/Gemfire/PRESENTATION/Pivotal_GemFire_820_b17919_Linux/bin/gfsh -e "start server --name=${SERVER_NAME} --server-port=0 --locators=${LOCATOR_IP}[10334] --classpath=/Users/nchandrappa/Documents/Gemfire/PRESENTATION/Gemfire_Workshop/Gemfire-WS/Domain/target/Domain-0.0.1-SNAPSHOT.jar:${CLASSPATH} --initial-heap=1g --max-heap=1g --cache-xml-file=config/serverCache.xml --J=-Dgemfire.start-dev-rest-api=true --J=-Dgemfire.http-service-port=7071 --J=-Dgemfire.http-service-bind-address=localhost"
/Users/nchandrappa/Documents/Gemfire/PRESENTATION/Pivotal_GemFire_820_b17919_Linux/bin/gfsh -e "start server --name=${SERVER_NAME_2} --server-port=0 --locators=${LOCATOR_IP}[10334] --classpath=/Users/nchandrappa/Documents/Gemfire/PRESENTATION/Gemfire_Workshop/Gemfire-WS/Domain/target/Domain-0.0.1-SNAPSHOT.jar:${CLASSPATH} --initial-heap=1g --max-heap=1g --cache-xml-file=config/serverCache.xml"
