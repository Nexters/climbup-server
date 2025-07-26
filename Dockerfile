FROM eclipse-temurin:17-jre-jammy
COPY ./build/libs/*SNAPSHOT.jar project.jar
EXPOSE 9090
ENTRYPOINT ["java", "-jar", "project.jar"]