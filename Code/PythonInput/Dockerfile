FROM jfloff/alpine-python:2.7

RUN apk update
RUN apk add git
RUN git clone https://github.com/earl/beanstalkc.git
RUN pip install beanstalkc

COPY beanstalk_test.py /beanstalk_test.py 

CMD sh
