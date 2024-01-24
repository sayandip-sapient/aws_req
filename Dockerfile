FROM jenkins/jenkins:lts

ARG JENKINS_ADMIN_ID
ARG JENKINS_ADMIN_PASSWORD

ENV JENKINS_ADMIN_ID=${JENKINS_ADMIN_ID}
ENV JENKINS_ADMIN_PASSWORD=${JENKINS_ADMIN_PASSWORD}

# Install plugins
COPY plugins.txt /usr/share/jenkins/ref/plugins.txt
RUN jenkins-plugin-cli --plugin-file /usr/share/jenkins/ref/plugins.txt

# Install vim
USER root
# RUN apt-get update && apt-get install -y vim
RUN apt-get update && \
    apt-get install -y vim && \
    apt-get install -y openjdk-17-jdk && \
    apt-get install -y maven && \
    rm -rf /var/lib/apt/lists/*
    
USER jenkins

COPY security.groovy /usr/share/jenkins/ref/init.groovy.d/security.groovy
COPY seed-job.groovy /usr/share/jenkins/ref/init.groovy.d/seed-job.groovy

