# MongoDB-Billing-System

## MongoDB Sharded Cluster Setup Guide

### Overview

This document provides complete instructions for setting up a production-ready MongoDB sharded cluster using Docker Compose. The cluster includes:

- 3-node Config Server Replica Set  
- 3 Shards, each with 3-node Replica Sets  
- 2 MongoDB Query Routers (mongos)

### Prerequisites

- Docker and Docker Compose installed  
- MongoDB Shell (mongosh) installed on your host machine  
- Installation: `brew install mongosh` (macOS) or follow instructions at [MongoDB Shell](https://www.mongodb.com/try/download/shell)

### Files Structure

```
mongodb-cluster/
├── docker-compose.yml
├── init-cluster.sh
└── README.md
```

---

## Phase 1: Start the Containers

### Create a directory for your project and navigate to it:

```bash
mkdir mongodb-cluster
cd mongodb-cluster
```

### Create the docker-compose.yml file with the following content:

[docker-compose.yml](MongoDB-arch/docker-compose.yml)

### Start the containers:

```bash
docker-compose up -d
```

### Verify all containers are running:

```bash
docker-compose ps
```

---

## Phase 2: Initialize Replica Sets

### Create and run the initialization script:

Create a file named `init-cluster.sh` with the following content:

[init-cluster.sh](MongoDB-arch/init-cluster.sh)

### Make the script executable:

```bash
chmod +x init-cluster.sh
```

### Run the initialization script:

```bash
./init-cluster.sh
```

---

## Phase 3: Configure the Cluster via Mongos

### Create and run the cluster configuration script:

Create a file named `configure-cluster.sh` with the following content:

[configure-cluster.sh](MongoDB-arch/configure-cluster.sh)

### Make the script executable:

```bash
chmod +x configure-cluster.sh
```

### Run the configuration script:

```bash
./configure-cluster.sh
```

---

## Complete Setup Script

### For a complete automated setup, create a master script that runs all phases:

Create a file named `setup-cluster.sh` with the following content:

[setup-cluster.sh](MongoDB-arch/setup-cluster.sh)

### Make the script executable:

```bash
chmod +x setup-cluster.sh
```

### Run the complete setup:

```bash
./setup-cluster.sh
```

---

## Verification

After running all scripts, verify the cluster is working properly:

### Expected Output
```
ankitrajput@Ankits-MacBook-Pro MongoDB-arch % ./setup-cluster.sh    
MongoDB Sharded Cluster Setup
=============================
========Sit Back and Relax===
Phase 1: Starting containers...
WARN[0000] /Users/ankitrajput/Desktop/MongoDB Billing/MongoDB-Billing-System/MongoDB-arch/docker-compose.yml: the attribute `version` is obsolete, it will be ignored, please remove it to avoid potential confusion 
[+] Running 15/15
 ✔ Network mongodb-arch_mongo_net  Created                                                                                                                          0.1s 
 ✔ Container shard3c               Started                                                                                                                          0.4s 
 ✔ Container configsvr2            Started                                                                                                                          0.2s 
 ✔ Container shard3a               Started                                                                                                                          0.3s 
 ✔ Container configsvr3            Started                                                                                                                          0.3s 
 ✔ Container configsvr1            Started                                                                                                                          0.3s 
 ✔ Container shard1c               Started                                                                                                                          0.2s 
 ✔ Container shard2c               Started                                                                                                                          0.3s 
 ✔ Container shard3b               Started                                                                                                                          0.4s 
 ✔ Container shard1b               Started                                                                                                                          0.4s 
 ✔ Container shard1a               Started                                                                                                                          0.4s 
 ✔ Container shard2b               Started                                                                                                                          0.3s 
 ✔ Container shard2a               Started                                                                                                                          0.2s 
 ✔ Container mongos1               Started                                                                                                                          0.4s 
 ✔ Container mongos2               Started                                                                                                                          0.4s 
Containers started. Waiting for them to be ready...
Phase 2: Initializing replica sets...
Starting MongoDB Sharded Cluster Initialization...
==================================================
Waiting for containers to be ready...
Initializing Config Server Replica Set...
✓ Success: Config Server Replica Set Initialization
Initializing Shard 1 Replica Set...
✓ Success: Shard 1 Replica Set Initialization
Initializing Shard 2 Replica Set...
✓ Success: Shard 2 Replica Set Initialization
Initializing Shard 3 Replica Set...
✓ Success: Shard 3 Replica Set Initialization
Waiting for replica sets to elect primaries...
Replica Set Initialization Complete!
====================================
Phase 3: Configuring cluster...
Starting MongoDB Cluster Configuration...
=========================================
Waiting for mongos to be ready...
Adding shards to the cluster...
✓ Success: Adding shards to cluster
Cluster status:
shardingVersion
{ _id: 1, clusterId: ObjectId('68a62dcafbd11cb96b4917c6') }
---
shards
[
  {
    _id: 'shard1RS',
    host: 'shard1RS/shard1a:27018,shard1b:27018,shard1c:27018',
    state: 1,
    topologyTime: Timestamp({ t: 1755721293, i: 2 })
  },
  {
    _id: 'shard2RS',
    host: 'shard2RS/shard2a:27018,shard2b:27018,shard2c:27018',
    state: 1,
    topologyTime: Timestamp({ t: 1755721293, i: 6 })
  },
  {
    _id: 'shard3RS',
    host: 'shard3RS/shard3a:27018,shard3b:27018,shard3c:27018',
    state: 1,
    topologyTime: Timestamp({ t: 1755721293, i: 19 })
  }
]
---
active mongoses
[ { '7.0.23': 2 } ]
---
autosplit
{ 'Currently enabled': 'yes' }
---
balancer
{
  'Currently enabled': 'yes',
  'Currently running': 'no',
  'Failed balancer rounds in last 5 attempts': 0,
  'Migration Results for the last 24 hours': 'No recent migrations'
}
---
shardedDataDistribution
[]
---
databases
[
  {
    database: { _id: 'config', primary: 'config', partitioned: true },
    collections: {}
  }
]
Cluster Configuration Complete!
===============================
=========================================
MongoDB Sharded Cluster Setup Complete!
Connect to mongos at: localhost:27017
=========================================
```

### Architecture Diagram of MongoDB

```
┌─────────────────────────────────────────────────────────────────────────────┐
│                        MONGODB SHARDED CLUSTER                              │
├─────────────────────────────────────────────────────────────────────────────┤
│                                                                             │
│  CLIENT APPLICATIONS                                                        │
│  ┌─────────────┐  ┌─────────────┐  ┌─────────────┐                          │
│  │ Application │  │ Application │  │ Application │                          │
│  │     #1      │  │     #2      │  │     #3      │                          │
│  └─────────────┘  └─────────────┘  └─────────────┘                          │
│         │              │              │                                     │
│         └───────┬──────┴───────┬──────┘                                     │
│                 │              │                                            │
│          ┌──────▼──────┐  ┌────▼──────┐                                     │
│          │   MONGOS    │  │   MONGOS  │      QUERY ROUTERS                  │
│          │   ROUTER    │  │   ROUTER  │     (Load Balancers)                │
│          │ (mongos1)   │  │ (mongos2) │                                     │
│          │ port:27017  │  │ port:27018│                                     │
│          └──────┬──────┘  └────┬──────┘                                     │
│                 │              │                                            │
│         ┌───────┴──────────────┴───────┐                                    │
│         │                              │                                    │
│  ┌──────▼──────┐                ┌──────▼──────┐                             │
│  │  CONFIG     │                │    SHARDS   │                             │
│  │  SERVERS    │                │             │                             │
│  │ Replica Set │                │  ┌─────────────────────────────────┐      │
│  │   (cfgRS)   │                │  │ SHARD 1 (shard1RS)              │      │
│  │             │                │  │ ┌─────────┬─────────┬─────────┐ │      │
│  │ ┌─────────┐ │                │  │ │ shard1a │ shard1b │ shard1c │ │      │
│  │ │configsvr1│◄────────────────┼──┼─► 27018   │  27018  │  27018  │ │      │
│  │ │ 27019   │ │                │  │ └─────────┴─────────┴─────────┘ │      │
│  │ ├─────────┤ │                │  │                                 │      │
│  │ │configsvr2││                │  │ SHARD 2 (shard2RS)              │      │
│  │ │ 27019   │ │                │  │ ┌─────────┬─────────┬─────────┐ │      │
│  │ ├─────────┤ │                │  │ │ shard2a │ shard2b │ shard2c │ │      │
│  │ │configsvr3│◄────────────────┼──┼─► 27018   │  27018  │  27018  │ │      │
│  │ │ 27019   │ │                │  │ └─────────┴─────────┴─────────┘ │      │
│  │ └─────────┘ │                │  │                                 │      │
│  └─────────────┘                │  │ SHARD 3 (shard3RS)              │      │
│        ▲                        │  │ ┌─────────┬─────────┬─────────┐ │      │
│        │                        │  │ │ shard3a │ shard3b │ shard3c │ │      │
│        │                        │  │ │ 27018   │  27018  │  27018  │ │      │
│        └────────────────────────┼──┼─┴─────────┴─────────┴─────────┘ │      │
│                                 │  └─────────────────────────────────┘      │
│                                 │                                           │
└─────────────────────────────────┴───────────────────────────────────────────┘
```

### Connect to mongos:

```bash
mongosh --host localhost --port 27017
```

### Run cluster status check:

```javascript
sh.status()
```

You should see all three shards with `state: 1 (online)`

---

## Management Commands

### Common Operations

**Stop the cluster:**  
```bash
docker-compose down
```

**Start the cluster:**  
```bash
docker-compose up -d
```

**View logs:**  
```bash
docker-compose logs [service_name]
```

### Troubleshooting

If containers fail to start, check logs:

```bash
docker-compose logs [service_name]
```

If replica set initialization fails, check if containers are fully started:

```bash
docker-compose ps
```

If you need to reset everything:

```bash
docker-compose down -v
```

---

### To Connect with MongoDB Database
Use MongoDB Compass
Download this tool from this URL: https://www.mongodb.com/products/tools/compass

and once installed
Configure this MongoDB database like this in MongoDB Compass:
![Demo](MongoDB-arch/Screenshot%202025-08-20%20at%205.15.28 PM.png)
