# Étape 1 : Compilation du projet Maven et génération du .war
FROM maven:3.9-eclipse-temurin-21 AS build
WORKDIR /app
COPY pom.xml .
COPY src ./src
RUN mvn clean package -DskipTests

# Étape 2 : Déploiement sur WildFly
FROM quay.io/wildfly/wildfly:latest
ENV JAVA_OPTS="-Xms128m -Xmx320m -Djboss.bind.address=0.0.0.0 -Djboss.bind.address.management=0.0.0.0"

# Copie le WAR produit dans target/ vers le dossier de déploiement WildFly
COPY --from=build /app/target/*.war /opt/jboss/wildfly/standalone/deployments/

EXPOSE 8080
CMD ["/opt/jboss/wildfly/bin/standalone.sh", "-b", "0.0.0.0", "-bmanagement", "0.0.0.0"]
