pipeline {
    agent any
	
	environment {
        GIT_CREDENTIALS_ID = 'github-credentials'
		REPOSITORY_URL = 'https://github.com/cams7/casa_das_quentinhas'
		GIT_URL = "${REPOSITORY_URL}.git"
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
        RELEASE_VERSION         = pom.getVersion().replace('-SNAPSHOT', '')
				
		NEXUS_CREDENTIALS_ID = 'nexus-credentials'
		GITHUB_PACKAGES_CREDENTIALS_ID = 'github-packages-credentials'
		
		APP_URL                 = 'http://192.168.100.8:28080/casa-das-quentinhas'
		WEBDRIVER_URL           = 'http://192.168.100.13:4444/wd/hub'
		
		JDBC_DATABASE_URL       = 'jdbc:postgresql://172.42.42.200:15432/casa_das_quentinhas'
		JDBC_DATABASE_USERNAME  = 'dono_da_cozinha'
		JDBC_DATABASE_PASSWORD  = 'abc12345'
		
		MAVEN_CENTRAL           = 'http://172.42.42.200:18081/repository/maven-group/'
		NEXUS_SNAPSHOTS         = 'http://172.42.42.200:18081/repository/maven-snapshots/'
		NEXUS_RELEASES          = 'http://172.42.42.200:18081/repository/maven-releases/'
		GITHUB_PKG              = 'https://maven.pkg.github.com/cams7/casa_das_quentinhas'
		
		TOMCAT_USER             = 'root'
		TOMCAT_IMAGE            = 'cams7/tomcat'
		TOMCAT_WEBAPPS          = '/usr/local/tomcat/webapps/'
    }
	
    tools {
        maven 'apache-maven'
    }
	
    triggers {
        pollSCM 'H/30 * * * *'
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
			name: 'RELEASE', 
			description: 'Build a release from current commit.', 
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
		stage('Quality Gate Status Check') {			
            steps {			
				timeout(time: 1, unit: 'HOURS') {
					waitForQualityGate abortPipeline: true
				}
            }
        }
		stage('Deploy to Tomcat'){ 
			steps {	
				sshagent(['tomcat9-ssh']) {
					sh "scp -o StrictHostKeyChecking=no ${MAVEN_TARGET_PATH}/*.war ${TOMCAT_USER}@${getTomcatContainerName()}:${TOMCAT_WEBAPPS}"
					//sh 'sleep 10'
					//sh "bash ${ROOT_PATH}/healthcheck.sh ${APP_URL}"
				}
			}
		}
		
		stage('Check Availability') {
		  steps {
			  waitUntil {
				sh "timeout 120 wget --retry-connrefused --tries=120 --waitretry=1 -q ${APP_URL} -O /dev/null"
			  }
		   }
	   }
		
		stage('Test') {
            steps {
                sh "mvn -U -s ${MAVEN_SETTINGS_PATH} -P${params.MAVEN_PROFILE} test"
            }			
            /*post {
                always {
                    junit "${MAVEN_TARGET_PATH}/surefire-reports/*.xml"
                }
            }*/
        }
		stage('Release') {
            when {
                expression { params.RELEASE }
            }
            steps {
			    checkout([$class: 'GitSCM', 
				  branches: [[name: '*/master']], 
				  extensions: [
					  [$class: 'UserIdentity', email: "${GIT_USER_EMAIL}", name: "${GIT_USER_NAME}"],
					  [$class: 'WipeWorkspace'], 
					  [$class: 'LocalBranch', localBranch: 'master']], 
				  userRemoteConfigs: [[credentialsId: "${GIT_CREDENTIALS_ID}", url: "${GIT_URL}"]]])
				
			    withCredentials([usernamePassword(credentialsId: "${GIT_CREDENTIALS_ID}", usernameVariable: 'GIT_USERNAME', passwordVariable: 'GIT_PASSWORD')]) {
					withCredentials([usernamePassword(credentialsId: "${'prod'.equals(params.MAVEN_PROFILE) ? GITHUB_PACKAGES_CREDENTIALS_ID : NEXUS_CREDENTIALS_ID}", usernameVariable: 'NEXUS_USERNAME', passwordVariable: 'NEXUS_PASSWORD')]) {
						sh "mvn --batch-mode -s ${MAVEN_SETTINGS_PATH} -P${params.MAVEN_PROFILE} -DskipTests -Darguments=-DskipTests release:clean release:prepare release:perform -DreleaseVersion=${RELEASE_VERSION} -Dtag=v${RELEASE_VERSION} -DdevelopmentVersion=${getSnapshotVersion()} -Dusername=${GIT_USERNAME} -Dpassword=${GIT_PASSWORD}"
					}
                }                
            }
        }
	}
	post {
        always {
            deleteDir()
        }
    }    
}
def getSnapshotVersion() {
	def array = "${RELEASE_VERSION}".split("\\.")
	return "${array[0]}.${array[1]}.${(array[2] as Integer) + 1}-SNAPSHOT"
}
def getTomcatContainerName() {
	def containerName = 'devops_tomcat_1'
	return containerName
}
