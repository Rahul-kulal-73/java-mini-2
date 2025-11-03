# Stage 1: Build the WAR file using a base JDK 17 image and install Maven
# This approach bypasses problematic 'maven:x.x.x' tags.
FROM openjdk:17-jdk-slim AS build

# Install Maven dependencies and Maven itself
RUN apt-get update && \
    apt-get install -y --no-install-recommends maven && \
    rm -rf /var/lib/apt/lists/*

WORKDIR /app
COPY . .
# Package the application into the study-platform.war file
RUN mvn clean package -DskipTests

# Stage 2: Deploy the WAR file onto a Tomcat image (JDK 17)
# Using the proven Tomcat tag that was found in your previous logs
FROM tomcat:10-jdk17-temurin

# Remove default app
RUN rm -rf /usr/local/tomcat/webapps/*

# Copy the built WAR file from the build stage to Tomcat's root webapps directory
# Ensure 'study-platform.war' matches the <finalName> in your pom.xml
COPY --from=build /app/target/study-platform.war /usr/local/tomcat/webapps/ROOT.war

# Expose the default Tomcat port
EXPOSE 8080
