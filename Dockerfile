FROM openjdk:8-jre-alpine
ADD /build/libs/binToDec-all.jar ./
WORKDIR /
CMD ["java",  "-jar", "./binToDec-all.jar"]
