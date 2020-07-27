public class Config {

	public static final int MIN_PARTICIPANTS = 4;	
	
	public static final int MAX_PARTICIPANTS = 100;	
	
	// Lists for Cargo names and Destinations
	/** Array of supported pokemon types.*/
	public static final String[] TYPE_ARRAY = 
				{"FIRE","WATER","GRASS","NORMAL","ELECTRIC","FIGHTING","POISON","ICE","DARK","GHOST","STEEL","FAIRY","GROUND","PSYCHIC",
						"ROCK","DRAGON","BUG","FLYING"};
	
	/** Message with some info on each selection.*/
	public static final String HELP_BLURB = "Load File: Load info from a .bf on pokemon, trainers, and tournaments. Current information will be overwritten."
			+ "\n\nCreate New Pokemon: Create a completely new pokemon to store within the app."
			+ "\n\nCreate New Participant: Create a completely new participant to store within the app."
			+ "\n\nCreate New Tournament: Create a new tournament, including setting rules, active participants and their pokemon."
			+ "\n\nLoad Existing Tournament: Load a pre-existing tournament in progress (From a .bf file or created in same session.)"
			+ "\n\nDelete Pokemon: Delete an existing pokemon.\n\nDelete Participant: Delete an existing participant."
			+ "\n\n(WARNING: Deleting a pokemon or participant currently active in a tournament will delete said tournament as well.)"
			+ "\n\nDelete Tournament: Delete the in progress tournament."
			+ "\n\nSave to File: Save all info from current session to a new or existing .bf file. Existing files will be completely overwritten."
			+ "\n\nOrganize Boxes: Sort boxes by a variety of methods such as type, alphabetical, etc.";
	
	/** User menu between user choices */
	public static final String PROMPT_MENU =
			"1. Load File; 2. Create New Pokemon; 3. Create New Participant; 4. Create New Tournament;"
					+ "\n5. Load Existing Tournament; 6. Delete Pokemon; 7. Delete Participant; 8. Create new Box."
					+ "\n9. Save to File; 10. Organize Boxes; 11. Help; 12. Quit";
	
	public static final String LOADING_MENU =
			"1. Load only pokemon.\n2. Load only trainers.\n3. Load All"
			+ "\nWARNING: All connections of pokemon to participants are not received. Use \"Load Existing Tournament\" to obtain that information.";
	
	public static final String SAVING_MENU =
			"1. Save only pokemon.\n2. Save only trainers.\n3. Save All"
			+ "\nWARNING: All connections of pokemon to participants are not saved. Please remember to choose Y to save when exiting an ongoing tournament.";
	
	public static final String ORGANIZING_MENU =
			"1. Reorganize by type.\n2. Reorganize into a set number of boxes randomly."
			+ "\n3. Move individual pokemon.\n4. Move multiple Pokemon.\n5. Check a Pokemon\n6. Delete Box"
			+ "\nWARNING: All boxes and their previous organization will be lost upon reorganizing.";
	
	public static final String TOURNAMENT_INTRO = 
			"Welcome to the Battle Factory Tournament Creator, where all the existing pokemon you have stored away on this system"
					+ "\ncan now be used to randomly assign to the selected participants and battled in either a brack style"
					+ "\nor round robin style tournament. You need between 3-6 pokemon per participant, and between 4-64 participants."
					+ "\nPlease be aware that participant numbers not equal to 4,8,16,32, or 64 participants for a bracket tournament"
					+ "\nwill cause an inbalance in number of battles to become champion. Also, exiting the tournament prematurely"
					+ "\nwill save your current information to a .bft file and can be picked up at a later date.";
	
}
