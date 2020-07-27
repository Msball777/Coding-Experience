const Sequelize = require('sequelize');

const {frameColors, Special_Items, Rarities} = require('../config.json');

const sequelize = new Sequelize('database', 'user', 'password', {
	host: 'localhost',
	dialect: 'sqlite',
	logging: false,
	storage: 'database.sqlite',
});

const driverList = sequelize.import('../models/driverList');

driverList.sync();

module.exports = {
	name: 'add-driver',
	description: 'Add a driver',
	args: true,
	usage: '[Driver Name] [Special Item Number] [Rarity] [Pipeframe Color] [exclusivity] [traits] [spotlights] [releaseDate]\
 [driverPortrait Image] [driverEmblem Image] [topShelf Courses] [middleShelf Courses]',
	async execute(message, args) {
		if(args.length !== 12) return;
		const driverName = args.shift().replace(/-/g, " ");

		const specialItem = parseInt(args.shift(), 10);
		if(specialItem > 19 || specialItem < 0)
			return message.reply('Invalid special item selection.');

		const rarity = parseInt(args.shift());
		if(rarity > 2 || rarity < 0)
			return message.reply('Invalid rarity selection.');

		const color = args.shift();
		if(!(color in frameColors))
			return message.reply('Invalid pipeframe color.');

		let exclusivity = args.shift();
		exclusivity = exclusivity.replace(/-/g, " ");
		let traits = args.shift();
		traits = traits.replace(/-/g, " ");
		let spotlights = args.shift();
		spotlights = spotlights.replace(/-/g, " ");
		let releaseDate = args.shift();
		releaseDate = releaseDate.replace(/-/g, " ");

		const driverPortrait = args.shift();
		if(driverPortrait.match(/\.(jpeg|jpg|gif|png)$/) === null)
			return message.reply('Not a valid picture.')

		const driverEmblem = args.shift();
		if(driverEmblem.match(/\.(jpeg|jpg|gif|png)$/) === null)
			return message.reply('Not a valid picture.')

		const topShelf = args.shift().replace(/-/g, " ");
		const middleShelf = args.shift().replace(/-/g, " ");

		try {
			const driver = await driverList.create({
				driverName: driverName,
				exclusivity: exclusivity,
				traits: traits,
				driverPortrait: driverPortrait,
				driverEmblem: driverEmblem,
				spotlights: spotlights,
				releaseDate: releaseDate,
				rarity: rarity,
				topShelf: topShelf,
				middleShelf: middleShelf,
				specialItem: specialItem,
				pipeColor: color,
			});
			return message.channel.send(`Driver ${driver.driverName} added.`);
		}
		catch (e) {
			if (e.name === 'SequelizeUniqueConstraintError') {
				return message.reply('That driver already exists.');
			}
			return message.reply('Something went wrong with adding the driver.');
		}

	},
};