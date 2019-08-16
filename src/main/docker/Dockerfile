#基础镜像
FROM java:8
VOLUME /tmp
ADD update-dns.jar app.jar
RUN bash -c 'touch /app.jar'
ENTRYPOINT ["java", "-jar", "-Duser.timezone=GMT+8 -Xmx256m -Xms256m", "/app.jar"]