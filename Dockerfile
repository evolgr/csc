FROM eclipse-temurin:17-jdk-alpine
WORKDIR /app
COPY target/csc-simulator.jar app.jar
ENTRYPOINT ["java", "-jar", "app.jar"]
