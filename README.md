# SISTEMA DE HOTELES - INTEGRADOR 

## Tecnologías

- SpringBoot
- Java
- Html - CSS - JavaScript
- AWS - Base de datos - MySQL

## Clonación y despliegue de aplicación

Clonación de repositorio

```git
git clone https://github.com/andrebm99/sistema_hoteles_integrador.git
```

En la ruta `src/resources/` debemos crear el archivo de configuración de la base de datos.
Añadir el archivo de configuración

```application.properties
spring.application.name=springboot-sistemahoteles
server.port=8081

logging.file.name=logs/app.log
logging.logback.rollingpolicy.max-file-size=20KB

spring.datasource.driver-class-name=com.mysql.cj.jdbc.Driver
# spring.datasource.url=jdbc:mysql://root:JbjKHRZAwCuWfluVYVtqbtTljXNeljaZ@metro.proxy.rlwy.net:55790/railway
spring.datasource.url=jdbc:mysql://db-portafolio.c90o6igcwv61.us-east-2.rds.amazonaws.com:3306/integrador?useSSL=false&serverTimezone=UTC
spring.datasource.username=ronal
spring.datasource.password=Hack4u1615;$!

spring.jpa.hibernate.ddl-auto=create
spring.jpa.show-sql=true
spring.jpa.properties.hibernate.format_sql=true

spring.mail.host=smtp.gmail.com
spring.mail.port=587
spring.mail.username=jcervanteslivon@gmail.com
spring.mail.password=iwwgsgntqbvlxmfq
spring.mail.properties.mail.smtp.auth=true
spring.mail.properties.mail.smtp.starttls.enable=true
```

Por ultimo ejecutar el archivo en la ruta `src/main/java/com/springboot/sistema/hoteles/springboot_sistemahoteles/IntegradorspringbootApplication.java`