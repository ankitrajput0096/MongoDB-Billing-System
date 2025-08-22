#!/bin/bash
# Script: create-admin-user.sh
# Description: Creates the initial admin user using localhost exception

echo "Creating initial admin user..."
echo "==============================="

# Connect to mongos1 using localhost exception (no authentication)
docker exec -it mongos1 mongosh --port 27017 --eval '
db.getSiblingDB("admin").createUser({
  user: "billing_superadmin",
  pwd: "superadmin_password_123",
  roles: [
    { role: "root", db: "admin" },
    { role: "clusterAdmin", db: "admin" }
  ]
})
'

echo "Admin user created successfully!"
echo "Username: billing_superadmin"
echo "Password: superadmin_password_123"