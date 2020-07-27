const Sequelize = require('sequelize');

const {frameColors, Boost_Attributes, Rarities} = require('../config.json');

const sequelize = new Sequelize('database', 'user', 'password', {
	host: 'localhost',
	dialect: 'sqlite',
	logging: false,
	storage: 'database.sqlite',
});

const kartList = sequelize.import('../models/kartList');

kartList.sync();

module.exports = {
	name: 'add-kart',
	description: 'Add a Kart',
	args: true,
	usage: '[Driver Name] [Boost Attribute Number] [Rarity] [Your Color] [exclusivity] [spotlights] [releaseDate]\
 [kart Image] [topShelf Courses] [middleShelf Courses]',
	async execute(message, args) {
		if(args.length !== 10) return;
		const kartName = args.shift().replace(/-/g, " ");

		const boostAttribute = parseInt(args.shift(), 10);
		if(boostAttribute > 10 || boostAttribute < 0)
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

		const kartImage = args.shift();
		if(kartImage.match(/\.(jpeg|jpg|gif|png)$/) === null)
			return message.reply('Not a valid picture.')

		const topShelf = args.shift().replace(/-/g, " ");
		const middleShelf = args.shift().replace(/-/g, " ");

		try {
			const kart = await kartList.create({
				kartName: kartName,
				exclusivity: exclusivity,
				kartImage: kartImage,
				spotlights: spotlights,
				releaseDate: releaseDate,
				rarity: rarity,
				topShelf: topShelf,
				middleShelf: middleShelf,
				color: color,
				boostAttribute: boostAttribute,
			});
			return message.channel.send(`Kart added.`);
		}
		catch (e) {
			if (e.name === 'SequelizeUniqueConstraintError') {
				return message.reply('That kart already exists.');
			}
			console.log(e);
			return message.reply('Something went wrong with adding the kart.');
		}

	},
};