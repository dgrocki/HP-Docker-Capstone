#!/usr/bin/env bash
cp ${WORKSPACE}/build/libs/binToDec-all.jar ./

docker build -t iceberg00/hp-docker-capstone:latest -t iceberg00/hp-docker-capstone:${BUILD_NUMBER} .
docker login -u DOCKER_CREDS_USR -p DOCKER_CREDS_PSW
docker push iceberg00/hp-docker-capstone:${BUILD_NUMBER}
docker push iceberg00/hp-docker-capstone:latest

rm -r ./dockerFiles
