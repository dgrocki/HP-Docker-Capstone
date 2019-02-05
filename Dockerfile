FROM openjdk:8-jre-alpine

RUN ls
ADD ./binToDec-all.jar ./
RUN ls

CMD ["java",  "-jar", "./binToDec-all.jar"]