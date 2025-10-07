FROM maven:3.9.9-eclipse-temurin-17 AS build

WORKDIR /app

# Cache dependencies first for faster rebuilds
COPY pom.xml .
RUN mvn -B -q dependency:go-offline

# Copy source and build
COPY src ./src
RUN mvn -B -q clean package -DskipTests

# Runtime image
FROM eclipse-temurin:17-jre-jammy

WORKDIR /app

# Install curl for healthcheck (optional but useful)
RUN apt-get update && apt-get install -y --no-install-recommends curl ca-certificates && rm -rf /var/lib/apt/lists/*

# Non-root user for better security
RUN groupadd -r appuser && useradd -r -g appuser appuser

# Copy the built jar without hardcoding version
COPY --from=build /app/target/*.jar app.jar
RUN chown appuser:appuser app.jar

USER appuser

EXPOSE 8080

# Healthcheck hits actuator endpoint
HEALTHCHECK --interval=30s --timeout=10s --start-period=10s --retries=3 \
    CMD curl -fsS http://localhost:8080/actuator/health || exit 1

ENTRYPOINT ["java","-jar","app.jar"]
