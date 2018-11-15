
build:
	docker build -t javatest:latest .
run:
	docker run javatest
term:
	docker run -it javatest sh
