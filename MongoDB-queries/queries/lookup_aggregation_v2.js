// lookup_aggregation.js
// Join transactions with user information to create enriched documents
db.transactions.aggregate([
  {
    $lookup: {
      from: "users",
      localField: "user_id",
      foreignField: "_id",
      as: "user_info"
    }
  },
  { $unwind: "$user_info" },
  {
    $project: {
      _id: 1,
      amount: 1,
      status: 1,
      timestamp: 1,
      userName: "$user_info.name",
      userEmail: "$user_info.email",
      userBalance: "$user_info.balance"
    }
  }
]);