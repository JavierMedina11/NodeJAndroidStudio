module.exports = app => {
    const bicycles = require("../controllers/bicycle.controller.js");
  
    var router = require("express").Router();
  
    // Create a new Bicycle
    router.post("/", bicycles.create);
  
    // Retrieve all Bicycles
    router.get("/", bicycles.findAll);
  
    // Retrieve a single bicycle with id
    router.get("/:id", bicycles.findOne);
  
    // Update a bicycle with id
    router.put("/:id", bicycles.update);
  
    // Delete a bicycle with id
    router.delete("/:id", bicycles.delete);
  
    // Delete all bicycles
    router.delete("/", bicycles.deleteAll);
  
    app.use('/api/bicycles', router);
  };