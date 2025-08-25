// not.js (not)
// Find users who are not active
db.users.find({ isActive: { $not: { $eq: true } } });