FROM ubuntu:22.04

WORKDIR backend

ENV KEYCLOAK_VERSION 16.0.0

RUN apt update &&\
    apt install -y wget curl unzip hostname openssl tar openjdk-17-jdk-headless

COPY . .

EXPOSE 8080

ENTRYPOINT ["./mvnw"]
CMD ["mn:run"]
