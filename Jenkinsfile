pipeline {
    agent {
        docker {
            image 'maven:3-alpine' 
            args '-v /opt/java/m2:/root/.m2 -e "DATABASE_URL=postgres://postgres:postgres@192.168.33.11:5432/casa_das_quentinhas"' 
        }
    }
    stages {
        stage('Build') { 
            steps {
                sh 'mvn -B -DskipTests clean package' 
            }
        }	
    }
    
}
