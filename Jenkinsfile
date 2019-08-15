pipeline {
    agent any
    parameters {
        string(name:'repoBranch', defaultValue: 'master', description: '仓库')
        string(name:'repoUrl', defaultValue: 'git@github.com:jinghan99/updateDnsexit.git', description: '项目地址')
        string(name:'image_name', defaultValue: 'yf_girl/update-dns', description: 'docker build image name')
    }
    stages {
        stage('git') {
            steps {
                 echo "git 拉 取 version: $git_version  git   $params.repoUrl   "
                 git credentialsId: '7f54672e-d7e7-447d-b663-41961ad15d0a', url: 'git@github.com:jinghan99/updateDnsexit.git',branch: params.repoBranch
            }
        }
        stage('package') {
            steps {
                 echo "maven 打包 构建Docker镜像"
                 sh '''
                echo " $image_name "

                old_iamge=`docker images |grep $image_name |awk '{print $3}'`
                 echo "maven 打包 构建Docker镜像 ：$old_iamge"
                if [ x"$old_iamge" != x ]
                    then
                    echo "先删除 旧的重复版本 $old_iamge"
                    docker rmi $image_name
                fi

                mvn clean  package -Dmaven.test.skip=true
                 '''


            }
        }

        stage('push docker') {
            steps {
                sh'''

                image_id=$(docker images |grep $image_name  |awk '{print $3}')

                echo "show imageid ：$image_id docker tag： $git_version "

                old_tag=$(docker images |grep registry.cn-hangzhou.aliyuncs.com/yf_girl/update_dns| grep $git_version   |awk '{print $3}')
                old_run=$(docker ps -a |grep registry.cn-hangzhou.aliyuncs.com/yf_girl/update_dns   |awk '{print $1}')

                if [ x"$old_run" != x ]
                    then
                    echo "先删除 停止 旧的重复版本 运行 $old_run"
                    docker stop $old_run
                    docker rm $old_run
                 fi

                if [ x"$old_tag" != x ]
                    then
                    echo "先删除 旧的重复版本 $old_tag"
                    echo "先查询重复版本 是否在运行 $old_tag"
                    docker rmi $old_tag
                fi

                if [ x$image_id == x ]; then
                    echo "image_id not found"
                else
                    echo "image_id : $image_id"

                    docker tag $image_id registry.cn-hangzhou.aliyuncs.com/yf_girl/update_dns:$git_version
                    echo "推送远程镜像容器 registry.cn-hangzhou.aliyuncs.com/yf_girl/update_dns:$git_version"
                    docker push registry.cn-hangzhou.aliyuncs.com/yf_girl/update_dns:$git_version

                    echo "更新 远程镜像容器 latest registry.cn-hangzhou.aliyuncs.com/yf_girl/update_dns:latest"
                    docker tag $image_id registry.cn-hangzhou.aliyuncs.com/yf_girl/update_dns:latest
                    docker push registry.cn-hangzhou.aliyuncs.com/yf_girl/update_dns:latest
                fi
                '''
            }
        }

        stage('run docker') {
            steps {
                 echo "运行 当前docker compose "
                 sh'''
                 cd $WORKSPACE
                 echo "当前路径：$PWD"
                 docker-compose  up -d
                '''
            }
        }

    }
}