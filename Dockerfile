# Usa una imagen base de Java 21
FROM openjdk:21-jdk

# Establece el directorio de trabajo
WORKDIR /app

# Copia los archivos de Maven Wrapper y pom.xml para instalar dependencias
COPY mvnw .
COPY .mvn .mvn
COPY pom.xml .

# Dale permisos de ejecución a mvnw
RUN chmod +x ./mvnw

# Ejecuta Maven para descargar dependencias sin compilar
RUN ./mvnw dependency:resolve

# Copia el código fuente del proyecto
COPY src ./src

# Ejecuta Maven para compilar el proyecto y generar el .jar
RUN ./mvnw clean package -DskipTests

# Copia el archivo .jar generado en el contenedor
RUN mv target/*.jar app.jar

# Exponer el puerto en el que correrá la aplicación
EXPOSE 8080

# Comando para ejecutar la aplicación
ENTRYPOINT ["java", "-jar", "app.jar"]
