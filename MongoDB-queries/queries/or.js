// or.js (or)
// Find transactions that are either completed or failed
db.transactions.find({ 
  $or: [
    { status: "completed" },
    { status: "failed" }
  ] 
});