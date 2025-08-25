// lookup_aggregation.js
// Join transactions with user information
db.transactions.aggregate([
  { $match: { status: "completed" } },
  { $lookup: {
      from: "users",
      localField: "user_id",
      foreignField: "_id",
      as: "userInfo"
  }},
  { $unwind: "$userInfo" },
  { $project: {
      transactionId: "$_id",
      amount: 1,
      timestamp: 1,
      userName: "$userInfo.name",
      userEmail: "$userInfo.email",
      userBalance: "$userInfo.balance"
  }},
  { $sort: { timestamp: -1 } }
]);