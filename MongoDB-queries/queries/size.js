// size.js (match array size)
// Find invoices with exactly 2 items
db.invoices.find({ items: { $size: 2 } });