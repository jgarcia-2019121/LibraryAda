# Usa una imagen base de Java 21
FROM openjdk:21-jdk

# Establece el directorio de trabajo
WORKDIR /app

# Copia el archivo mvnw y dale permisos de ejecución
COPY mvnw .
RUN chmod +x ./mvnw

# Copia el archivo pom.xml y la carpeta src al contenedor
COPY pom.xml .
COPY src ./src

# Ejecuta Maven para compilar el proyecto y generar el .jar
RUN ./mvnw clean package -DskipTests

# Copia el archivo .jar generado en el contenedor
COPY target/biblioteca-0.0.1-SNAPSHOT.jar app.jar

# Exponer el puerto en el que correrá la aplicación
EXPOSE 8080

# Comando para ejecutar la aplicación
ENTRYPOINT ["java", "-jar", "app.jar"]
