FROM openjdk:17.0.2

RUN mkdir -p /back_info
RUN touch /back_info/musspring_backend.log
RUN chmod 777 /back_info/musspring_backend.log

COPY "./build/libs/MusSpring-backend-0.0.1-SNAPSHOT.jar" "musspring.jar"
ENTRYPOINT ["java", "-jar", "musspring.jar"]