FROM openjdk:8-jre-alpine
WORKDIR /

ADD ./build/libs/binToDec-all.jar ./
CMD ["java",  "-jar", "./binToDec-all.jar"]
