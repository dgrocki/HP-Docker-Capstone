#!/bin/sh

echo -n "Input file:\t./files/TestPDF.pdf"
echo -n "output file:\t./files/FinalPDF.pdf\n"

echo -n "Order of operations:"
echo -n "* Start Beanstalk (Work Queue Manager)"
echo -n "* Start Riak with docker-compose (Distributed file storage system)"
echo -n "* Start Work-Manager (Distributes work to Worker As)"
echo -n "* Start Worker-B (Organizes multiple pages into one page)"
echo -n "* Start Worker-A (Places content in blank PDF)"

echo -n "A file named container_ids.tmp will be created that stores the started container ids.  Use it to kill and rm the containers once complete."

#First remove file from previous run recording container IDs
rm -f ./container_ids.tmp

#Copy over new input file and remove the output file, if it exists
rm -f ./files/FinalPDF.pdf
cp ./files/BlankPDF.pdf ./files/TestPDF.pdf


echo -n "Starting...."
#beanstalk
if [ ! "$(docker ps -q -f name=beanstalk)" ]; then
    if [ "$(docker ps -aq -f status=exited -f name=beanstalk)" ]; then
        # cleanup
        docker rm beanstalk
    fi
    # run your container
    docker run -d -p 11300:11300 --name beanstalk  schickling/beanstalkd >> ./container_ids.tmp
	echo -n "Beanstalk has been started on port 11300"
fi

#Setup Riak
if [ -d "./HP-Docker-Capstone-WorkManager"]; then
	#If directory exists, update and run docker compose
	git pull
	docker-compose -f ./HP-Docker-Capstone-WorkManager/docker-compose.yml up -d 
else
	#Clone repo to get compose file
	git clone https://github.com/dgrocki/HP-Docker-Capstone-WorkManager.git
	docker-compose -f ./HP-Docker-Capstone-WorkManager/docker-compose.yml up -d 
fi
echo -n "Riak has been built and started"

#Work manager
#if [ ! "$(docker ps -q -f name=workmanager)" ]; then
#	if [ "$(docker ps -aq -f status=exited -f name=workmanager)" ]; then
#		# cleanup
#		docker rm workmanager
	#fi
	# run your container
	docker run --net=host --name workmanager -d -v $PWD/files:/mnt iceberg00/hp-docker-capstone-workmanager:latest >> ./container_ids.tmp
#fi

echo -n "Work Manager has been started"

#Worker B
if [ ! "$(docker ps -q -f name=workerb)" ]; then
	if [ "$(docker ps -aq -f status=exited -f name=workerb)" ]; then
		# cleanup
		docker rm workerb
	fi
	# run your container
	docker run --net=host --name workerb -d -v $PWD/files:/mnt iceberg00/hp-docker-capstone-workerb:latest >> ./container_ids.tmp
fi

echo -n "Worker B has been started"

#Worker A
if [ ! "$(docker ps -q -f name=workera)" ]; then
	if [ "$(docker ps -aq -f status=exited -f name=workera)" ]; then
		# cleanup
		docker rm workera
	fi
	# run your container
	docker run --net=host --name workera -d -v $PWD/files:/mnt iceberg00/hp-docker-capstone-workera:latest >> ./container_ids.tmp
fi

echo -n "Worker A has been started"

#Sends a POST request using the json in request.json
curl --header "Content-Type: application/json" --request POST --data-binary "@./request.json" http://localhost:8080/workManager/submitWork
echo -n "Work request has been submitted"
echo -n "Check ./files for the completed PDF"




