#!/bin/bash
set -e

yarn install
node_modules/.bin/webpack
mkdir -p src/main/resources/public/styles
sass --style compressed src/main/resources/styles:src/main/resources/public/styles
sbt clean universal:packageBin

echo "Done, you can copy to production: ./target/universal/mpasa-0.1.0.zip"
