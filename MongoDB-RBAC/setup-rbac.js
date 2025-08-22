// setup-rbac.js
// Role-Based Access Control setup for Billing System

// Use admin database for creating roles and users
db = db.getSiblingDB('admin');

// Create custom roles for the billing system
db.createRole({
  role: "billingAdmin",
  privileges: [
    {
      resource: { db: "billing", collection: "" },
      actions: [ "find", "insert", "update", "remove", "createCollection", "dropCollection", "createIndex", "dropIndex", "collStats" ]
    },
    {
      resource: { db: "billing", collection: "users" },
      actions: [ "find", "insert", "update", "remove" ]
    },
    {
      resource: { db: "billing", collection: "transactions" },
      actions: [ "find", "insert", "update", "remove" ]
    },
    {
      resource: { db: "billing", collection: "invoices" },
      actions: [ "find", "insert", "update", "remove" ]
    },
    {
      resource: { db: "billing", collection: "subscriptions" },
      actions: [ "find", "insert", "update", "remove" ]
    },
    {
      resource: { db: "billing", collection: "audit_logs" },
      actions: [ "find", "insert" ]
    }
  ],
  roles: []
});

db.createRole({
  role: "billingUser",
  privileges: [
    {
      resource: { db: "billing", collection: "users" },
      actions: [ "find", "update" ]
    },
    {
      resource: { db: "billing", collection: "transactions" },
      actions: [ "find", "insert" ]
    },
    {
      resource: { db: "billing", collection: "invoices" },
      actions: [ "find" ]
    },
    {
      resource: { db: "billing", collection: "subscriptions" },
      actions: [ "find" ]
    }
  ],
  roles: []
});

db.createRole({
  role: "billingReadOnly",
  privileges: [
    {
      resource: { db: "billing", collection: "" },
      actions: [ "find" ]
    }
  ],
  roles: []
});

db.createRole({
  role: "billingAuditor",
  privileges: [
    {
      resource: { db: "billing", collection: "audit_logs" },
      actions: [ "find" ]
    },
    {
      resource: { db: "billing", collection: "transactions" },
      actions: [ "find" ]
    }
  ],
  roles: []
});

// Create users with different roles
db.createUser({
  user: "billing_admin",
  pwd: "admin_password_123",
  roles: [ "billingAdmin" ]
});

db.createUser({
  user: "billing_app",
  pwd: "app_password_123",
  roles: [ "billingUser" ]
});

db.createUser({
  user: "billing_analyst",
  pwd: "analyst_password_123",
  roles: [ "billingReadOnly" ]
});

db.createUser({
  user: "billing_auditor",
  pwd: "auditor_password_123",
  roles: [ "billingAuditor" ]
});

// Create a superuser with cluster admin privileges (for emergency access)
// db.createUser({
//   user: "billing_superadmin",
//   pwd: "superadmin_password_123",
//   roles: [ 
//     { role: "root", db: "admin" },
//     { role: "clusterAdmin", db: "admin" }
//   ]
// });

print("RBAC setup completed successfully!");