const db = require("../models");
const Bicycle = db.bicycles;
const Op = db.Sequelize.Op;

exports.create = (req, res) => {
  if (!req.body.brand || !req.body.model) {
    res.status(400).send({
      message: "Content can not be empty!"
    });
    return;
  }

  const bicycle = {
    brand: req.body.brand,
    model: req.body.model
  };

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