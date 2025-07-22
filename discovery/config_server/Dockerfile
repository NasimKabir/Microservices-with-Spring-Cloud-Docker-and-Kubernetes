# ============================================
# Build Stage
# ============================================
FROM maven:3.9.6-amazoncorretto-17 AS build

WORKDIR /build

COPY pom.xml .
COPY src ./src

RUN mvn clean package -DskipTests

# ============================================
# Runtime Stage
# ============================================
FROM amazoncorretto:17-alpine

WORKDIR /app

# Install wget for health check
RUN apk add --no-cache curl

COPY --from=build /build/target/*.jar /app/app.jar

EXPOSE 8071

ENV JAVA_TOOL_OPTIONS="-XX:+UseContainerSupport -XX:MaxRAMPercentage=75.0 -XX:+UseG1GC -XX:+ExitOnOutOfMemoryError -Duser.home=/tmp"


ENTRYPOINT ["java", "-jar", "/app/app.jar"]
