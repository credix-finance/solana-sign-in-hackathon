# Solana Sign In

## Usage

### Keycloak

Go to the `solana-sign-in-keycloak` directory and build/run the docker container.

```sh
docker build . -t keycloak
docker run -p 8080:8080 keycloak
```

Unfortunately it's not possible to export secrets with Keycloak, so it's still needed to regenerate this before continuing.

Go to `http://127.0.0.1:8080` 
 - click on `Administration Console`
 - click on `Clients`
 - click on `micronaut`
 - click on `credentials`
 - click on `regenerate secret`

Copy this secret in the `solana-sign-in-backend` and `solana-sign-in-bff` folder in the `application.yml` file.

### Backend

Go to the `solana-sign-in-backend` directory and run the backend.

```sh
./mvnw mn:run
```

### BFF (Backend For Frontend)

Go to the `solana-sign-in-bff` directory and run the bff.

```sh
./mvnw mn:run
```

### Frontend

Go to the `solana-sign-in-frontend` directory and run the frontend.

```sh
yarn dev
```
