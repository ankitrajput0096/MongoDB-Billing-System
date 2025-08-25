// count.js
// Count all active users
db.users.find({ isActive: true }).count();