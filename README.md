# RESTApp

Этот проект представляет собой RESTful веб-приложение, которое управляет и предоставляет API для колледжей, студентов и предметов. Он включает сервлеты для обработки HTTP-запросов, связанных с колледжами, студентами и предметами. Сервлеты взаимодействуют с базой данных PostgreSQL с помощью JDBC и предоставляют JSON-ответы.

## Сервлеты
### CollegeServlet
- Обрабатывает HTTP-запросы, связанные с колледжами.
- Поддерживает операции, такие как создание колледжей, добавление предметов в колледжи, обновление названий и рейтингов колледжей, получение списка всех колледжей и получение колледжа по его имени.

### StudentServlet
- Управляет HTTP-запросами, связанными со студентами.
- Поддерживает операции, такие как создание студентов, обновление информации о студенте, получение студентов по имени и удаление студентов.

### SubjectServlet
- Управляет HTTP-запросами, связанными с предметами.
- Поддерживает операции, такие как создание предметов, обновление названий предметов, получение списка всех предметов и удаление предметов.

## Настройка базы данных
Проект использует базу данных PostgreSQL, а предоставленный файл `docker-compose.yml` настраивает базу данных в контейнере Docker. Он создает экземпляр PostgreSQL с необходимыми конфигурациями окружения и инициализирует базу данных схемой и данными из папки `/src/main/resources`.

## Зависимости
Проект имеет следующие зависимости:
- javax.servlet:javax.servlet-api:4.0.1
- org.postgresql:postgresql:42.7.2
- org.jupiter:junit-jupiter-api:5.9.2
- org.junit.jupiter:junitupiter-engine:5.9.2
- org.testcontainers:postgresql:1.19.3
- org.projectlombok:lombok:1.18.30
- org.mockito:ito-junit-jupiter:5.11.0
- org.mockito:mockito-core:5..0
- com.google.code.gson:gson:2.10.1
- com.fasterxml.jackson.core:jackson-databind:2.16.1