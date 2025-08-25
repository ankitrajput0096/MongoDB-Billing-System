// explain.js
// Analyze the query execution plan
db.users.find({ isActive: true }).explain("executionStats");