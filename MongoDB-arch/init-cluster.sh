#!/bin/bash

# Function to check if a command was successful
check_success() {
    if [ $? -eq 0 ]; then
        echo "✓ Success: $1"
    else
        echo "✗ Failed: $1"
        exit 1
    fi
}

echo "Starting MongoDB Sharded Cluster Initialization..."
echo "=================================================="

# Wait for containers to be fully ready
echo "Waiting for containers to be ready..."
sleep 10

# Initialize Config Server Replica Set
echo "Initializing Config Server Replica Set..."
docker exec -it configsvr1 mongosh --port 27019 --eval '
rs.initiate(
  {
    _id: "cfgRS",
    configsvr: true,
    members: [
      { _id: 0, host: "configsvr1:27019" },
      { _id: 1, host: "configsvr2:27019" },
      { _id: 2, host: "configsvr3:27019" }
    ]
  }
)' > /dev/null 2>&1
check_success "Config Server Replica Set Initialization"

# Initialize Shard 1 Replica Set
echo "Initializing Shard 1 Replica Set..."
docker exec -it shard1a mongosh --port 27018 --eval '
rs.initiate(
  {
    _id: "shard1RS",
    members: [
      { _id: 0, host: "shard1a:27018" },
      { _id: 1, host: "shard1b:27018" },
      { _id: 2, host: "shard1c:27018" }
    ]
  }
)' > /dev/null 2>&1
check_success "Shard 1 Replica Set Initialization"

# Initialize Shard 2 Replica Set
echo "Initializing Shard 2 Replica Set..."
docker exec -it shard2a mongosh --port 27018 --eval '
rs.initiate(
  {
    _id: "shard2RS",
    members: [
      { _id: 0, host: "shard2a:27018" },
      { _id: 1, host: "shard2b:27018" },
      { _id: 2, host: "shard2c:27018" }
    ]
  }
)' > /dev/null 2>&1
check_success "Shard 2 Replica Set Initialization"

# Initialize Shard 3 Replica Set
echo "Initializing Shard 3 Replica Set..."
docker exec -it shard3a mongosh --port 27018 --eval '
rs.initiate(
  {
    _id: "shard3RS",
    members: [
      { _id: 0, host: "shard3a:27018" },
      { _id: 1, host: "shard3b:27018" },
      { _id: 2, host: "shard3c:27018" }
    ]
  }
)' > /dev/null 2>&1
check_success "Shard 3 Replica Set Initialization"

# Wait for replica sets to elect primaries
echo "Waiting for replica sets to elect primaries..."
sleep 30

echo "Replica Set Initialization Complete!"
echo "===================================="