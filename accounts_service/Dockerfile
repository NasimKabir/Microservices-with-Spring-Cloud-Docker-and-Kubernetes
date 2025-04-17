# Build stage
FROM eclipse-temurin:17-jdk-jammy as builder
WORKDIR /workspace
COPY . .
RUN ./mvnw clean package

# Runtime stage
FROM eclipse-temurin:17-jdk-jammy
WORKDIR /app
COPY --from=builder /workspace/target/accounts-service-*.jar accounts-service.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "accounts-service.jar"]