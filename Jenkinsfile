node('docker') {
	environment {
		DOCKER_CREDS = credentials('docker-hub')
	}
	stage('Clone repository on slave') {
		/* Let's make sure we have the repository cloned to our workspace */
	checkout scm
	}

	stage('Gradle Build') {
	sh './gradlew --stop'
	sh './gradlew build --verbose --status'
	}

	stage('Gradle Tests') {
	sh './gradlew test'
	}	

	stage('Build image') {
		/* This builds the actual image; synonymous to
		* docker build on the command line */
	sh (
		script: 'ls -al',
		returnStdout: true
	)
	sh './build/libs/*.jar ./'
	sh './dockerbuild.sh'
	}

	stage('Test image') {

	}
}
