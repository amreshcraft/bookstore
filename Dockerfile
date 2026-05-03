# Stage 1: build
FROM gradle:9.5.0-jdk25 AS builder
WORKDIR /app
COPY . .
RUN gradle build -x test

# Stage 2: run
FROM eclipse-temurin:25.0.3_9-jre-ubi10-minimal
WORKDIR /app
COPY --from=builder /app/build/libs/*.jar bookstore.jar

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "bookstore.jar"]