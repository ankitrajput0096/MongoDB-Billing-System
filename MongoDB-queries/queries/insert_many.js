db.transactions.insertMany([
  {
    "user_id": db.users.findOne({ "email": "david.kim@example.com" })._id,
    "amount": 10.05,
    "status": "pending",
    "timestamp": new Date(),
    "createdAt": new Date(),
    "updatedAt": new Date(),
    "metadata": { "gateway": "Test", "transaction_id": "test1" }
  },
  {
    "user_id": db.users.findOne({ "email": "michael.chen@example.com" })._id,
    "amount": 20.09,
    "status": "completed",
    "timestamp": new Date(),
    "createdAt": new Date(),
    "updatedAt": new Date(),
    "metadata": { "gateway": "Test", "transaction_id": "test2" }
  }
])

