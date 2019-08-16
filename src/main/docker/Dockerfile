#基础镜像
FROM java:8
VOLUME /tmp
ADD update-dns.jar app.jar
RUN bash -c 'touch /app.jar'
ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom  -Xms256M -Xmx256M","-jar","/app.jar"]
