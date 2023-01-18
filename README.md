# Course Work in the discipline "Development Of Server Parts Of Internet Resources" from RTU MIREA

This is a service part of a music streaming service written in Java using the Spring Boot framework. 

- Backend: Java + Spring Boot + Spring Security + Spring Data JPA + Flyway
- Database: PostgreSQL
---
The service can works with a local database on PostgreSQL and can also work with a database in Docker. By default, the service works on a Docker-containers together with the Database.

A Super Admin is created at system startup if it is not in the Database. If you don't have such a user in your Database, enter his data in the _.env_ file.

If you want to connect service to local Database you need to change settings in _application.yml_. All tables in a local DB will be created automatically by Flyway. The SQL-script for Flyway you can find in _src/main/resources/db/migration_.

Docker ports:
- backend: 8080
- database: 5433 (if PostgreSQL is not running on the host, set port 5432 to _docker-compose.yml_)