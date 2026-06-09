# Tahap 1: Build aplikasi menggunakan Maven dan JDK 17
FROM maven:3.8.5-openjdk-17 AS build
WORKDIR /app
COPY . .
RUN mvn clean package -DskipTests

# Tahap 2: Jalankan aplikasi menggunakan JRE yang lebih ringan
FROM openjdk:17-jdk-slim
WORKDIR /app
COPY --from=build /app/target/*.jar app.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]
