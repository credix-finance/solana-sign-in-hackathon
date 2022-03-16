# Solana Sign In BFF

## Setup

In order to use GraalVM, install following components.

```sh
sdk install java 22.0.0.2.r17-grl
gu install native-image
sdk use java 22.0.0.2.r17-grl
./mvnw package -Dpackaging=native-image
```

## Usage

```sh
./mvnw mn:run
```
