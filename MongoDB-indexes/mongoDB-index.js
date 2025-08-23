// mongoDB-indexes.js
// Get billing database
const dbBilling = db.getSiblingDB("billing");

// Users Collection Indexes
// Note: Cannot create unique index on email in sharded collection without including shard key
dbBilling.users.createIndex({ "billingAddress.zip": 1 }); // Single field (embedded)
dbBilling.users.createIndex({ name: 1, isActive: -1 }); // Compound
dbBilling.users.createIndex({ hobbies: 1 }); // Multikey (array)
dbBilling.users.createIndex({ name: "text" }); // Text

// Transactions Collection Indexes
dbBilling.transactions.createIndex({ timestamp: -1 }); // Single field
dbBilling.transactions.createIndex({ user_id: 1, status: 1 }); // Compound
dbBilling.transactions.createIndex({ "metadata.gateway": 1 }); // Single field (embedded)
dbBilling.transactions.createIndex({ "metadata.transaction_id": 1 }); // Single field (embedded)

// Invoices Collection Indexes
dbBilling.invoices.createIndex({ dueDate: 1 }); // Single field
dbBilling.invoices.createIndex({ user_id: 1, status: 1 }); // Compound
dbBilling.invoices.createIndex({ "items.productName": 1 }); // Multikey (embedded array)

// Subscriptions Collection Indexes
dbBilling.subscriptions.createIndex({ status: 1 }); // Single field
dbBilling.subscriptions.createIndex({ "plan.billingCycle": 1, startDate: -1 }); // Compound (embedded)
dbBilling.subscriptions.createIndex({ endDate: 1 }); // Single field

// Audit Logs Collection Indexes
dbBilling.audit_logs.createIndex({ entityType: 1, entityId: 1 }); // Compound
dbBilling.audit_logs.createIndex({ timestamp: -1 }); // Single field
dbBilling.audit_logs.createIndex({ action: "hashed" }); // Hashed

// Verify all indexes
print("Users indexes:");
dbBilling.users.getIndexes();
print("\nTransactions indexes:");
dbBilling.transactions.getIndexes();
print("\nInvoices indexes:");
dbBilling.invoices.getIndexes();
print("\nSubscriptions indexes:");
dbBilling.subscriptions.getIndexes();
print("\nAudit logs indexes:");
dbBilling.audit_logs.getIndexes();

print("All indexes created successfully!");

// Index Category Summary
print("\n=== INDEX CATEGORY SUMMARY ===");
print("USERS COLLECTION:");
print("  - billingAddress.zip: Single field (embedded)");
print("  - name + isActive: Compound");
print("  - hobbies: Multikey (array)");
print("  - name: Text");
print("\nTRANSACTIONS COLLECTION:");
print("  - timestamp: Single field");
print("  - user_id + status: Compound");
print("  - metadata.gateway: Single field (embedded)");
print("  - metadata.transaction_id: Single field (embedded)");
print("\nINVOICES COLLECTION:");
print("  - dueDate: Single field");
print("  - user_id + status: Compound");
print("  - items.productName: Multikey (embedded array)");
print("\nSUBSCRIPTIONS COLLECTION:");
print("  - status: Single field");
print("  - plan.billingCycle + startDate: Compound (embedded)");
print("  - endDate: Single field");
print("\nAUDIT LOGS COLLECTION:");
print("  - entityType + entityId: Compound");
print("  - timestamp: Single field");
print("  - action: Hashed");