FROM livingobjects/jre8
VOLUME /tmp
ADD update-dns.jar app.jar
RUN bash -c 'touch /app.jar'
ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom -Duser.timezone=Asia/Shanghai -Xms256M -Xmx256M","-jar","/app.jar"]
