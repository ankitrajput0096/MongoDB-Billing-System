// mod.js (modulo operation)
// Find users where amount modulo 10 equals 0
db.users.find({ balance: { $mod: [10, 0] } });