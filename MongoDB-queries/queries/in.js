// in.js (in array)
// Find transactions with specific statuses
db.transactions.find({ status: { $in: ["completed", "pending"] } });