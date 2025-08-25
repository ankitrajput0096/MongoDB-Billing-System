// gte.js (greater than or equal)
// Find invoices with due date on or after 2024-08-01
db.invoices.find({ dueDate: { $gte: new Date("2024-08-01") } });