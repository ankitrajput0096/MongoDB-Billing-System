// Insert a new user with name John Doe, email john.doe@example.com
db.users.insertOne(
  {
    "name": "Ankit Rajput",
    "email": "ankitrajput@gmail.com",
    "age": 28,
    "balance": 1500000.75,
    "isActive": false,
    "createdAt": new Date("2024-02-15T10:30:00Z"),
    "updatedAt": new Date("2024-08-20T14:45:00Z"),
    "hobbies": ["yoga", "cooking", "swimming", "gym"],
    "billingAddress": {"city": "New York", "zip": NumberInt(10001), "country": "USA"}
  }
);