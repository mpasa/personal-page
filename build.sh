#!/bin/bash
set -e

mkdir -p src/main/resources/public/styles
stylus src/main/resources/styles --out src/main/resources/public/styles -u nib -c -r --compress
sbt clean universal:packageBin

echo "Done, you can copy to production: ./target/universal/mpasa-0.1.0.zip"
