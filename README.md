# docker-image-downloader project

[![Build Status](https://travis-ci.org/KuuDS/docker-image-downloader.svg?branch=masterrs)](https://travis-ci.org/github/KuuDS/docker-image-downloader)

This project uses Quarkus, the Supersonic Subatomic Java Framework.

If you want to learn more about Quarkus, please visit its website: https://quarkus.io/ .

## Running the application in dev mode

You can run your application in dev mode that enables live coding using:
```
./mvnw quarkus:dev
```

## Packaging and running the application

The application can be packaged using `./mvnw package`.
It produces the `docker-image-downloader-1.0-SNAPSHOT-runner.jar` file in the `/target` directory.
Be aware that it’s not an _über-jar_ as the dependencies are copied into the `target/lib` directory.

The application is now runnable using `java -jar target/docker-image-downloader-1.0-SNAPSHOT-runner.jar`.

## Build as NATIVE execution

```bash
./mvnw clean package -Pnative
```

## Build as NATIVE container image

```bash
./mvnw clean package -Pnative,docker
```

# Docker Image Downloader

Index: `/index.html`.
Query: `/query.html`.

# Tips

Set environment argument `DOCKER_HOST` with `tcp://docker_host:2375`.

# Docker Example

```bash
#/usr/bin/env bash
docker kill docker_image_downloader
docker rm docker_image_downloader
mvn clean package docker:build
docker run -d -p 8080:8080 --name docker_image_downloader \
  -e JAVA_OPTS="-Xmx512m -Xmn128m -XX:+UseG1GC -XX:maxDirectMemorySize=512m"
  -e FETCH_WITH_HTTPS=false \
  -e DEFAULT_REGISTRY=register:5000 \
  -e REGISTRY_PREFIXES=/ \
  -e DOCKER_HOST=tcp://docker:2375 \
  docker-image-downloader:latest
```

## Use Docker-Compose

```bash
docker-compose up -d
```
