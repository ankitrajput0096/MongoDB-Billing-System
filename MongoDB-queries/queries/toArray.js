// toArray.js
// Get all active users as an array
var activeUsers = db.users.find({ isActive: true }).toArray();
print("the active users are: ")
print(activeUsers);