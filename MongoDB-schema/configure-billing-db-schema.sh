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
mongosh --host localhost --port 27017 --authenticationDatabase admin -u billing_superadmin -p superadmin_password_123 --file "$SCHEMA_FILE"


# Check if the command was successful
if [ $? -eq 0 ]; then
    echo "✓ Schema loaded successfully!"
else
    echo "✗ Failed to load schema. Please check your MongoDB connection."
    exit 1
fi