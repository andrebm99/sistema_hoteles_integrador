# SISTEMA DE HOTELES - INTEGRADOR 

## Descripción:

En este proyecto Aurum Hotel, es una aplicación web para la gestión y reservas de un pequeño hotel, en este proyecto implementé:
- Reservas de habitaciones validando disponibilidad por fecha y hora.
- Administración de reservas por el administrador.
- Rutas protegidas por tipo de usuario.
- Encriptación de contraseñas con Bcrypt
- Formulario para:
    - Reservas de habitaciones
    - Nueva habitación
    - Nuevo trabajador del hotel
    - Subir link de PowerBI

## Tecnologías

- SpringBoot 3.5.8: Web, Data JPA, Security, Thymeleaf, Mail (Backend)
- Java 21
- Html - CSS - JavaScript (FrontEnd)
- AWS - Base de datos - MySQL
- Bcrypt
- Maven
- Boostrap

## Clonación y despliegue de aplicación

### Clonación de repositorio

```git
git clone https://github.com/andrebm99/sistema_hoteles_integrador.git
```

En la ruta `src/resources/` se debe crear el archivo de configuración `application.properties`.

### Ejecución del programa:

Debemos ejecutar el archivo en la ruta `src/main/java/com/springboot/sistema/hoteles/springboot_sistemahoteles/MainSistemaHoteles.java`