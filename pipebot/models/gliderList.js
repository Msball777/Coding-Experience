module.exports = (sequelize, DataTypes) => {
	return sequelize.define('gliderList', {
		gliderName: {
			type: DataTypes.STRING,
			primaryKey: true,
		},
		exclusivity: DataTypes.STRING,
		gliderImage: DataTypes.STRING,
		spotlights: DataTypes.TEXT,
		releaseDate: DataTypes.STRING,
		rarity: DataTypes.INTEGER,
		topShelf: DataTypes.TEXT,
		middleShelf: DataTypes.TEXT,
		color: DataTypes.STRING,
		boostItem: {
			type: DataTypes.INTEGER,
			defaultValue: 0,
			allowNull: false,
		}
	}, {
		timestamps: false,
	});
};