# ---------- Build Stage ----------
FROM gradle:9.5.0-jdk25 AS builder

WORKDIR /app

COPY gradlew .
COPY gradle gradle
COPY build.gradle .
COPY settings.gradle .

RUN chmod +x gradlew

# dependency cache
RUN ./gradlew dependencies

COPY . .

RUN ./gradlew clean build -x test

# ---------- Runtime Stage ----------
FROM eclipse-temurin:25-jre

WORKDIR /app

COPY --from=builder /app/build/libs/*.jar app.jar

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "app.jar"]