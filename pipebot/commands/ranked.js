const Discord = require('discord.js');
const {Sequelize, Op} = require('sequelize');

const {frameColors} = require('../config.json');

const sequelize = new Sequelize('database', 'user', 'password', {
	host: 'localhost',
	dialect: 'sqlite',
	logging: false,
	storage: 'database.sqlite',
});

const rankList = sequelize.import('../models/rankList');

rankList.sync();

module.exports = {
	name: 'ranked',
	description: 'Create a tag.',
	args: true,
	usage: '[rank Name]',
	async execute(message, args) {
		const rankName = args.join(' ');
		// equivalent to: SELECT * FROM tags WHERE name = 'tagName' LIMIT 1;
		const rank = await rankList.findOne({ where: { rankName: {[Op.substring]: rankName} } });
		if (rank) {
			console.log(rank.color);
			console.log(frameColors[rank.color]);
			// equivalent to: UPDATE tags SET usage_count = usage_count + 1 WHERE name = 'tagName';
			rankOfficialName = rank.rankName.split(";")[0];
			tracks = rank.cupTracks.split(";");
			const rankEmbed = new Discord.MessageEmbed()
				.setColor(frameColors[rank.color])
				.setDescription(`${tracks[0]}\
					\n\n--Top Shelf Drivers--\n${rank.topShelfDriver1.split(";").join("\n")}\
					\n\n--Top Shelf Karts--\n${rank.topShelfKart1.split(";").join("\n")}\
					\n\n--Top Shelf Gliders--\n${rank.topShelfGlider1.split(";").join("\n")}\
					\n\n==================\n${tracks[1]}\
					\n\n--Top Shelf Drivers--\n${rank.topShelfDriver2.split(";").join("\n")}\
					\n\n--Top Shelf Karts--\n${rank.topShelfKart2.split(";").join("\n")}\
					\n\n--Top Shelf Gliders--\n${rank.topShelfGlider2.split(";").join("\n")}\
					\n\n==================\n${tracks[2]}\
					\n\n--Top Shelf Drivers--\n${rank.topShelfDriver3.split(";").join("\n")}\
					\n\n--Top Shelf Karts--\n${rank.topShelfKart3.split(";").join("\n")}\
					\n\n--Top Shelf Gliders--\n${rank.topShelfGlider3.split(";").join("\n")}`)
				.setAuthor(rankOfficialName + ' - ' + rank.cupName,
					'https://www.mariowiki.com/images/0/02/MKT_Icon_Combo.png','')
				.setThumbnail(rank.cupImage)
				.addFields(
					{ name: 'StartDate', value: rank.startDate, inline: true },
					{ name: 'EndDate', value: rank.endDate, inline: true}
				)
				.setFooter('Please note that shown top shelf items may vary from their regular track top \
shelves due to cup boosts and tour boosts.')
			return message.channel.send(rankEmbed);
		}
		return message.reply(`Could not find rank: ${rankName}`);
	},
};