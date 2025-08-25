// simple_match_group.js
// Group transactions by status and count occurrences
db.transactions.aggregate([
  { $match: { status: { $in: ["completed", "pending", "failed"] } } },
  { $group: { _id: "$status", count: { $sum: 1 } } }
]);