// nor.js (nor)
// Find invoices that are neither pending nor overdue
db.invoices.find({ 
  $nor: [
    { status: "pending" },
    { status: "overdue" }
  ] 
});