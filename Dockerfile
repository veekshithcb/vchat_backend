FROM openjdk:19-jdk AS build
WORKDIR /app
COPY pom.xml .
COPY src src

# Copy Maven wrapper
COPY mvnw .
COPY .mvn .mvn

# Set execution permission for the Maven wrapper
RUN chmod +x ./mvnw
RUN ./mvnw clean package -DskipTests

# Stage 2: Create the final Docker image using OpenJDK 19
FROM openjdk:19-jdk
VOLUME /tmp

# Copy the JAR from the build stage
COPY --from=build /app/target/*.jar app.jar
ENTRYPOINT ["java","-jar","/app.jar"]
EXPOSE 8080

## Use an official OpenJDK runtime as a parent image
#FROM openjdk:17-jdk-alpine
#
## Set the working directory inside the container
#WORKDIR /app
#
## Add the jar file built from the Spring Boot app
#ARG JAR_FILE=target/*.jar
#
## Copy the jar file into the container
#COPY ${JAR_FILE} app.jar
#
## Expose the port on which the Spring Boot app will run
#EXPOSE 8088
#
## Run the jar file
#ENTRYPOINT ["java", "-jar", "/app/app.jar"]
