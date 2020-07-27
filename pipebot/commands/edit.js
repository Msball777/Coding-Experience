const {Sequelize, Op} = require('sequelize');

const {frameColors, Special_Items, Rarities} = require('../config.json');

const sequelize = new Sequelize('database', 'user', 'password', {
	host: 'localhost',
	dialect: 'sqlite',
	logging: false,
	storage: 'database.sqlite',
});

const driverList = sequelize.import('../models/driverList');
const kartList = sequelize.import('../models/kartList');
const gliderList = sequelize.import('../models/gliderList');
const trackList = sequelize.import('../models/trackList');
const rankList = sequelize.import('../models/rankList');

module.exports = {
	name: 'edit',
	description: 'Edit something.',
	args: true,
	usage: '[Object Type] [thisObject Name] [Option] [name to be added or number for deletion or replacement]',
	async execute(message, args) {
		const objectType = args.shift().toLowerCase();
		let typeName = objectType.concat('Name');
		let listType = objectType.concat('List');
		if(!sequelize.models[listType])
			return message.reply('This type of object does not exist');
		const attriList = Object.keys(sequelize.models[listType].rawAttributes);
		if(args[0].toLowerCase() === "options"){
			let i = 0;
			let sentString = "\n";
			attriList.forEach(function(element){
				i++;
				sentString = sentString + i + ". " + element + "\n";
				if(i === (attriList.length)){
					sentString = sentString + "1 - "   + attriList.length + " for adding to list, " + (attriList.length+1) + " - " + 
					(attriList.length*2) + " for complete replacement, and " + ((attriList.length*2)+1) + " - " +
					(attriList.length*3) + " for deletion of item."
					return message.reply(sentString);
				}
			});
			return;
		}

		if(args.length < 3)
			return message.reply('Not enough arguments.')

		const selectedName = args.shift().replace(/-/g, " ");
		let selection = parseInt(args.shift(), 10);
		let selectedChange = args.join(' ');

		if(selection > (attriList.length*3) || selection < 0)
			return message.reply('Invalid choice');

		if(selection > attriList.length && selection < (attriList.length*2)){
			selectedChange = parseInt(selectedChange, 10);
			if(isNaN(selectedChange))
				return message.reply('Need a number for this option. Use zero if deletion is not a list.');
		}

		let selectedThing = attriList[(selection-1)%attriList.length];

		console.log(selection);
		console.log(selectedThing);
		console.log(selectedName);
		console.log(listType);
		// equivalent to: SELECT * FROM tags WHERE name = 'tagName' LIMIT 1;
		const thisObject = await sequelize.models[listType].findOne({ where: { [typeName]: {[Op.substring]: selectedName} } });
		if (thisObject) {
			let affectedRows = 0;
			let updatedList = "";
			console.log(selection);
			console.log(selection <= attriList.length);
			if(selection <= attriList.length){
				console.log("Entered addition.");
				if(!thisObject[selectedThing])
					updatedList = thisObject[selectedThing].concat(selectedChange);
				else
					updatedList = thisObject[selectedThing].concat(";" + selectedChange);

				affectedRows = await sequelize.models[listType].update({ [selectedThing]: updatedList },{where:{[typeName]:{[Op.substring]: selectedName}}});
				if (affectedRows > 0) {
					return message.reply(selectedThing + ' success');
				}
				return message.channel.send('Failure to update ' + selectedThing);
			}
			else if(selection <= (attriList.length*2)){
				if(thisObject[selectedThing].split(";").length <= selectedChange)
					return message.reply('Not a valid selection for deletion.')
				else{
					theList = thisObject[selectedThing].split(";");
					theList.splice(selectedChange,1);
					updatedList = theList.join(";");
				}
				affectedRows = await sequelize.models[listType].update({ [selectedThing]: updatedList },{where:{[typeName]:{[Op.substring]: selectedName}}});
				if (affectedRows > 0) {
					return message.reply(selectedThing + ' item removed.');
				}
				return message.channel.send('Failure to delete item from ' + selectedThing);
			}

			else if(selection <= (attriList.length*3)){
				affectedRows = await sequelize.models[listType].update({ [selectedThing]: selectedChange },{where:{[typeName]:{[Op.substring]: selectedName}}});
				if (affectedRows > 0) {
					return message.reply(selectedThing + ' item replaced.');
				}
				return message.channel.send('Failure to delete item from ' + selectedThing);
			}

			else return message.reply('Failure on getting option.');
			
		}
		else return message.reply('Failure to find desired object.')
	},
};