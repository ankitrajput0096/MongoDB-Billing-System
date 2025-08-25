// limit.js
// Get first 5 active users
db.users.find({ isActive: true }).limit(5);