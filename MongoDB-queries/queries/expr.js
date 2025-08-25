// expr.js (expression)
// Find users where the balance is greater than twice their age (if age exists)
// This demonstrates conditional logic and field comparison
db.users.find({
  $expr: {
    $and: [
      { $ne: ["$age", null] },
      { $gt: ["$balance", { $multiply: ["$age", 2] }] }
    ]
  }
});