#!/bin/sh

#Dependencies: 
#* wget
#* git

#Order of operations:
#* install docker
#* install docker-compose
#* install virtualbox
#* install kubectl
#* install minikube
#* install gradlew
#* install java
#* reboot

#* Install and Enable docker:
sudo yum install -y yum-utils device-mapper-persistent-data lvm2
sudo yum-config-manager --add-repo https://download.docker.com/linux/centos/docker-ce.repo
sudo yum install -y docker-ce docker-ce-cli containerd.io
sudo systemctl enable --now docker

#* Install docker-compose:
sudo curl -L "https://github.com/docker/compose/releases/download/1.24.0/docker-compose-$(uname -s)-$(uname -m)" -o /usr/local/bin/docker-compose
sudo chmod +x /usr/local/bin/docker-compose

#* Install virtualbox
cd /etc/yum.repos.d/
sudo wget http://download.virtualbox.org/virtualbox/rpm/rhel/virtualbox.repo
sudo yum update
sudo rpm -Uvh https://dl.fedoraproject.org/pub/epel/epel-release-latest-7.noarch.rpm
sudo yum install -y binutils gcc make patch libgomp glibc-headers glibc-devel kernel-headers kernel-devel dkms
sudo yum install -y VirtualBox-6.0
sudo service vboxdrv set
sudo usermod -a -G vboxusers user_name




