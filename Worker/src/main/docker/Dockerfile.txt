FROM dockerfile/java:oracle-java8

VOLUME /tmp
ADD Worker.jar /opt/Worker/
EXPOSE 8080
WORKDIR /opt/Worker/
CMD ["java", "-Djava.security.egd=file:/dev/./urandom", "-Xms512m", "-Xmx1g", "-jar", "Worker.jar"]
