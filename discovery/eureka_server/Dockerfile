# Stage 1: Build with Maven
FROM maven:3.9.6-eclipse-temurin-17-alpine as builder

WORKDIR /build

# Copy pom.xml and download dependencies
COPY pom.xml .
RUN mvn dependency:go-offline

# Copy entire project and build
COPY . .
RUN mvn clean package -DskipTests

# Stage 2: Run
FROM eclipse-temurin:17-jdk-alpine

WORKDIR /app

# Copy the jar from the builder stage
COPY --from=builder /build/target/*.jar app.jar

EXPOSE 8761


ENTRYPOINT ["java", "-jar", "app.jar"]
