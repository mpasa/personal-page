#!/usr/bin/env bash

# Use this script to watch for stylus changes and transpile them to CSS
mkdir -p src/main/resources/public/styles
stylus src/main/resources/styles --out src/main/resources/public/styles -u nib -c -r --watch
