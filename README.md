# testcontainers-examples

## Intro
Showcasing different examples with [testcontainers](https://testcontainers.com/)

This repository functions as a collection of examples using testcontainers. Tests are written with [Kotest](https://kotest.io/).

> This repository is not meant to be run as an actual application

## Examples

### SFTP

`SSHServerContainer`: A `testcontainer` based off [`linuxserver/openssh-server`](https://docs.linuxserver.io/images/docker-openssh-server/)

This container is used in conjunction with `SSHKeyGenerator`, which will generate an RSA keypair in a temp directory.

As showcased in `SftpSessionFactoryIntegrationTest`, the keypair is generated, then the SFTP container is created, with the public key path provided as a parameter before starting the container.

The test then showcases how to build an SFTP session factory and make a real SFTP call into the running container.

### Cockroach

`CockroachDbContainer`: A `testcontainer` based off the official Cockroach image. Notice that it does not automatically start - this is to allow the test utilizing the `testcontainer` to control when to start it.

I have provided two different examples showcasing `testcontainers` with Cockroach: a JPA-centered approach, and a more manual `PGSimpleDataSource` approach.

The `CockroachDbContainer` is utilized in both integration tests, but with the JPA test, it uses Spring to configure the data source and other beans. The other integration test constructs the data source manually with the container details.

As showcased in `BookRepositoryIntegrationTest`, I utilize a base specification, `JpaDatabaseSpec`, which handles configuring the application context and beans for the repository test. It then demonstrates saving a simple entity and querying for it to prove the `testcontainer` Cockroach database container.

As showcased in `PGSimpleDataSourceIntegrationTest`, the data source is constructed with the details of the container, and verifies the data source connection is valid.
