# Usa una imagen base de Java 21 para compilación
FROM openjdk:21-jdk AS build
WORKDIR /app
COPY . .
RUN ./mvnw clean package -DskipTests

# Usa otra imagen de Java 21 para el contenedor final
FROM openjdk:21-jdk
WORKDIR /app
COPY --from=build /app/target/biblioteca-0.0.1-SNAPSHOT.jar app.jar

# Exponer el puerto de la aplicación
EXPOSE 8080

# Comando para ejecutar la aplicación
ENTRYPOINT ["java", "-jar", "app.jar"]
