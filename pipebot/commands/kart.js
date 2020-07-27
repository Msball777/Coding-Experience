const Discord = require('discord.js');
const {Sequelize, Op} = require('sequelize');

const {frameColors, Boost_Attribute, Rarities} = require('../config.json');

const sequelize = new Sequelize('database', 'user', 'password', {
	host: 'localhost',
	dialect: 'sqlite',
	logging: false,
	storage: 'database.sqlite',
});

const kartList = sequelize.import('../models/kartList');

kartList.sync();

module.exports = {
	name: 'kart',
	description: 'Create a tag.',
	args: true,
	usage: '[kart Name]',
	async execute(message, args) {
		const kartName = args.join(' ');
		// equivalent to: SELECT * FROM tags WHERE name = 'tagName' LIMIT 1;
		const kart = await kartList.findOne({ where: { kartName: {[Op.substring]: kartName} } });
		if (kart) {
			// equivalent to: UPDATE tags SET usage_count = usage_count + 1 WHERE name = 'tagName';
			kartOfficialName = kart.kartName.split(";")[0];
			const kartEmbed = new Discord.MessageEmbed()
				.setColor(frameColors[kart.color])
				.setAuthor(kartOfficialName + ' - ' + Rarities[kart.rarity] + ' Kart',
					'https://www.mariowiki.com/images/thumb/7/78/MKT_Icon_Karts.png/90px-MKT_Icon_Karts.png','')
				.setThumbnail(kart.kartImage)
				.addFields(
					{ name: 'Boost Attribute', value: Boost_Attribute[kart.boostAttribute], inline: true },
					{ name: 'Availability', value: kart.exclusivity.split(";"), inline: true },
					{ name: 'Release Date', value: kart.releaseDate, inline: true },
					{ name: 'Spotlights/Pipes/Packs', value: kart.spotlights.split(";"), inline: true },
					{ name: 'Top Shelf Courses', value: kart.topShelf.split(";")},
					{ name: 'Middle Shelf Courses', value: kart.middleShelf.split(";")},
				)
			return message.channel.send(kartEmbed);
		}
		return message.reply(`Could not find kart: ${kartName}`);
	},
};