// countDocuments.js
// Count the number of active users in the system
db.users.countDocuments({ isActive: true });