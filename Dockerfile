FROM openjdk:20

RUN mkdir -p /back_info
RUN touch /back_info/musspring_backend.log
RUN chmod 777 /back_info/musspring_backend.log

RUN mkdir -p /storage
RUN chmod 777 /storage

COPY "./build/libs/MusSpring-backend-0.0.1-SNAPSHOT.jar" "musspring.jar"
ENTRYPOINT ["java", "-jar", "musspring.jar"]