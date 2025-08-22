#!/bin/bash

# Script: load-rbac.sh
# Description: Loads RBAC configuration into MongoDB sharded cluster
# Usage: ./load-rbac.sh

echo "Starting MongoDB RBAC Configuration..."
echo "======================================"

# Check if mongosh is available
if ! command -v mongosh &> /dev/null; then
    echo "Error: mongosh is not installed. Please install it first."
    echo "On macOS: brew install mongosh"
    echo "Or download from: https://www.mongodb.com/try/download/shell"
    exit 1
fi

# Check if RBAC file exists
RBAC_FILE="setup-rbac.js"
if [ ! -f "$RBAC_FILE" ]; then
    echo "Error: RBAC file '$RBAC_FILE' not found in the current directory."
    exit 1
fi

echo "Connecting to MongoDB cluster at localhost:27017..."
echo "Loading RBAC configuration from $RBAC_FILE..."

# Execute the RBAC file
# mongosh --host localhost --port 27017 --file "$RBAC_FILE"

mongosh --host localhost --port 27017 --authenticationDatabase admin -u billing_superadmin -p superadmin_password_123 --file "$RBAC_FILE"

# Check if the command was successful
if [ $? -eq 0 ]; then
    echo "✓ RBAC configuration loaded successfully!"
    echo ""
    echo "Summary of roles and users created:"
    echo "- billingAdmin: Full access to billing database"
    echo "- billingUser: Standard user access for applications"
    echo "- billingReadOnly: Read-only access to all billing data"
    echo "- billingAuditor: Access to audit logs and transactions"
    echo ""
    echo "Users created:"
    echo "- billing_admin (billingAdmin role)"
    echo "- billing_app (billingUser role)"
    echo "- billing_analyst (billingReadOnly role)"
    echo "- billing_auditor (billingAuditor role)"
    echo "- billing_superadmin (root and clusterAdmin roles)"
else
    echo "✗ Failed to load RBAC configuration. Please check your MongoDB connection."
    exit 1
fi

echo ""
echo "To test the RBAC setup, you can connect with different users:"
echo "1. Read-only access: mongosh --host localhost --port 27017 -u billing_analyst -p analyst_password_123 --authenticationDatabase admin billing"
echo "2. App access: mongosh --host localhost --port 27017 -u billing_app -p app_password_123 --authenticationDatabase admin billing"
echo "3. Admin access: mongosh --host localhost --port 27017 -u billing_admin -p admin_password_123 --authenticationDatabase admin billing"