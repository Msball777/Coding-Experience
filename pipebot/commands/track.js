const Discord = require('discord.js');
const {Sequelize, Op} = require('sequelize');

const {frameColors} = require('../config.json');

const sequelize = new Sequelize('database', 'user', 'password', {
	host: 'localhost',
	dialect: 'sqlite',
	logging: false,
	storage: 'database.sqlite',
});

const trackList = sequelize.import('../models/trackList');

trackList.sync();

module.exports = {
	name: 'track',
	description: 'Create a tag.',
	args: true,
	usage: '[track Name]',
	async execute(message, args) {
		const trackName = args.join(' ');
		// equivalent to: SELECT * FROM tags WHERE name = 'tagName' LIMIT 1;
		const track = await trackList.findOne({ where: { trackName: {[Op.substring]: trackName} } });
		if (track) {
			// equivalent to: UPDATE tags SET usage_count = usage_count + 1 WHERE name = 'tagName';
			console.log(track.trackName);
			trackOfficialName = track.trackName.split(";")[0];
			const trackEmbed = new Discord.MessageEmbed()
				.setColor(frameColors[track.color])
				.setDescription(`--Top Shelf Drivers--\n${track.topShelfDriver.split(";").join("\n")}\
					\n\n--Top Shelf Karts--\n${track.topShelfKart.split(";").join("\n")}\
					\n\n--Top Shelf Gliders--\n${track.topShelfGlider.split(";").join("\n")}\
					\n\n--Middle Shelf Drivers--\n${track.middleShelfDriver.split(";").join("\n")}\
					\n\n--Middle Shelf Karts--\n${track.middleShelfKart.split(";").join("\n")}\
					\n\n--Middle Shelf Gliders--\n${track.middleShelfGlider.split(";").join("\n")}`)
				.setAuthor(trackOfficialName,
					'https://www.mariowiki.com/images/a/a3/MKT_099CB.png','')
				//.setThumbnail()
				.addFields(
					{ name: 'Release Date', value: track.releaseDate, inline: true }
				)
				.setFooter('Please note that tracks during tours may vary from their regular track top and middle \
shelves due to cup boosts and tour boosts.')
			return message.channel.send(trackEmbed);
		}
		return message.reply(`Could not find track: ${trackName}`);
	},
};