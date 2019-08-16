#基础镜像
FROM java:8
VOLUME /tmp
ADD update-dns.jar app.jar
RUN bash -c 'touch /app.jar'
RUN /bin/cp /usr/share/zoneinfo/Asia/Shanghai /etc/localtime \
&& echo 'Asia/Shanghai' >/etc/timezone \
ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom -Duser.timezone=Asia/Shanghai  -Xms256M -Xmx256M","-jar","/app.jar"]
