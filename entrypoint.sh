#!/bin/sh
set -e

XML_PATH="/opt/jboss/wildfly/standalone/configuration/standalone.xml"
DB_HOST="${DB_HOST:-}"
DB_PORT="${DB_PORT:-5432}"
DB_NAME="${DB_NAME:-gestion_conditionnement_db}"
DB_USER="${DB_USER:-}"
DB_PASS="${DB_PASS:-}"

if ! grep -q "gestion_conditionnement_db" "$XML_PATH"; then
  DRIVER_FILE=$(mktemp)
  DS_FILE=$(mktemp)

  cat << EOF > "$DRIVER_FILE"
<driver name="postgresql" module="org.postgresql">
  <driver-class>org.postgresql.Driver</driver-class>
</driver>
EOF

  cat << EOF > "$DS_FILE"
<datasource jndi-name="java:jboss/datasources/gestion_conditionnement_db" pool-name="gestion_conditionnement_db_pool" enabled="true" use-java-context="true">
  <connection-url>jdbc:postgresql://${DB_HOST}:${DB_PORT}/${DB_NAME}?sslmode=require</connection-url>
  <driver>postgresql</driver>
  <security>
    <user-name>${DB_USER}</user-name>
    <password>${DB_PASS}</password>
  </security>
  <validation>
    <valid-connection-checker class-name="org.jboss.jca.adapters.jdbc.extensions.postgres.PostgreSQLValidConnectionChecker"/>
    <validate-on-match>true</validate-on-match>
    <background-validation>false</background-validation>
  </validation>
</datasource>
EOF

  awk -v dfile="$DRIVER_FILE" '
    /<drivers>/ && !d_done {
      print
      while ((getline line < dfile) > 0) print line
      close(dfile)
      d_done = 1
      next
    }
    { print }
  ' "$XML_PATH" > "${XML_PATH}.tmp1" && mv "${XML_PATH}.tmp1" "$XML_PATH"

  awk -v sfile="$DS_FILE" '
    /<datasources>/ && !s_done {
      print
      while ((getline line < sfile) > 0) print line
      close(sfile)
      s_done = 1
      next
    }
    { print }
  ' "$XML_PATH" > "${XML_PATH}.tmp2" && mv "${XML_PATH}.tmp2" "$XML_PATH"

  rm -f "$DRIVER_FILE" "$DS_FILE"
fi

exec /opt/jboss/wildfly/bin/standalone.sh -b 0.0.0.0 -bmanagement 0.0.0.0