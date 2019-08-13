FROM livingobjects/jre8
VOLUME /tmp
ADD update-dns.jar app.jar


#设置变量
ENV AccessKeyId=""
ENV AccessKeySecret=""
ENV domain=""

RUN bash -c 'touch /app.jar'
ENTRYPOINT ["java","-Duser.timezone=Asia/Shanghai -Xms256M -Xmx256M","-jar","/app.jar --api.domain=$domain"]
