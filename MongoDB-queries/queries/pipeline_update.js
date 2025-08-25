// pipeline_update.js
// Update subscriptions with calculated duration using aggregation pipeline
db.subscriptions.updateMany(
  { endDate: { $exists: true } },
  [
    {
      $set: {
        durationDays: {
          $ceil: {
            $divide: [
              { $subtract: ["$endDate", "$startDate"] },
              1000 * 60 * 60 * 24 // Convert milliseconds to days
            ]
          }
        },
        updatedAt: new Date()
      }
    }
  ]
);