FROM openjdk:21-jdk

WORKDIR /app

# Copia el archivo mvnw y dale permisos de ejecución
COPY mvnw .
RUN chmod +x mvnw

# Copia el resto del proyecto en el contenedor
COPY . .

# Ejecuta Maven para compilar el proyecto
RUN ./mvnw clean package -DskipTests

EXPOSE 8080
ENTRYPOINT ["java", "-jar", "target/biblioteca-0.0.1-SNAPSHOT.jar"]
