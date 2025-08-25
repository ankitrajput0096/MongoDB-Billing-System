// aggregation_limit.js
// Get top 5 highest transactions
db.transactions.aggregate([
  { $sort: { amount: -1 } },
  { $limit: 5 },
  { $project: {
      transactionId: "$_id",
      userId: "$user_id",
      amount: 1,
      status: 1,
      timestamp: 1
  }}
]);