#基础镜像
FROM jinghan94/centos_jdk:8
VOLUME /tmp
ADD update-dns.jar app.jar
RUN bash -c 'touch /app.jar'
ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-jar", "-Duser.timezone=Asia/Shanghai", "/app.jar"]