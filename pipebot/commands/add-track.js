const Sequelize = require('sequelize');

const {frameColors, Boost_Items, Rarities} = require('../config.json');

const sequelize = new Sequelize('database', 'user', 'password', {
	host: 'localhost',
	dialect: 'sqlite',
	logging: false,
	storage: 'database.sqlite',
});

const trackList = sequelize.import('../models/trackList');

trackList.sync();

module.exports = {
	name: 'add-track',
	description: 'Add a track',
	args: true,
	usage: '[]',
	async execute(message, args) {
		if(args.length !== 11) return;
		let trackName = args.shift().replace(/-/g, " ");
		trackName = trackName.replace(/-/g, " ");

		const color = args.shift();
		if(!(color in frameColors))
			return message.reply('Invalid frame color.');

		const releaseDate = args.shift().replace(/-/g, " ");

		const trackImage = args.shift();
		if(trackImage.match(/\.(jpeg|jpg|gif|png)$/) === null)
			return message.reply('Not a valid picture.');

		let appearances = args.shift();
		appearances = appearances.replace(/-/g, " ");

		topShelfDriver = args.shift().replace(/-/g, " ");
		middleShelfDriver = args.shift().replace(/-/g, " ");
		topShelfKart = args.shift().replace(/-/g, " ");
		middleShelfKart = args.shift().replace(/-/g, " ");
		topShelfGlider = args.shift().replace(/-/g, " ");
		middleShelfGlider = args.shift().replace(/-/g, " ");

		try {
			const track = await trackList.create({
				trackName: trackName,
				releaseDate: releaseDate,
				trackImage: trackImage,
				appearances: appearances,
				topShelfKart: topShelfKart,
				middleShelfKart: middleShelfKart,
				topShelfGlider: topShelfGlider,
				middleShelfGlider: middleShelfGlider,
				topShelfDriver: topShelfDriver,
				middleShelfDriver: middleShelfDriver,
				color: color,
			});
			return message.channel.send(`Track ${track.trackName} added.`);
		}
		catch (e) {
			if (e.name === 'SequelizeUniqueConstraintError') {
				return message.reply('That track already exists.');
			}
			return message.reply('Something went wrong with adding the track.');
		}

	},
};