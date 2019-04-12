#!/bin/sh

#Instead of downloading each repo and building, this downloads the latest 
#docker images from dockerhub

#Order of operations:
#* start beanstalk
#* start riak with docker-compose (from work manager repo)
#* start work-manager
#* start Worker B
#* start Worker A

#beanstalk
if [ ! "$(docker ps -q -f name=beanstalk)" ]; then
    if [ "$(docker ps -aq -f status=exited -f name=beanstalk)" ]; then
        # cleanup
        docker rm beanstalk
    fi
    # run your container
    docker run -d -p 11300:11300 --name beanstalk  schickling/beanstalkd
fi

#Setup Riak
#Clone repo to get compose file
git clone https://github.com/dgrocki/HP-Docker-Capstone-WorkManager.git
docker-compose -f ./HP-Docker-Capstone-WorkManager/docker-compose.yml up -d 
#Repo will be downloaded and deleted each time script is ran
rm -r -f ./HP-Docker-Capstone-WorkManager

#For each docker container:
#* Check if the container is running
#* If it is, continue.
#* Else, check if it is still present from a previous run
#* If it is, remove it.
#* Run the container from dockerhub with the 'latest' tag

#Each container is named what it is to ease deleting

#Work manager
if [ ! "$(docker ps -q -f name=workmanager)" ]; then
	if [ "$(docker ps -aq -f status=exited -f name=workmanager)" ]; then
		# cleanup
		docker rm workmanager
	fi
	# run your container
	docker run --net=host --name workmanager -d -v /home/centos/MultiTest/files:/mnt iceberg00/hp-docker-capstone-workmanager:latest
fi

#Worker B
if [ ! "$(docker ps -q -f name=workerb)" ]; then
	if [ "$(docker ps -aq -f status=exited -f name=workerb)" ]; then
		# cleanup
		docker rm workerb
	fi
	# run your container
	docker run --net=host --name workerb -d -v /home/centos/MultiTest/files:/mnt iceberg00/hp-docker-capstone-workerb:latest
fi

#Worker A
if [ ! "$(docker ps -q -f name=workera)" ]; then
	if [ "$(docker ps -aq -f status=exited -f name=workera)" ]; then
		# cleanup
		docker rm workera
	fi
	# run your container
	docker run --net=host --name workera -d -v /home/centos/MultiTest/files:/mnt iceberg00/hp-docker-capstone-workera:latest
fi
#Everything should be running in the background
