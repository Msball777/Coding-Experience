const fs = require('fs');

const Discord = require('discord.js');
const Sequelize = require('sequelize');
const {prefix, token} = require('./config.json');
const client = new Discord.Client();

const sequelize = new Sequelize('database', 'user', 'password', {
	host: 'localhost',
	dialect: 'sqlite',
	logging: false,
	storage: 'database.sqlite',
});

const driverList = sequelize.import('models/driverList');
const kartList = sequelize.import('models/kartList');
const gliderList = sequelize.import('models/gliderList');
const trackList = sequelize.import('models/trackList');
const rankList = sequelize.import('models/rankList');

driverList.sync();
kartList.sync();
gliderList.sync();
trackList.sync();
rankList.sync();

client.commands = new Discord.Collection();
const commandFiles = fs.readdirSync('./commands').filter(file => file.endsWith('.js'));

for (const file of commandFiles) {
	const command = require(`./commands/${file}`);
	client.commands.set(command.name, command);
}

client.once('ready', () => {
	console.log('Ready!');
});

client.on('message', message => {
	if (!message.content.startsWith(prefix) || message.author.bot) return;

	const args = message.content.slice(prefix.length).split(/ +/);
	const commandName = args.shift().toLowerCase();

	if (!client.commands.has(commandName)) return;

	const command = client.commands.get(commandName);

	if (command.args && !args.length) {
		let reply = 'Usage: '

		if (command.usage) {
			reply += `\"${prefix}${command.name} ${command.usage}\"`;
		}

		return message.channel.send(reply);
	}

	try {
		command.execute(message, args);
	} catch (error) {
		console.error(error);
		message.reply('there was an error trying to execute that command!');
	}


});

client.login(token);
