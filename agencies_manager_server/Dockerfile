FROM gradle:7.5.1-jdk18 AS build

WORKDIR /srv
COPY build.gradle.kts settings.gradle.kts ./
RUN gradle dependencies

COPY . .
RUN gradle build -x test

FROM openjdk:17-alpine

COPY --from=build /srv/build/libs/agencies-manager-server.jar app.jar

ENTRYPOINT ["java","-jar","app.jar"]