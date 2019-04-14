CONTIDS:=$(shell cat ./container_ids.tmp)

setup:
	./RunMe.sh

test : 
	./MakeRequest.sh

clean :
	docker kill $(CONTIDS)
	docker rm $(CONTIDS)
	rm -f ./container_ids.tmp
	rm -f ./files/*.pdf
	rm -f -r ./HP-Docker-Capstone-WorkManager/
