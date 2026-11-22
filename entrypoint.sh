
#!/bin/sh
set -e

XML_PATH="/opt/jboss/wildfly/standalone/configuration/standalone.xml"

if ! grep -q "gestion_conditionnement_db" "$XML_PATH"; then
  python3 - "$XML_PATH" << 'EOF'
import os, re, sys

path = sys.argv[1]
with open(path, "r", encoding="utf-8") as f:
    xml = f.read()

host = os.environ.get("DB_HOST", "")
port = os.environ.get("DB_PORT", "5432")
db = os.environ.get("DB_NAME", "gestion_conditionnement_db")
user = os.environ.get("DB_USER", "")
password = os.environ.get("DB_PASS", "")

driver_xml = """
<driver name="postgresql" module="org.postgresql">
  <driver-class>org.postgresql.Driver</driver-class>
</driver>"""

ds_xml = f"""
<datasource jndi-name="java:jboss/datasources/gestion_conditionnement_db" pool-name="gestion_conditionnement_db_pool" enabled="true" use-java-context="true">
  <connection-url>jdbc:postgresql://{host}:{port}/{db}?sslmode=require</connection-url>
  <driver>postgresql</driver>
  <security>
    <user-name>{user}</user-name>
    <password>{password}</password>
  </security>
  <validation>
    <valid-connection-checker class-name="org.jboss.jca.adapters.jdbc.extensions.postgres.PostgreSQLValidConnectionChecker"/>
    <validate-on-match>true</validate-on-match>
    <background-validation>false</background-validation>
  </validation>
</datasource>"""

xml = re.sub(r"(<drivers>)", r"\1" + driver_xml, xml, count=1)
xml = re.sub(r"(<datasources>)", r"\1" + ds_xml, xml, count=1)

with open(path, "w", encoding="utf-8") as f:
    f.write(xml)
EOF
fi

exec /opt/jboss/wildfly/bin/standalone.sh -b 0.0.0.0 -bmanagement 0.0.0.0