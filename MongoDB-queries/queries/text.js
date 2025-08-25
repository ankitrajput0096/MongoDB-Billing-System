// text.js (text search)
// Find users with "Johnson" in their name field using text search
// This will work with the existing text index on the name field
db.users.find({ $text: { $search: "Johnson" } });