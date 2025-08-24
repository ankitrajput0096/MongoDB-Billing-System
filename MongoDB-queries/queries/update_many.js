db.transactions.updateMany(
  { "status": "pending" },
  { "$set": { "status": "completed" } }
)