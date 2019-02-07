FROM openjdk:8-jre-alpine

ADD ./GroovyGradle.jar ./

CMD ["java",  "-jar", "./binToDec-all.jar"]