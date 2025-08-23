#!/bin/bash

echo "Starting MongoDB Data Insertion Process..."
echo "======================================="

# MongoDB connection parameters
MONGO_HOST="localhost"
MONGO_PORT="27017"
MONGO_USER="billing_superadmin"
MONGO_PASS="superadmin_password_123"
MONGO_AUTH_DB="admin"
MONGO_DB="billing"

echo "Connecting to MongoDB cluster at $MONGO_HOST:$MONGO_PORT..."
echo "Inserting sample data into $MONGO_DB database..."

# Temporary JavaScript file for data insertion
DATA_FILE="/tmp/insert_billing_data.js"

# Create JavaScript file with insert commands
cat > "$DATA_FILE" << 'EOF'
// Get billing database
const dbBilling = db.getSiblingDB("billing");

// Clear existing data
dbBilling.users.deleteMany({});
dbBilling.transactions.deleteMany({});
dbBilling.invoices.deleteMany({});
dbBilling.subscriptions.deleteMany({});
dbBilling.audit_logs.deleteMany({});

// Function to insert with error handling
function insertWithErrorHandling(collection, documents, collectionName) {
  try {
    const result = collection.insertMany(documents, { ordered: false });
    print(`Successfully inserted ${result.insertedCount} documents into ${collectionName}`);
  } catch (e) {
    print(`Error inserting into ${collectionName}: ${e}`);
    if (e.writeErrors) {
      e.writeErrors.forEach(err => {
        print(`Document ${err.index}: ${err.errmsg}`);
      });
    }
  }
}

// Insert Users
insertWithErrorHandling(dbBilling.users, [
  {
    "_id": ObjectId("66c7a0000000000000000001"),
    "name": "Alice Johnson",
    "email": "alice.johnson@example.com",
    "age": 28,
    "balance": 150.75,
    "isActive": true,
    "createdAt": new Date("2024-02-15T10:30:00Z"),
    "updatedAt": new Date("2024-08-20T14:45:00Z"),
    "hobbies": ["yoga", "cooking"],
    "billingAddress": {"city": "New York", "zip": NumberInt(10001), "country": "USA"}
  },
  {
    "_id": ObjectId("66c7a0000000000000000003"),
    "name": "Carol Lee",
    "email": "carol.lee@example.com",
    "age": null,
    "balance": 500.25,
    "isActive": true,
    "createdAt": new Date("2024-05-22T16:00:00Z"),
    "updatedAt": new Date("2024-07-30T18:10:00Z"),
    "hobbies": ["reading", "traveling"],
    "billingAddress": {"city": "Chicago", "zip": NumberInt(60601), "country": "USA"}
  },
  {
    "_id": ObjectId("66c7a0000000000000000004"),
    "name": "David Kim",
    "email": "david.kim@example.com",
    "age": 42,
    "balance": 99.99,
    "isActive": true,
    "createdAt": new Date("2023-09-10T08:45:00Z"),
    "updatedAt": new Date("2024-03-15T12:30:00Z"),
    "hobbies": ["gaming", "music"],
    "billingAddress": {"city": "San Francisco", "zip": NumberInt(94101), "country": "USA"}
  },
  {
    "_id": ObjectId("66c7a0000000000000000006"),
    "name": "Frank Wong",
    "email": "frank.wong@example.com",
    "age": 29,
    "balance": 75.50,
    "isActive": true,
    "createdAt": new Date("2023-12-18T17:55:00Z"),
    "updatedAt": new Date("2024-04-25T19:05:00Z"),
    "hobbies": ["cycling", "cooking"],
    "billingAddress": {"city": "Seattle", "zip": NumberInt(98101), "country": "USA"}
  },
  {
    "_id": ObjectId("66c7a0000000000000000008"),
    "name": "Henry Novak",
    "email": "henry.novak@example.com",
    "age": null,
    "balance": 45.75,
    "isActive": false,
    "createdAt": new Date("2023-10-29T14:35:00Z"),
    "updatedAt": new Date("2025-02-18T16:50:00Z"),
    "hobbies": ["fishing", "sports"],
    "billingAddress": {"city": "Denver", "zip": NumberInt(80201), "country": "USA"}
  },
  {
    "_id": ObjectId("66c7a000000000000000000a"),
    "name": "Jack Thompson",
    "email": "jack.thompson@example.com",
    "age": 40,
    "balance": 125.30,
    "isActive": true,
    "createdAt": new Date("2023-08-03T07:25:00Z"),
    "updatedAt": new Date("2024-05-09T09:40:00Z"),
    "hobbies": ["running", "photography"],
    "billingAddress": {"city": "Phoenix", "zip": NumberInt(85001), "country": "USA"}
  },{
  "_id": ObjectId("66c7a000000000000000000b"),
  "name": "Emma Rodriguez",
  "email": "emma.rodriguez@example.com",
  "age": 35,
  "balance": 225.80,
  "isActive": true,
  "createdAt": new Date("2024-01-10T09:15:00Z"),
  "updatedAt": new Date("2024-07-22T11:30:00Z"),
  "hobbies": ["painting", "hiking"],
  "billingAddress": {"city": "Austin", "zip": NumberInt(73301), "country": "USA"}
},
{
  "_id": ObjectId("66c7a000000000000000000c"),
  "name": "Michael Chen",
  "email": "michael.chen@example.com",
  "age": 31,
  "balance": 89.50,
  "isActive": true,
  "createdAt": new Date("2023-11-05T14:20:00Z"),
  "updatedAt": new Date("2024-06-18T16:45:00Z"),
  "hobbies": ["photography", "coding"],
  "billingAddress": {"city": "Boston", "zip": NumberInt(2108), "country": "USA"}
},
{
  "_id": ObjectId("66c7a000000000000000000d"),
  "name": "Sophia Williams",
  "email": "sophia.williams@example.com",
  "age": 27,
  "balance": 350.25,
  "isActive": true,
  "createdAt": new Date("2024-03-22T10:10:00Z"),
  "updatedAt": new Date("2024-08-10T13:25:00Z"),
  "hobbies": ["yoga", "reading"],
  "billingAddress": {"city": "Portland", "zip": NumberInt(97201), "country": "USA"}
},
{
  "_id": ObjectId("66c7a000000000000000000e"),
  "name": "James Brown",
  "email": "james.brown@example.com",
  "age": 45,
  "balance": 42.75,
  "isActive": false,
  "createdAt": new Date("2023-08-12T16:40:00Z"),
  "updatedAt": new Date("2024-02-28T19:15:00Z"),
  "hobbies": ["gardening", "cooking"],
  "billingAddress": {"city": "Miami", "zip": NumberInt(33101), "country": "USA"}
},
{
  "_id": ObjectId("66c7a000000000000000000f"),
  "name": "Olivia Garcia",
  "email": "olivia.garcia@example.com",
  "age": null,
  "balance": 175.60,
  "isActive": true,
  "createdAt": new Date("2024-04-05T08:30:00Z"),
  "updatedAt": new Date("2024-07-30T10:55:00Z"),
  "hobbies": ["dancing", "traveling"],
  "billingAddress": {"city": "San Diego", "zip": NumberInt(92101), "country": "USA"}
}
], "users");

// Insert Transactions
insertWithErrorHandling(dbBilling.transactions, [
  {
    "_id": ObjectId("66c7b0000000000000000001"),
    "user_id": ObjectId("66c7a0000000000000000001"),
    "amount": 49.99,
    "status": "completed",
    "timestamp": new Date("2024-03-10T12:00:00Z"),
    "createdAt": new Date("2024-03-10T12:00:00Z"),
    "updatedAt": new Date("2024-03-10T12:05:00Z"),
    "metadata": {"gateway": "Stripe", "transaction_id": "txn_abc123"}
  },
  {
    "_id": ObjectId("66c7b0000000000000000002"),
    "user_id": ObjectId("66c7a0000000000000000002"),
    "amount": 29.50,
    "status": "pending",
    "timestamp": new Date("2024-07-15T14:30:00Z"),
    "createdAt": new Date("2024-07-15T14:30:00Z"),
    "updatedAt": new Date("2024-07-15T14:35:00Z"),
    "metadata": {"gateway": "PayPal", "transaction_id": "txn_def456"}
  },
  {
    "_id": ObjectId("66c7b0000000000000000004"),
    "user_id": ObjectId("66c7a0000000000000000004"),
    "amount": 75.25,
    "status": "completed",
    "timestamp": new Date("2024-04-05T16:15:00Z"),
    "createdAt": new Date("2024-04-05T16:15:00Z"),
    "updatedAt": new Date("2024-04-05T16:20:00Z"),
    "metadata": {"gateway": "Braintree", "transaction_id": "txn_jkl012"}
  },
  {
    "_id": ObjectId("66c7b0000000000000000005"),
    "user_id": ObjectId("66c7a0000000000000000005"),
    "amount": 15.99,
    "status": "pending",
    "timestamp": new Date("2025-01-25T11:00:00Z"),
    "createdAt": new Date("2025-01-25T11:00:00Z"),
    "updatedAt": new Date("2025-01-25T11:05:00Z"),
    "metadata": {"gateway": "Stripe", "transaction_id": "txn_mno345"}
  },
  {
    "_id": ObjectId("66c7b0000000000000000008"),
    "user_id": ObjectId("66c7a0000000000000000008"),
    "amount": 89.99,
    "status": "completed",
    "timestamp": new Date("2023-11-01T10:20:00Z"),
    "createdAt": new Date("2023-11-01T10:20:00Z"),
    "updatedAt": new Date("2023-11-01T10:25:00Z"),
    "metadata": {"gateway": "Braintree", "transaction_id": "txn_vwx234"}
  },
  {
    "_id": ObjectId("66c7b0000000000000000009"),
    "user_id": ObjectId("66c7a0000000000000000009"),
    "amount": 35.75,
    "status": "pending",
    "timestamp": new Date("2024-08-18T15:55:00Z"),
    "createdAt": new Date("2024-08-18T15:55:00Z"),
    "updatedAt": new Date("2024-08-18T16:00:00Z"),
    "metadata": {"gateway": "Stripe", "transaction_id": "txn_yza567"}
  },
  {
  "_id": ObjectId("66c7b000000000000000000b"),
  "user_id": ObjectId("66c7a000000000000000000b"),
  "amount": 129.99,
  "status": "completed",
  "timestamp": new Date("2024-06-12T13:45:00Z"),
  "createdAt": new Date("2024-06-12T13:45:00Z"),
  "updatedAt": new Date("2024-06-12T13:50:00Z"),
  "metadata": {"gateway": "Stripe", "transaction_id": "txn_emr789"}
},
{
  "_id": ObjectId("66c7b000000000000000000c"),
  "user_id": ObjectId("66c7a000000000000000000c"),
  "amount": 45.25,
  "status": "failed",
  "timestamp": new Date("2024-07-20T09:30:00Z"),
  "createdAt": new Date("2024-07-20T09:30:00Z"),
  "updatedAt": new Date("2024-07-20T09:35:00Z"),
  "metadata": {"gateway": "PayPal", "transaction_id": "txn_mch101"}
},
{
  "_id": ObjectId("66c7b000000000000000000d"),
  "user_id": ObjectId("66c7a000000000000000000d"),
  "amount": 199.50,
  "status": "pending",
  "timestamp": new Date("2024-08-05T15:20:00Z"),
  "createdAt": new Date("2024-08-05T15:20:00Z"),
  "updatedAt": new Date("2024-08-05T15:25:00Z"),
  "metadata": {"gateway": "Braintree", "transaction_id": "txn_swi222"}
},
{
  "_id": ObjectId("66c7b000000000000000000e"),
  "user_id": ObjectId("66c7a000000000000000000e"),
  "amount": 22.99,
  "status": "completed",
  "timestamp": new Date("2024-01-30T11:10:00Z"),
  "createdAt": new Date("2024-01-30T11:10:00Z"),
  "updatedAt": new Date("2024-01-30T11:15:00Z"),
  "metadata": {"gateway": "Stripe", "transaction_id": "txn_jbr333"}
},
{
  "_id": ObjectId("66c7b000000000000000000f"),
  "user_id": ObjectId("66c7a000000000000000000f"),
  "amount": 79.99,
  "status": "completed",
  "timestamp": new Date("2024-07-15T16:40:00Z"),
  "createdAt": new Date("2024-07-15T16:40:00Z"),
  "updatedAt": new Date("2024-07-15T16:45:00Z"),
  "metadata": {"gateway": "PayPal", "transaction_id": "txn_ogg444"}
}
], "transactions");

// Insert Invoices
insertWithErrorHandling(dbBilling.invoices, [
  {
    "_id": ObjectId("66c7c0000000000000000002"),
    "user_id": ObjectId("66c7a0000000000000000002"),
    "items": [{"productName": "Wireless Headphones", "quantity": NumberInt(1)}],
    "status": "paid",
    "dueDate": new Date("2024-08-01T00:00:00Z"),
    "createdAt": new Date("2024-07-20T14:00:00Z"),
    "updatedAt": new Date("2024-07-25T14:05:00Z")
  },
  {
    "_id": ObjectId("66c7c0000000000000000004"),
    "user_id": ObjectId("66c7a0000000000000000004"),
    "items": [{"productName": "External Hard Drive", "quantity": NumberInt(2)}],
    "status": "pending",
    "dueDate": new Date("2024-05-15T00:00:00Z"),
    "createdAt": new Date("2024-04-20T16:00:00Z"),
    "updatedAt": new Date("2024-04-20T16:05:00Z")
  },
  {
    "_id": ObjectId("66c7c0000000000000000006"),
    "user_id": ObjectId("66c7a0000000000000000006"),
    "items": [{"productName": "Bluetooth Speaker", "quantity": NumberInt(1)}],
    "status": "overdue",
    "dueDate": new Date("2024-03-20T00:00:00Z"),
    "createdAt": new Date("2024-02-25T18:00:00Z"),
    "updatedAt": new Date("2024-02-25T18:05:00Z")
  },
  {
    "_id": ObjectId("66c7c0000000000000000008"),
    "user_id": ObjectId("66c7a0000000000000000008"),
    "items": [{"productName": "USB Cable", "quantity": NumberInt(5)}],
    "status": "paid",
    "dueDate": new Date("2023-11-30T00:00:00Z"),
    "createdAt": new Date("2023-11-05T10:00:00Z"),
    "updatedAt": new Date("2023-11-10T10:05:00Z")
  },
  {
    "_id": ObjectId("66c7c000000000000000000a"),
    "user_id": ObjectId("66c7a000000000000000000a"),
    "items": [{"productName": "Printer Ink", "quantity": NumberInt(3)}],
    "status": "pending",
    "dueDate": new Date("2025-04-15T00:00:00Z"),
    "createdAt": new Date("2025-03-20T08:00:00Z"),
    "updatedAt": new Date("2025-03-20T08:05:00Z")
  },
  {
  "_id": ObjectId("66c7c000000000000000000b"),
  "user_id": ObjectId("66c7a000000000000000000b"),
  "items": [
    {"productName": "Gaming Monitor", "quantity": NumberInt(1)},
    {"productName": "HDMI Cable", "quantity": NumberInt(2)}
  ],
  "status": "paid",
  "dueDate": new Date("2024-07-01T00:00:00Z"),
  "totalAmount": 299.99,
  "createdAt": new Date("2024-06-15T14:20:00Z"),
  "updatedAt": new Date("2024-06-25T14:25:00Z")
},
{
  "_id": ObjectId("66c7c000000000000000000c"),
  "user_id": ObjectId("66c7a000000000000000000c"),
  "items": [
    {"productName": "Webcam", "quantity": NumberInt(1)},
    {"productName": "Microphone", "quantity": NumberInt(1)}
  ],
  "status": "pending",
  "dueDate": new Date("2024-08-20T00:00:00Z"),
  "totalAmount": 125.50,
  "createdAt": new Date("2024-07-25T10:15:00Z"),
  "updatedAt": new Date("2024-07-25T10:20:00Z")
},
{
  "_id": ObjectId("66c7c000000000000000000d"),
  "user_id": ObjectId("66c7a000000000000000000d"),
  "items": [
    {"productName": "Smart Watch", "quantity": NumberInt(1)}
  ],
  "status": "overdue",
  "dueDate": new Date("2024-07-15T00:00:00Z"),
  "totalAmount": 249.99,
  "createdAt": new Date("2024-06-28T16:30:00Z"),
  "updatedAt": new Date("2024-07-16T16:35:00Z")
},
{
  "_id": ObjectId("66c7c000000000000000000e"),
  "user_id": ObjectId("66c7a000000000000000000e"),
  "items": [
    {"productName": "Wireless Earbuds", "quantity": NumberInt(1)},
    {"productName": "Charging Case", "quantity": NumberInt(1)}
  ],
  "status": "paid",
  "dueDate": new Date("2024-02-28T00:00:00Z"),
  "totalAmount": 89.99,
  "createdAt": new Date("2024-02-10T12:05:00Z"),
  "updatedAt": new Date("2024-02-20T12:10:00Z")
},
{
  "_id": ObjectId("66c7c000000000000000000f"),
  "user_id": ObjectId("66c7a000000000000000000f"),
  "items": [
    {"productName": "Fitness Tracker", "quantity": NumberInt(1)},
    {"productName": "Replacement Band", "quantity": NumberInt(2)}
  ],
  "status": "pending",
  "dueDate": new Date("2024-09-05T00:00:00Z"),
  "totalAmount": 149.95,
  "createdAt": new Date("2024-08-10T09:40:00Z"),
  "updatedAt": new Date("2024-08-10T09:45:00Z")
}
], "invoices");

// Insert Subscriptions
insertWithErrorHandling(dbBilling.subscriptions, [
  {
    "_id": ObjectId("66c7d0000000000000000001"),
    "user_id": ObjectId("66c7a0000000000000000001"),
    "plan": {"planName": "Basic", "price": 9.99, "billingCycle": "monthly"},
    "startDate": new Date("2024-01-01T00:00:00Z"),
    "endDate": null,
    "status": "active",
    "createdAt": new Date("2024-01-01T00:00:00Z"),
    "updatedAt": new Date("2024-08-22T00:00:00Z")
  },
  {
    "_id": ObjectId("66c7d0000000000000000002"),
    "user_id": ObjectId("66c7a0000000000000000002"),
    "plan": {"planName": "Premium", "price": 19.99, "billingCycle": "yearly"},
    "startDate": new Date("2023-10-15T00:00:00Z"),
    "endDate": new Date("2024-10-15T00:00:00Z"),
    "status": "cancelled",
    "createdAt": new Date("2023-10-15T00:00:00Z"),
    "updatedAt": new Date("2024-10-15T00:00:00Z")
  },
  {
    "_id": ObjectId("66c7d0000000000000000003"),
    "user_id": ObjectId("66c7a0000000000000000003"),
    "plan": {"planName": "Pro", "price": 29.99, "billingCycle": "monthly"},
    "startDate": new Date("2024-04-10T00:00:00Z"),
    "endDate": null,
    "status": "active",
    "createdAt": new Date("2024-04-10T00:00:00Z"),
    "updatedAt": new Date("2024-08-22T00:00:00Z")
  },
  {
    "_id": ObjectId("66c7d0000000000000000004"),
    "user_id": ObjectId("66c7a0000000000000000004"),
    "plan": {"planName": "Basic", "price": 99.99, "billingCycle": "yearly"},
    "startDate": new Date("2023-07-20T00:00:00Z"),
    "endDate": new Date("2024-07-20T00:00:00Z"),
    "status": "inactive",
    "createdAt": new Date("2023-07-20T00:00:00Z"),
    "updatedAt": new Date("2024-07-20T00:00:00Z")
  },
  {
    "_id": ObjectId("66c7d0000000000000000005"),
    "user_id": ObjectId("66c7a0000000000000000005"),
    "plan": {"planName": "Premium", "price": 14.99, "billingCycle": "monthly"},
    "startDate": new Date("2024-12-05T00:00:00Z"),
    "endDate": null,
    "status": "active",
    "createdAt": new Date("2024-12-05T00:00:00Z"),
    "updatedAt": new Date("2025-08-22T00:00:00Z")
  },
  {
    "_id": ObjectId("66c7d0000000000000000006"),
    "user_id": ObjectId("66c7a0000000000000000006"),
    "plan": {"planName": "Pro", "price": 199.99, "billingCycle": "yearly"},
    "startDate": new Date("2024-02-01T00:00:00Z"),
    "endDate": new Date("2025-02-01T00:00:00Z"),
    "status": "cancelled",
    "createdAt": new Date("2024-02-01T00:00:00Z"),
    "updatedAt": new Date("2025-02-01T00:00:00Z")
  },
  {
    "_id": ObjectId("66c7d0000000000000000007"),
    "user_id": ObjectId("66c7a0000000000000000007"),
    "plan": {"planName": "Basic", "price": 9.99, "billingCycle": "monthly"},
    "startDate": new Date("2024-05-25T00:00:00Z"),
    "endDate": null,
    "status": "active",
    "createdAt": new Date("2024-05-25T00:00:00Z"),
    "updatedAt": new Date("2024-08-22T00:00:00Z")
  },
  {
    "_id": ObjectId("66c7d0000000000000000008"),
    "user_id": ObjectId("66c7a0000000000000000008"),
    "plan": {"planName": "Premium", "price": 19.99, "billingCycle": "monthly"},
    "startDate": new Date("2023-09-10T00:00:00Z"),
    "endDate": new Date("2024-03-10T00:00:00Z"),
    "status": "inactive",
    "createdAt": new Date("2023-09-10T00:00:00Z"),
    "updatedAt": new Date("2024-03-10T00:00:00Z")
  },
  {
    "_id": ObjectId("66c7d0000000000000000009"),
    "user_id": ObjectId("66c7a0000000000000000009"),
    "plan": {"planName": "Pro", "price": 29.99, "billingCycle": "yearly"},
    "startDate": new Date("2024-06-15T00:00:00Z"),
    "endDate": null,
    "status": "active",
    "createdAt": new Date("2024-06-15T00:00:00Z"),
    "updatedAt": new Date("2024-08-22T00:00:00Z")
  },
  {
    "_id": ObjectId("66c7d000000000000000000a"),
    "user_id": ObjectId("66c7a000000000000000000a"),
    "plan": {"planName": "Basic", "price": 99.99, "billingCycle": "yearly"},
    "startDate": new Date("2023-06-01T00:00:00Z"),
    "endDate": new Date("2024-06-01T00:00:00Z"),
    "status": "cancelled",
    "createdAt": new Date("2023-06-01T00:00:00Z"),
    "updatedAt": new Date("2024-06-01T00:00:00Z")
  },
  {
  "_id": ObjectId("66c7d000000000000000000b"),
  "user_id": ObjectId("66c7a000000000000000000b"),
  "plan": {"planName": "Enterprise", "price": 49.99, "billingCycle": "monthly"},
  "startDate": new Date("2024-02-10T00:00:00Z"),
  "endDate": null,
  "status": "active",
  "createdAt": new Date("2024-02-10T00:00:00Z"),
  "updatedAt": new Date("2024-08-22T00:00:00Z")
},
{
  "_id": ObjectId("66c7d000000000000000000c"),
  "user_id": ObjectId("66c7a000000000000000000c"),
  "plan": {"planName": "Student", "price": 4.99, "billingCycle": "monthly"},
  "startDate": new Date("2023-12-01T00:00:00Z"),
  "endDate": new Date("2024-12-01T00:00:00Z"),
  "status": "active",
  "createdAt": new Date("2023-12-01T00:00:00Z"),
  "updatedAt": new Date("2024-08-22T00:00:00Z")
},
{
  "_id": ObjectId("66c7d000000000000000000d"),
  "user_id": ObjectId("66c7a000000000000000000d"),
  "plan": {"planName": "Family", "price": 24.99, "billingCycle": "yearly"},
  "startDate": new Date("2024-05-15T00:00:00Z"),
  "endDate": null,
  "status": "active",
  "createdAt": new Date("2024-05-15T00:00:00Z"),
  "updatedAt": new Date("2024-08-22T00:00:00Z")
},
{
  "_id": ObjectId("66c7d000000000000000000e"),
  "user_id": ObjectId("66c7a000000000000000000e"),
  "plan": {"planName": "Basic", "price": 9.99, "billingCycle": "monthly"},
  "startDate": new Date("2023-10-10T00:00:00Z"),
  "endDate": new Date("2024-04-10T00:00:00Z"),
  "status": "cancelled",
  "createdAt": new Date("2023-10-10T00:00:00Z"),
  "updatedAt": new Date("2024-04-10T00:00:00Z")
},
{
  "_id": ObjectId("66c7d000000000000000000f"),
  "user_id": ObjectId("66c7a000000000000000000f"),
  "plan": {"planName": "Premium", "price": 19.99, "billingCycle": "monthly"},
  "startDate": new Date("2024-06-01T00:00:00Z"),
  "endDate": null,
  "status": "active",
  "createdAt": new Date("2024-06-01T00:00:00Z"),
  "updatedAt": new Date("2024-08-22T00:00:00Z")
}
], "subscriptions");

// Insert Audit Logs
insertWithErrorHandling(dbBilling.audit_logs, [
  {
    "_id": ObjectId("66c7e0000000000000000001"),
    "entityType": "user",
    "entityId": ObjectId("66c7a0000000000000000001"),
    "action": "updated",
    "timestamp": new Date("2024-08-20T14:45:00Z"),
    "createdAt": new Date("2024-08-20T14:45:00Z"),
    "updatedAt": new Date("2024-08-20T14:45:00Z"),
    "details": {"performedBy": "admin", "changes": {"balance": 150.75}}
  },
  {
    "_id": ObjectId("66c7e0000000000000000002"),
    "entityType": "transaction",
    "entityId": ObjectId("66c7b0000000000000000001"),
    "action": "created",
    "timestamp": new Date("2024-03-10T12:00:00Z"),
    "createdAt": new Date("2024-03-10T12:00:00Z"),
    "updatedAt": new Date("2024-03-10T12:00:00Z"),
    "details": {"performedBy": "system", "changes": {"status": "completed"}}
  },
  {
    "_id": ObjectId("66c7e0000000000000000003"),
    "entityType": "invoice",
    "entityId": ObjectId("66c7c0000000000000000001"),
    "action": "status_change",
    "timestamp": new Date("2023-12-01T09:00:00Z"),
    "createdAt": new Date("2023-12-01T09:00:00Z"),
    "updatedAt": new Date("2023-12-01T09:00:00Z"),
    "details": {"performedBy": "user", "changes": {"status": "overdue"}}
  },
  {
    "_id": ObjectId("66c7e0000000000000000004"),
    "entityType": "subscription",
    "entityId": ObjectId("66c7d0000000000000000001"),
    "action": "cancelled",
    "timestamp": new Date("2024-10-15T00:00:00Z"),
    "createdAt": new Date("2024-10-15T00:00:00Z"),
    "updatedAt": new Date("2024-10-15T00:00:00Z"),
    "details": {"performedBy": "user", "changes": {"status": "cancelled"}}
  },
  {
    "_id": ObjectId("66c7e0000000000000000005"),
    "entityType": "user",
    "entityId": ObjectId("66c7a0000000000000000005"),
    "action": "deactivated",
    "timestamp": new Date("2025-06-05T15:40:00Z"),
    "createdAt": new Date("2025-06-05T15:40:00Z"),
    "updatedAt": new Date("2025-06-05T15:40:00Z"),
    "details": {"performedBy": "admin", "changes": {"isActive": false}}
  },
  {
    "_id": ObjectId("66c7e0000000000000000006"),
    "entityType": "transaction",
    "entityId": ObjectId("66c7b0000000000000000006"),
    "action": "updated",
    "timestamp": new Date("2024-02-28T18:35:00Z"),
    "createdAt": new Date("2024-02-28T18:35:00Z"),
    "updatedAt": new Date("2024-02-28T18:35:00Z"),
    "details": {"performedBy": "system", "changes": {"amount": 200.00}}
  },
  {
    "_id": ObjectId("66c7e0000000000000000007"),
    "entityType": "invoice",
    "entityId": ObjectId("66c7c0000000000000000007"),
    "action": "created",
    "timestamp": new Date("2024-06-10T13:00:00Z"),
    "createdAt": new Date("2024-06-10T13:00:00Z"),
    "updatedAt": new Date("2024-06-10T13:00:00Z"),
    "details": {"performedBy": "system", "changes": {"totalAmount": 150}}
  },
  {
    "_id": ObjectId("66c7e0000000000000000008"),
    "entityType": "subscription",
    "entityId": ObjectId("66c7d0000000000000000008"),
    "action": "renewed",
    "timestamp": new Date("2024-03-10T00:00:00Z"),
    "createdAt": new Date("2024-03-10T00:00:00Z"),
    "updatedAt": new Date("2024-03-10T00:00:00Z"),
    "details": {"performedBy": "user", "changes": {"status": "inactive"}}
  },
  {
    "_id": ObjectId("66c7e0000000000000000009"),
    "entityType": "user",
    "entityId": ObjectId("66c7a0000000000000000009"),
    "action": "profile_update",
    "timestamp": new Date("2024-08-01T22:15:00Z"),
    "createdAt": new Date("2024-08-01T22:15:00Z"),
    "updatedAt": new Date("2024-08-01T22:15:00Z"),
    "details": {"performedBy": "user", "changes": {"hobbies": ["swimming", "movies"]}}
  },
  {
    "_id": ObjectId("66c7e000000000000000000a"),
    "entityType": "transaction",
    "entityId": ObjectId("66c7b000000000000000000a"),
    "action": "failed",
    "timestamp": new Date("2025-03-12T08:15:00Z"),
    "createdAt": new Date("2025-03-12T08:15:00Z"),
    "updatedAt": new Date("2025-03-12T08:15:00Z"),
    "details": {"performedBy": "system", "changes": {"status": "completed"}}
  },
  {
  "_id": ObjectId("66c7e000000000000000000b"),
  "entityType": "user",
  "entityId": ObjectId("66c7a000000000000000000b"),
  "action": "created",
  "timestamp": new Date("2024-01-10T09:15:00Z"),
  "createdAt": new Date("2024-01-10T09:15:00Z"),
  "updatedAt": new Date("2024-01-10T09:15:00Z"),
  "details": {"performedBy": "system", "changes": {"name": "Emma Rodriguez"}}
},
{
  "_id": ObjectId("66c7e000000000000000000c"),
  "entityType": "transaction",
  "entityId": ObjectId("66c7b000000000000000000c"),
  "action": "failed",
  "timestamp": new Date("2024-07-20T09:35:00Z"),
  "createdAt": new Date("2024-07-20T09:35:00Z"),
  "updatedAt": new Date("2024-07-20T09:35:00Z"),
  "details": {"performedBy": "system", "changes": {"status": "failed"}}
},
{
  "_id": ObjectId("66c7e000000000000000000d"),
  "entityType": "invoice",
  "entityId": ObjectId("66c7c000000000000000000d"),
  "action": "status_change",
  "timestamp": new Date("2024-07-16T16:35:00Z"),
  "createdAt": new Date("2024-07-16T16:35:00Z"),
  "updatedAt": new Date("2024-07-16T16:35:00Z"),
  "details": {"performedBy": "system", "changes": {"status": "overdue"}}
},
{
  "_id": ObjectId("66c7e000000000000000000e"),
  "entityType": "subscription",
  "entityId": ObjectId("66c7d000000000000000000e"),
  "action": "cancelled",
  "timestamp": new Date("2024-04-10T00:00:00Z"),
  "createdAt": new Date("2024-04-10T00:00:00Z"),
  "updatedAt": new Date("2024-04-10T00:00:00Z"),
  "details": {"performedBy": "user", "changes": {"status": "cancelled"}}
},
{
  "_id": ObjectId("66c7e000000000000000000f"),
  "entityType": "user",
  "entityId": ObjectId("66c7a000000000000000000f"),
  "action": "balance_update",
  "timestamp": new Date("2024-07-30T10:55:00Z"),
  "createdAt": new Date("2024-07-30T10:55:00Z"),
  "updatedAt": new Date("2024-07-30T10:55:00Z"),
  "details": {"performedBy": "system", "changes": {"balance": 175.60}}
}
], "audit_logs");

print("All sample data insertion attempts completed!");
EOF

# Execute the data insertion script
mongosh --host "$MONGO_HOST" --port "$MONGO_PORT" --authenticationDatabase "$MONGO_AUTH_DB" -u "$MONGO_USER" -p "$MONGO_PASS" --file "$DATA_FILE"

# Check if the command was successful
if [ $? -eq 0 ]; then
    echo "✓ Sample data insertion process completed!"
else
    echo "✗ Failed to execute data insertion script. Please check MongoDB connection or review error messages above."
    exit 1
fi

# Clean up temporary file
rm -f "$DATA_FILE"
echo "======================================="
echo "Data insertion process completed!"