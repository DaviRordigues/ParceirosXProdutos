show dbs
use order-management

db.createUser(
{
user: "admin",
pwd: "admin",
roles: [
{
role: "userAdminAnyDatabase",
db: "admin"
},
{
role: "readWriteAnyDatabase",
db: "admin"
}
]
}
)