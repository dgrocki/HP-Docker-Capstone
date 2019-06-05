# HP-Docker-Capstone

Build with 
./gradlew build

Built file found in /build/libs
run the *-all* file with
java -jar <filename>



Start Docker Beanstalk with the following command
docker run -d -p 11300:11300 schickling/beanstalkd

Client Library for beanstalkd Java
https://github.com/jpeffer/JavaBeanstalkClient

Make sure the beanstalk connection in .groovy is connected to the same port the Docker container is running on

Start up Riak with 
docker-compose up -d

Make sure the beanstalkd container and our contaner our running on the same network (use the --net=host)
example run command:

docker run --net=host -v /home/grockidile/capstone/HP-Docker-Capstone-WorkerA/PDF:/mnt/ work-manager
