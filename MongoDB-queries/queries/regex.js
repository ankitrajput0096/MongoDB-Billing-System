// regex.js (Regular expression)
// Find users with email from example.com domain
db.users.find({ email: { $regex: /@example\.com$/ } });