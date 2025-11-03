# Stage 1: Build the WAR file using a Maven image (JDK 17)
FROM maven:3.9.5-openjdk-17 AS build
WORKDIR /app
COPY . .
# Package the application into the study-platform.war file
RUN mvn clean package -DskipTests

# Stage 2: Deploy the WAR file onto a Tomcat image (JDK 17)
FROM tomcat:10.1.15-jdk17-temurin-jammy
# Remove default app
RUN rm -rf /usr/local/tomcat/webapps/*
# Copy the built WAR file and rename it to ROOT.war for base URL access
COPY --from=build /app/target/study-platform.war /usr/local/tomcat/webapps/ROOT.war
# Expose the default Tomcat port
EXPOSE 8080
# Render will automatically run Tomcat