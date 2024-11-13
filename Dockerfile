FROM eclipse-temurin:23.0.1_11-jre-ubi9-minimal
LABEL authors="anthonyikeda"
LABEL service="book-service"

WORKDIR /app

# Copy the JAR file into the container
COPY target/book-service-0.0.1-SNAPSHOT.jar /app/app.jar

# Expose the port your application listens on
EXPOSE 8080

# Command to run your application
CMD ["java", "-jar", "app.jar"]
