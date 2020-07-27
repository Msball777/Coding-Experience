const Sequelize = require('sequelize');

const {frameColors, Boost_Items, Rarities} = require('../config.json');

const sequelize = new Sequelize('database', 'user', 'password', {
	host: 'localhost',
	dialect: 'sqlite',
	logging: false,
	storage: 'database.sqlite',
});

const rankList = sequelize.import('../models/rankList');

rankList.sync();

module.exports = {
	name: 'add-rank',
	description: 'Add a rank',
	args: true,
	usage: '[Rank Week Name] [Cup Name] [rank number] [Your Color] [cupTracks] [start date] [end date]\
 [cup Image] [topShelf for tracks 1, 2 & 3 for driver kart and glider]',
	async execute(message, args) {
		if(args.length !== 17) return;
		let rankName = args.shift().replace(/-/g, " ");
		rankName = rankName.replace(/-/g, " ");

		let cupName = args.shift().replace(/-/g, " ");
		cupName = cupName.replace(/-/g, " ");

		let rankNumber = args.shift();
		rankNumber = parseInt(rankNumber, 10);
		if(isNaN(rankNumber))
			return message.reply('Need a number for rank number.')

		const color = args.shift();
		if(!(color in frameColors))
			return message.reply('Invalid frame color.');

		const startDate = args.shift();

		const endDate = args.shift();

		const cupImage = args.shift();
		if(cupImage.match(/\.(jpeg|jpg|gif|png)$/) === null)
			return message.reply('Not a valid picture.');

		let cupTracks = args.shift();
		cupTracks = cupTracks.replace(/-/g, " ");
		if(cupTracks.split(";").length !== 3)
			return message.reply('Ranked needs exactly 3 tracks.');

		topShelfKart1 = args.shift().replace(/-/g, " ");
		topShelfKart2 = args.shift().replace(/-/g, " ");
		topShelfKart3 = args.shift().replace(/-/g, " ");
		topShelfGlider1 = args.shift().replace(/-/g, " ");
		topShelfGlider2 = args.shift().replace(/-/g, " ");
		topShelfGlider3 = args.shift().replace(/-/g, " ");
		topShelfDriver1 = args.shift().replace(/-/g, " ");
		topShelfDriver2 = args.shift().replace(/-/g, " ");
		topShelfDriver3 = args.shift().replace(/-/g, " ");

		try {
			const rank = await rankList.create({
				rankName: rankName,
				cupName: cupName,
				color: color,
				rankNumber: rankNumber,
				startDate: startDate,
				endDate: endDate,
				cupImage: cupImage,
				cupTracks: cupTracks,
				topShelfKart1: topShelfKart1,
				topShelfKart2: topShelfKart2,
				topShelfKart3: topShelfKart3,
				topShelfGlider1: topShelfGlider1,
				topShelfGlider2: topShelfGlider2,
				topShelfGlider3: topShelfGlider3,
				topShelfDriver1: topShelfDriver1,
				topShelfDriver2: topShelfDriver2,
				topShelfDriver3: topShelfDriver3,
			});
			return message.channel.send(`Rank week ${rank.rankName} added.`);
		}
		catch (e) {
			if (e.name === 'SequelizeUniqueConstraintError') {
				return message.reply('That rank already exists.');
			}
			return message.reply('Something went wrong with adding the rank.');
		}

	},
};