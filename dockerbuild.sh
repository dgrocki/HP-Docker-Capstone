#!/usr/bin/env bash
./gradlew build
ls ./
ls ./build
ls ./build/libs/
cp -r "./build/libs/*" ./

docker build -t iceberg00/hp-docker-capstone:latest -t iceberg00/hp-docker-capstone:${BUILD_NUMBER} .
docker login -u iceberg00 -p DOCKER_CREDS_PSW
docker push iceberg00/hp-docker-capstone:${BUILD_NUMBER}
docker push iceberg00/hp-docker-capstone:latest