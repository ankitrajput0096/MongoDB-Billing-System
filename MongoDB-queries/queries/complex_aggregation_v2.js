// complex_aggregation.js
// Analyze subscription patterns with user details and billing information
db.subscriptions.aggregate([
  // Stage 1: Match active subscriptions
  { $match: { status: "active" } },
  
  // Stage 2: Lookup user information
  {
    $lookup: {
      from: "users",
      localField: "user_id",
      foreignField: "_id",
      as: "user_info"
    }
  },
  { $unwind: "$user_info" },
  
  // Stage 3: Calculate annual value
  {
    $addFields: {
      annualValue: {
        $cond: {
          if: { $eq: ["$plan.billingCycle", "yearly"] },
          then: "$plan.price",
          else: { $multiply: ["$plan.price", 12] }
        }
      }
    }
  },
  
  // Stage 4: Group by plan type
  {
    $group: {
      _id: "$plan.planName",
      subscriberCount: { $sum: 1 },
      totalAnnualValue: { $sum: "$annualValue" },
      avgUserBalance: { $avg: "$user_info.balance" },
      userLocations: { $addToSet: "$user_info.billingAddress.city" }
    }
  },
  
  // Stage 5: Sort by total annual value
  { $sort: { totalAnnualValue: -1 } },
  
  // Stage 6: Project final format
  {
    $project: {
      _id: 0,
      planName: "$_id",
      subscriberCount: 1,
      totalAnnualValue: 1,
      avgUserBalance: { $round: ["$avgUserBalance", 2] },
      locationCount: { $size: "$userLocations" }
    }
  }
]);