// complex_aggregation.js
// Analyze user spending patterns with multiple stages
db.transactions.aggregate([
  { $match: { status: "completed" } },
  { $group: { 
      _id: "$user_id", 
      totalSpent: { $sum: "$amount" },
      avgTransaction: { $avg: "$amount" },
      transactionCount: { $sum: 1 }
  }},
  { $sort: { totalSpent: -1 } },
  { $limit: 10 },
  { $project: {
      userId: "$_id",
      totalSpent: 1,
      avgTransaction: 1,
      transactionCount: 1,
      _id: 0
  }}
]);