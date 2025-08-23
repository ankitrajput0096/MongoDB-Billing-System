#!/bin/bash

# Script: load-rbac.sh
# Description: Loads RBAC configuration into MongoDB sharded cluster
# Usage: ./load-rbac.sh

echo "Starting MongoDB RBAC Configuration..."
echo "======================================"

# Check if RBAC file exists
RBAC_FILE="setup-rbac.js"
if [ ! -f "$RBAC_FILE" ]; then
    echo "Error: RBAC file '$RBAC_FILE' not found in the current directory."
    exit 1
fi

echo "Connecting to MongoDB cluster at localhost:27017..."
echo "Loading RBAC configuration from $RBAC_FILE..."

# Execute the RBAC file
mongosh --host localhost --port 27017 --authenticationDatabase admin -u billing_superadmin -p superadmin_password_123 --file "$RBAC_FILE"

# Check if the command was successful
if [ $? -eq 0 ]; then
    echo "✓ RBAC configuration loaded successfully!"
else
    echo "✗ Failed to load RBAC configuration. Please check your MongoDB connection."
    exit 1
fi

echo ""
echo "To test the RBAC setup, you can connect with different users:"
echo "1. Read-only access: mongosh --host localhost --port 27017 -u billing_analyst -p analyst_password_123 --authenticationDatabase admin billing"
echo "2. App access: mongosh --host localhost --port 27017 -u billing_app -p app_password_123 --authenticationDatabase admin billing"
echo "3. Admin access: mongosh --host localhost --port 27017 -u billing_admin -p admin_password_123 --authenticationDatabase admin billing"