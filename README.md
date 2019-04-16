## Testing and Running Locally
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

---

## Testing and Running in Kubernetes
### Create `pdf_io` directory
First we need to create a directory to hold the pdf input and output of the system. 
Create this directory at `/home`:

`mkdir /home/pdf_io`

Next add read and write permissions to it with chmod:

`chmod 777 /home/pdf_io`

You don't need to use the 777 permission set, we just aren't taking any chances in these instructions.
Just make sure it has read and write privileges for your user (: ) 
Next, copy the `TestPDF.pdf` from this directory to the `/home/pdf_io` directory.


### Starting the deployments
Run the following commands to create the kubernetes pods. 
If this is the first time running these commands, it may take upwards of 5 minutes for them to finish. 

Manually: this should be done from the minikube directory
* `kubectl apply -f riak.yaml`
* `kubectl apply -f volume.yaml`
* `kubectl apply -f main.yaml`

The system should be ready once all the pods for these deployments are created. 
You can check when the deployment is ready

Note: Even after the riak pods are created (member and coordinator) it takes some time for them to estabilsh the proper connection so the system make take a minute to work properly even after it says thoes pods are running.

Now the system should be up and running. 
The workers pod contains our entire dockerized system that we wrote in groovy. 
Those containers are based off of images that we created and pushed to DockerHub. 
There should be 2 sets of workers pods showcasing our ability to create replicas. 
To submit a job, do the following:

### Exec into the python pod to start a job

There is one pod called python-input. 
This pod is responsible for providing a json string of input to beanstalk for the system to work on. 
In order to submit a job, we must first enter the python pod.

Run kubectl get pods to get all of the pods that we have created. There should be one pod called "python-input-########" Copy this entire pod name.

`kubectl get pods`

Next run the following command to exec into the container.

`kubectl exec -it nameofpodyoucopied -- /bin/bash`

You should now have a prompt in the pod. Type `ls` to verify that you can see a script named `beanstalk_test.py`.

Run `beanstalk_test.py` with python2.7

`python2.7 beanstalk_test.py`

A job has now been submitted. Given 20-30 seconds and there should be a pdf titled `Out_workerspodname.pdf` in your `/home/pdf_io` directory.
If there is not, check the troubleshooting section.


## Troubleshooting

If kubectl does not work, you will need to start the minikube vm. This is done with the command `minikube start`.

If no files are being created, it may be an issue with the volumes, check the following.
Verify that there is a directory `/home/pdf_io`
This directory must have both read and write permissons.
If it doesn't have the permissions, use `chmod` to add these permissions.
Inside this directory. There should be a PDF called `TestPDF.pdf`.
If there is not, copy a blank 9 page pdf into that directory and give it that name.
BlankPDF's can be found in the PDF directory in any of the WorkManager, WorkerA, WorkerB directories
If you don't have read/write permissions on the `TestPDF.pdf`, then that could be an issue, trying running chmod 777 on that file

If no Out.pdf is being created (followed by its pod name), but other files are being added or modified (such as Final.pdf) then there may be an issue with the riak connection. 
It is possible that because of the exception handeling that the workers pods did not properly connect to the Riak instance due to it not being fully up. 
To solve this, restart the workers deployment. 
This can be done by the following:

* `kubectl delete deploy workers`
* `kubectl apply -f main.yaml`

Once these pods are up and running then try inputing again

If everything seems to not be working, restart the all deployments:

`kubectl delete deploy --all`

Then follow manual instructions above.
