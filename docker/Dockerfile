FROM tomcat:jdk8-adoptopenjdk-hotspot

USER root

#TOMCAT 7
ENV CATALINA_HOME /usr/local/tomcat
ENV PATH $CATALINA_HOME/bin:$PATH

ENV SERVER_IP 127.0.0.1
ENV CAT_HTTP_PORT 8080
ENV JVM_OPTS "-Xmx512m -Xms512m"
ENV CAT_WEB_SERVER "localhost"

COPY ./cat-alpha-3.0.0.war /root/workspace/cat/cat.war
WORKDIR /root/workspace/cat

RUN cp /root/workspace/cat/cat.war $CATALINA_HOME/webapps/cat.war
COPY ./datasources.xml /data/appdatas/cat/datasources.xml
COPY ./init.sh /cat_init.sh
COPY ./client.xml /data/appdatas/cat/client.xml
RUN sed -i "s/port=\"8080\"/port=\"8080\"\ URIEncoding=\"utf-8\"/g" $CATALINA_HOME/conf/server.xml \
    && cp /usr/share/zoneinfo/Asia/Shanghai /etc/localtime \
    && chmod +x /cat_init.sh \
    && sed -i '1a\JAVA_OPTS="-Dhost.ip=\$SERVER_IP -Dcat.web.server=\$CAT_WEB_SERVER -Duser.timezone=GMT+8 \$JVM_OPTS"' /usr/local/tomcat/bin/catalina.sh

CMD /cat_init.sh

EXPOSE 8080
