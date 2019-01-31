node {
    def app

    stage('Clone repository locally') {
        /* Let's make sure we have the repository cloned to our workspace */

        checkout scm
    }
}

node('docker') {

    stage('Clone repository on slave') {
        /* Let's make sure we have the repository cloned to our workspace */

        checkout scm

    }

	 stage('Gradle Build') {
		sh './gradlew --stop'
		 sh './gradlew build --verbose --status'
		 sh 'pwd'
	 }

	 stage('Gradle Tests') {
		 sh './gradlew test'
	 }

    stage('Build image') {
        /* This builds the actual image; synonymous to
         * docker build on the command line */
	sh 'pwd'
        app = docker.build("iceberg00/hp-docker-capstone")
    }

    stage('Test image') {
        app.inside {
            sh 'echo "Tests passed"'
        }
    }

    stage('Push image') {
        /* Finally, we'll push the image with two tags:
         * First, the incremental build number from Jenkins
         * Second, the 'latest' tag.
         * Pushing multiple tags is cheap, as all the layers are reused. */
        docker.withRegistry('https://registry.hub.docker.com', 'docker-hub') {
            app.push("${env.BUILD_NUMBER}")
            app.push("latest")
        }
    }
}
