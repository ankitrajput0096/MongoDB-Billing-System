// array_operators.js
// Analyze user hobbies with array operators
db.users.aggregate([
  { $match: { isActive: true } },
  { $project: {
      name: 1,
      hobbies: 1,
      hobbyCount: { $size: "$hobbies" },
      firstHobby: { $arrayElemAt: ["$hobbies", 0] },
      hasCooking: { $in: ["cooking", "$hobbies"] },
      isHobbiesArray: { $isArray: "$hobbies" }
  }}
]);