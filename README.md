# LibraryAda

Este es un proyecto de una aplicación de biblioteca construido con **Spring Boot** en el backend y **MongoDB** para el almacenamiento de datos. La aplicación se despliega en **Render** utilizando Docker.

## Tabla de Contenidos

- [Características](#características)
- [Tecnologías Utilizadas](#tecnologías-utilizadas)
- [Requisitos Previos](#requisitos-previos)
- [Configuración del Proyecto](#configuración-del-proyecto)
- [Despliegue en Render](#despliegue-en-render)
- [Uso](#uso)
- [Contribución](#contribución)
- [Licencia](#licencia)

## Características

- CRUD de libros y usuarios
- Autenticación con JWT (opcional)
- Despliegue automatizado en Render con Docker

## Tecnologías Utilizadas

- **Java 21**
- **Spring Boot**
- **MongoDB**
- **Docker**
- **Render** (para despliegue en producción)

## Requisitos Previos

Antes de iniciar, asegúrate de tener los siguientes requisitos:

- **Java 21** o superior
- **Docker** instalado en tu máquina
- **MongoDB** para almacenamiento de datos
- Cuenta en **Render** (opcional, solo si deseas hacer despliegue en la nube)

## Configuración del Proyecto

1. Clona el repositorio:

   ```bash
   git clone https://github.com/jgarcia-2019121/LibraryAda.git
   cd LibraryAda
2. Compila el proyecto localmente (esto generará el archivo .jar):
   
   ./mvnw clean package -DskipTests
   
3. Verifica que el archivo .jar se haya generado en la carpeta target:
   ls target/biblioteca-0.0.1-SNAPSHOT.jar

4. Ejecuta la aplicación localmente usando Docker:
   docker build -t libraryada-app .
   docker run -p 8080:8080 libraryada-app

5. Accede a la aplicación en http://localhost:8080.

Despliegue en Render
El despliegue en Render ya está configurado para ejecutarse automáticamente con cada push al repositorio. Si necesitas desplegar manualmente:

Ve a tu cuenta de Render y selecciona el servicio.
Haz clic en Manual Deploy > Clear build cache & deploy.
Render tomará el Dockerfile y desplegará la aplicación automáticamente.
Uso
Una vez en funcionamiento, puedes acceder a los endpoints de la API. Ejemplo:

GET /api/libros - Obtiene todos los libros
POST /api/libros - Crea un nuevo libro
PUT /api/libros/{id} - Actualiza un libro existente
DELETE /api/libros/{id} - Elimina un libro
Puedes probar estos endpoints en Postman o cualquier cliente de API.

Contribución
Si deseas contribuir a este proyecto, por favor sigue los siguientes pasos:

Haz un fork del proyecto
Crea una nueva rama (git checkout -b feature/nueva-funcionalidad)
Haz commit a tus cambios (git commit -m 'Añadir nueva funcionalidad')
Haz push a la rama (git push origin feature/nueva-funcionalidad)
Abre un Pull Request

Licencia
Este proyecto está bajo la licencia MIT. Consulta el archivo LICENSE para más detalles.

Guarda este contenido en un archivo llamado `README.md` en la raíz de tu proyecto. Este archivo proporciona una guía completa sobre las características, configuración y despliegue de tu aplicación.
