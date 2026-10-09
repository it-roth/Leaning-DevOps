pipeline{
    agent any 
    stages{
        stage("Task1 "){
            steps{
                echo "This is the first task "
            }
        }
        stage("Parrallel Tasks"){
            parallel{
                stage("Tasks2"){
                    steps{
                        echo "Running tasks 2 "
                    }
                }

                stage("Task 3 "){
                    steps{
                        echo "Running tasks 3 "
                    }
                }

                stage("Task 4 "){
                    steps{
                        echo "Running tasks 4 "
                    }
                }
            }
            
        }
    }
}