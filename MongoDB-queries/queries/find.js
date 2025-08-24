
db.transactions.find({ "user_id": db.users.findOne({ "email": "alice.johnson@example.com" })._id })