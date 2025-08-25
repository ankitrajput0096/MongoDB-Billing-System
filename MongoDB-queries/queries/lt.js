// lt.js (less than)
// Find users with balance less than $100
db.users.find({ balance: { $lt: 100 } });