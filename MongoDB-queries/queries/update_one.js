db.users.updateOne(
  { "_id": db.users.findOne({ "email": "michael.chen@example.com" })._id },
  { "$set": { "balance": 200.09 } }
)