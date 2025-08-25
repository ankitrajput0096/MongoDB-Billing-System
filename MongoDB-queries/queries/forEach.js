// forEach.js
// Print all user names
db.users.find().forEach(function(user) {
  print("User: " + user.name);
});