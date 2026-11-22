# Étape 1 : Compilation du WAR avec Maven (Java 17)
FROM maven:3.9-eclipse-temurin-17 AS build
WORKDIR /app
COPY pom.xml .
COPY src ./src
RUN mvn clean package -DskipTests

# Étape 2 : WildFly + module JDBC PostgreSQL + entrypoint
FROM quay.io/wildfly/wildfly:latest
ENV JAVA_OPTS="-Xms128m -Xmx320m -Djboss.bind.address=0.0.0.0 -Djboss.bind.address.management=0.0.0.0"

COPY --from=build /app/target/Gestion_conditionnement-1.0-SNAPSHOT.war /opt/jboss/wildfly/standalone/deployments/ROOT.war

# Installe le driver PostgreSQL comme module WildFly org.postgresql
USER root
RUN mkdir -p /opt/jboss/wildfly/modules/system/layers/base/org/postgresql/main && \
    unzip -j /opt/jboss/wildfly/standalone/deployments/ROOT.war "WEB-INF/lib/postgresql-*.jar" -d /opt/jboss/wildfly/modules/system/layers/base/org/postgresql/main/ && \
    PG_JAR=$(ls /opt/jboss/wildfly/modules/system/layers/base/org/postgresql/main/postgresql-*.jar | head -n 1 | xargs basename) && \
    printf '<?xml version="1.0" encoding="UTF-8"?>\n<module xmlns="urn:jboss:module:1.9" name="org.postgresql">\n  <resources>\n    <resource-root path="%s"/>\n  </resources>\n  <dependencies>\n    <module name="javax.api"/>\n    <module name="jakarta.enterprise.api"/>\n    <module name="jakarta.transaction.api"/>\n  </dependencies>\n</module>\n' "$PG_JAR" > /opt/jboss/wildfly/modules/system/layers/base/org/postgresql/main/module.xml && \
    chown -R jboss:jboss /opt/jboss/wildfly/modules/system/layers/base/org/postgresql

# Ajoute le script entrypoint.sh
COPY entrypoint.sh /opt/jboss/wildfly/bin/entrypoint.sh
RUN chmod +x /opt/jboss/wildfly/bin/entrypoint.sh && \
    chown jboss:jboss /opt/jboss/wildfly/bin/entrypoint.sh

USER jboss
EXPOSE 8080
CMD ["/opt/jboss/wildfly/bin/entrypoint.sh"]