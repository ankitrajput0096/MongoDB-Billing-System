// map.js
// Create an array of user names
var userNames = db.users.find().map(function(user) {
  return user.name;
});
print("usernames are: ");
print(userNames);