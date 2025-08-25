// gt.js (Greater Than)
// Find transactions with amount greater than $50
db.transactions.find({ amount: { $gt: 50 } });