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
---

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


## Phases to Start this cluster configured MongoDB
### Phase 0: Generating Keyfile for authentication and authorization
### navigate to directory 'MongoDB-arch':

```bash
cd MongoDB-arch
```
### Create a keyfile by running following commands
```bash
openssl rand -base64 756 > mongodb-keyfile
chmod 400 mongodb-keyfile
```

---


### Phase 1: Start the Containers
### Create the docker-compose.yml file with the following content:

[docker-compose.yml](docker-compose.yml)

### Start the containers:

```bash
docker-compose up -d
```

### Verify all containers are running:

```bash
docker-compose ps
```

---

### Phase 2: Initialize Replica Sets

### navigate to directory 'MongoDB-arch' and run the cluster initialization script:
```bash
cd MongoDB-arch
```
### Make the script executable:
```bash
chmod +x init-cluster.sh
```
### Run the initialization script:

```bash
./init-cluster.sh
```
---


### Phase 3: Setting up admin account for cluster configuration

### navigate to directory 'MongoDB-arch' and run the  admin account creation script:
```bash
cd MongoDB-arch
```
### Make the script executable:

```bash
chmod +x create-admin-user.sh
```

### Run the creation script:

```bash
./create-admin-user.sh
```

---

### Phase 4: Configure the Cluster via Mongos

### navigate to directory 'MongoDB-arch' and run the cluster configuration script:
```bash
cd MongoDB-arch
```
### Make the script executable:

```bash
chmod +x configure-cluster.sh
```

### Run the configuration script:

```bash
./configure-cluster.sh
```

---



### Phase 5: To configure the MongoDB schema
### navigate to directory 'MongoDB-schema' and run the cluster configuration script:
```bash
cd MongoDB-schema
```

### Make the script executable:

```bash
chmod +x configure-billing-db-schema.sh
```

### Run the script:

```bash
./configure-billing-db-schema.sh
```

---

### Phase 6: To configure the MongoDB role based access control (RBAC)
### navigate to directory 'MongoDB-RBAC' and run the cluster configuration script:
```bash
cd MongoDB-RBAC
```

### Make the script executable:

```bash
chmod +x load-rbac.sh
```

### Run the script:

```bash
./load-rbac.sh
```

To see all the configured roles, login to MongoDB compass and execute this command
![RBAC](MongoDB-RBAC/Screenshot%202025-08-22%20at%2011.51.19 AM.png)

---

### Phase 7: To configure the MongoDB indexes
### navigate to directory 'MongoDB-indexes' and run the cluster configuration script:
```bash
cd MongoDB-indexes
```
### Make the script executable:

```bash
chmod +x load-indexes.sh
```

### Run the script:

```bash
./load-indexes.sh
```

---

### Phase 8: To insert the MongoDB data 
### navigate to directory 'MongoDB-documents' and run the cluster configuration script:
```bash
cd MongoDB-documents
```
### Make the script executable:

```bash
chmod +x load-data.sh
```

### Run the script:

```bash
./load-data.sh
```
---

## Complete Setup Script

### For a complete automated setup, a master script that runs all phases:

Create a file named `setup-cluster.sh` with the following content:

[setup-cluster.sh](setup-cluster.sh)

### Make the script executable:

```bash
chmod +x setup-cluster.sh
```

### Run the complete setup:

```bash
./setup-cluster.sh
```

---

### To Connect with MongoDB Database
Use MongoDB Compass
Download this tool from this URL: https://www.mongodb.com/products/tools/compass

and once installed
Configure this MongoDB database like this in MongoDB Compass:
![Demo](MongoDB-arch/Screenshot%202025-08-22%20at%2011.48.36 AM.png)

---

## To execute sample queries
### navigate to directory 'MongoDB-queries' and run the Generic mongoDB execution script:
```bash
cd MongoDB-queries
```
### Make the script executable:

```bash
chmod +x Generic_run_query.sh
```

### Run the configuration script as show below and pass the name of the query as a parameter:

```bash
./Generic_run_query.sh query_2
```

After executing the above command, the result will be present in the 'query_results' folder
with name as '<query_name>_result.js'. Make sure the 'query_2.js' is present in the folder 'queries'.

## Management Commands

### Common Operations

**View logs:**  
```bash
docker-compose logs [service_name]
```

**Bring down the cluster:**
```bash
docker-compose down -v
```
---
