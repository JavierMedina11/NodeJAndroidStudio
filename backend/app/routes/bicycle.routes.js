module.exports = app => {
    const bicycles = require("../controllers/bicycle.controller.js");
  
    var router = require("express").Router();
  
    // Create a new travel
    router.post("/", bicycles.create);
  
    // Retrieve all travels
    router.get("/", bicycles.findAll);
  
    // Retrieve a single travel with id
    router.get("/:id", bicycles.findOne);
  
    // Update a travel with id
    router.put("/:id", bicycles.update);
  
    // Delete a travel with id
    router.delete("/:id", bicycles.delete);
  
    // Delete all travels
    router.delete("/", bicycles.deleteAll);
  
    app.use('/api/travels', router);
  };