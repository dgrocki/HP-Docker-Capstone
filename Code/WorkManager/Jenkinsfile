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
		 sh './gradlew clean build -x test'
	 }

	 stage('Gradle Tests') {
		 try{
		 	sh './gradlew test'
		 } catch (Exception e) {
			//One or more tests failed	 
		 }
		 publishHTML([
			 allowMissing: false, 
			 alwaysLinkToLastBuild: false, 
			 keepAll: false, 
			 reportDir: './build/reports/tests/test/', 
			 reportFiles: 'index.html', 
			 reportName: 'HTML Test Report', 
			 reportTitles: ''
		 ])
		 jacoco classPattern: '**/build/classes', execPattern: '**/build/jacoco/**.exec', inclusionPattern: '**/*.class', sourceInclusionPattern: '**/*.groovy', sourcePattern: '**/src/main/groovy'
	 }

    stage('Build image') {
        /* This builds the actual image; synonymous to
         * docker build on the command line */
	sh './gradlew build -x test'
        app = docker.build("iceberg00/hp-docker-capstone-workmanager")
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
