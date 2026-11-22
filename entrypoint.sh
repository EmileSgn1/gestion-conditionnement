#!/bin/sh
set -e

XML_PATH="/opt/jboss/wildfly/standalone/configuration/standalone.xml"
DB_HOST="${DB_HOST:-localhost}"
DB_PORT="${DB_PORT:-5432}"
DB_NAME="${DB_NAME:-gestion_conditionnement_db}"
DB_USER="${DB_USER:-postgres}"
DB_PASS="${DB_PASS:-postgres}"

if ! grep -q "gestion_conditionnement_db" "$XML_PATH"; then
  /opt/jboss/wildfly/bin/jboss-cli.sh << EOF
embed-server --std-out=echo
/subsystem=datasources/jdbc-driver=postgresql:add(driver-name=postgresql,driver-module-name=org.postgresql,driver-class-name=org.postgresql.Driver)
data-source add --name=gestion_conditionnement_db --jndi-name=java:jboss/datasources/gestion_conditionnement_db --driver-name=postgresql --connection-url="jdbc:postgresql://${DB_HOST}:${DB_PORT}/${DB_NAME}?sslmode=require" --user-name="${DB_USER}" --password="${DB_PASS}" --validate-on-match=true --background-validation=false
stop-embedded-server
EOF
fi

exec /opt/jboss/wildfly/bin/standalone.sh -b 0.0.0.0 -bmanagement 0.0.0.0