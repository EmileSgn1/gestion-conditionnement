# Étape 1 : Compilation du WAR avec Maven (Java 17)
FROM maven:3.9-eclipse-temurin-17 AS build
WORKDIR /app
COPY pom.xml .
COPY src ./src
RUN mvn clean package -DskipTests

# Étape 2 : WildFly + configuration automatique de la Datasource PostgreSQL
FROM quay.io/wildfly/wildfly:latest
ENV JAVA_OPTS="-Xms128m -Xmx320m -Djboss.bind.address=0.0.0.0 -Djboss.bind.address.management=0.0.0.0"

COPY --from=build /app/target/Gestion_conditionnement-1.0-SNAPSHOT.war /opt/jboss/wildfly/standalone/deployments/ROOT.war

# Ajoute le driver PostgreSQL et la datasource JNDI java:jboss/datasources/gestion_conditionnement_db dans standalone.xml
RUN /opt/jboss/wildfly/bin/jboss-cli.sh --connect --command="embed-server, \
  /subsystem=datasources/jdbc-driver=postgresql:add(driver-name=postgresql,driver-module-name=org.postgresql,driver-class-name=org.postgresql.Driver), \
  data-source add --name=gestion_conditionnement_db --jndi-name=java:jboss/datasources/gestion_conditionnement_db --driver-name=postgresql --connection-url=jdbc:postgresql://${env.DB_HOST}:${env.DB_PORT}/${env.DB_NAME}?sslmode=require --user-name=${env.DB_USER} --password=${env.DB_PASS} --validate-on-match=true --background-validation=false, \
  stop-embedded-server"

EXPOSE 8080
CMD ["/opt/jboss/wildfly/bin/standalone.sh", "-b", "0.0.0.0", "-bmanagement", "0.0.0.0"]