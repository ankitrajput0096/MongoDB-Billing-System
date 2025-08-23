// Get billing database
const dbBilling = db.getSiblingDB("billing");

// Enable sharding for the database
sh.enableSharding("billing");

// Create users collection with schema validation and remaining BSON types
dbBilling.createCollection("users", {
  validator: {
    $jsonSchema: {
      bsonType: "object",
      required: ["name", "email", "billingAddress", "createdAt", "updatedAt", "isActive"],
      properties: {
        name: { bsonType: "string" },
        email: { bsonType: "string" },
        age: { bsonType: ["int", "null"] },
        balance: { bsonType: "double" },
        largeNumber: { bsonType: "long" },
        preciseBalance: { bsonType: "decimal" },
        isActive: { bsonType: "bool" },
        createdAt: { bsonType: "date" },
        updatedAt: { bsonType: "date" },
        hobbies: { bsonType: "array", items: { bsonType: "string" } },
        billingAddress: {
          bsonType: "object",
          required: ["city", "zip"],
          properties: {
            city: { bsonType: "string" },
            zip: { bsonType: "int" },
            country: { bsonType: ["string", "null"] }
          }
        },
        nullField: { bsonType: "null" },
        minKey: { bsonType: "minKey" },
        maxKey: { bsonType: "maxKey" },
        timestamp: { bsonType: "timestamp" }
      }
    }
  }
});

// Shard users collection on hashed _id
sh.shardCollection("billing.users", { _id: "hashed" });

// Insert sample user with remaining BSON types
dbBilling.users.insertOne({
  _id: new ObjectId(),
  name: "Alice Smith",
  email: "alice@example.com",
  age: 30,
  balance: 123.45,
  largeNumber: NumberLong("123456789012345"),
  preciseBalance: NumberDecimal("123.456789"),
  isActive: true,
  createdAt: new Date(),
  updatedAt: new Date(),
  hobbies: ["reading", "sports"],
  billingAddress: { city: "Seattle", zip: 98101, country: "USA" },
  nullField: null,
  timestamp: new Timestamp(1, 2),
  minKey: new MinKey(),
  maxKey: new MaxKey()
});

// Create transactions collection with embedded metadata
dbBilling.createCollection("transactions", {
  validator: {
    $jsonSchema: {
      bsonType: "object",
      required: ["user_id", "amount", "status", "timestamp", "metadata", "createdAt", "updatedAt"],
      properties: {
        user_id: { bsonType: "objectId" },
        amount: { bsonType: "double" },
        status: { enum: ["pending", "completed", "failed"] },
        timestamp: { bsonType: "date" },
        createdAt: { bsonType: "date" },
        updatedAt: { bsonType: "date" },
        metadata: {
          bsonType: "object",
          required: ["gateway", "transaction_id"],
          properties: {
            gateway: { bsonType: "string" },
            transaction_id: { bsonType: "string" }
          }
        }
      }
    }
  }
});

// Shard transactions on hashed user_id
sh.shardCollection("billing.transactions", { user_id: "hashed" });

// Insert sample transaction
dbBilling.transactions.insertOne({
  user_id: dbBilling.users.findOne()._id,
  amount: 49.99,
  status: "completed",
  timestamp: new Date(),
  createdAt: new Date(),
  updatedAt: new Date(),
  metadata: { gateway: "Stripe", transaction_id: "txn_123" }
});

// Create invoices collection with embedded items array, without price and totalAmount
dbBilling.createCollection("invoices", {
  validator: {
    $jsonSchema: {
      bsonType: "object",
      required: ["user_id", "items", "status", "dueDate", "createdAt", "updatedAt"],
      properties: {
        user_id: { bsonType: "objectId" },
        items: {
          bsonType: "array",
          items: {
            bsonType: "object",
            required: ["productName", "quantity"],
            properties: {
              productName: { bsonType: "string" },
              quantity: { bsonType: "int" }
            }
          }
        },
        status: { enum: ["pending", "paid", "overdue"] },
        dueDate: { bsonType: "date" },
        totalAmount: {bsonType: "double" },
        createdAt: { bsonType: "date" },
        updatedAt: { bsonType: "date" }
      }
    }
  }
});

// Shard invoices on hashed _id
sh.shardCollection("billing.invoices", { _id: "hashed" });

// Insert sample invoice
dbBilling.invoices.insertOne({
  user_id: dbBilling.users.findOne()._id,
  items: [
    { productName: "Wireless Mouse", quantity: 2 },
    { productName: "Keyboard", quantity: 1 }
  ],
  status: "pending",
  dueDate: new Date("2025-09-01"),
  totalAmount: 59.99,
  createdAt: new Date(),
  updatedAt: new Date()
});

// Create subscriptions collection with embedded plan details
dbBilling.createCollection("subscriptions", {
  validator: {
    $jsonSchema: {
      bsonType: "object",
      required: ["user_id", "plan", "startDate", "status", "createdAt", "updatedAt"],
      properties: {
        user_id: { bsonType: "objectId" },
        plan: {
          bsonType: "object",
          required: ["planName", "price", "billingCycle"],
          properties: {
            planName: { bsonType: "string" },
            price: { bsonType: "double" },
            billingCycle: { enum: ["monthly", "yearly"] }
          }
        },
        startDate: { bsonType: "date" },
        endDate: { bsonType: ["date", "null"] },
        status: { enum: ["active", "inactive", "cancelled"] },
        createdAt: { bsonType: "date" },
        updatedAt: { bsonType: "date" }
      }
    }
  }
});

// Shard subscriptions on hashed user_id
sh.shardCollection("billing.subscriptions", { user_id: "hashed" });

// Insert sample subscription
dbBilling.subscriptions.insertOne({
  user_id: dbBilling.users.findOne()._id,
  plan: { planName: "Premium", price: 19.99, billingCycle: "monthly" },
  startDate: new Date(),
  endDate: null,
  status: "active",
  createdAt: new Date(),
  updatedAt: new Date()
});

// Create audit_logs collection for tracking changes
dbBilling.createCollection("audit_logs", {
  validator: {
    $jsonSchema: {
      bsonType: "object",
      required: ["entityType", "entityId", "action", "timestamp", "createdAt", "updatedAt"],
      properties: {
        entityType: { enum: ["user", "transaction", "invoice", "subscription"] },
        entityId: { bsonType: "objectId" },
        action: { bsonType: "string" },
        timestamp: { bsonType: "date" },
        createdAt: { bsonType: "date" },
        updatedAt: { bsonType: "date" },
        details: { bsonType: "object" }
      }
    }
  }
});

// Shard audit_logs on hashed _id
sh.shardCollection("billing.audit_logs", { _id: "hashed" });

// Insert sample audit log
dbBilling.audit_logs.insertOne({
  entityType: "user",
  entityId: dbBilling.users.findOne()._id,
  action: "created",
  timestamp: new Date(),
  createdAt: new Date(),
  updatedAt: new Date(),
  details: { performedBy: "system", changes: { name: "Alice Smith" } }
});

// Verify sharding status
sh.status();