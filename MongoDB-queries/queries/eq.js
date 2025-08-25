// eq.js (equal to)
// Find users with exactly $150.75 balance
db.users.find({ balance: { $eq: 150.75 } });