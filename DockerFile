FROM tomcat:11-jdk21-temurin

RUN rm -rf /usr/local/tomcat/webapps/*

COPY MovieTicketBooking.war /usr/local/tomcat/webapps/MovieTicketBooking.war

EXPOSE 10000

CMD ["sh", "-c", "sed -i 's/8080/10000/g' /usr/local/tomcat/conf/server.xml && catalina.sh run"]