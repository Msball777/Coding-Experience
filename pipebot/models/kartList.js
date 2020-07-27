module.exports = (sequelize, DataTypes) => {
	return sequelize.define('kartList', {
		kartName: {
			type: DataTypes.STRING,
			primaryKey: true,
		},
		exclusivity: DataTypes.STRING,
		kartImage: DataTypes.STRING,
		spotlights: DataTypes.TEXT,
		releaseDate: DataTypes.STRING,
		rarity: DataTypes.INTEGER,
		topShelf: DataTypes.TEXT,
		middleShelf: DataTypes.TEXT,
		color: DataTypes.STRING,
		boostAttribute: {
			type: DataTypes.INTEGER,
			defaultValue: 0,
			allowNull: false,
		}
	}, {
		timestamps: false,
	});
};