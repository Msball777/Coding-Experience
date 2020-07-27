const Discord = require('discord.js');
const {Sequelize, Op} = require('sequelize');

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
	name: 'driver',
	description: 'Create a tag.',
	args: true,
	usage: '[Driver Name]',
	async execute(message, args) {
		const driverName = args.join(' ');
		// equivalent to: SELECT * FROM tags WHERE name = 'tagName' LIMIT 1;
		const driver = await driverList.findOne({ where: { driverName: {[Op.substring]: driverName} } });
		if (driver) {
			// equivalent to: UPDATE tags SET usage_count = usage_count + 1 WHERE name = 'tagName';
			driverOfficialName = driver.driverName.split(";")[0];
			const driverEmbed = new Discord.MessageEmbed()
				.setColor(frameColors[driver.pipeColor])
				.setAuthor(driverOfficialName + ' - ' + Rarities[driver.rarity] + ' Driver', driver.driverEmblem,'')
				.setThumbnail(driver.driverPortrait)
				.addFields(
					{ name: 'Special Item', value: Special_Items[driver.specialItem], inline: true },
					{ name: 'Availability', value: driver.exclusivity.split(";"), inline: true },
					{ name: 'Release Date', value: driver.releaseDate, inline: true },
					{ name: 'Traits', value: driver.traits.split(";"), inline: true },
					{ name: 'Spotlights/Pipes/Packs', value: driver.spotlights.split(";"), inline: true },
					{ name: 'Top Shelf Courses', value: driver.topShelf.split(";")},
					{ name: 'Middle Shelf Courses', value: driver.middleShelf.split(";")},
				)
			return message.channel.send(driverEmbed);
			//return message.channel.send(driverOfficialName + ' is a ' + Rarities[driver.rarity] + ' driver with the special item of '
			 //+ Special_Items[driver.specialItem]);
		}
		return message.reply(`Could not find driver: ${driverName}`);
	},
};