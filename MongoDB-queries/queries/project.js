// project.js
// Get user names and emails only
db.users.find({}, { name: 1, email: 1, _id: 0 });