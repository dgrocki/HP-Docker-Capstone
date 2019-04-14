#!/bin/sh

echo -e "Input file:\t./files/TestPDF.pdf"
echo -e "output file:\t./files/FinalPDF.pdf\n"

echo -e "Order of operations:"
echo -e "* Start Beanstalk (Work Queue Manager)"
echo -e "* Start Riak with docker-compose (Distributed file storage system)"
echo -e "* Start Work-Manager (Distributes work to Worker As)"
echo -e "* Start Worker-B (Organizes multiple pages into one page)"
echo -e "* Start Worker-A (Places content in blank PDF)\n"

echo -e "A file named container_ids.tmp will be created that stores the started container ids.  Use it to kill and rm the containers once complete."

#Copy over new input file and remove the output file, if it exists
rm -f ./files/*.pdf
cp ./TestPDF.pdf ./files/TestPDF.pdf


echo -e "Starting....\n"
#beanstalk
if [ ! "$(docker ps -q -f name=beanstalk)" ]; then
    if [ "$(docker ps -aq -f status=exited -f name=beanstalk)" ]; then
        # cleanup
        docker rm beanstalk
    fi
    # run your container
    docker run -d -p 11300:11300 --name beanstalk  schickling/beanstalkd >> ./container_ids.tmp
	echo -e "Beanstalk has been started on port 11300"
fi

#Setup Riak
if test -d ./HP-Docker-Capstone-WorkManager
then
	#If directory exists, update and run docker compose
	git pull
	docker-compose -f ./HP-Docker-Capstone-WorkManager/docker-compose.yml up -d 
else
	#Clone repo to get compose file
	git clone https://github.com/dgrocki/HP-Docker-Capstone-WorkManager.git
	docker-compose -f ./HP-Docker-Capstone-WorkManager/docker-compose.yml up -d 
fi
#Store containers id's that were started by docker-compose
docker-compose -f ./HP-Docker-Capstone-WorkManager/docker-compose.yml ps -q >> ./container_ids.tmp


echo -e "Riak has been built and started"

#Work manager
#if [ ! "$(docker ps -q -f name=workmanager)" ]; then
#	if [ "$(docker ps -aq -f status=exited -f name=workmanager)" ]; then
#		# cleanup
#		docker rm workmanager
	#fi
	# run your container
	docker run --net=host --name workmanager -d -v $PWD/files:/mnt iceberg00/hp-docker-capstone-workmanager:19 >> ./container_ids.tmp
#fi

echo -e "Work Manager has been started"

#Worker B
if [ ! "$(docker ps -q -f name=workerb)" ]; then
	if [ "$(docker ps -aq -f status=exited -f name=workerb)" ]; then
		# cleanup
		docker rm workerb
	fi
	# run your container
	docker run --net=host --name workerb -d -v $PWD/files:/mnt iceberg00/hp-docker-capstone-workerb:latest >> ./container_ids.tmp
fi

echo -e "Worker B has been started"

#Worker A
if [ ! "$(docker ps -q -f name=workera)" ]; then
	if [ "$(docker ps -aq -f status=exited -f name=workera)" ]; then
		# cleanup
		docker rm workera
	fi
	# run your container
	docker run --net=host --name workera -d -v $PWD/files:/mnt iceberg00/hp-docker-capstone-workera:25 >> ./container_ids.tmp
fi

echo -e "Worker A has been started"
#
#sleep for 5 seconds to let things get started
#sleep 5
#Sends a POST request using the json in request.json
#curl --header "Content-Type: application/json" --request POST --data-binary "@./request.json" http://localhost:8080/workManager/submitWork
#echo -e "Work request has been submitted"
#echo -e "Check ./files for the completed PDF\n"

echo -e "\nTo make a request, run \"make test\" or \"./MakeRequest.sh\"\n"

echo -e "Containers can be stopped with \"make clean\" or:"
echo -e "  docker kill \$(cat ./container_ids.tmp)"
echo -e "  docker rm \$(cat ./container_ids.tmp)"


