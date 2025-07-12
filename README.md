# Clinica
# 🏥 API de Gestión de Clínica Médica

Este proyecto es una API REST desarrollada con **Spring Boot** para la gestión de una clínica médica. Permite registrar médicos, agendar y cancelar consultas, documentar la API con Swagger, ejecutar pruebas unitarias y preparar la aplicación para producción.

---

## 🚀 Tecnologías Utilizadas

- Java 17
- Spring Boot 3
  - Spring Web
  - Spring Security
  - Spring Data JPA
  - Spring Validation
- MySQL
- Maven
- Flyway (migraciones de base de datos)
- Swagger / SpringDoc OpenAPI
- JUnit 5 / Mockito / MockMvc
- JacksonTester
- GraalVM (opcional, para imagen nativa)

---

## 📦 Estructura de Funcionalidades

### 📁 Médicos
- Registrar médico (`POST /medicos`)
- Listar médicos (`GET /medicos`)
- Validación con `@Valid`
- Uso de `DTOs` (DatosRegistroMedico, DatosDetalleMedico)

### 📁 Consultas
- Agendar consulta (`POST /consulta`)
- Cancelar consulta (`DELETE /consulta`)
- Validaciones personalizadas (disponibilidad, estado activo, etc.)
- Polimorfismo con interfaz `ValidadorDeConsultas`

---

## 🔐 Seguridad
- Autenticación JWT con `/login`
- Filtro de seguridad para validar token
- Swagger configurado con autenticación vía `bearer-key`

---

## 📄 Documentación Swagger
Disponible en: documentacio (http://localhost:8080/swagger-ui.html)



Generada automáticamente con `springdoc-openapi`.

---

## 🧪 Pruebas Automatizadas
- Pruebas con `MockMvc` para controllers
- `@MockBean` para simular servicios o repositorios
- Serialización de objetos con `JacksonTester`
- Pruebas sin afectar la base real (perfil `test`)

---

## ⚙️ Perfiles de Aplicación

- `application.properties` → local/dev
- `application-test.properties` → pruebas
- `application-prod.properties` → producción

Activación por línea de comandos:

```bash
java -jar target/api-0.0.1-SNAPSHOT.jar --spring.profiles.active=prod
