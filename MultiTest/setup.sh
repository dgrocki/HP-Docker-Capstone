#!/bin/sh

#Order of operations:
#* install docker
#* install docker-compose
#* install virtualbox
#* install gradlew
#* install java
#* install kubectl
#* install minikube
#* reboot

#* Install and Enable docker:
sudo yum install -y yum-utils device-mapper-persistent-data lvm2
sudo yum-config-manager --add-repo https://download.docker.com/linux/centos/docker-ce.repo
sudo yum install -y docker-ce docker-ce-cli containerd.io
sudo systemctl enable docker

#* Install docker-compose:
sudo curl -L "https://github.com/docker/compose/releases/download/1.24.0/docker-compose-$(uname -s)-$(uname -m)" -o /usr/local/bin/docker-compose
sudo chmod +x /usr/local/bin/docker-compose


