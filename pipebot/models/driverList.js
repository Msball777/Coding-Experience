module.exports = (sequelize, DataTypes) => {
	return sequelize.define('driverList', {
		driverName: {
			type: DataTypes.STRING,
			primaryKey: true,
		},
		exclusivity: DataTypes.STRING,
		driverPortrait: DataTypes.STRING,
		driverEmblem: DataTypes.STRING,
		spotlights: DataTypes.TEXT,
		releaseDate: DataTypes.STRING,
		traits: DataTypes.TEXT,
		rarity: DataTypes.INTEGER,
		topShelf: DataTypes.TEXT,
		middleShelf: DataTypes.TEXT,
		specialItem: {
			type: DataTypes.INTEGER,
			defaultValue: 0,
			allowNull: false,
		},
		pipeColor: {
			type: DataTypes.STRING,
			defaultValue: "Red",
			allowNull: false,
		}
	}, {
		timestamps: false,
	});
};