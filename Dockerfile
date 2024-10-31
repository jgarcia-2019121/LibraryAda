FROM openjdk:21-jdk

WORKDIR /app

# Copia el wrapper de Maven y dale permisos de ejecución
COPY mvnw .
RUN chmod +x mvnw

# Copia el proyecto y ejecuta Maven
COPY . .
RUN ./mvnw clean package -DskipTests

EXPOSE 8080
ENTRYPOINT ["java", "-jar", "target/biblioteca-0.0.1-SNAPSHOT.jar"]
