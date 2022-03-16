# Solana Sign In

## Usage

Currently we hardcoded 2 random keys in keycloak for convenience. Adding more users is possible through the Keycloak UI.

- `FLwEU3qyKWJ7PbPphpXsStjmMHRcdHkif9weotiG2bqZ`

```
[12,242,40,222,230,250,211,14,107,17,7,11,94,103,39,56,59,71,203,19,182,203,209,100,87,45,174,58,15,150,245,86,213,30,249,13,111,180,15,38,55,214,7,12,93,66,138,28,193,198,140,131,165,120,150,192,221,250,208,74,82,147,65,64]
```

- `3U9LcJxit7XKcK8f8TX77EhamgvdhXdzu7U4351GPPKj`

```
[154,221,227,7,92,218,180,53,240,100,122,238,174,98,85,214,166,144,202,3,65,26,199,174,17,145,234,6,83,205,170,180,36,171,123,205,15,88,227,30,168,35,238,15,8,243,8,165,100,157,135,221,137,160,63,17,23,94,75,208,41,131,255,182]
```

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
This backend will be a small example on how to for example make a one-to-many relation between public keys and users.

```sh
./mvnw mn:run
```

### BFF (Backend For Frontend)

Go to the `solana-sign-in-bff` directory and run the bff. This application will act as an intermediary and manage login etc.

```sh
./mvnw mn:run
```

### Frontend

Go to the `solana-sign-in-frontend` directory and run the frontend.

```sh
yarn dev
```
