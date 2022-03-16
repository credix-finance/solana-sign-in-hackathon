FROM ubuntu:22.04

ENV KEYCLOAK_VERSION 16.0.0

RUN apt update &&\
    apt install -y wget curl unzip hostname openssl tar openjdk-17-jdk-headless

RUN wget https://github.com/keycloak/keycloak/releases/download/${KEYCLOAK_VERSION}/keycloak-${KEYCLOAK_VERSION}.zip && \
    unzip keycloak-${KEYCLOAK_VERSION}.zip && \
    rm keycloak-${KEYCLOAK_VERSION}.zip

WORKDIR keycloak-${KEYCLOAK_VERSION}

# add admin user
RUN ./bin/add-user-keycloak.sh -u admin -p admin --realm master
# add default user
RUN ./bin/add-user-keycloak.sh -u FLwEU3qyKWJ7PbPphpXsStjmMHRcdHkif9weotiG2bqZ -p redundant --realm master
RUN ./bin/add-user-keycloak.sh -u 3U9LcJxit7XKcK8f8TX77EhamgvdhXdzu7U4351GPPKj -p redundant --realm master

COPY target/simple-authenticator.jar standalone/deployments
COPY export/master_realm.json /tmp/master_realm.json

EXPOSE 8080

ENTRYPOINT ["./bin/standalone.sh", "-b", "0.0.0.0", "-Dkeycloak.migration.action=import", "-Dkeycloak.migration.provider=singleFile", "-Dkeycloak.migration.realmName=Master-Realm", "-Dkeycloak.migration.file=/tmp/master_realm.json"]
