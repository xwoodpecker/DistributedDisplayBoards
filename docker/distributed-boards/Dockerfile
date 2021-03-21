# frontend build
FROM node:lts-alpine as frontend-build-stage
WORKDIR /frontend
COPY ./board_frontend /frontend
RUN npm install && npm run build

# backend build
FROM maven as backend-build-stage
WORKDIR /backend
COPY ./backend /backend
COPY --from=frontend-build-stage /frontend/dist /backend/src/main/resources/public
RUN mvn clean package -DskipTests

# container build
FROM adoptopenjdk/openjdk11:jre-11.0.10_9 as production-stage
EXPOSE 80
COPY --from=backend-build-stage /backend/target/*.jar .
ENTRYPOINT ["java","-jar","/backend-1.0-SNAPSHOT.jar"]