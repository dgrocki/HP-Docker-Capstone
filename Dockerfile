FROM openjdk:8-jre-alpine

CMD ["git", "clone", "https://github.com/dgrocki/HP-Docker-Capstone.git"]
CMD ["cd", "./HP-Docker-Capstone"]
CMD ["git", "checkout", "Jenkins-file"]
CMD ["./gradlew", "build"]


ADD ./build/libs/binToDec-all.jar ./
WORKDIR /
CMD ["java",  "-jar", "./binToDec-all.jar"]
