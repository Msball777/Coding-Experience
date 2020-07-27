module.exports = (sequelize, DataTypes) => {
	return sequelize.define('rankList', {
		rankName: {
			type: DataTypes.STRING,
			primaryKey: true,
		},
		cupName: DataTypes.STRING,
		rankNumber: DataTypes.INTEGER,
		startDate: DataTypes.STRING,
		endDate: DataTypes.STRING,
		cupImage: DataTypes.STRING,
		cupTracks: DataTypes.TEXT,
		topShelfKart1: DataTypes.TEXT,
		topShelfKart2: DataTypes.TEXT,
		topShelfKart3: DataTypes.TEXT,
		topShelfGlider1: DataTypes.TEXT,
		topShelfGlider2: DataTypes.TEXT,
		topShelfGlider3: DataTypes.TEXT,
		topShelfDriver1: DataTypes.TEXT,
		topShelfDriver2: DataTypes.TEXT,
		topShelfDriver3: DataTypes.TEXT,
		color: DataTypes.STRING
	}, {
		timestamps: false,
	});
};