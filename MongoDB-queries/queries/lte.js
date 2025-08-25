// lte.js (less than or equal)
// Find subscriptions ending on or before 2024-12-31
db.subscriptions.find({ endDate: { $lte: new Date("2024-12-31") } });