db.users.insertOne({
  "name": "Test User",
  "email": "test@example.com",
  age: 30,
  balance: 123.45,
  largeNumber: NumberLong("123456789012345"),
  preciseBalance: NumberDecimal("123.456789"),
  isActive: true,
  createdAt: new Date(),
  updatedAt: new Date(),
  hobbies: ["reading", "sports", "gym"],
  billingAddress: { city: "Seattle", zip: 98101, country: "USA" },
  nullField: null,
  timestamp: new Timestamp(1, 2),
  minKey: new MinKey(),
  maxKey: new MaxKey()
}
)