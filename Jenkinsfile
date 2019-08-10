pipeline {
    agent any
    parameters {
        string(name:'repoBranch', defaultValue: 'master', description: 'This is an test.')
        string(name:'repoUrl', defaultValue: 'git@github.com:jinghan99/updateDnsexit.git', description: '项目地址')
    }
    stages {
        stage('git') {
            steps {
                echo "git 拉 取  git  url:params.repoUrl ,branch:params.repoBranch "
                git branch: params.repoBranch, credentialsId: '813ed607-91a3-41cf-a356-785039e376b8', url: 'git@github.com:jinghan99/updateDnsexit.git'
            }
        }
        stage('package') {
            steps {
                 echo "maven 打包"
                sh "mvn clean  package -Dmaven.test.skip=true"
            }
        }

        stage('Deploy') {
            steps {
                echo "pipelin Deploy success!"
            }
        }
    }
}