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

echo "Starting MongoDB Cluster Configuration..."
echo "========================================="

# Wait for mongos to be ready
echo "Waiting for mongos to be ready..."
sleep 30

# Add shards to the cluster (we only configure mongos1 and all these gets replicated to mongos2 also via configuration servers)
# all mongos instances automatically synchronize their configuration from the same central source - the config servers.
echo "Adding shards to the cluster..."
docker exec -it mongos1 mongosh --port 27017 --eval '
sh.addShard("shard1RS/shard1a:27018,shard1b:27018,shard1c:27018");
sh.addShard("shard2RS/shard2a:27018,shard2b:27018,shard2c:27018");
sh.addShard("shard3RS/shard3a:27018,shard3b:27018,shard3c:27018");
' > /dev/null 2>&1
check_success "Adding shards to cluster"

# Verify cluster status
echo "Cluster status mongos1:"
docker exec -it mongos1 mongosh --port 27017 --eval 'sh.status()'

# Verify cluster status
echo "Cluster status mongos2:"
docker exec -it mongos2 mongosh --port 27018 --eval 'sh.status()'


echo "Cluster Configuration Complete!"
echo "==============================="