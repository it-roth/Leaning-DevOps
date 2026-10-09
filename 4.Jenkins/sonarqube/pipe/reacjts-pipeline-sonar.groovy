pipeline {
    agent any

    // declaring variable 
    environment{
        TAG="v1.0.${env.BUILD_NUMBER}" // built-in
        IMG_NAME="jenkins-g12-reactjs"
        DH_USER="lyvanna544"
        FULL_IMG="${DH_USER}/${IMG_NAME}:${TAG}"
    }
    stages {
        stage("Checkout"){
            steps{
                git 'https://github.com/keoKAY/reactjs-devop8-template'
          
            }
        }

        // sonarqube scanning here !
        // scan the project, show the reports 
        stage("Scan with Sonarqube "){
            environment{
                scannerHome = tool 'sonar-scanner'
            }
            steps{
                withSonarQubeEnv(credentialsId: 'SONARQUBE-TOKEN', installationName: 'sonar-scanner') {
                   script{
                        def projectName="ReactjsTestScan"
                        def projectVersion="1.0.0" 
                        def projectKey="reactjs-test-scan"
                        sh """

                        ${scannerHome}/bin/sonar-scanner \
                            -Dsonar.projectName="${projectName}" \
                            -Dsonar.projectKey=${projectKey} \
                            -Dsonar.projectVersion=${projectVersion}

                        """
                   }     
                }
                
            }

        } 
        // wait to get result Passed or Failed when scan is done
        stage("Wait for QualityGate"){
            
            steps{
                script{
                    def qg = waitForQualityGate() 
                    if(qg.status !='OK'){
                        echo "Quality Gate is Failed !! "
                        // send telegram to team to fix 
                        currentBuild.result = 'FAILURE'
                        error("Quality Gate Failed cannot proceced")
                        return // to stop the pipeline

                    }else{
                        echo "Quality Gate is Passed ! "
                        currentBuild.result='SUCCESS'
                    }
                }
            }
        }
         stage('Build') {
            steps {
               sh "ls -lrt "
               sh """
                docker build -t ${FULL_IMG} -f prod.Dockerfile . 
               """
            }
        }

         stage("Push"){
            steps{
                withCredentials([usernamePassword(credentialsId: 'DH_CREDIT', passwordVariable: 'PASSWORD', usernameVariable: 'USERNAME')]) {

            sh """

           echo "${PASSWORD}" | docker login -u ${USERNAME} --password-stdin

           docker push "${FULL_IMG}"
            """
            }
            }
         }
  
      
    }
}




