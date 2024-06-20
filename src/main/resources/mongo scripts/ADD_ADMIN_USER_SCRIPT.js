use records
db.createUser(
  {
    user: "oneskillusername",
    pwd: "oneskillpassword",
    roles: [ { role: "userAdmin", db: "records" } ]
  }
)