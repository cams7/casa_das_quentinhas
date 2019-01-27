pipeline {
    agent {
        docker {
            image 'maven:3-alpine' 
            args ' --net=shared_nw190126 --ip=192.168.33.15 -e "DATABASE_URL=postgres://postgres:postgres@192.168.33.11:5432/casa_das_quentinhas" -v /opt/java/m2:/root/.m2 -v /opt/webdrivers:/opt/webdrivers' 
        }
    }
    stages {
        stage('Build') { 
            steps {
                sh 'mvn -B -DskipTests clean package' 
            }
	    post {
                always {
                    sh 'nohup java -jar casa_das_quentinhas-web/target/dependency/jetty-runner.jar --host 0.0.0.0 --port 8080 casa_das_quentinhas-web/target/*.war &'
		    sh 'sleep 10m'
                }
            }
        }
	/*stage('Test') {
            steps {
                sh 'mvn test -Phomologacao'
            }
        }*/	
    }
    
}
