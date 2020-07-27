module.exports = {
	name: 'reload',
	description: 'Reloads a command',
	args: true,
	usage: '[Command to reload]',
	execute(message, args) {
		const commandName = args[0].toLowerCase();
		const command = message.client.commands.get(commandName)

		delete require.cache[require.resolve(`./${command.name}.js`)];

		try {
			const newCommand = require(`./${command.name}.js`);
			message.client.commands.set(newCommand.name, newCommand);
		} catch (error) {
			console.log(error);
			message.channel.send(`There was an error while reloading a command \`${command.name}\`:\n\`${error.message}\``);
		}


		message.channel.send(`Command \`${command.name}\` was reloaded!`);
	},
};