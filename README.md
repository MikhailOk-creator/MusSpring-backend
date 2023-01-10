# Course Work in the discipline "Development Of Server Parts Of Internet Resources" from RTU MIREA

This is a service part of a music streaming service written in Java using the Spring Boot framework. 

- Backend: Java + Spring Boot + Spring Security + Spring Data JPA
- Database: PostgreSQL
---
The service can works with a local database on PostgreSQL and can also work with a database in Docker (being developed).

A Super Admin is created at system startup if it is not in the Database. If you don't have such a user in your Database, enter his data in the _.env_ file.

If you want to connect service to local Database you need to change settings in _application.yml_

Docker ports:
- backend: 80
- database: 5433 (if PostgreSQL is not running on the host, set port 5432 to _docker-compose.yml_)