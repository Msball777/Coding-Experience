const Discord = require('discord.js');
const {frameColors, NormalShopDrivers, NormalShopKarts, NormalShopGliders, PremiumNormalDrivers, PremiumNormalKarts,
PremiumNormalGliders, SuperShopDrivers, SuperShopKarts, SuperShopGliders, PremiumSuperDrivers, PremiumSuperKarts,
PremiumSuperGliders, HEShopDrivers, HEShopKarts, HEShopGliders, PremiumHEDrivers, PremiumHEKarts,
PremiumHEGliders} = require('../config.json');

module.exports = {
	name: 'shop',
	description: 'Send an example embed.',
	execute(message, args) {
		const shopEmbed = new Discord.MessageEmbed()
			.setColor(frameColors["Red"])
			.setAuthor('Shop','','')
			.setThumbnail('https://www.mariowiki.com/images/thumb/7/7d/MKT_Icon_Shop.png/90px-MKT_Icon_Shop.png')
			.addFields(
				{ name: 'Normal Drivers (800 coins)', value: NormalShopDrivers, inline: true },
				{ name: 'Super Drivers (3000 coins)', value: SuperShopDrivers, inline: true },
				{ name: 'High-End Drivers (12000 coins)', value: HEShopDrivers, inline: true },

				{ name: 'Normal Karts (500 coins)', value: NormalShopKarts, inline: true },
				{ name: 'Super Karts (2000 coins)', value: SuperShopKarts, inline: true },
				{ name: 'High-End Karts (10000 coins)', value: HEShopKarts, inline: true },

				{ name: 'Normal Gliders (500 coins)', value: NormalShopGliders, inline: true },
				{ name: 'Super Gliders (2000 coins)', value: SuperShopGliders, inline: true },
				{ name: 'High-End Gliders (10000 coins)', value: HEShopGliders, inline: true },

				{ name: 'Premium Normal Drivers (1500 coins)', value: PremiumNormalDrivers, inline: true },
				{ name: 'Premium Super Drivers (9000 coins)', value: PremiumSuperDrivers, inline: true },
				{ name: 'Premium High-End Drivers (30000 coins)', value: PremiumHEDrivers, inline: true },

				{ name: 'Premium Normal Karts (1000 coins)', value: PremiumNormalKarts, inline: true },
				{ name: 'Premium Super Karts (7500 coins)', value: PremiumSuperKarts, inline: true },
				{ name: 'Premium High-End Karts (25000 coins)', value: PremiumHEKarts, inline: true },

				{ name: 'Premium Normal Gliders (1000 coins)', value: PremiumNormalGliders, inline: true },
				{ name: 'Premium Super Gliders (7500 coins)', value: PremiumSuperGliders, inline: true },
				{ name: 'Premium High-End Gliders (25000 coins)', value: PremiumHEGliders, inline: true },
			)

		message.channel.send(shopEmbed);
	},
};