// all.js (match all array elements)
// Find users with both "yoga" and "cooking" hobbies
db.users.find({ hobbies: { $all: ["yoga", "cooking"] } });