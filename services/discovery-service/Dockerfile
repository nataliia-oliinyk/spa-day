FROM maven:3.9.6-eclipse-temurin-21-jammy as builder
WORKDIR /app
COPY pom.xml .
RUN mvn dependency:go-offline
COPY src/ ./src/
RUN mvn clean package -DskipTests=true

FROM eclipse-temurin:21-jammy
RUN mkdir /app
COPY --from=builder /app/target/*.jar /app/app.jar
WORKDIR /app
EXPOSE 8061
ENTRYPOINT ["java","-jar","/app/app.jar"]