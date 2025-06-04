# ============================================
# Build stage (Maven 3.6.3 with JDK 17)
# ============================================
FROM maven:3.6.3-openjdk-17-slim AS builder

WORKDIR /app

# 1. Copy only the POM first to cache dependencies
COPY pom.xml .
# Verify we can download dependencies before building
RUN mvn verify --fail-never dependency:resolve-plugins dependency:resolve

# 2. Copy source code
COPY src ./src

# 3. Build with more memory and debug options
RUN mvn clean package \
    -DskipTests \
    -Dmaven.test.skip=true \
    -B \
    -e \
    -X \
    -Dorg.slf4j.simpleLogger.log.org.apache.maven.cli.transfer.Slf4jMavenTransferListener=warn

# ============================================
# Runtime stage (Lightweight JRE)
# ============================================
FROM eclipse-temurin:17.0.15_6-jre-noble

# Create app user and directory
RUN groupadd --system spring && \
    useradd --system --gid spring spring && \
    mkdir -p /app && \
    chown spring:spring /app

WORKDIR /app

# Copy the built JAR file
COPY --from=builder --chown=spring:spring /app/target/*.jar app.jar

USER spring

HEALTHCHECK --interval=30s --timeout=3s \
  CMD curl -f http://localhost:8071/actuator/health || exit 1

EXPOSE 8071

# Production-ready JVM options
ENV JAVA_OPTS="-XX:+UseContainerSupport -XX:MaxRAMPercentage=75.0 -XX:+UseG1GC -XX:+ExitOnOutOfMemoryError"

ENTRYPOINT ["sh", "-c", "exec java ${JAVA_OPTS} -jar app.jar"]