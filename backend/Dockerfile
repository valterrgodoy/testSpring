FROM openjdk:16

ARG PROFILE
ARG ADDITIONAL_OPTS

ENV PROFILE=${PROFILE}
ENV ADDITIONAL_OPTS=${ADDITIONAL_OPTS}

WORKDIR /opt/app

COPY /target/testSpring*.jar testSpring.jar

SHELL ["/bin/sh", "-c"]

EXPOSE 5005
EXPOSE 80

cmd java ${ADDITIONAL_OPTS} -jar testSpring.jar --spring.profiles.active=${PROFILE}