// string_operators.js
// Format user information with string operators
db.users.aggregate([
  { $match: { isActive: true } },
  { $project: {
      name: 1,
      email: 1,
      uppercaseName: { $toUpper: "$name" },
      lowercaseEmail: { $toLower: "$email" },
      emailDomain: {
        $arrayElemAt: [
          { $split: ["$email", "@"] },
          1
        ]
      }
  }}
]);