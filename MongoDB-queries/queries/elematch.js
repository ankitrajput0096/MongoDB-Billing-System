// elemMatch.js (Match array elements)
// Find invoices with items where quantity is greater than 1
db.invoices.find({ 
  items: { 
    $elemMatch: { quantity: { $gt: 1 } } 
  } 
});