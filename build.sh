#!/bin/bash
set -e

# Install dependencies
mise install

# Build depdendencies
bun install
node_modules/.bin/webpack
mkdir -p src/main/resources/public/styles
./node_modules/sass/sass.js --style compressed src/main/resources/styles:src/main/resources/public/styles

# Build docker image
sbt clean docker:publishLocal

# Tag locally and push image
TAG=$(date +"%Y%m%d-%H%M")
docker tag mpasa:0.1.0 mpasa:$TAG
docker tag mpasa:$TAG ghcr.io/mpasa/mpasa:$TAG
docker tag mpasa:$TAG ghcr.io/mpasa/mpasa:latest
docker push ghcr.io/mpasa/mpasa:$TAG
docker push ghcr.io/mpasa/mpasa:latest

# Push image and restart deployment
microk8s ctr image import target/docker/mpasa.tar --digests=true
microk8s kubectl -n mpasa set image deployment/mpasa mpasa=mpasa:0.1.0
microk8s kubectl -n mpasa rollout restart deployment/mpasa
microk8s kubectl -n mpasa set image deployment/mpasa mpasa=ghcr.io/mpasa/mpasa:$TAG

echo "Done. Deployment is restarting..."
