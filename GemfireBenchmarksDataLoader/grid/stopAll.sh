#!/bin/bash

# Issue commands to gfsh to start locator and launch a server
echo "Stopping server and locator..."
/Users/nchandrappa/Documents/Gemfire/PRESENTATION/Pivotal_GemFire_820_b17919_Linux/bin/gfsh -e "connect --locator=${LOCATOR_IP}[10334]" -e "shutdown --include-locators=true"
