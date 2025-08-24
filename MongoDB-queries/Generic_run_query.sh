#!/bin/bash

# Check if query name is provided
if [ $# -eq 0 ]; then
    echo "Usage: $0 <query_name>"
    echo "Example: $0 query1"
    exit 1
fi

QUERY_NAME=$1
URI="mongodb://billing_superadmin:superadmin_password_123@localhost:27017,localhost:27018/billing?authSource=admin"
QUERY_FILE="queries/${QUERY_NAME}.js"
RESULT_FILE="query_results/${QUERY_NAME}_result.js"

# Check if query file exists
if [ ! -f "$QUERY_FILE" ]; then
    echo "Query file $QUERY_FILE not found!"
    exit 1
fi

# Execute the query and save to result file
mongosh "$URI" --eval "$(cat $QUERY_FILE)" --quiet > "$RESULT_FILE"

echo "Query executed. Result saved to $RESULT_FILE"