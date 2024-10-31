# Usa una imagen base de Java 21
FROM openjdk:21-jdk

# Establece el directorio de trabajo
WORKDIR /app

# Copia el archivo .jar generado en el contenedor
COPY target/biblioteca-0.0.1-SNAPSHOT.jar app.jar

# Expone el puerto en el que correrá la aplicación
EXPOSE 8080

# Comando para ejecutar la aplicación
ENTRYPOINT ["java", "-jar", "app.jar"]
