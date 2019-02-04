#!/usr/bin/env bash
./gradlew build .
docker build -t iceberg00/hp-docker-capstone:latest -t iceberg00/hp-docker-capstone:${BUILD_NUMBER} .
docker push iceberg00/hp-docker-capstone:${BUILD_NUMBER}
docker push iceberg00/hp-docker-capstone:latest
