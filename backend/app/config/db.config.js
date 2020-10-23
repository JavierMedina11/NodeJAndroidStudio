module.exports = {
  HOST: "localhost",
  USER: "root",
  PASSWORD: "test1234",
  DB: "db_travels",
  dialect: "mysql",
  pool: {
    max: 5,
    min: 0,
    acquire: 30000,
    idle: 10000
  }
};