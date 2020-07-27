module.exports = (sequelize, DataTypes) => {
	return sequelize.define('trackList', {
		trackName: {
			type: DataTypes.STRING,
			primaryKey: true,
		},
		releaseDate: DataTypes.STRING,
		trackImage: DataTypes.STRING,
		appearances: DataTypes.TEXT,
		topShelfKart: DataTypes.TEXT,
		middleShelfKart: DataTypes.TEXT,
		topShelfGlider: DataTypes.TEXT,
		middleShelfGlider: DataTypes.TEXT,
		topShelfDriver: DataTypes.TEXT,
		middleShelfDriver: DataTypes.TEXT,
		color: DataTypes.STRING
	}, {
		timestamps: false,
	});
};