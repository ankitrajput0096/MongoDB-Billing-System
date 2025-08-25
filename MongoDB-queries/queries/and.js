// and.js (And)
// Find active users with balance over $200
db.users.find({ 
  $and: [
    { isActive: true },
    { balance: { $gt: 200 } }
  ] 
});