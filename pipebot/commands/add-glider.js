const Sequelize = require('sequelize');

const {frameColors, Boost_Items, Rarities} = require('../config.json');

const sequelize = new Sequelize('database', 'user', 'password', {
	host: 'localhost',
	dialect: 'sqlite',
	logging: false,
	storage: 'database.sqlite',
});

const gliderList = sequelize.import('../models/gliderList');

gliderList.sync();

module.exports = {
	name: 'add-glider',
	description: 'Add a glider',
	args: true,
	usage: '[Driver Name] [Item Boost Number] [Rarity] [Your Color] [exclusivity] [spotlights] [releaseDate]\
 [glider Image] [topShelf Courses] [middleShelf Courses]',
	async execute(message, args) {
		if(args.length !== 10) return;
		const gliderName = args.shift().replace(/-/g, " ");

		const boostItem = parseInt(args.shift(), 10);
		if(boostItem > 10 || boostItem < 0)
			return message.reply('Invalid boost item selection.');

		const rarity = parseInt(args.shift());
		if(rarity > 2 || rarity < 0)
			return message.reply('Invalid rarity selection.');

		const color = args.shift();
		if(!(color in frameColors))
			return message.reply('Invalid pipeframe color.');

		let exclusivity = args.shift();
		exclusivity = exclusivity.replace(/-/g, " ");
		let spotlights = args.shift();
		spotlights = spotlights.replace(/-/g, " ");
		let releaseDate = args.shift();
		releaseDate = releaseDate.replace(/-/g, " ");

		const gliderImage = args.shift();
		if(gliderImage.match(/\.(jpeg|jpg|gif|png)$/) === null)
			return message.reply('Not a valid picture.')

		const topShelf = args.shift().replace(/-/g, " ");
		const middleShelf = args.shift().replace(/-/g, " ");

		console.log(exclusivity);

		try {
			const glider = await gliderList.create({
				gliderName: gliderName,
				exclusivity: exclusivity,
				gliderImage: gliderImage,
				spotlights: spotlights,
				releaseDate: releaseDate,
				rarity: rarity,
				topShelf: topShelf,
				middleShelf: middleShelf,
				boostItem: boostItem,
				color: color,
			});
			return message.channel.send(`Glider ${glider.gliderName} added.`);
		}
		catch (e) {
			if (e.name === 'SequelizeUniqueConstraintError') {
				return message.reply('That glider already exists.');
			}
			return message.reply('Something went wrong with adding the glider.');
		}

	},
};