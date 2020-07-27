const Discord = require('discord.js');
const {Sequelize, Op} = require('sequelize');

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
	name: 'glider',
	description: 'Create a tag.',
	args: true,
	usage: '[Glider Name]',
	async execute(message, args) {
		const gliderName = args.join(' ');
		// equivalent to: SELECT * FROM tags WHERE name = 'tagName' LIMIT 1;
		const glider = await gliderList.findOne({ where: { gliderName: {[Op.substring]: gliderName} } });
		if (glider) {
			// equivalent to: UPDATE tags SET usage_count = usage_count + 1 WHERE name = 'tagName';
			gliderOfficialName = glider.gliderName.split(";")[0];
			const gliderEmbed = new Discord.MessageEmbed()
				.setColor(frameColors[glider.color])
				.setAuthor(gliderOfficialName + ' - ' + Rarities[glider.rarity] + ' Glider',
					'https://www.mariowiki.com/images/4/47/MKT_Icon_Gliders.png','')
				.setThumbnail(glider.gliderImage)
				.addFields(
					{ name: 'Boost Item', value: Boost_Items[glider.boostItem], inline: true },
					{ name: 'Availability', value: glider.exclusivity.split(";"), inline: true },
					{ name: 'Release Date', value: glider.releaseDate, inline: true },
					{ name: 'Spotlights/Pipes/Packs', value: glider.spotlights.split(";"), inline: true },
					{ name: 'Top Shelf Courses', value: glider.topShelf.split(";")},
					{ name: 'Middle Shelf Courses', value: glider.middleShelf.split(";")},
				)
			return message.channel.send(gliderEmbed);
		}
		return message.reply(`Could not find glider: ${gliderName}`);
	},
};