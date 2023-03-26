FROM openjdk:17

COPY justbuild.it-WebApp/target/justbuild.it-WebApp-1.0.0-SNAPSHOT.jar /app.jar
COPY offers.json /offers.json
EXPOSE 8080

CMD ["java", "-jar", "/app.jar"]