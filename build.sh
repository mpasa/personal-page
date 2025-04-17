#!/bin/bash
set -e

# Install dependencies
mise install

# Build depdendencies
yarn install
node_modules/.bin/webpack
mkdir -p src/main/resources/public/styles
./node_modules/sass/sass.js --style compressed src/main/resources/styles:src/main/resources/public/styles

# Build docker image
sbt clean docker:publishLocal

# Tag locally
docker save -o "target/docker/mpasa.tar" "mpasa:0.1.0"

# Load into microk8s and restart deployment
microk8s ctr image import target/docker/mpasa.tar --digests=true
microk8s kubectl -n mpasa set image deployment/mpasa mpasa=mpasa:0.1.0
microk8s kubectl -n mpasa rollout restart deployment/mpasa

echo "Done. Deployment is restarting..."
