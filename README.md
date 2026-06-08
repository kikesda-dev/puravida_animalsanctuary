# Pura Vida Animal Sanctuary 🐾

API RESTful para la gestión de un santuario de animales de granja. Permite administrar animales rescatados, voluntarios, coordinadores, tareas de cuidado y donaciones, con autenticación JWT y control de acceso basado en roles.

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

### Enumeraciones
- `HealthStatus`: `HEALTHY`, `SICK`, `IN_TREATMENT`
- `TaskStatus`: `PENDING`, `IN_PROGRESS`, `COMPLETED`

---

## Configuración

### Requisitos previos
- **Java 25**
- **Maven 3.9+**
- **MySQL 8+**

### Clonar el repositorio
```bash
git clone https://github.com/kikesda-dev/puravida_animalsanctuary.git
cd sanctuary
```

### Configurar base de datos
Crea una base de datos MySQL llamada `santuario_db` (o se crea automáticamente):

```sql
CREATE DATABASE IF NOT EXISTS santuario_db;
```

Las credenciales por defecto en `application.properties`:
```
spring.datasource.url=jdbc:mysql://localhost:3306/santuario_db?createDatabaseIfNotExist=true&serverTimezone=UTC
spring.datasource.username=root
spring.datasource.password=root
```

### Ejecutar la aplicación
```bash
mvn spring-boot:run
```

La aplicación arranca en `http://localhost:8080`.

Al iniciar por primera vez, se crea automáticamente un **coordinador admin**:
- Email: `admin@puravidasanctuary.com`
- Password: `1234`

---

## Tecnologías utilizadas

- **Java 25** · Spring Boot · Spring Data JPA · Spring Security · Spring Web MVC · MySQL · JWT (auth0) · Lombok · Maven

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

### Uso de ejemplo (login + token)
```bash
# Login
curl -X POST http://localhost:8080/api/login \
  -H "Content-Type: application/json" \
  -d '{"email": "admin@puravidasanctuary.com", "password": "1234"}'

# Usar el token devuelto para rutas protegidas
curl http://localhost:8080/animals \
  -H "Authorization: Bearer <token>"
```

---

## Enlaces adicionales

| Recurso          | Enlace                                          |
|------------------|-------------------------------------------------|
| Trello           | *Pendiente*                                     |
| Presentación     | *Pendiente*                                     |
| Repositorio GitHub | [https://github.com/kikesda-dev/puravida_animalsanctuary](https://github.com/kikesda-dev/puravida_animalsanctuary) |

---

## Recursos

- [Spring Boot 4.0.6 Reference Guide](https://docs.spring.io/spring-boot/docs/4.0.6/reference/htmlsingle/)
- [Spring Security](https://docs.spring.io/spring-security/reference/)
- [Auth0 java-jWT](https://github.com/auth0/java-jwt)
- [MySQL Documentation](https://dev.mysql.com/doc/)

---

## Miembros del equipo

- **Kike Sánchez de Amo** - *Desarrollador de Software Backend*
