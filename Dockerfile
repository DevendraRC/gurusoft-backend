FROM openjdk:17-jdk-alpine
WORKDIR /app
COPY target/dtect-springboot-0.0.1-SNAPSHOT.jar app.jar
EXPOSE 8080
CMD [ "java","-jar" ,"app.jar"]

# Use the Eclipse temurin alpine official image
# https://hub.docker.com/_/eclipse-temurin
FROM eclipse-temurin:21-jdk-alpine

# Create and change to the app directory.
WORKDIR /app

# Copy local code to the container image.
COPY . ./
COPY mvnw .
COPY .mvn .mvn
COPY pom.xml .

# Build the app.
RUN ./mvnw -DoutputFile=target/mvn-dependency-list.log -B -DskipTests clean dependency:list install
RUN ./mvnw dependency:go-offline

# Copy the rest of the application
COPY src ./src

# Package the application (without running tests)
RUN ./mvnw clean package -DskipTests

# Run the app by dynamically finding the JAR file in the target directory
CMD ["sh", "-c", "java -jar target/*.jar"]

# Stage 1: build the jar
FROM maven:3.8.5-openjdk-17 AS build
WORKDIR /app
COPY pom.xml .
COPY src ./src
RUN mvn clean package -DskipTests

# Stage 2: run the jar
FROM openjdk:17-jdk-slim
WORKDIR /app
COPY --from=build /app/target/*.jar app.jar
ENTRYPOINT ["java", "-jar", "app.jar"]
