// multiple_accumulators.js
// Comprehensive subscription analysis
db.subscriptions.aggregate([
  { $group: {
      _id: "$plan.billingCycle",
      count: { $sum: 1 },
      totalRevenue: { $sum: "$plan.price" },
      avgPrice: { $avg: "$plan.price" },
      minPrice: { $min: "$plan.price" },
      maxPrice: { $max: "$plan.price" },
      plans: { $addToSet: "$plan.planName" },
      firstSubscription: { $first: "$startDate" },
      lastSubscription: { $last: "$startDate" }
  }},
  { $sort: { _id: 1 } }
]);