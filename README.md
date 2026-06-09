# Pura Vida Animal Sanctuary

API RESTful desarrollada para la gestión integral de un santuario de animales de granja. El sistema permite administrar de forma centralizada animales rescatados, voluntarios, coordinadores, tareas de cuidado y donaciones.

<div align="center">

![Java 25](https://img.shields.io/badge/Java_25-ED8B00?style=flat-square&logo=openjdk&logoColor=white)
![Spring Boot](https://img.shields.io/badge/Spring_Boot-6DB33F?style=flat-square&logo=springboot&logoColor=white)
![Spring Data JPA](https://img.shields.io/badge/Spring_Data_JPA-6DB33F?style=flat-square&logo=spring&logoColor=white)
![Spring Security](https://img.shields.io/badge/Spring_Security-6DB33F?style=flat-square&logo=springsecurity&logoColor=white)
![Spring Web MVC](https://img.shields.io/badge/Spring_Web_MVC-6DB33F?style=flat-square&logo=spring&logoColor=white)
![Spring AI](https://img.shields.io/badge/Spring_AI-6DB33F?style=flat-square&logo=spring&logoColor=white)
![MySQL](https://img.shields.io/badge/MySQL-4479A1?style=flat-square&logo=mysql&logoColor=white)
![JWT](https://img.shields.io/badge/JWT-000000?style=flat-square&logo=jsonwebtokens&logoColor=white)
![Lombok](https://img.shields.io/badge/Lombok-BC4521?style=flat-square&logo=lombok&logoColor=white)
![Maven](https://img.shields.io/badge/Maven-C71A36?style=flat-square&logo=apachemaven&logoColor=white)

</div>

**Características principales:**
*   **Seguridad:** Autenticación mediante **JWT (JSON Web Tokens)** con control de acceso basado en roles (`Volunteer` / `Coordinator`).
*   **Arquitectura:** Diseño basado en **Spring Boot** con persistencia mediante **Spring Data JPA**.
*   **Gestión:** CRUD completo para todas las entidades del sistema.

---

## Requisitos previos

- **Java 25**
- **Maven 3.9+**
- **MySQL 8+**
- **Cuenta de OpenAI** con API key (para el asistente IA)

---

## Recursos

- [Spring Boot 4.0.6 Reference Guide](https://docs.spring.io/spring-boot/docs/4.0.6/reference/htmlsingle/)
- [Spring Security](https://docs.spring.io/spring-security/reference/)
- [Auth0 java-jWT](https://github.com/auth0/java-jwt)
- [MySQL Documentation](https://dev.mysql.com/doc/)

---

## Diagramas

### Diagrama de clases
<img src="diagrams/Diagrama%20de%20clases.drawio.png" width="500"/>

### Diagrama de casos de uso
<img src="diagrams/Diagrama%20UML.drawio.png" width="500"/>

### Herencia y relaciones
- **User → Volunteer / Coordinator**: Herencia JOINED (tabla padre `users`, tablas hijas `volunteer` / `coordinator`)
- **Volunteer 1:N Task**: Un voluntario puede tener varias tareas asignadas
- **Animal 1:N Task**: Un animal puede tener varias tareas asociadas
- **Donation N:1 User**: Una donación puede estar vinculada a un usuario registrado (opcional)

### Enums
- `HealthStatus`: `HEALTHY`, `SICK`, `IN_TREATMENT`
- `TaskStatus`: `PENDING`, `IN_PROGRESS`, `COMPLETED`

---

## Configuración y Ejecución

### Variables de Entorno
La aplicación requiere las siguientes variables de entorno configuradas en tu sistema:

```properties
DB_USER=root                    # Usuario de MySQL
DB_PASSWORD=your_password       # Contraseña de MySQL
OPENAI_API_KEY=sk-...           # API key de OpenAI
```

### Configuración de Base de Datos
La aplicación está configurada para crear la base de datos automáticamente al iniciar.

### Ejecutar la aplicación
```bash
mvn spring-boot:run
```

La aplicación arranca en `http://localhost:8080`.

Al iniciar por primera vez, se crea automáticamente un **coordinador admin**:
- Email: `admin@puravidasanctuary.com`
- Password: `****`

---


## Estructura de controladores y rutas

### Autenticación
| Método | Ruta         | Descripción                  | Auth     |
|--------|--------------|------------------------------|----------|
| POST   | `/api/login` | Iniciar sesión (email + pass) | Público  |

### Animales (`/animals`)
| Método | Ruta       | Descripción                    | Auth   |
|--------|------------|--------------------------------|--------|
| GET    | `/animals`       | Listar todos los animales      | Bearer |
| GET    | `/animals/{id}`  | Obtener animal por ID          | Bearer |
| POST   | `/animals`       | Crear un nuevo animal          | Bearer |
| PUT    | `/animals`       | Actualizar un animal           | Bearer |
| DELETE | `/animals/{id}`  | Eliminar un animal             | Bearer |

### Usuarios (`/users`)
| Método | Ruta               | Descripción                        | Auth     |
|--------|--------------------|------------------------------------|----------|
| GET    | `/users`           | Listar todos los usuarios          | Bearer   |
| GET    | `/users/{id}`      | Obtener usuario por ID             | Bearer   |
| POST   | `/users/volunteer` | Registrar un nuevo voluntario      | Público  |
| POST   | `/users/coordinator` | Registrar un nuevo coordinador   | Bearer   |
| DELETE | `/users/{id}`      | Eliminar un usuario                | Bearer   |

### Tareas (`/tasks`)
| Método | Ruta       | Descripción                   | Auth   |
|--------|------------|-------------------------------|--------|
| GET    | `/tasks`         | Listar todas las tareas       | Bearer |
| GET    | `/tasks/{id}`    | Obtener tarea por ID          | Bearer |
| POST   | `/tasks`         | Crear una nueva tarea         | Bearer |
| PUT    | `/tasks/{id}`    | Actualizar una tarea          | Bearer |
| DELETE | `/tasks/{id}`    | Eliminar una tarea            | Bearer |

### Donaciones (`/donations`)
| Método | Ruta           | Descripción                      | Auth   |
|--------|----------------|----------------------------------|--------|
| GET    | `/donations`         | Listar todas las donaciones      | Bearer |
| GET    | `/donations/{id}`    | Obtener donación por ID          | Bearer |
| POST   | `/donations`         | Registrar una donación           | Bearer |
| DELETE | `/donations/{id}`    | Eliminar una donación            | Bearer |

### Inteligencia Artificial (Spring AI) `/api/ai`
Se ha integrado un asistente virtual conectado a la base de datos. Al proporcionar el ID de un animal, el sistema recupera su expediente clínico en tiempo real y solicita a la IA de OpenAI un plan de cuidados veterinarios, manteniendo el contexto de la conversación mediante memoria de chat.

| Método | Ruta | Descripción | Auth |
|--------|------|-------------|------|
| GET | `/api/ai/advice/{conversationId}` | Obtener consejo de cuidado animal pasando su `animalId` por parámetro | Bearer |

*Ejemplo de uso (solicitando consejo para el animal con ID 1):*
```bash
curl "http://localhost:8080/api/ai/advice/sesion-1?animalId=1" \
  -H "Authorization: Bearer <JWT>"
```

### Uso de ejemplo (login + token)
```bash
# Login
curl -X POST http://localhost:8080/api/login \
  -H "Content-Type: application/json" \
  -d '{"email": "admin@puravidasanctuary.com", "password": "****"}'

# Usar el token devuelto para rutas protegidas
curl http://localhost:8080/animals \
  -H "Authorization: Bearer <token>"
```

---

## Enlaces adicionales

| Recurso          | Enlace                                          |
|------------------|-------------------------------------------------|
| Trello           | [https://trello.com/invite/b/6a26ef2a669a12783a7dee00/ATTIaceeb7dc8ba850a2ae19ba7fb0b8a6a74AAA2F8C/proyecto-final-sanctuary](https://trello.com/invite/b/6a26ef2a669a12783a7dee00/ATTIaceeb7dc8ba850a2ae19ba7fb0b8a6a74AAA2F8C/proyecto-final-sanctuary) |
| Presentación     | *Pendiente*                                     |
| Repositorio GitHub | [https://github.com/kikesda-dev/puravida_animalsanctuary](https://github.com/kikesda-dev/puravida_animalsanctuary) |

---

## Miembros del equipo

- **Kike Sánchez de Amo** - *Desarrollador de Software Backend*
