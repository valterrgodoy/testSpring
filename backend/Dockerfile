FROM openjdk:11

ARG PROFILE
ARG ADDITIONAL_OPTS

ENV PROFILE=${PROFILE}
ENV ADDITIONAL_OPTS=${ADDITIONAL_OPTS}

WORKDIR /opt/app

COPY testSpring-0.0.1-SNAPSHOT.jar testSpring.jar

SHELL ["/bin/sh", "-c"]

EXPOSE 5005
EXPOSE 8080

cmd java ${ADDITIONAL_OPTS} -jar testSpring.jar --spring.profiles.active=${PROFILE}