# build with maven
FROM maven
COPY ./backend /backend
WORKDIR /backend
RUN mvn clean package -DskipTests

# start jar file
FROM adoptopenjdk/openjdk11:jre-11.0.10_9
EXPOSE 8000
COPY --from=0 /backend/target/*.jar .
ENTRYPOINT ["java","-jar","/backend-1.0-SNAPSHOT.jar"]

