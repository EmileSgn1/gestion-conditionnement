# Étape 1 : Compilation du WAR avec Maven (Java 17)
FROM maven:3.9-eclipse-temurin-17 AS build
WORKDIR /app
COPY pom.xml .
COPY src ./src
RUN mvn clean package -DskipTests

# Étape 2 : WildFly + module JDBC PostgreSQL + Datasource JNDI au démarrage
FROM quay.io/wildfly/wildfly:latest
ENV JAVA_OPTS="-Xms128m -Xmx320m -Djboss.bind.address=0.0.0.0 -Djboss.bind.address.management=0.0.0.0"

COPY --from=build /app/target/Gestion_conditionnement-1.0-SNAPSHOT.war /opt/jboss/wildfly/standalone/deployments/ROOT.war

# Copie le driver PostgreSQL depuis le WAR vers un module WildFly org.postgresql
USER root
RUN mkdir -p /opt/jboss/wildfly/modules/system/layers/base/org/postgresql/main && \
    unzip -j /opt/jboss/wildfly/standalone/deployments/ROOT.war "WEB-INF/lib/postgresql-*.jar" -d /opt/jboss/wildfly/modules/system/layers/base/org/postgresql/main/ && \
    PG_JAR=$(ls /opt/jboss/wildfly/modules/system/layers/base/org/postgresql/main/postgresql-*.jar | head -n 1 | xargs basename) && \
    printf '<?xml version="1.0" encoding="UTF-8"?>\n<module xmlns="urn:jboss:module:1.9" name="org.postgresql">\n  <resources>\n    <resource-root path="%s"/>\n  </resources>\n  <dependencies>\n    <module name="javax.api"/>\n    <module name="jakarta.enterprise.api"/>\n    <module name="jakarta.transaction.api"/>\n  </dependencies>\n</module>\n' "$PG_JAR" > /opt/jboss/wildfly/modules/system/layers/base/org/postgresql/main/module.xml && \
    chown -R jboss:jboss /opt/jboss/wildfly/modules/system/layers/base/org/postgresql

USER jboss

EXPOSE 8080

CMD ["/bin/sh", "-c", "\
  /opt/jboss/wildfly/bin/standalone.sh -b 0.0.0.0 -bmanagement 0.0.0.0 & \
  WF_PID=$!; \
  until /opt/jboss/wildfly/bin/jboss-cli.sh --connect --command=':read-attribute(name=server-state)' 2>/dev/null | grep -q 'running'; do sleep 2; done; \
  /opt/jboss/wildfly/bin/jboss-cli.sh --connect --command=\"/subsystem=datasources/jdbc-driver=postgresql:add(driver-name=postgresql,driver-module-name=org.postgresql,driver-class-name=org.postgresql.Driver)\" 2>/dev/null || true; \
  /opt/jboss/wildfly/bin/jboss-cli.sh --connect --command=\"data-source add --name=gestion_conditionnement_db --jndi-name=java:jboss/datasources/gestion_conditionnement_db --driver-name=postgresql --connection-url=jdbc:postgresql://${DB_HOST}:${DB_PORT}/${DB_NAME}?sslmode=require --user-name=${DB_USER} --password=${DB_PASS} --validate-on-match=true --background-validation=false\" 2>/dev/null || true; \
  wait $WF_PID"]