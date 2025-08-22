#!/bin/bash

echo "MongoDB Sharded Cluster Setup"
echo "============================="
echo "========Sit Back and Relax==="

# Check if keyfile already exists
if [ -f "mongodb-keyfile" ]; then
    echo "Keyfile already exists, skipping generation..."
else
    echo "========Generating key file for authentication=========="
    openssl rand -base64 756 > mongodb-keyfile
    chmod 400 mongodb-keyfile
    echo "Keyfile generated successfully."
fi

# Phase 1: Start containers
echo "Phase 1: Starting containers..."
docker-compose up -d
echo "Containers started. Waiting for them to be ready..."
sleep 90

# Phase 2: Initialize replica sets
echo "Phase 2: Initializing replica sets..."
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

echo "========================================="
echo "MongoDB Sharded Cluster Setup Complete!"
echo "Connect to mongos at: localhost:27017"
echo "========================================="