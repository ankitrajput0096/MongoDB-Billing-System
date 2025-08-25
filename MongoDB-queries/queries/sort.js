// sort.js
// Get transactions sorted by amount in descending order
db.transactions.find().sort({ amount: -1 });