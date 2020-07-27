module.exports = (sequelize, DataTypes) => {
	return sequelize.define('rankList', {
		tourName: {
			type: DataTypes.STRING,
			primaryKey: true,
		},
		trackList: DataTypes.STRING,
		rankOne: DataTypes.INTEGER,
		rankTwo: DataTypes.INTEGER,
		startDate: DataTypes.STRING,
		endDate: DataTypes.STRING,
		tourImage: DataTypes.STRING,
		tourNewDrivers: DataTypes.TEXT,
		tourNewKarts: DataTypes.TEXT,
		tourNewGliders: DataTypes.TEXT,
		tourNewTracks: DataTypes.TEXT
	}, {
		timestamps: false,
	});
};