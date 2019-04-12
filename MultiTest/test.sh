#!/bin/sh

#Order of operations:
#* start beanstalk
#* start riak with docker-compose (from work manager repo)
#* start work-manager

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
if [ -d ./HP-Docker-Capstone-WorkManager]; then
	#If directory exists, update and run docker compose
	git pull
	docker-compose -f ./HP-Docker-Capstone-WorkManager/docker-compose.yml up -d 
else
	#Clone repo to get compose file
	git clone https://github.com/dgrocki/HP-Docker-Capstone-WorkManager.git
	docker-compose -f ./HP-Docker-Capstone-WorkManager/docker-compose.yml up -d 
fi

#Work manager
#if [ ! "$(docker ps -q -f name=workmanager)" ]; then
#	if [ "$(docker ps -aq -f status=exited -f name=workmanager)" ]; then
#		# cleanup
#		docker rm workmanager
	#fi
	# run your container
	docker run --net=host --name workmanager -d -v $PWD/files:/mnt iceberg00/hp-docker-capstone-workmanager:latest
#fi

#Worker B
if [ ! "$(docker ps -q -f name=workerb)" ]; then
	if [ "$(docker ps -aq -f status=exited -f name=workerb)" ]; then
		# cleanup
		docker rm workerb
	fi
	# run your container
	docker run --net=host --name workerb -d -v $PWD/files:/mnt iceberg00/hp-docker-capstone-workerb:latest
fi

#Worker A
if [ ! "$(docker ps -q -f name=workera)" ]; then
	if [ "$(docker ps -aq -f status=exited -f name=workera)" ]; then
		# cleanup
		docker rm workera
	fi
	# run your container
	docker run --net=host --name workera -d -v $PWD/files:/mnt iceberg00/hp-docker-capstone-workera:25
fi

#Sends a POST request using the json in request.json
curl --header "Content-Type: application/json" --request POST --data-binary "@./request.json" http://localhost:8080/workManager/submitWork




