# Solana Sign In Keycloak

# Usage

Build Keycloak custom authenticator jar.

```sh
mvn clean install
```

Build and run Keycloak. Admin dashboard will be available on port http://127.0.0.1:9999 with username and password `admin`.

```sh
docker build . -t cloak
docker run -v -p 9999:8080 cloak
```

To dynamically add a user first get the <container_id> with `docker ps`.

```sh
docker exec <container_id> sh -c "./bin/add-user-keycloak.sh -u <public_key> -p redundant --realm master"
```

# Debugging

In order to dynamically reload the custom authenticator into Keycloak.

```sh
docker cp ./target/simple-authenticator.jar <docker_contianer_id>:/keycloak-16.0.0/standalone/deployments/simple-authenticator.jar
```
