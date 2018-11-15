# First pull down a java8 alpine container
FROM anapsix/alpine-java
WORKDIR /
# Copy compiled groovy program into the container.
ADD ./build/libs/GroovyGradle.jar GroovyGradle.jar
#Open a port for communication
EXPOSE 8080
#Start the program
CMD java - jar GroovyGradle.jar
