# HP-Docker-Capstone

## Building and Testing
To build and test the entire system locally, we will use the scripts located in `./MultiTest`.

Once you are in the directory, first setup the work environment by running `./setup.sh`.
This setup your system for running docker containers by installing:

* Virtualbox (For minikube)
* Docker
* Docker-Compose
* kubectl
* minikube
* gradlew
* java

This will enable you to build the projects from source, run them locally, and run them in kubernetes.

Once the dependencies are installed, you can test the system locally by running `make` or `make setup` from within `./MultiTest`.
This will download and start the containers needed to run the system, including:

* Work Manager
* Worker A
* Worker B
* Riak
* Beanstalk

To perform a mock request, you can run `./MakeRequest.sh` or `make test`.
It will use the json present in `request.json`, where you can customize filenames of the input and output PDF's.

To clean up the docker containers and extraneous files after testing, you can use `make clean`.
