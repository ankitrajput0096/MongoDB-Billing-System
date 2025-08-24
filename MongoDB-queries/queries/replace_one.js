db.transactions.replaceOne(
  { "metadata.transaction_id": "test1" },
  {
    "user_id": db.users.findOne({ "email": "david.kim@example.com" })._id,
    "amount": 15.07,
    "status": "failed",
    "timestamp": new Date(),
    "createdAt": new Date(),
    "updatedAt": new Date(),
    "metadata": { "gateway": "NewTest", "transaction_id": "newtest1" }
  }
)