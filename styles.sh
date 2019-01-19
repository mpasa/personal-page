#!/usr/bin/env bash

# Use this script to watch for SASS changes and transpile them to CSS
mkdir -p src/main/resources/public/styles
sass --watch src/main/resources/styles:src/main/resources/public/styles