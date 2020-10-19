const db = require("../models");
const Bicycle = db.bicycles;
const Op = db.Sequelize.Op;

// Create and Save a new Bicycle
// req --> request (contains the body)
exports.create = (req, res) => {
  // Validate request
  if (!req.body.brand || !req.body.model) {
    res.status(400).send({
      message: "Content can not be empty!"
    });
    return;
  }

  // Create a Bicycle
  const bicycle = {
    brand: req.body.brand,
    model: req.body.model
  };

  // Save Bicycle in the database
  Bicycle.create(bicycle)
    .then(data => {
      res.send(data);
    })
    .catch(err => {
      res.status(500).send({
        message:
          err.message || "Some error occurred while creating the Bicycle."
      });
    });
};

// Retrieve all Bicycles from the database.
exports.findAll = (req, res) => {
  
    Bicycle.findAll()
      .then(data => {
        res.send(data);
      })
      .catch(err => {
        res.status(500).send({
          message:
            err.message || "Some error occurred while retrieving bicycles."
        });
      });
};

// Find a single Bicycle with an id
exports.findOne = (req, res) => {
  let id = req.params.id;
  Bicycle.findByPk(id)
    .then(data => {
      console.log("estos son los datos")
      console.log(data);
      if(!data){
        res.status(400).send({
          message:
            "No Bicycle found with that id"
        })
      }
      res.send(data);
      return;
    })
    .catch(err => {
      console.log(err.message);
      console.log("hola");
      res.status(500).send({
        message:
          err.message || "Some error occurred while retrieving bicycle with id"
      });
      return;
    });
};

// Update a Bicycle by the id in the request
exports.update = (req, res) => {
  const id = req.params.id;

  Bicycle.update(req.body, {
    where: { id: id }
  })
    .then(num => {
      if (num == 1) {
        res.send({
          message: "Bicycle was updated successfully."
        });
      } else {
        res.send({
          message: `Cannot update Bicycle with id=${id}. Maybe Bicycle was not found or req.body is empty!`
        });
      }
    })
    .catch(err => {
      res.status(500).send({
        message: "Error updating Bicycle with id=" + id
      });
    });
};

// Delete a Tutorial with the specified id in the request
exports.delete = (req, res) => {
  const id = req.params.id;

  Bicycle.destroy({
    where: { id: id }
  })
    .then(num => {
      if (num == 1) {
        res.send({
          message: "Bicycle was deleted successfully!"
        });
      } else {
        res.send({
          message: `Cannot delete Bicycle with id=${id}. Maybe Bicycle was not found!`
        });
      }
    })
    .catch(err => {
      res.status(500).send({
        message: "Could not delete Bicycle with id=" + id
      });
    });
};

// Delete all Bicycles from the database.
exports.deleteAll = (req, res) => {
  Bicycle.destroy({
    where: {},
    truncate: false
  })
    .then(nums => {
      res.send({ message: `${nums} Bicycles were deleted successfully!` });
    })
    .catch(err => {
      res.status(500).send({
        message:
          err.message || "Some error occurred while removing all Bicycles."
      });
    });
};