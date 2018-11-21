# First pull down a java8 alpine container
FROM anapsix/alpine-java
WORKDIR /
# Copy compiled groovy program into the container.
#ADD ./build/libs/GroovyGradle.jar GroovyGradle.jar
RUN cd ./home
#Download gradle
RUN wget https://services.gradle.org/distributions/gradle-4.10.2-bin.zip
RUN mkdir /opt/gradle
#unzip and add gradle to path
RUN unzip -d /opt/gradle gradle-4.10.2-bin.zip
ENV PATH="${PATH}:/opt/gradle/gradle-4.10.2/bin"

RUN apk add git make
RUN apk add --update bash libstdc++ curl zip && \
    rm -rf /var/cache/apk/*
# Workaround  https://issues.apache.org/jira/browse/GROOVY-7906 and other 'busybox' related issues.
RUN rm /bin/sh && ln -s /bin/bash /bin/sh

# Install groovy
# Use curl -L to follow redirects
# Also, use sed to make a workaround for https://issues.apache.org/jira/browse/GROOVY-7906
RUN curl -L https://bintray.com/artifact/download/groovy/maven/apache-groovy-binary-2.4.8.zip -o /tmp/groovy.zip && \
	cd /usr/local && \
	unzip /tmp/groovy.zip && \
	rm /tmp/groovy.zip && \
	ln -s /usr/local/groovy-2.4.8 groovy && \
	/usr/local/groovy/bin/groovy -v && \
	cd /usr/local/bin && \
	ln -s /usr/local/groovy/bin/groovy groovy

#clone repo
RUN git clone https://github.com/dgrocki/HP-Docker-Capstone.git
#Build the project
RUN ./HP-Docker-Capstone/GroovyGradle/gradlew

WORKDIR /HP-Docker-Capstone/GroovyGradle/src/main/groovy
RUN make
