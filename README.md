# The Development Team

This is an application for a fictional team of developers.  
Used knowledge of **Spring Boot**, **Spring Security**, **Spring Data**, and **Thymeleaf**. 
Also used unit and integration tests to ensure proper functionality of the code and to create a maintainable code base that can be extended later.

[![License: GPL v3][shield-gplv3]](LICENSE)
[![Java v17][shield-java]](https://openjdk.java.net/projects/jdk/17/)
[![Spring Boot v3][shield-spring-boot]](https://spring.io/projects/spring-boot)
[![Spring Security v6][shield-spring-security]](https://spring.io/projects/spring-security)
[![Thymeleaf v3][shield-thymeleaf]](https://www.thymeleaf.org/doc/tutorials/3.1/extendingthymeleaf.html)


## Table of Contents
- [Used technologies](#used-technologies)
- [Requirements](#requirements)
- [Building](#building)
- [Frontend Development](#frontend-development)
  - [Watching for Changes](#watching-for-changes)
  - [Serving with BrowserSync](#serving-with-browsersync)
- [Configuration](#configuration)
- [Database](#database)
  - [Flyway](#flyway)
  - [Testcontainers](#testcontainers)
  - [Generate random users](#generate-random-users)
- [Docker](#docker)
- [Security](#security)
  - [Authentication](#authentication)
  - [Authorization](#authorization)
- [Internationalization](#internationalization)
- [Running](#running)
- [Testing](#testing)
- [License](#license)
- [Author](#author)
- [Contributing](#contributing)
## Used technologies
* Java 17
* Gradle 8.8
* Spring Boot 3
* Spring Security 6
* Thymeleaf 3
* Tailwindcss 3.2
* Alpinejs 3.14
* JUnit 5
* Testcontainers 1.20
* Datafaker 2.3

## Requirements
The list of tools required to build and run the project:

* Open JDK 17
* npm 10.8.1
* Node 21.2.0

## Building
To build the project, run the following command:
```sh
./gradlew build
```

## Frontend Development
Building and copying HTML, JavaScript, CSS and SVG files:
```sh
npm run build:html
npm run build:css
npm run build:js
npm run build:svg
```
### Watching for Changes
To watch for changes in the HTML, JavaScript, CSS and SVG files, use the following command:
```sh
npm run watch
```
### Serving with BrowserSync
To serve the application with **BrowserSync**, use the following command:
```sh
npm run watch:serve
```

## Configuration
This configures npm to have 2 main tasks:
* `build` - builds the HTML, Javascript, and CSS and copies it to the build directory.
* `watch` - watches for changes in the HTML, JavaScript and CSS files and rebuilds them when they change.

Configuration in `package.json`:
```plaintext
"scripts": {
  "build": "npm-run-all --parallel build:*",
  "build:html": "recursive-copy \"src/main/resources/templates\" build/resources/main/templates -w",
  "build:css": "mkdirp build/resources/main/static/css && postcss src/main/resources/static/css/*.css -d build/resources/main/static/css",
  "build:js": "path-exists src/main/resources/static/js && (mkdirp build/resources/main/static/js && babel src/main/resources/static/js/ --out-dir build/resources/main/static/js/) || echo \"No 'src/main/resources/static/js' directory found.\"",
  "build:svg": "path-exists src/main/resources/static/svg && recursive-copy \"src/main/resources/static/svg\" build/resources/main/static/svg -w -f \"**/*.svg\" || echo \"No 'src/main/resources/static/svg' directory found.\"",
  "watch": "npm-run-all --parallel watch:*",
  "watch:html": "onchange \"src/main/resources/templates/**/*.html\" -- npm-run-all --serial build:css build:html",
  "watch:css": "onchange \"src/main/resources/static/css/**/*.css\" -- npm run build:css",
  "watch:js": "onchange \"src/main/resources/static/js/**/*.js\" -- npm run build:js",
  "watch:svg": "onchange \"src/main/resources/static/svg/**/*.svg\" -- npm run build:svg",
  "watch:serve": "browser-sync start --no-inject-changes --proxy localhost:8080 --files \"build/resources/main/templates\" \"build/resources/main/static\""
}
```

## Database
The project uses **PostgreSQL** database.

The database configuration is stored in the [application-local.properties](src/main/resources/application-local.properties) file:
```properties
spring.datasource.url=jdbc:postgresql://localhost/xxxxxx
spring.datasource.driver-class-name=org.postgresql.Driver
spring.datasource.username=xxxxxx
spring.datasource.password=xxxxxx
spring.jpa.hibernate.ddl-auto=validate
spring.jpa.open-in-view=false
```
### Flyway
Used **Flyway** to manage database migrations.  
Folder [db/migration](src/main/resources/db/migration) contains SQL scripts for creating tables and inserting data.

### Testcontainers
Used **Testcontainers** to run **PostgreSQL** database in a **Docker** container for testing purposes.  
Use a special JDBC URL which will instruct **Testcontainers** to start a **Docker** image with **PostgreSQL**.  
By activating the `data-jpa-test` profile, the test will read the [application-data-jpa-test.properties](src/test/resources/application-data-jpa-test.properties) file,
that has the needed properties to make it all work:
```properties
spring.datasource.url=jdbc:tc:postgresql:16:///xxxxxx?TC_TMPFS=/testtmpfs:rw
spring.datasource.driver-class-name=org.testcontainers.jdbc.ContainerDatabaseDriver
spring.datasource.username=xxxxxx
spring.datasource.password=xxxxxx
spring.jpa.database-platform=org.hibernate.dialect.PostgreSQLDialect
spring.jpa.hibernate.ddl-auto=validate
logging.level.org.hibernate.SQL=DEBUG
spring.jpa.properties.hibernate.show_sql=false
```
### Generate random users
To generate random users(names, birthdays, email addresses, ...), use the `init-db` profile.  
[DataInitializer](src/main/java/com/vasylenko/application/util/DataInitializer.java) class is used to populate the database with random users.

## Docker
To quickly spin up a PostgreSQL database, we can use [docker-compose.yaml](docker-compose.yaml) file:
```yaml
version: '3.9'
services:
  db:
    image: 'postgres:16'
    shm_size: 128mb
    ports:
    - "5432:5432"
    environment:
      POSTGRES_DB: ${POSTGRES_DATABASE}
      POSTGRES_USER: ${POSTGRES_USER}
      POSTGRES_PASSWORD: ${POSTGRES_PASSWORD}
```
All credentials are stored in the [.env](.env) file:
```properties
POSTGRES_DATABASE=xxxxxx
POSTGRES_USER=xxxxxx
POSTGRES_PASSWORD=xxxxxxx
```
To start the database, use the following command:
```sh
docker-compose up -d
```

## Security
The project uses **Spring Security** to secure the application.  
The [SecurityConfig](src/main/java/com/vasylenko/application/config/WebSecurityConfiguration.java) class is used to configure the security settings.
Password encoding is done using **BCrypt**.

### Authentication
BCrypt is used to encode passwords, it is recommended to change one of them to a known one:
```plaintext
|                       BCrypted password                      | Encoded password |
| $2a$12$EFqhlkknWg1l5XvGVYa8ROtShpD8nhCXQNqyQgEWAJaNZfb.v9Ppq |     Passw0rd     |
```

### Authorization
The project uses **Role-based authorization**.
The [UserRole](src/main/java/com/vasylenko/application/model/user/UserRole.java) class is used to define the roles.

## Internationalization
**English** is the default language and used [messages.properties](src/main/resources/i18n/messages.properties) file.  
**Spanish** is the secondary language and used [messages_es.properties](src/main/resources/i18n/messages_es.properties) file.  
Use cookie or add `/?lang=es` to the URL to switch to Spanish.

## Running
To run the project, use the following command:
```sh
# build the project
./gradlew build

# build and copy HTML, JavaScript, CSS and SVG files
npm run build

# run the project
./gradlew bootRun
```

## Testing
To run the tests, use the following command:
```sh
./gradlew test
```

## License
This project is licensed under the terms of the GPL v3 license - see the [LICENSE](LICENSE) file for details.

## Contributing
Pull requests are welcome.  
For major changes, please open an issue first to discuss what you would like to change.
### How to Contribute

1. **Fork the Repository**  
   Fork the repository by clicking the "**Fork**" button on the top right of the repository page.  This will create a copy of the repository under your GitHub account.
2. **Clone your fork**  
   Clone your forked repository to your local machine using the following command:
   ```sh
   git clone https://github.com/your-username/your-forked-repo.git
   cd your-forked-repo
    ```
3. **Create a new branch**  
   Create a new branch for your feature or bugfix:
   ```sh
   git checkout -b feature-or-bugfix-name
   ```
4. **Make your changes**  
   Make your changes to the code base. 
5. **Commit your changes**  
   Commit your changes with a descriptive commit message.
    ```sh
    git add .
    git commit -m "Your commit message"
    ```
6. **Push to your fork**  
    Push your changes to your forked repository:
    ```sh
    git push origin feature-or-bugfix-name
    ```
7. **Create a Pull Request**  
   Go to the original repository and create a pull request from your forked repository.  
   Provide a clear description of your changes and why they are necessary.
8. **Review Process**  
    Wait for the maintainers to review your pull request. Make changes if necessary.
9. **Merge**  
    Once your pull request is approved and all checks pass, it will be merged into the original repository.
## Author
Copyright &copy; 2024, Dmytro Vasylenko

[![][gravatar-dmytrovsl]]()

[shield-gplv3]: https://img.shields.io/badge/License-GPLv3-blue.svg
[shield-java]: https://img.shields.io/badge/Java-17-blue.svg
[shield-spring-boot]: https://img.shields.io/badge/Spring%20Boot-3-blue.svg
[shield-spring-security]: https://img.shields.io/badge/Spring%20Secyrity-6-blue.svg
[shield-thymeleaf]: https://img.shields.io/badge/Thymeleaf-3-blue.svg
[gravatar-dmytrovsl]: https://gravatar.com/avatar/b1aa9a589c80f793d84da34f321b9bf0