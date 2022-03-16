# Solana Sign In Backend

## Setup

In order to use GraalVM, install following components.

```sh
sdk install java 22.0.0.2.r17-grl
gu install native-image
sdk use java 22.0.0.2.r17-grl
./mvnw package -Dpackaging=native-image
```

### Keycloak

```sh
docker run --platform=linux/arm64 -p 8080:8080 -e KEYCLOAK_USER=admin -e KEYCLOAK_PASSWORD=admin sleighzy/keycloak
```

## Usage

```sh
./mvnw mn:run
```

The different possible API calls are also present in our Postman collection.

Login first with the public key and signed message.

```sh
curl --location --request POST 'http://127.0.0.1:8080/login' \
--header 'Content-Type: application/json' \
--data-raw '{
  "publicKey": "Ej5zJzej7rrUoDngsJ3jcpfuvfVyWpcDcK7uv9cE2LdL",
  "signature": "b6jdc1Sxk1iZoxYwUikFpNqKaKRtrrFYarGQRWa4K9GVSyTbXipwhLALsCJGdWCf7iuM1wWVqaQY4cVu8Bq2nW2"
}'
```

An example of getting information.

```sh
curl --location --request GET 'http://127.0.0.1:8080/api/users/me'
```

Adding a new public key.

```sh
curl --location --request POST 'http://127.0.0.1:8080/api/publickeys' \
--header 'Authorization: Bearer <token>' \
--header 'Content-Type: application/json' \
--data-raw '{
    "publicKey": "GbUNtQUt9WzjexoVxWuiJ8nt9HcAvRNqdxjvSdUPJRP7"
}'
```

## Testing

```sh
mvn test
```

## Linting

```sh
./mvnw spotless:check
./mvnw spotless:apply
```
