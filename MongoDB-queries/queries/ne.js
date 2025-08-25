// ne.js (not equal to)
// Find all inactive users
db.users.find({ isActive: { $ne: true } });