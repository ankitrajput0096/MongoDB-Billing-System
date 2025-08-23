#!/bin/bash

# Script: load-indexes.sh
# Description: Loads indexes into MongoDB sharded cluster
# Usage: ./load-indexes.sh

echo "Starting MongoDB Index Loading..."
echo "=================================="

# Check if index file exists
INDEX_FILE="mongoDB-index.js"
if [ ! -f "$INDEX_FILE" ]; then
    echo "Error: Index file '$INDEX_FILE' not found in the current directory."
    exit 1
fi

echo "Connecting to MongoDB cluster at localhost:27017..."
echo "Loading indexes from $INDEX_FILE..."

# Execute the index file
mongosh --host localhost --port 27017 --authenticationDatabase admin -u billing_superadmin -p superadmin_password_123 --file "$INDEX_FILE"

# Check if the command was successful
if [ $? -eq 0 ]; then
    echo "✓ Indexes loaded successfully!"
else
    echo "✗ Failed to load indexes. Please check your MongoDB connection."
    exit 1
fi

echo ""
echo "To verify the indexes, you can connect and run:"
echo "mongosh --host localhost --port 27017 -u billing_superadmin -p superadmin_password_123 --authenticationDatabase admin --eval 'db.getSiblingDB(\"billing\").users.getIndexes()'"