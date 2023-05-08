# Builder stage
FROM gradle:jdk17 AS builder
WORKDIR /usr/app/kalaha

COPY . . 
RUN gradle build

# Production stage
FROM openjdk:17-jdk-slim
WORKDIR /usr/app/kalaha

COPY --from=builder /usr/app/kalaha .

EXPOSE 8080
ENTRYPOINT exec java -jar build/libs/kalaha-0.0.1-SNAPSHOT.jar
