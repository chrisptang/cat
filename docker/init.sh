#!/usr/bin/env bash
echo "resolving datasource placeholders...";
sed -i "s/MYSQL_URL/${MYSQL_URL}/g" /data/appdatas/cat/datasources.xml;
sed -i "s/MYSQL_PORT/${MYSQL_PORT}/g" /data/appdatas/cat/datasources.xml;
sed -i "s/MYSQL_USERNAME/${MYSQL_USERNAME}/g" /data/appdatas/cat/datasources.xml;
sed -i "s/MYSQL_PASSWD/${MYSQL_PASSWD}/g" /data/appdatas/cat/datasources.xml;
sed -i "s/MYSQL_SCHEMA/${MYSQL_SCHEMA}/g" /data/appdatas/cat/datasources.xml;
echo "please check the content of: /data/appdatas/cat/datasources.xml";
cat /data/appdatas/cat/datasources.xml;

echo "resolving cat client placeholders...";
sed -i "s/SERVER_IP/${SERVER_IP}/g" /data/appdatas/cat/client.xml;
sed -i "s/CAT_HTTP_PORT/${CAT_HTTP_PORT}/g" /data/appdatas/cat/client.xml;
echo "please check the content of: /data/appdatas/cat/client.xml";
cat /data/appdatas/cat/client.xml;

echo "please check catalina.sh configurations:"
cat /usr/local/tomcat/bin/catalina.sh;

/usr/local/tomcat/bin/catalina.sh run