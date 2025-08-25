// arithmetic_operators.js
// Calculate invoice statistics with various arithmetic operations
db.invoices.aggregate([
  { $match: { status: "paid" } },
  { $project: {
      invoiceId: "$_id",
      totalAmount: 1,
      itemCount: { $size: "$items" },
      avgItemPrice: { 
        $divide: ["$totalAmount", { $sum: "$items.quantity" }] 
      },
      discountAmount: { $multiply: ["$totalAmount", 0.1] },
      finalAmount: { $subtract: ["$totalAmount", { $multiply: ["$totalAmount", 0.1] }] },
      roundedAmount: { $floor: "$totalAmount" }
  }}
]);