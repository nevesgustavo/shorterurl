FROM adoptopenjdk:11-jre-hotspot
ADD target/shorter-url.jar shorter-url.jar
EXPOSE 8085
ENTRYPOINT ["java", "-Djava.security.egd=file:/dev/./urandom", "-Dspring.profiles.active=container", "-jar", "shorter-url.jar"]

