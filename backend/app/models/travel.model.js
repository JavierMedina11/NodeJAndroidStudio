module.exports = (sequelize, Sequelize) => {
  const Bicycle = sequelize.define("travel", {
    name: {
      type: Sequelize.STRING
    },
    descripcion: {
      type: Sequelize.STRING
    }
  }, { timestamps: false});

  return Bicycle;
};