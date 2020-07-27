const {Sequelize, Op} = require('sequelize');

const {frameColors, Special_Items, Rarities} = require('../config.json');

const sequelize = new Sequelize('database', 'user', 'password', {
	host: 'localhost',
	dialect: 'sqlite',
	logging: false,
	storage: 'database.sqlite',
});

const driverList = sequelize.import('../models/driverList');
const kartList = sequelize.import('../models/kartList');
const gliderList = sequelize.import('../models/gliderList');
const trackList = sequelize.import('../models/trackList');
const rankList = sequelize.import('../models/rankList');

module.exports = {
	name: 'delete',
	description: 'Delete something.',
	args: true,
	usage: '[type of object] [name of object]',
	async execute(message, args) {
		const objectType = args.shift().toLowerCase();
		let typeName = objectType.concat('Name');
		let listType = objectType.concat('List');
		console.log(attriList);
		if(!sequelize.models[listType])
			return message.reply('This type of object does not exist');
		const selectedName = args.join(' ');
		// equivalent to: DELETE from tags WHERE name = ?;
		const rowCount = await sequelize.models[listType].destroy({ where: { [typeName]: {[Op.substring]: selectedName} } });
		if (!rowCount) return message.reply('That tag did not exist.');

		return message.reply('Driver deleted.');
	},
};