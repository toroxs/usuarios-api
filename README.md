# API de Usuarios (Spring Boot, H2, JWT)

Implementa un endpoint **POST /api/usuarios** (http://localhost:8080/api/usuarios) que recibe/retorna **solo JSON**.
Valida email y password por **regex**, genera y **persiste** un **JWT**, y responde
errores con `{ "mensaje": "..." }`. BD en memoria **H2** con **Hibernate/JPA**.
Build con **Gradle**, servidor **Tomcat** embebido.

Schema de BD ubicado en src/main/resources/schema.sql

## Ejecutar
```bash
./gradlew bootRun
