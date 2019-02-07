FROM openjdk:8-jre-alpine

ADD ./binToDec-all.jar ./

CMD ["java",  "-jar", "./binToDec-all.jar"]