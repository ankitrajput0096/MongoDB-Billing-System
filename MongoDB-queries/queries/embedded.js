// embedded_query.js
// Embedded Document Query: Find transactions where the metadata gateway is "Stripe"
db.transactions.find({ "metadata.gateway": "Stripe" });