// addFields_aggregation.js
// Add calculated fields to audit logs using a fixed reference date
db.audit_logs.aggregate([
  { 
    $addFields: {
      actionType: {
        $switch: {
          branches: [
            { case: { $eq: ["$action", "created"] }, then: "Creation" },
            { case: { $eq: ["$action", "updated"] }, then: "Modification" }
          ],
          default: "Other"
        }
      },
      daysSinceAction: {
        $floor: {
          $divide: [
            { $subtract: [new Date("2024-08-25"), "$timestamp"] }, // Use a fixed reference date
            1000 * 60 * 60 * 24 // Convert milliseconds to days
          ]
        }
      }
    }
  },
  { 
    $match: { 
      daysSinceAction: { $lt: 30 },
      daysSinceAction: { $gte: 0 } // Only include past dates
    } 
  }
]);