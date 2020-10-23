const express = require("express");
const bodyParser = require("body-parser");
const cors = require("cors");

const app = express();

var corsOptions = {
    origin: "*"
};

app.use(cors(corsOptions));

app.use(bodyParser.json());


app.use(bodyParser.urlencoded({ extended: true }));

app.use(express.static('public'));

const db = require("./app/models");


db.sequelize.sync();

require("./app/routes/bicycle.routes")(app);

// set port, listen for requests
const PORT = process.env.PORT || 8080;
app.listen(PORT, () => {
    console.log(`Server is running on port ${PORT}.`);
});