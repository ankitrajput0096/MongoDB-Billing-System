// group_aggregation.js
// Group invoices by status and calculate statistics
db.invoices.aggregate([
  {
    $group: {
      _id: "$status",
      count: { $sum: 1 },
      totalAmount: { $sum: "$totalAmount" },
      avgAmount: { $avg: "$totalAmount" },
      minAmount: { $min: "$totalAmount" },
      maxAmount: { $max: "$totalAmount" },
      invoiceIds: { $push: "$_id" }
    }
  },
  { $sort: { count: -1 } }
]);