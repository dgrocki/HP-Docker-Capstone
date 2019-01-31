FROM openjdk:8-jre-alpine
WORKDIR /

ADD /../../../../jenkins_home/workspace/Jenkins Branch test/build/libs/binToDec-all.jar ./
CMD ["java",  "-jar", "./binToDec-all.jar"]
