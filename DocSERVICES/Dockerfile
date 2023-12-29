# Start with a base image containing Java runtime based on Debian
FROM openjdk:17-slim

# Install Tesseract
RUN apt-get update && apt-get install -y tesseract-ocr

# Add a volume pointing to /tmp
VOLUME /tmp

# Make port 8082 available to the world outside this container
EXPOSE 8082

# The application's jar file
ARG JAR_FILE=target/DocSERVICES-0.0.1-SNAPSHOT.jar

# Add the application's jar to the container
ADD ${JAR_FILE} docservices.jar

# Run the jar file
ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-jar","/docservices.jar"]
