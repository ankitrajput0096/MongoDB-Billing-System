#!/bin/bash

echo "MongoDB Sharded Cluster Setup"
echo "============================="
echo "========Sit Back and Relax==="

# Phase 1: Start containers
echo "Phase 1: Starting containers..."
docker-compose up -d
echo "Containers started. Waiting for them to be ready..."
sleep 60

# Phase 2: Initialize replica sets
echo "Phase 2: Initializing replica sets..."
./init-cluster.sh
sleep 80

# Phase 3: Configure cluster
echo "Phase 3: Configuring cluster..."
./configure-cluster.sh

echo "========================================="
echo "MongoDB Sharded Cluster Setup Complete!"
echo "Connect to mongos at: localhost:27017"
echo "========================================="