#!/bin/bash

echo "Starting MongoDB Schema Load Process..."
echo "======================================="

# Check if schema file exists
SCHEMA_FILE="create_billing_collections.js"
if [ ! -f "$SCHEMA_FILE" ]; then
    echo "Error: Schema file '$SCHEMA_FILE' not found in the current directory."
    exit 1
fi

echo "Connecting to MongoDB cluster at localhost:27017..."
echo "Loading schema from $SCHEMA_FILE..."

# Execute the schema file
# mongosh --host localhost --port 27017 --file "$SCHEMA_FILE"
mongosh --host localhost --port 27017 --authenticationDatabase admin -u billing_superadmin -p superadmin_password_123 --file "$SCHEMA_FILE"


# Check if the command was successful
if [ $? -eq 0 ]; then
    echo "✓ Schema loaded successfully!"
    echo ""
    echo "Summary of actions performed:"
    echo "- Created 'billing' database"
    echo "- Enabled sharding on 'billing' database"
    echo "- Created and sharded 5 collections:"
    echo "  • users (sharded on _id)"
    echo "  • transactions (sharded on user_id)"
    echo "  • invoices (sharded on _id)"
    echo "  • subscriptions (sharded on user_id)"
    echo "  • audit_logs (sharded on _id)"
    echo "- Applied JSON schema validation to all collections"
else
    echo "✗ Failed to load schema. Please check your MongoDB connection."
    exit 1
fi