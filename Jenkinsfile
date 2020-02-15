pipeline {
    agent {
        docker { image '172.42.42.200:18082/node:lts-alpine3.9' }
    }
	
	environment {
        GIT_CREDENTIALS_ID = 'github-credentials'
		GIT_URL = 'https://github.com/cams7/casa_das_quentinhas.git'
		GIT_USER_EMAIL = 'ceanma@gmail.com'
		GIT_USER_NAME = 'César A. Magalhães'
		
		ROOT_PATH               = "${pwd()}"
		APP_BASE_PATH           = "${ROOT_PATH}/app-base"
		APP_ENTITY_PATH         = "${ROOT_PATH}/casa_das_quentinhas-entity"
		APP_PATH                = "${ROOT_PATH}/casa_das_quentinhas-web"
        MAVEN_TARGET_PATH       = "${APP_PATH}/target"
		MAVEN_SETTINGS_PATH     = "${ROOT_PATH}/settings.xml"

        def pom                 = readMavenPom(file: "${ROOT_PATH}/pom.xml")
        ARTIFACT_ID             = pom.getArtifactId()
        RELEASE_VERSION         = pom.getVersion().replace("-SNAPSHOT", "")
				
		NEXUS_CREDENTIALS_ID = 'nexus-credentials'
		GITHUB_PACKAGES_CREDENTIALS_ID = 'github-packages-credentials'
    }
	
    tools {
        maven 'apache-maven'
    }
	
    triggers {
        pollSCM "H/30 * * * *"
    }
    
    options {
        timestamps()
    }

    parameters {
		choice (
			choices: ['test', 'prod'],
            name: 'MAVEN_PROFILE', 
			description: 'Maven profile'
		)
		
        booleanParam (
			name: "RELEASE", 
			description: "Build a release from current commit.", 
			defaultValue: false
		)
    }
	
	stages {	
		stage('Build and SonarQube Analysis') {			
            steps {			
				withSonarQubeEnv('sonarqube-service') {
					sh "mvn -U -s ${MAVEN_SETTINGS_PATH} -P${params.MAVEN_PROFILE} -DskipTests clean install sonar:sonar"
				}
            }
        }
		
		/*stage('Quality Gate Status Check') {
			steps {	
				timeout(time: 1, unit: 'HOURS') {
					def qg = waitForQualityGate()
					if (qg.status != 'OK') {             
						error "Pipeline aborted due to quality gate failure: ${qg.status}"
					}
				}
			}
		}*/  
	}
    
}
