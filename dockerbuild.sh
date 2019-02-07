#!/usr/bin/env bash
./gradlew build
ls ./
echo "SPACE\n"
ls ./build
echo "SPACE\n"
ls ./build/libs/
echo "SPACE\n"
cp "./build/libs/GroovyGradle.jar" ./

docker build -t iceberg00/hp-docker-capstone:latest -t iceberg00/hp-docker-capstone:${BUILD_NUMBER} .
docker login -u iceberg00 -p DOCKER_CREDS_PSW
docker push iceberg00/hp-docker-capstone:${BUILD_NUMBER}
docker push iceberg00/hp-docker-capstone:latest