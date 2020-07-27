import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.Scanner;
import java.util.Random;

public class FactoryMain {
	
	public static void main(String[] args) {
		Scanner input = new Scanner(System.in);
		Random rand = new Random();
		TrainerList fullTrainerList = new TrainerList();
		PCBox PC = new PCBox();
		File folder = new File(".");
		System.out.println("Welcome to the Battle Factory Distribution Program.");
		System.out.println("Our system works that teams consist of between 3-6 Pokemon, and that"
				+ " there are atleast " + Config.MIN_PARTICIPANTS + " participants.\n");
		boolean active = true;
		while(active){
			System.out.println("What would you like to do? Please enter the corresponding number to make a choice.");
			System.out.println(Config.PROMPT_MENU);
			int selection = inputCheck(input,"\nMake a selection: ", 1, 12);
			switch (selection) {
				case 1: loadOptions(folder, input, PC, fullTrainerList);
						break;
				case 2: createNewPokemon(input, rand, PC);
						break;
				case 3: createNewTrainer(input, rand, fullTrainerList);
						break;
				case 4: initiateTournament(input, PC, fullTrainerList, rand);
						break;
				case 5: System.out.println("Under construction...");
						break;
				case 6: deleteFromBox(input, PC);
						break;
				case 7: deleteParticipant(input, fullTrainerList);
						break;
				case 8: createBox(input, null, PC);
						break;
				case 9: saveOptions(folder, input, PC, fullTrainerList);
						break;
				case 10:organizeOptions(input,PC);
						break;
				case 11: System.out.println(Config.HELP_BLURB);
						break;
				case 12: active = false;
						break;
				default:
					System.out.println("Oops!");
					break;
			}
		}

	}
	
	public static boolean validType(String type){
		for(int i = 0; i < Config.TYPE_ARRAY.length; i++){
			if(Config.TYPE_ARRAY[i].toUpperCase().equals(type))
				return true;
		}
		
		System.out.println("Please enter a valid type.");
		
		return false;
	}
		
	public static int inputCheck(Scanner input, String prompt, int min, int max){
		int acceptedInput = 0;
		boolean validInput = false;
		String invalid = "";
		
		do{
			System.out.print(prompt);
			
			if(input.hasNextInt()){
				acceptedInput = input.nextInt();
				if (acceptedInput < min || acceptedInput > max) {
					System.out.println("Please enter a number between " + min + " and " + max + ".");
					input.nextLine();
				}
				else
					validInput = true;
			}			
			else{
				invalid = input.nextLine();
				System.out.println("Error: expected a number between " + min + " and " + max + " but found: " + invalid);
			}
		}while(!validInput);
		
	input.nextLine();
	return acceptedInput;
	}
	
	public static void createNewTrainer(Scanner input, Random rand, TrainerList fullTrainerList){
		System.out.print("Enter the name of the new participant: ");
		String name = input.nextLine().trim();
		int BFID = 0;
		do{
			BFID = rand.nextInt(65536);
		} while(fullTrainerList.BFIDexist(BFID));
		fullTrainerList.addTrainer(name, BFID);
		fullTrainerList.allTrainers();
	}
	
	public static void createNewPokemon(Scanner input, Random rand, PCBox PC){
		System.out.println("To create a new Pokemon, you must include its name, typing, stats, level, form (if possible), and item (if one exists).\n");
		
		System.out.print("Enter the name: ");
		String name = input.nextLine().trim();
		
		System.out.println("Single or Dual type?");
		System.out.println("1) Single\n2) Dual");
		int choice = inputCheck(input, "Choice: ", 1,2);
		String[] typing = new String[2];
		if(choice == 1){
			do{
				System.out.print("Enter type: ");
				typing[0] = input.nextLine().toUpperCase();
			}while(!validType(typing[0]));
			typing[1] = "N/A";
		} else{
			do{
				System.out.print("Enter first type: ");
				typing[0] = input.nextLine().toUpperCase();
			}while(!validType(typing[0]));
			do{
				System.out.print("Enter second type: ");
				typing[1] = input.nextLine().toUpperCase();
			}while(!validType(typing[1]));
		}
		
		int[] stats = new int[6];
		System.out.println("Please enter all 6 stats\n");
		stats[0] = inputCheck(input, "HP: ", 1, 999); stats[1] = inputCheck(input, "ATK: ", 1, 999); stats[2] = inputCheck(input, "DEF: ", 1, 999);
		stats[3] = inputCheck(input, "SP-ATK: ", 1, 999); stats[4] = inputCheck(input, "SP-DEF: ", 1, 999); stats[5] = inputCheck(input, "SPD: ", 1, 999);
		
		int level = inputCheck(input, "Please input this Pokemon's level: ", 1, 100);
		
		System.out.print("Pokemon's Ability: ");
		String ability = input.nextLine();
		
		String[] moves = new String[4];
		System.out.println("Choose the Pokemon's moves (Blank if does not exist): ");
		for(int i = 0; i < 4; i++){
			System.out.print("Move " + (i+1) + ": ");
			moves[i] = input.nextLine().trim();
		}
		
		System.out.print("Pokemon's Held Item (Blank if no item): ");
		String item = input.nextLine().trim();
		if(item.equals("")){
			item = "N/A";
		}
		
		System.out.print("Pokemon's Current Form (Blank if not available): ");
		String form = input.nextLine().trim();
		if(form.equals("")){
			form = "N/A";
		}
		
		String gender = "N/A";
		System.out.println("Choose Gender:\n1) Male\n2) Female\n3) N/A");
		int selection = inputCheck(input, "Choice: ", 1, 3);
		switch (selection){
			case 1: gender = "Male"; break;
			case 2: gender = "Female"; break;
			case 3: gender = "N/A"; break;
			case 4: System.out.println("Oops!"); break;
		}
		
		PokeInfo newPoke = new PokeInfo(name, -1, level, stats, moves, ability, form, typing, item, gender);
		newPoke.printPokemon();
		if(PC.isEmpty()){
			System.out.println("Your storage contains no boxes, please create a new one to store this pokemon.");
			createBox(input, newPoke, PC);
		}
		else{
			PC.displayAllBoxes();
			boolean safeStore = false;
			int boxNum = 0;
			while(!safeStore){
				boxNum = inputCheck(input, "Choose a box to store or enter 0 to create a new box: ", 0, PC.getSize());
				if(boxNum == 0){
					createBox(input, newPoke, PC);
					safeStore = true;
					break;
				}
				else{
					safeStore = PC.getBox(boxNum-1).store(newPoke);
				}
			}
		}
	}
	
	public static void createBox(Scanner input, PokeInfo pokemon, PCBox PC){
		String attribute = "";
		boolean okBox = false;
		do{
			System.out.print("Enter an attribute or leave blank to allow all: ");
			attribute = input.nextLine().toUpperCase().trim();
			
			if(attribute.equals("")){
				okBox = true;
				attribute = "ALL";
				continue;
			}
			
			if(!validType(attribute)){
				System.out.println("Error: Invalid type for attribute.");
				continue;
			}
				
			if(pokemon != null){
				if(pokemon.getType1().toUpperCase().equals(attribute) || pokemon.getType2().toUpperCase().equals(attribute))
					okBox = true;
				else
					System.out.println("Error: The Pokemon will not be able to be stored with the following attribute");
			}
			else
				okBox = true;
		} while(!okBox);
		
		if(pokemon != null)
			PC.createBox(attribute, pokemon);
		else
			PC.createBox(attribute);
		
		PC.displayAllBoxes();
	}
	
	public static void deleteFromBox(Scanner input, PCBox PC){
		if(PC.isEmpty()){
			System.out.println("Error: No Pokemon to delete.");
			return;
		}
		PC.displayAllBoxes();
		int boxNum = inputCheck(input, "Choose a box, 0 to cancel: ", 0, PC.getSize());
		if(boxNum == 0)
			return;
		Box deleteBox = PC.getBox(boxNum-1);
		if(deleteBox.isEmpty()){
			System.out.println("Box is empty!");
			return;
		}
		deleteBox.allBoxPoke();
		int pokeNum = inputCheck(input, "Choose a pokemon to delete, 0 to cancel: ", 0, PC.getSize());
		if(pokeNum == 0)
			return;
		deleteBox.release(pokeNum-1);
	}
	
	public static void deleteParticipant(Scanner input, TrainerList fullTrainerList){
		if(fullTrainerList.isEmpty()){
			System.out.println("Error: No Participants to delete.");
			return;
		}
		fullTrainerList.allTrainers();
		int trainerNum = inputCheck(input, "Choose a participant to delete, 0 to cancel: ", 0, fullTrainerList.getSize());
		if(trainerNum == 0)
			return;
		fullTrainerList.removeTrainer(trainerNum-1);
	}
	
	public static void loadOptions(File folder, Scanner input, PCBox PC, TrainerList fullTrainerList){
		String enteredFile = selectFile(folder, input);
		if(enteredFile.toUpperCase().trim().equals("0"))
			return;
		try {
			Scanner fileInfo = new Scanner(new File(enteredFile));
			 fileInfo.close();
		} catch (FileNotFoundException e) {
			System.out.println("Error: Unable to read from file: " + enteredFile);
			return;
		}
		System.out.println(Config.LOADING_MENU);
		int option = inputCheck(input,"\nMake a selection, 0 to quit: ", 0, 3);
		if(option == 0)
			return;
		readFile(folder, input, PC, fullTrainerList, enteredFile, option);
	}
	
	public static String selectFile(File folder, Scanner input){		
		System.out.println("Battle Factory Files: ");
		for ( File file : folder.listFiles()) {
			if (file.getName().endsWith(".bf")) { //This shows the list of files currently visible to the program
				System.out.println("    " + file.getName());
			}
		}
		System.out.print("Please enter the name of the file or enter 0 to quit: ");
		return input.nextLine().trim();
	}
	
	public static void readFile(File folder, Scanner input, PCBox PC, TrainerList fullTrainerList, String enteredFile, int option){	
		System.out.println("Reading file: " + enteredFile);
		
		try {
			Scanner fileInfo = new Scanner(new File(enteredFile)); //The current file is put into a scanner in 
			 while(fileInfo.hasNextLine()){						  //order to pull needed data
				 String fileSplit = fileInfo.nextLine().trim();
				 //System.out.println("Got line: " + fileSplit);
				 if(fileSplit.equals(""))
					 continue;
				 switch (fileSplit.toUpperCase()){
				 	case "": continue;
				 	case "BOXLIST":
				 		if(option == 1 || option == 3){
				 			if(!readPokemon(fileInfo,PC)) 
				 				return;
				 		}
				 		else{
				 			while(fileInfo.hasNextLine()){
				 				if(fileInfo.next().toUpperCase().trim().equals("END"))
				 					break;
				 			}
				 		}
				 		break;

				 	case "TRAINERLIST": 
				 		if(option == 2 || option == 3){
				 			if(!readTrainer(fileInfo,fullTrainerList)) 
				 				return;
				 		}
				 		else{
				 			while(fileInfo.hasNextLine()){
				 				if(fileInfo.next().toUpperCase().trim().equals("END"))
				 					break;
				 			}
				 		}
				 		break;

				 	default: System.out.println("Error: Improper formatting detected."); return;
				 }
					  
			 }
			 fileInfo.close();
		} catch (FileNotFoundException e) {
			System.out.println("Unable to read from file: " + enteredFile);
		}
		System.out.println("Success!");
	}
	
	public static boolean readPokemon(Scanner fileInfo, PCBox PC){
		while(fileInfo.hasNextLine()){
			String attribute = fileInfo.nextLine().trim().toUpperCase();
			if(attribute.equals("ENDBOXLIST"))
				return true;
			if(!validType(attribute) && !attribute.equals("ALL")){
				System.out.println("Error: Invalid Box Attribute. Please note tournament attributes are not valid in loading.");
				return false;
			}
			else{
				Box newBox = new Box(attribute);
				while(fileInfo.hasNextLine()){
					String[] pokemonSplit = fileInfo.nextLine().toUpperCase().split(";");
					if(pokemonSplit[0].trim().toUpperCase().equals("END"))
						break;
					else if(pokemonSplit.length != 9){
						System.out.println("Error: Invalid PokeInfo format.");
						return false;
					}
					else{
						int level = Integer.parseInt(pokemonSplit[1].trim());
						int[] stats = new int[6];
						String[] moves = new String[4];
						String[] typing = new String[2];
						try{
							String[] statSplit = pokemonSplit[2].split("/");
							for(int i = 0; i < 6; i++)
								stats[i] = Integer.parseInt(statSplit[i].trim());
							String[] moveSplit = pokemonSplit[3].split("/");
							for(int i = 0; i < 4; i++){
								moves[i] = moveSplit[i].trim();
							}
							String[] typeSplit = pokemonSplit[6].split("/");
							typing[0] = typeSplit[0];
							if(!validType(typing[0])){
								System.out.println("Error: Invalid type detected.");
								return false;
							}				
							if(typeSplit.length == 1 || typeSplit[1].trim().equals("")){
								typing[1] = "N/A";
							}
							else{
								typing[1] = typeSplit[1];
								if(!validType(typing[1])){
									System.out.println("Error: Invalid type detected.");
									return false;
								}
									
							}
							if(!typing[0].equals(newBox.getAttribute()) && !typing[0].equals(newBox.getAttribute()) && !newBox.getAttribute().equals("ALL")){
								System.out.println("Error: Pokemon does not fit box it is listed under.");
								return false;
							}
							if(pokemonSplit[5].trim().equals(""))
								pokemonSplit[5] = "N/A";
							if(pokemonSplit[7].trim().equals(""))
								pokemonSplit[7] = "N/A";
							if(pokemonSplit[8].trim().equals(""))
								pokemonSplit[8] = "N/A";
							if(!pokemonSplit[8].equals("MALE") && !pokemonSplit[8].equals("FEMALE") && !pokemonSplit[8].equals("N/A")){
								System.out.println("Error: Invalid gender option");
								return false;
							}
		    			}
		    			catch(NumberFormatException e){
		    				System.out.println("Error: Pokemon unusable");
		    				return false;
		    			}
						PokeInfo newPoke = new PokeInfo(pokemonSplit[0], -1,level,stats, moves, pokemonSplit[4], pokemonSplit[5],
								typing, pokemonSplit[7], pokemonSplit[8]);
						newBox.store(newPoke);
					}
				}
				PC.createBox(newBox);
			}
		}
		
		System.out.println("Warning: File completely read, but did not end properly.");
		return false;
			
			
	}
	
	public static boolean readTrainer(Scanner fileInfo, TrainerList fullTrainerList){
		while(fileInfo.hasNextLine()){
			String[] trainerSplit = fileInfo.nextLine().split(";");
			if(trainerSplit[0].trim().toUpperCase().equals("END"))
				return true;
			else if(trainerSplit.length != 2){
				System.out.println("Error: Invalid TrainerInfo format.");
				return false;
			}
			else{
				Random tempRand = new Random();
				int BFID = 0;
				if(trainerSplit[1].trim().equals("")){
					do{
						BFID = tempRand.nextInt(65536);
					} while(fullTrainerList.BFIDexist(BFID));
				}
				else{
					try{
	    				BFID = Integer.parseInt(trainerSplit[1].trim());
	    				if(fullTrainerList.BFIDexist(BFID)){
	    					System.out.println("Error: BFID already exists");
	    					return false;
	    				}
	    			}
	    			catch(NumberFormatException e){
	    				System.out.println("Error: ID unusable");
	    				return false;
	    			}
				}
				fullTrainerList.addTrainer(trainerSplit[0],BFID);
			}
		}
			
		System.out.println("Warning: File completely read, but did not end properly.");
		return false;
	}
	
	public static void saveOptions(File folder, Scanner input, PCBox PC, TrainerList fullTrainerList){
		String enteredFile = selectFile(folder, input);
		if(enteredFile.toUpperCase().trim().equals("0"))
			return;
		if(!enteredFile.endsWith(".bf"))
			enteredFile = enteredFile.concat(".bf");
		System.out.println(Config.SAVING_MENU);
		int option = inputCheck(input,"\nMake a selection, 0 to quit: ", 0, 3);
		if(option == 0)
			return;
		writeFile(folder, input, PC, fullTrainerList, enteredFile, option);
	}
	
	public static void writeFile(File folder, Scanner input, PCBox PC, TrainerList fullTrainerList, String enteredFile, int option){
		
		try {
			PrintWriter out = new PrintWriter( new File(enteredFile) ); //Creates a printerwriter
			if((option == 1 || option == 3) && !PC.isEmpty()){
				out.println("BOXLIST");
				LinkedListIterator<Box> boxItr = PC.iterator();
				while(boxItr.hasNext()){
					Box thisBox = boxItr.next();
					if(thisBox.isEmpty())
						continue;
					out.println(thisBox.getAttribute());
					LinkedListIterator<PokeInfo> pokeItr = thisBox.iterator();
					while(pokeItr.hasNext()){
						out.println(pokeItr.next().writePokemonToFile());
					}
					out.println("END");
				}
				out.println("ENDBOXLIST");
			}
			if((option == 2 || option == 3) && !fullTrainerList.isEmpty()){
				out.println("TRAINERLIST");
				LinkedListIterator<TrainerInfo> trainerItr = fullTrainerList.iterator();
				while(trainerItr.hasNext()){
					TrainerInfo thisTrainer = trainerItr.next();
					out.println(thisTrainer.getTrainerName() + ";" + thisTrainer.getBFID());
				}
				out.println("END");
			}
			out.close(); //Data can no longer be added to the file
		} catch(FileNotFoundException e) {
			System.out.println("Unable to write to file: " + enteredFile);			
		}
	}
	
	public static void organizeOptions(Scanner input, PCBox PC){
		boolean active = true;
		while(active){
			System.out.println(Config.ORGANIZING_MENU);
			int option = inputCheck(input,"\nMake a selection, 0 to quit: ", 0, 6);
			if(option == 0)
				return;
			switch (option){
				case 1: typeOrganize(input, PC); break;
				case 2: sizedRandOrganize(input, PC); break;
				case 3: movePokemon(input, PC); break;
				case 4: //moveMultiPokemon(input, PC)
					System.out.println("Under Construction"); break;
				case 5: checkPokemon(input, PC); break;
				case 6: deleteBox(input,PC); break;
				case 0: active = false; break;
				default: System.out.println("Oops!"); break;
			}
		}
	}
	
	public static void typeOrganize(Scanner input, PCBox PC){
		if(PC.isEmpty()){
			System.out.println("Error: No Pokemon to move.");
			return;
		}
		PCBox newPC = new PCBox();
		
		for(int i = 0; i < Config.TYPE_ARRAY.length; i++){
			newPC.createBox(Config.TYPE_ARRAY[i]);
		}
		LinkedListIterator<Box> boxItr = PC.iterator();
		//for(int i = 0; i < PC.getSize(); i++){
		while(boxItr.hasNext()){
			//Box thisBox = PC.getBox(i);
			Box thisBox = boxItr.next();
			LinkedListIterator<PokeInfo> pokeItr = thisBox.iterator();
			//for(int j = 0; j < thisBox.boxSize(); j++){
			while(pokeItr.hasNext()){
				//PokeInfo thisPoke = thisBox.getPoke(j);
				PokeInfo thisPoke = pokeItr.next();
				String type = thisPoke.getType1();
				System.out.println(type);
				int boxNum = -1;
				switch(type){
				case "FIRE":boxNum=0; break; case "WATER":boxNum=1; break; case "GRASS":boxNum=2; break; case "NORMAL":boxNum=3; break; 
				case "ELECTRIC":boxNum=4; break; case "FIGHTING":boxNum=5; break; case "POISON":boxNum=6; break; case "ICE":boxNum=7; break;
				case "DARK":boxNum=8; break; case "GHOST":boxNum=9; break; case "STEEL":boxNum=10; break; case "FAIRY":boxNum=11; break;
				case "GROUND":boxNum=12; break; case "PSYCHIC":boxNum=13; break; case "ROCK":boxNum=14; break; case "DRAGON":boxNum=15; break;
				case "BUG":boxNum=16; break; case "FLYING":boxNum=17; break;
				}
				System.out.println(boxNum);
				newPC.getBox(boxNum).store(thisPoke);
			}
		}
		while(!PC.isEmpty()){
			PC.removeBox(0);
		}
		while(!newPC.isEmpty()){
			if(!newPC.getBox(0).isEmpty())
				PC.createBox(newPC.removeBox(0));
			else
				newPC.removeBox(0);
		}
		newPC.displayAllBoxes();
	}
	
	public static void sizedRandOrganize(Scanner input, PCBox PC){
		if(PC.isEmpty()){
			System.out.println("Error: No Pokemon to move.");
			return;
		}
		System.out.println(PC.totalPCPokeNum());
		Random tempRand = new Random();
		PCBox genPC = new PCBox();
		PCBox finalPC = new PCBox();
		int totalPokeNum = PC.totalPCPokeNum();
		int boxSize = inputCheck(input,"\nChoose the size of each box: ", 0, totalPokeNum);
		int numBoxes = (totalPokeNum + boxSize - 1) / boxSize;
		int remainderSize = totalPokeNum%boxSize;
		System.out.println(remainderSize);
		System.out.println(remainderSize == 0);
		for(int i = 0; i < numBoxes; i++)
			genPC.createBox("ALL");
		
		System.out.println(genPC.getSize());
		genPC.getBox(2);
		
		boolean remainderBox = false;
		if(!(remainderSize == 0))
			remainderBox = true;
		LinkedListIterator<Box> boxItr = PC.iterator();
		int counter = 0;
		while(boxItr.hasNext()){
		//for(int i = 0; i < PC.getSize(); i++){
			//Box thisBox = PC.getBox(i);
			Box thisBox = boxItr.next();
			LinkedListIterator<PokeInfo> pokeItr = thisBox.iterator();
			//for(int j = 0; j < thisBox.boxSize(); j++){
			while(pokeItr.hasNext()){
				counter = counter +1;
				System.out.println(counter);
				int randChoice = tempRand.nextInt(numBoxes);
				System.out.println("Randchoice is: " + randChoice);
				//genPC.getBox(randChoice).store(thisBox.getPoke(j));
				genPC.getBox(randChoice).store(pokeItr.next());
				int newBoxSize = genPC.getBox(randChoice).boxSize();
				if(remainderBox && (newBoxSize >= remainderSize)){
					System.out.println("Entered");
					remainderBox = false;
					numBoxes = numBoxes-1;
					System.out.println(numBoxes);
					finalPC.createBox(genPC.removeBox(randChoice));
					continue;
				}
				if(newBoxSize >= boxSize){
					numBoxes--;
					System.out.println(numBoxes);
					finalPC.createBox(genPC.removeBox(randChoice));
				}
			}
		}
//		System.out.println(genPC.isEmpty());
//		for(int i = 0; i < genPC.getSize(); i++){
//			finalPC.createBox(genPC.removeBox(i));
//		}
		while(!PC.isEmpty()){
			PC.removeBox(0);
		}
		//PC.displayAllBoxes();
		while(!finalPC.isEmpty()){
			PC.createBox(finalPC.removeBox(0));
		}
		PC.displayAllBoxes();
		
	}
	
	public static void movePokemon(Scanner input, PCBox PC){
		PC.displayAllBoxes();
		int boxNum = inputCheck(input, "Choose a box, 0 to cancel: ", 0, PC.getSize());
		if(boxNum == 0)
			return;
		Box selectedBox = PC.getBox(boxNum-1);
		selectedBox.allBoxPoke();
		int pokeNum = inputCheck(input, "Choose a pokemon to move, 0 to cancel: ", 0, selectedBox.boxSize());
		if(pokeNum == 0)
			return;
		PC.displayAllBoxes();
		int boxNumtoMove = inputCheck(input, "Choose a box to move to, 0 to cancel: ", 0, PC.getSize());
		Box movetoBox = PC.getBox(boxNumtoMove-1);
		PokeInfo thisPoke = selectedBox.getPoke(pokeNum-1);
		if(!movetoBox.getAttribute().equals(thisPoke.getType1()) && !movetoBox.getAttribute().equals(thisPoke.getType2())
				&& !movetoBox.getAttribute().equals("ALL")){
			System.out.println("Error: Pokemon can't be moved to a box of this attribute.");
			return;
		}
		movetoBox.store(thisPoke);
		selectedBox.release(pokeNum-1);
	}
	
	public static void moveMultiPokemon(Scanner input, PCBox PC){
		
	}
	
	public static void checkPokemon(Scanner input, PCBox PC){
		PC.displayAllBoxes();
		int boxNum = inputCheck(input, "Choose a box, 0 to cancel: ", 0, PC.getSize());
		if(boxNum == 0)
			return;
		Box selectedBox = PC.getBox(boxNum-1);
		selectedBox.allBoxPoke();
		int pokeNum = inputCheck(input, "Choose a pokemon to display its details, 0 to cancel: ", 0, selectedBox.boxSize());
		if(pokeNum == 0)
			return;
		selectedBox.getPoke(pokeNum-1).printPokemon();
	}
	
	public static void deleteBox(Scanner input, PCBox PC){
		PC.displayAllBoxes();
		int boxNum = inputCheck(input, "Choose a box to delete, 0 to cancel: ", 0, PC.getSize());
		if(boxNum == 0)
			return;
		if(!PC.getBox(boxNum-1).isEmpty()){
			System.out.println("WARING: Box contains Pokemon. Do you want to continue?");
			int ok = inputCheck(input, " 1 = yes, 0 = no: ", 0, 1);
			if(ok == 0)
				return;
		}
		PC.removeBox(boxNum-1);
	}
	public static void initiateTournament(Scanner input, PCBox PC, TrainerList fullTrainerList, Random rand){
		System.out.println(Config.TOURNAMENT_INTRO);
		int numParti = inputCheck(input, "\nSelect the number of participants: ", 4, 64);
		if(fullTrainerList.getSize() < numParti){
			System.out.println("Error: Not enough participants.");
			return;
		}
		int numPokePer = inputCheck(input, "\nSelect the number of pokemon per team: ", 3, 6);
		if((numPokePer*numParti) > PC.totalPCPokeNum()){
			System.out.println("Error: Not enough pokemon.");
			return;
		}
		int tournamentStyle = inputCheck(input, "\nSelect tournament style, 0 = Round Robin, 1 = Bracket: ", 0, 1);
		int randomizationStyle = inputCheck(input, "\nSelect randomization selection, 0 = One Per Box, 1 = Pure Random: ", 0, 1);
		
		if(randomizationStyle == 0){
			if(numPokePer > PC.getSize()){
				System.out.println("Error: Not enough boxes to create team.");
				return;
			}
			LinkedListIterator<Box> boxItr = PC.iterator();
			while(boxItr.hasNext()){
				if(boxItr.next().boxSize() < numParti){
					System.out.println("Error: Not enough pokemon in each box.");
					return;
				}
			}
		}
		
		TrainerList competingTrainers = new TrainerList();
		int chosenTrainer;
		boolean ok = false;
		for(int i = 0; i < numParti; i++){
			fullTrainerList.allTrainers();
			do{
				chosenTrainer = inputCheck(input, "Choose participant #" + (i+1) +": ", 1, fullTrainerList.getSize());
				if(competingTrainers.BFIDexist(fullTrainerList.getTrainer(chosenTrainer-1).getBFID())){
					System.out.println("Error: This trainer already is in the tournament.");
					ok = false;
				}
				else{
					competingTrainers.addTrainer(fullTrainerList.getTrainer(chosenTrainer-1));
					ok = true;
				}
			}while(!ok);
		}
		competingTrainers.allTrainers();
		if(randomizationStyle == 1)
			assignPureRandom(PC, competingTrainers, rand, numPokePer);
		else
			assignOnePerBox(PC, competingTrainers, rand, numPokePer);
		LinkedListIterator<TrainerInfo> trainerItr = competingTrainers.iterator();
		while(trainerItr.hasNext())
			trainerItr.next().printTrainer();
		if(tournamentStyle == 1)
			playTournamentBrack(input, competingTrainers, numParti);
		else{
			int numRounds = inputCheck(input, "\nSelect number of rounds", 3, 10);
			playTournamentRR(input, competingTrainers, numRounds, rand);
		}
	}
	
	public static void assignOnePerBox(PCBox PC, TrainerList competingTrainers, Random rand, int numPokePer){
		LinkedListIterator<TrainerInfo> trainerItr = competingTrainers.iterator();
		while (trainerItr.hasNext()){
			TrainerInfo thisTrainer = trainerItr.next();
			int boxChoice = -1;
			for(int j = 0; j < numPokePer; j++){
				boolean ok = false;
				Box thisBox;
				do{
					boxChoice = rand.nextInt(PC.getSize());
					thisBox = PC.getBox(boxChoice);
					if(!thisBox.getUsage()){
						thisBox.setUsage(true);
						ok = true;
					}
				}while(!ok);
				ok = false;
				do{
					int pokemonChoice = rand.nextInt(thisBox.boxSize());
					PokeInfo thisPoke = thisBox.getPoke(pokemonChoice);
					if(thisPoke.getBFID() == -1){	
						thisPoke.setBFID(1);
						thisTrainer.givePoke(thisPoke);;
						ok = true;
					}
				}while(!ok);
			}
			LinkedListIterator<Box> boxItr = PC.iterator();
			while(boxItr.hasNext())
				boxItr.next().setUsage(false);
			
		}
		LinkedListIterator<Box> boxItr = PC.iterator();
		while(boxItr.hasNext()){
			Box currBox = boxItr.next();
			LinkedListIterator<PokeInfo> pokeItr = currBox.iterator();
			while(pokeItr.hasNext())
				pokeItr.next().setBFID(-1);
		}
	}
	
	public static void assignPureRandom(PCBox PC, TrainerList competingTrainers, Random rand, int numPokePer){
		LinkedListIterator<TrainerInfo> trainerItr = competingTrainers.iterator();
		while (trainerItr.hasNext()){
			TrainerInfo thisTrainer = trainerItr.next();
			int boxChoice = -1;
			for(int j = 0; j < numPokePer; j++){
				boolean ok = false;
				Box thisBox;
				do{
					boxChoice = rand.nextInt(PC.getSize() - PC.getNumUsed());
					boxChoice = boxChoice + PC.getNumUsed();
					thisBox = PC.getBox(boxChoice);
					if(!(thisBox.boxSize() == thisBox.getNumUsed())){
						ok = true;
					}
					else{
						PC.moveThisBox(boxChoice);
					}
				}while(!ok);
				ok = false;
				do{
					int pokemonChoice = rand.nextInt(thisBox.boxSize()-thisBox.getNumUsed());
					pokemonChoice = pokemonChoice + thisBox.getNumUsed();
					PokeInfo thisPoke = thisBox.getPoke(pokemonChoice);
					if(thisPoke.getBFID() == -1){	
						thisBox.incrementUsed(pokemonChoice);
						thisTrainer.givePoke(thisPoke);
						ok = true;
					}
				}while(!ok);
			}
			LinkedListIterator<Box> boxItr = PC.iterator();
			while(boxItr.hasNext())
				boxItr.next().resetNumUsed();
			
		}
		LinkedListIterator<Box> boxItr = PC.iterator();
		PC.resetUsed();
		while(boxItr.hasNext())
			boxItr.next().resetNumUsed();
	}
	
	public static void saveCurrentTournament(Scanner input, PCBox PC, TrainerList fullTrainerList){
		
	}
	
	public static void loadTournament(Scanner input, PCBox PC, TrainerList fullTrainerList){
		
	}
	
	public static void playTournamentRR(Scanner input, TrainerList competingTrainers, int numRounds, Random rand){
		int battlesPerRound = (int) Math.ceil((double)competingTrainers.getSize()/2.0);
		
		TournamentList randomizedRound = new TournamentList(0);
		randomizedRound.addNodes(battlesPerRound);
		
		for(int i = 0; i < numRounds; i++){
			LinkedListIterator<TrainerInfo> trainerItr = competingTrainers.iterator();
			while(trainerItr.hasNext()){
				boolean ok = false;
				while(!ok){
					int choice = rand.nextInt(randomizedRound.getSize() - randomizedRound.getLayer());
					TournamentNode thisNode = randomizedRound.getNode(choice);
					if(thisNode.getLayer() == 0){
						thisNode.setCompetitor1(trainerItr.next());
						thisNode.setLayer(1);
						ok = true;
					}
					else if(thisNode.getLayer() == 1){
						thisNode.setCompetitor2(trainerItr.next());
						thisNode.setLayer(2);
						ok = true;
					}
					else
						thisNode.setLayer(2);
				}
			}
			LinkedListIterator<TournamentNode> battleItr = randomizedRound.iterator();
			while(battleItr.hasNext()){
				TournamentNode thisNode = battleItr.next();
				thisNode.printBattle();
				System.out.println();
				int winner = inputCheck(input, "Who won? 1 for leftside, 2 for rightside.", 1, 2);
				if(winner == 1){
					thisNode.getCompetitor(1).setWins(thisNode.getCompetitor(1).getWins()+1);
					thisNode.getCompetitor(0).setLosses(thisNode.getCompetitor(0).getLosses()+1);
				}
				else{
					thisNode.getCompetitor(0).setWins(thisNode.getCompetitor(0).getWins()+1);
					thisNode.getCompetitor(1).setLosses(thisNode.getCompetitor(1).getLosses()+1);
				}
				thisNode.setLayer(0);
			}
			System.out.println("==================");
		}
		System.out.println("Final Results:");
		LinkedListIterator<TrainerInfo> trainerItr = competingTrainers.iterator();
		while(trainerItr.hasNext())
			trainerItr.next().printTrainer();
	}

	public static void playTournamentBrack(Scanner input, TrainerList competingTrainers, int numParti){
		TournamentBrackets bracket = new TournamentBrackets(numParti);
		bracket.createLayers(competingTrainers);
		bracket.printBrackets();
		TrainerInfo finalWinner = null;
		LinkedListIterator<TournamentList> tournamentItr = bracket.iterator();
		while(tournamentItr.hasNext()){
			TournamentList roundList = tournamentItr.next();
			LinkedListIterator<TournamentNode> roundItr = roundList.iterator();
			while(roundItr.hasNext()){
				TournamentNode thisBattle = roundItr.next();
				thisBattle.printBattle();
				System.out.println();
				int winner = inputCheck(input, "Who won? 1 for leftside, 2 for rightside.", 1, 2);
				if(roundList.getLayer() == 0){
					if(winner == 1){
						finalWinner = thisBattle.getCompetitor(winner);
						thisBattle.setCompetitor1(null);
					}
					else{
						finalWinner = thisBattle.getCompetitor(winner);
						thisBattle.setCompetitor2(null);
					}
				}
				else
					thisBattle.advancedWinner(winner);
			}
		}
		System.out.println("Congratulations to " + finalWinner.getTrainerName() + "!\n");
		System.out.println("Their Final Team:\n");
		LinkedListIterator<PokeInfo> pokeItr = finalWinner.iterator();
		while(pokeItr.hasNext())
			pokeItr.next().printPokemon();
		System.out.println("\nFinal Bracket Results:\n");
		bracket.printBrackets();
		System.out.println(finalWinner.getTrainerName() + "\n");
		System.out.println("\nThank you for playing!");	
	}
// Extra Info:
//	System.out.println("Before we begin, please enter the following information.");
//	
//	teamNum = inputCheck(input, "\nHow many Pokemon in a team? ", 3, 6);
//	
//	trainerNum = inputCheck(input,"\nHow many participants? ", 4, 100);
//	
//	minimumPokemon = trainerNum * teamNum;
//	System.out.println("You will need at least " + minimumPokemon + " pokemon.");
//	int stats[] = {10, 3, 10, 6, 5, 4};
//	String moves[] = {"Smog", "Poison Gas", "Self-Destruct", "Toxic"};
//	String typing[] = {"Poison","N/A"};
//	PokeInfo Koffing = new PokeInfo("Koffing", 1, stats, moves, "Levitate", "N/A", typing);
//	Koffing.printPokemon();

	//Old print trainer
//	for(int i = 0; i < fullTrainerList.getSize(); i++){
//	TrainerInfo thisTrainer = fullTrainerList.getTrainer(i);
//	out.println(thisTrainer.getTrainerName() + ";" + thisTrainer.getBFID());
//}
	//Old Poke trainer
//	for(int i = 0; i < PC.getSize(); i++){
//	Box thisBox = PC.getBox(i);
//	if(thisBox.isEmpty())
//		continue;
//	out.println(thisBox.getAttribute());
//	for(int j = 0; j < thisBox.boxSize(); j++){
//		out.println(thisBox.getPoke(j).writePokemonToFile());
//	}
//	out.println("END");
//}
}
