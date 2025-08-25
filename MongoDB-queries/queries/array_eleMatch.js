// array_elemMatch_query.js
// Array Element Query with $elemMatch: Find invoices where the items array has an element with productName "Webcam" and quantity 1
db.invoices.find({ items: { $elemMatch: { productName: "Webcam", quantity: 1 } } });