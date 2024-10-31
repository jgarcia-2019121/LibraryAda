FROM openjdk:21-jdk
WORKDIR /app

# Copia todos los archivos del proyecto al contenedor
COPY . .

# Ejecuta Maven para construir el proyecto dentro del contenedor y saltar las pruebas
RUN ./mvnw clean package -DskipTests

# Copia el archivo .jar generado
COPY target/biblioteca-0.0.1-SNAPSHOT.jar app.jar

# Expone el puerto en el que correrá la aplicación
EXPOSE 8080

# Comando para ejecutar la aplicación
ENTRYPOINT ["java", "-jar", "app.jar"]
