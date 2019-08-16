#基础镜像
FROM java:8
VOLUME /tmp
ADD update-dns.jar app.jar
RUN bash -c 'touch /app.jar'
ENTRYPOINT ["java","-Xmx256m -Xms256m", "-jar", "-Duser.timezone=GMT+8", "/app.jar"]