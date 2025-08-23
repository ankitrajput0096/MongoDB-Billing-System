#!/bin/bash

echo "MongoDB Sharded Cluster Setup"
echo "============================="
echo "========Sit Back and Relax==="

# Check if mongosh is available
if ! command -v mongosh &> /dev/null; then
    echo "Error: mongosh is not installed. Please install it first."
    echo "On macOS: brew install mongosh"
    echo "Or download from: https://www.mongodb.com/try/download/shell"
    exit 1
fi

cd MongoDB-arch
# Phase 0: generating keyfile for authentication and authorization
echo "Phase 0: Generating keyfile for authentication and authorization..."
# Check if keyfile already exists
if [ -f "mongodb-keyfile" ]; then
    echo "Keyfile already exists, skipping generation..."
else
    echo "========Generating key file for authentication=========="
    openssl rand -base64 756 > mongodb-keyfile
    chmod 400 mongodb-keyfile
    echo "Keyfile generated successfully."
fi
cd ..

# Phase 1: Start containers
echo "Phase 1: Starting containers..."
docker-compose up -d
echo "Containers started. Waiting for them to be ready..."
sleep 90

# Phase 2: Initialize replica sets
echo "Phase 2: Initializing replica sets..."
cd MongoDB-arch
chmod +x ./init-cluster.sh
./init-cluster.sh
sleep 80

# Phase 3: setting up admin account for cluster configuration 
echo "Phase 3: Setting up admin account for cluster configuration ..."
chmod +x ./create-admin-user.sh
./create-admin-user.sh
sleep 60

# Phase 4: Configure cluster
echo "Phase 4: Configuring cluster..."
chmod +x ./configure-cluster.sh
./configure-cluster.sh
cd ..
sleep 30

# Phase 5: Configure schema
echo "Phase 5: Configuring schema..."
chmod +x ./MongoDB-schema/configure-billing-db-schema.sh
cd MongoDB-schema
./configure-billing-db-schema.sh
cd ..
sleep 30

# Phase 6: Configure Role Based Access Control (RBAC)
echo "Phase 6: Configuring Role Based Access Control (RBAC)..."
chmod +x ./MongoDB-RBAC/load-rbac.sh
cd MongoDB-RBAC
./load-rbac.sh
cd ..
sleep 30

# Phase 7: Configure indexes
echo "Phase 7: Configuring indexes..."
chmod +x ./MongoDB-indexes/load-indexes.sh
cd MongoDB-indexes
./load-indexes.sh
cd ..
sleep 30

# Phase 8: Insert real world sample data in each collection of Billing DB
echo "Phase 8: Insert real world sample data in each collection of Billing DB..."
chmod +x ./MongoDB-documents/load-data.sh
./MongoDB-documents/load-data.sh


echo "========================================="
echo "MongoDB Sharded Cluster Setup Complete!"
echo "========================================="