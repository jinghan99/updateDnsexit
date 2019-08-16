#基础镜像
FROM java:8
VOLUME /tmp
ADD update-dns.jar app.jar
RUN bash -c 'touch /app.jar'
ENTRYPOINT ["java", "-Xms125m -Xmx125m -Djava.security.egd=file:/dev/./urandom", "-jar", "-Duser.timezone=GMT+8", "/app.jar"]