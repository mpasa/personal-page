#!/bin/bash
set -e

yarn install
node_modules/.bin/webpack
mkdir -p src/main/resources/public/styles
sass --style compressed src/main/resources/styles:src/main/resources/public/styles
sbt clean docker:publishLocal
docker save -o "target/docker/mpasa.tar" "mpasa:0.1.0"

echo "Done, you can copy and load docker image to production: ./target/docker/mpasa.tar using docker load -i mpasa.tar" 
