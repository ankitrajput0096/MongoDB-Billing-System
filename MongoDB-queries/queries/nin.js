// nin.js (not in array)
// Find audit logs excluding specific actions
db.audit_logs.find({ action: { $nin: ["created", "updated"] } });