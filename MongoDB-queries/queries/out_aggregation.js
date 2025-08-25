// out_aggregation.js
// Create a summary of user subscriptions and save to a new collection
db.subscriptions.aggregate([
  { $match: { status: { $in: ["active", "cancelled"] } } },
  {
    $group: {
      _id: {
        planName: "$plan.planName",
        billingCycle: "$plan.billingCycle"
      },
      totalSubscriptions: { $sum: 1 },
      totalRevenue: { $sum: "$plan.price" },
      averagePrice: { $avg: "$plan.price" },
      userCount: { $addToSet: "$user_id" }
    }
  },
  {
    $addFields: {
      uniqueUsers: { $size: "$userCount" }
    }
  },
  { $sort: { totalRevenue: -1 } },
  {
    $out: "subscription_summary" // Creates a new collection with results
  }
]);