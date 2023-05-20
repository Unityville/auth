FROM maven:3.9.1-eclipse-temurin-20-alpine AS builder

WORKDIR /app
COPY . .
RUN mvn clean package -DskipTests

FROM eclipse-temurin:20-alpine
COPY --from=builder /app/target/* /app/
WORKDIR /app
ENTRYPOINT java -jar *.jar
