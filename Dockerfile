# ==============================
# 1. Build stage
# ==============================
FROM gradle:8.10.2-jdk21 AS builder
WORKDIR /app
COPY gradlew gradlew.bat build.gradle settings.gradle ./
COPY gradle ./gradle
RUN ./gradlew dependencies --no-daemon || return 0
COPY src ./src
RUN ./gradlew clean bootJar --no-daemon




# ==============================
# 2. Runtime stage
# ==============================
FROM eclipse-temurin:21-jre-alpine
WORKDIR /app
COPY --from=builder /app/build/libs/*.jar app.jar
RUN addgroup -S spring && adduser -S spring -G spring
USER spring
EXPOSE 8081
ENTRYPOINT ["java", "-jar", "app.jar"]
