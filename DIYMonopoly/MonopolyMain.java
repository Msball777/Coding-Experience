import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

public class MonopolyMain {
	
	static Scanner input = new Scanner(System.in);
	static Property goProperty = null;
	static int numberOfPlayers = 4;
	static Player[] PlayerList = new Player[4];
	static List<Player> PlacementList = new ArrayList<Player>();
	static Property jailProperty = null;
	
	static int numHouses = Config.STANDARD_HOUSE_NUM;
	static int numHotels = Config.STANDARD_HOTEL_NUM;

	public static void main(String[] args) {
		System.out.println(Config.MONOPOLY_INTRO);
		System.out.println("Press enter to set up standard board.");
		input.nextLine();
		File folder = new File(".");
		setBoard(folder);
		playGame();
		input.close();
	}
	
	
	/*
	 * This function is designed to handle integer-based inputs
	 * that the players can make during the game while insuring
	 * that they only pick valid options.
	 */
	public static int inputCheck(String prompt, int min, int max){
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
	
	/*
	 * This function creates two card lists, chance and community chest, that will be used in the game.
	 * 
	 */
	public static void setCards(File folder, String fileName, Property jailProperty,List<Card> cardList){
		try {
			Scanner fileInfo = new Scanner(new File(fileName)); //The current file is put into a scanner in 
			String fileSplit[];
			while(fileInfo.hasNextLine()){
				fileSplit = fileInfo.nextLine().trim().split(";");
				String type = fileSplit[0];
				if(type.equals(""))
					continue;
				Card newCard;
				switch(type){
					case "move": newCard = new moveCard(fileSplit[1], fileSplit[2], Integer.parseInt(fileSplit[3])); break;
					case "give": newCard = new giveCard(fileSplit[1], Integer.parseInt(fileSplit[2])); break;
					case "jailFree": newCard = new jailFreeCard(fileSplit[1]); break;
					case "jail": newCard = new jailCard(fileSplit[1], jailProperty); break;
					case "giveAll": newCard = new allCard(fileSplit[1],Integer.parseInt(fileSplit[2])); break;
					case "take": newCard = new payCard(fileSplit[1],Integer.parseInt(fileSplit[2])); break;
					case "takeAll": newCard = new payAllCard(fileSplit[1],Integer.parseInt(fileSplit[2])); break;
					case "buildingPay": newCard = new buildingPayCard(fileSplit[1],Integer.parseInt(fileSplit[2]),Integer.parseInt(fileSplit[3])); break;
					default: System.out.println("Oops"); return;
				}
				cardList.add(newCard);
			}
			fileInfo.close();
		}catch (FileNotFoundException e) {
			System.out.println("Unable to read from file.");
		}
		Iterator<Card> itr = cardList.iterator();
		//Player player = new Player("test", 1500, goProperty);
		int count = 0;
		while(itr.hasNext()){
			Card thisCard = itr.next();
			System.out.println(count++);
			thisCard.debugPrint();
//			thisCard.activateCard(player);
//			player.playerStatus();
//			System.out.println("Press enter to continue");
//			String showBoard = input.nextLine();
//			if(showBoard.equals("y")){
//				Property thisProperty = goProperty;
//				for(int i = 0; i < 40; i++){
//					thisProperty.printSquare(true);
//					thisProperty = thisProperty.advanceOne();	
//				}
//			}	
		}
		
	}
	
	/*
	 * 
	 */
	public static void setBoard(File folder){
		goProperty = null;
		try {
			Scanner fileInfo = new Scanner(new File("Standard.monp")); //The current file is put into a scanner in 
			String fileSplit[] = fileInfo.nextLine().trim().split(";");
			if(!fileSplit[0].toLowerCase().equals("go")){
				System.out.println("Failure. Doesn't start with go!");
				fileInfo.close();
				return;
			}
			else{
				goProperty = new goProperty(Integer.parseInt(fileSplit[1]));
			}
			Property thisProperty = goProperty;
			while(fileInfo.hasNextLine()){						  //order to pull needed data
				fileSplit = fileInfo.nextLine().trim().split(";"); //The line of text is split at ;
				//System.out.println("Got line: " + fileSplit);
				String type = fileSplit[0].toLowerCase();
				//System.out.println(fileSplit[0]);
				//System.out.println(fileSplit.length);
				Property newProperty;
				switch(type){
					case "prop": newProperty = new standardProperty(fileSplit[1], fileSplit[2], Integer.parseInt(fileSplit[3]),
							Integer.parseInt(fileSplit[4]), Integer.parseInt(fileSplit[5]), Integer.parseInt(fileSplit[6]),
							Integer.parseInt(fileSplit[7]), Integer.parseInt(fileSplit[8]), Integer.parseInt(fileSplit[9]), Integer.parseInt(fileSplit[10]));
							break;
					case "rail": newProperty = new railroadProperty(fileSplit[1], Integer.parseInt(fileSplit[2]), Integer.parseInt(fileSplit[3]), 
							Integer.parseInt(fileSplit[4]), Integer.parseInt(fileSplit[5]), Integer.parseInt(fileSplit[6])); break;
					case "free": newProperty = new freeParkProperty(); break;
					case "card": int cardType = Integer.parseInt(fileSplit[1]);
						if(cardType == 0)
							newProperty = new cardProperty("Community Chest", 0, null);
						else
							newProperty = new cardProperty("Chance", 1, null);
						break;
					
					case "police": newProperty = new policeProperty(); break;
					case "jail": newProperty = new jailProperty(); break;
					case "util": newProperty = new utilityProperty(fileSplit[1], Integer.parseInt(fileSplit[2]), Integer.parseInt(fileSplit[3]),
							Integer.parseInt(fileSplit[4])); break;
					case "tax": newProperty = new taxProperty(fileSplit[1], Integer.parseInt(fileSplit[2])); break;
					default: System.out.println("Oops"); return;
				}
				thisProperty.setNextProperty(newProperty);
				thisProperty = newProperty;
			}
			thisProperty.setNextProperty(goProperty);
			fileInfo.close();
		}catch (FileNotFoundException e) {
			System.out.println("Unable to read from file.");
		}
		Property thisProperty = goProperty;
		for(int i = 1; i < 5; i++){
			Player player = new Player(Integer.toString(i), 1500, thisProperty);
			PlayerList[i-1] = player;
			thisProperty.occupyProperty(player);
		}
		thisProperty = thisProperty.advanceOne();
		Property thisProperty2 = thisProperty;
		Property jailProperty = null;
		while(!thisProperty.getName().equals("Go")){
			//System.out.println("Looking at: " + thisProperty.getName());	
			if(thisProperty.getName().equals("Go to Jail")){
				Property jailFind = goProperty.advanceOne();
				boolean foundJail = false;
				while(!jailFind.getName().equals("Go")){
					//System.out.println(jailFind.getName() + " " + jailFind.getName().equals("Jail"));
					if(jailFind.getName().equals("Jail")){
						if(foundJail){
							System.out.println("There can be only one jail!");
							return;
						}
						jailProperty = jailFind;
						thisProperty.addJointProperty(jailFind);	
						foundJail = true;
					}
					jailFind = jailFind.advanceOne();
				}
				if(!foundJail){
					System.out.println("There is no jail to go to from go to jail space!");
					return;
				}
			}
			
			if(thisProperty.getColor().equals("None")){
				thisProperty = thisProperty.advanceOne();
				continue;
			}
			
			thisProperty2 = thisProperty;
			while(!thisProperty2.getName().equals("Go")){
				if(thisProperty2.getColor().equals("None") || thisProperty.equals(thisProperty2)){
					thisProperty2 = thisProperty2.advanceOne();
					continue;
				}
				if(thisProperty.getColor().toLowerCase().equals(thisProperty2.getColor().toLowerCase())){
					thisProperty.addJointProperty(thisProperty2);
				}
				thisProperty2 = thisProperty2.advanceOne();
			}
			thisProperty = thisProperty.advanceOne();
		}
		List<Card> chestCards = new ArrayList<Card>();
		List<Card> chanceCards = new ArrayList<Card>();
		setCards(folder, "communityChestCards.monp", jailProperty,chestCards);
		setCards(folder, "chanceCards.monp", jailProperty,chanceCards);
		thisProperty = goProperty;
		while(true){
			thisProperty = thisProperty.advanceOne();
			if(thisProperty.getName().equals("Community Chest")){
				System.out.println("Community Chest set!");
				cardProperty thisCardProperty = (cardProperty)thisProperty;
				thisCardProperty.setCardList(chestCards);
			}
			else if(thisProperty.getName().equals("Chance")){
				System.out.println("Chance set!");
				cardProperty thisCardProperty = (cardProperty)thisProperty;
				thisCardProperty.setCardList(chanceCards);
			}
			else if(thisProperty.getName().equals("Go"))
				break;
		}
	}
	
	public static void playGame(){
		while(true){
			for(int i = 0; i < numberOfPlayers; i++){
				if(numberOfPlayers <= 1){
					break;
				}
				Player thisPlayer = PlayerList[i];
				System.out.println("Ok " + thisPlayer.getName() + "... You're up!");
				if(thisPlayer.getJailTime() > 0){
					if(playerJailActions(thisPlayer))
						playerActions(1,thisPlayer,0);
				}
				else{
					playerActions(1,thisPlayer,0);
				}
				
				if(thisPlayer.getCash() == -1){
					if(i == (numberOfPlayers-1)){
						PlayerList[i] = null;
						numberOfPlayers--;
					}
					else{
						for(int j = i; j < numberOfPlayers-1; j++){
							PlayerList[j] = PlayerList[j+1];
						}
						numberOfPlayers--;
						PlayerList[numberOfPlayers] = null;
						i--;
					}
					continue;
				}
				if(numberOfPlayers <= 1){
					break;
				}
				playerActions(2,thisPlayer,0);
			}
			if(numberOfPlayers <= 1){
				break;
			}
		}
		int place = 4;
		for(int i = 0; i < (PlacementList.size()); i++){
			System.out.println(place + ". " + PlacementList.get(i).getName());
			place--;
		}
		System.out.println("1. " + PlayerList[0].getName() + "<---- WINNER!!");
	}
	
	public static boolean playerActions(int status, Player player, int payAmount){
		//0 = pay
		//1 = roll dice
		//2 = before end of turn
		int choice = 0;
		while(true){
			choice = 0;
			player.playerStatus();
			System.out.println(Config.PLAYER_OPTIONS);
			switch(status){
				case 0:
					System.out.println("5. Pay Due Amount");
					break;
				case 1:
					System.out.println("5. Roll Dice");
					break;
				case 2:
					if(payAmount == 1)
						System.out.println("5. Roll Again");
					else
						System.out.println("5. End Turn");
					break;
				default:
					System.out.println("Failure.");
					break;
			}
			choice = inputCheck("What do you want to do? ", 1, 5);
			switch(choice){
				case 1: manageProperties(player); continue;
				
				case 2: tradeOptions(player); continue;
				
				case 3: 
					if(status != 0 && numberOfPlayers > 2){
						System.out.println("You can't declare bankruptcy yet.");
						continue;
					}
					else if(bankruptcy(player))
							return false;
					else
						continue;
				
				case 4:	Property thisProperty = goProperty;
						for(int i = 0; i < 40; i++){
							thisProperty.printSquare(true);
							thisProperty = thisProperty.advanceOne();	
						}
						continue;
					
				case 5: 
					switch(status){
					case 0:
						if(player.getCash() < payAmount){
							System.out.println("You can't afford to pay! Trade or Sell to gain cash!");
							continue;
						}
						else{
							player.takeCash(payAmount);
							return true;
						}
						
					case 1:
						rollDice(player);
						return true;
						
					case 2: return true;
					
					default:
						System.out.println("Something went wrong and shouldn't get here.");
						System.exit(1);
						break;
					}
			}
		}
		
	}
	
	public static boolean rollDice(Player player){
		Random rand = new Random();
		boolean again = false;
		boolean stillPlaying = true;
		int againCount = 1;
		do{
			again = false;
			int dice1 = rand.nextInt(6) + 1;
			int dice2 = rand.nextInt(6) + 1;
			int result = dice1 + dice2;
			System.out.println(dice1 + " + " + dice2 + " = " + result);
			if(dice1 == dice2){
				againCount++;
				if(againCount == 3){
					System.out.println("No more!");
					return true;
				}
				again = true;
			}
			stillPlaying = player.move(result);
			if(again && player.getJailTime() <= 0 && stillPlaying)
				playerActions(2,player,1);
			else
				again = false;
		}while(again);
		
		return stillPlaying;
	}
	
	public static void manageProperties(Player player){
		if(player.propertyListLength() == 0){
			System.out.println("You have no properties to manage!");
			return;
		}
		System.out.println("Your Properties:");
		player.showOwnedProperties();
		System.out.println("Choose a number 1 to " + player.propertyListLength() + " or pick 0 to cancel.");
		int choice = inputCheck("What do you want to do? ", 0, player.propertyListLength());
		if(choice == 0)
			return;
		Property thisProperty = player.getProperty(choice-1);
		System.out.println("What would you like to do with " + thisProperty.getName() + "?");
		System.out.println(Config.PROPERTY_OPTIONS);
		System.out.println("Choose a number 1 to 3 or pick 0 to cancel.");
		choice = inputCheck("What do you want to do? ", 0, 3);
		if(choice == 0)
			return;
		switch(choice){
			case 1: mortgageProperties(thisProperty, player); break;
			case 2: housingOptions(thisProperty, player); break;
			case 3: thisProperty.printProperty(); break;
		}
		
	}
	
	public static void mortgageProperties(Property property, Player player){
		if(property.getHouseNum() != 0){
			System.out.println("You can not mortgage this because it still has buidlings on it. Sell them first!");
			return;
		}
		int mortgagePay = (property.getCost()/2);
		if(property.isMortgaged()){
			int unmortgageCost = mortgagePay + (mortgagePay/10);
			System.out.println("Do you want to unmortgage? It will cost $" + unmortgageCost);
			int choice = inputCheck("0. Yes, 1. No: ", 0, 1);
			if(choice == 0){
				if(player.getCash() < unmortgageCost){
					System.out.println("You can't afford this!");
					return;
				}
				else{
					System.out.println("Unmortgaged!");
					player.takeCash(unmortgageCost);
					property.setMortgage(false);
					return;
				}
			}
			else
				return;
		}
		else{
			System.out.println("If you mortgage, you will receive $" + mortgagePay + ", but the property will be locked until you unmortgage!\n"
					+ "Mortgage this property?");
			int choice = inputCheck("0. Yes, 1. No: ", 0, 1);
			if(choice == 0){
				System.out.println("Mortgaged! You received $" + mortgagePay + "!");
				player.giveCash(mortgagePay);
				property.setMortgage(true);
				return;
			}
			else
				return;
		}
	}
	
	public static void tradeOptions(Player player){
		int count = 1;
		int playerPosition = -1;
		for(int i = 0; i < numberOfPlayers; i++){
			Player thisPlayer = PlayerList[i];
			if(thisPlayer == player){
				playerPosition = i;
				continue;
			}
			System.out.println(count + ". " + thisPlayer.getName() + " $" + thisPlayer.getCash());
			count++;
		}
		
		int choice = inputCheck("Choose a player or 0 to cancel: ", 0, (numberOfPlayers - 1));
		
		if(choice == 0)
			return;
		
		choice--;
		
		if(choice >= playerPosition)
			choice++;
		
		Player thisPlayer = PlayerList[choice];
		
		System.out.println("You will trade with " + thisPlayer.getName());
		trading(player, thisPlayer);		
	}
	
	public static void trading(Player offeringPlayer, Player receivingPlayer){
		String finalOption = "8. Cancel offer.";
		
		List<Property> offeredProperties = new ArrayList<Property>();
		List<Property> receivedProperties = new ArrayList<Property>();
		int offeredMoney = 0;
		int receivedMoney = 0;
		
		while(true){
			System.out.println(offeringPlayer.getName() + " --> " + receivingPlayer.getName());
			System.out.println("\nProperties you're offering: ");
			printPropertyList(offeredProperties);
			System.out.println("Money you're offering: " + offeredMoney);
			System.out.println("\nProperties you're receiving: ");
			printPropertyList(receivedProperties);
			System.out.println("Money you're receiving: " + receivedMoney);
			System.out.println("\n" + Config.TRADE_OPTIONS);
			System.out.println(finalOption);
			int choice = inputCheck("What do you want to do? ", 1, 8);
			switch(choice){
				case 1: 
					if(offeringPlayer.propertyListLength() == 0){
						System.out.println("You have no properties to trade!");
						break;
					}
					System.out.println("Your Properties:");
					offeringPlayer.showOwnedProperties();
					System.out.println("Choose a number 1 to " + offeringPlayer.propertyListLength() + " or pick 0 to cancel.");
					int propChoice = inputCheck("What do you want to do? ", 0, offeringPlayer.propertyListLength());
					if(propChoice == 0)
						break;
					if(!offeredProperties.contains(offeringPlayer.getProperty(propChoice-1)))
						offeredProperties.add(offeringPlayer.getProperty(propChoice-1));
					else
						System.out.println("This property is already added!");
					break;
				
				case 2: 
					if(offeringPlayer.getCash() == 0){
						System.out.println("You have no money to trade!");
						break;
					}
					offeredMoney = inputCheck("How much money do you want to offer? ", 0, offeringPlayer.getCash());
					break;
					
				case 3: 
					if(offeredProperties.size() == 0){
						System.out.println("You have no properties to remove!");
						break;
					}
					System.out.println("Your to be traded Properties:");
					printPropertyList(offeredProperties);
					System.out.println("Choose a number 1 to " + offeredProperties.size() + " or pick 0 to cancel.");
					propChoice = inputCheck("What do you want to do? ", 0, offeredProperties.size());
					if(propChoice == 0)
						break;
					offeredProperties.remove(propChoice-1);
					break;
					
				case 4: 
					if(receivingPlayer.propertyListLength() == 0){
						System.out.println("They have no properties to trade!");
						break;
					}
					System.out.println("Their Properties:");
					receivingPlayer.showOwnedProperties();
					System.out.println("Choose a number 1 to " + receivingPlayer.propertyListLength() + " or pick 0 to cancel.");
					propChoice = inputCheck("What do you want to do? ", 0, receivingPlayer.propertyListLength());
					if(propChoice == 0)
						break;
					if(!receivedProperties.contains(receivingPlayer.getProperty(propChoice-1)))
						receivedProperties.add(receivingPlayer.getProperty(propChoice-1));
					else
						System.out.println("This property is already added!");
					break;
				case 5: 
					if(receivingPlayer.getCash() == 0){
						System.out.println("You have no money to trade!");
						break;
					}
					receivedMoney = inputCheck("How much money do you want to receive? ", 0, receivingPlayer.getCash());
					break;
				case 6: 
					if(receivedProperties.size() == 0){
						System.out.println("You have no properties to remove!");
						break;
					}
					System.out.println("Your to be traded Properties:");
					printPropertyList(receivedProperties);
					System.out.println("Choose a number 1 to " + receivedProperties.size() + " or pick 0 to cancel.");
					propChoice = inputCheck("What do you want to do? ", 0, receivedProperties.size());
					if(propChoice == 0)
						break;
					receivedProperties.remove(propChoice-1);
					break;
				case 7: 
					choice = inputCheck(receivingPlayer.getName() + ":\n1.Accept\n2.Counter Offer\n3.Decline", 1, 3);
					finalOption = "8. Decline Trade";
				
				switch(choice){
						case 1: //Give stuff.
							receivingPlayer.takeCash(receivedMoney);
							offeringPlayer.giveCash(receivedMoney);
							offeringPlayer.takeCash(offeredMoney);
							receivingPlayer.giveCash(offeredMoney);
							Iterator<Property> itr = receivedProperties.iterator();
							int returnedHouses;
							while(itr.hasNext()){
								Property thisProperty = itr.next();
								System.out.println("Giving: " + thisProperty.getName());
								returnedHouses = thisProperty.removeOwner(receivedProperties.containsAll(thisProperty.getJointPropertyList()));
								System.out.println("returned houses: " + returnedHouses);
								if(returnedHouses > 0){
									if(returnedHouses == 5){
										numHotels++;
										offeringPlayer.giveCash(thisProperty.getHousePrice()/2);
									}
									else{
										numHouses += returnedHouses;
										offeringPlayer.giveCash((thisProperty.getHousePrice()/2)*(returnedHouses));
									}
								}
								offeringPlayer.acquireProperty(thisProperty);
							}
							itr = offeredProperties.iterator();
							while(itr.hasNext()){
								Property thisProperty = itr.next();
								System.out.println("Giving: " + thisProperty.getName());
								returnedHouses = thisProperty.removeOwner(offeredProperties.containsAll(thisProperty.getJointPropertyList()));
								System.out.println("returned houses: " + returnedHouses);
								numHouses = numHouses + returnedHouses;
								if(returnedHouses > 0){
									if(returnedHouses == 5){
										numHotels++;
										offeringPlayer.giveCash(thisProperty.getHousePrice()/2);
									}
									else{
										numHouses = numHouses + returnedHouses;
										offeringPlayer.giveCash(thisProperty.getHousePrice()*(returnedHouses/2));
									}
								}
								receivingPlayer.acquireProperty(thisProperty);
							}
							return;
						case 2: //Swap receiving and offering
							List<Property> holdingProperties = offeredProperties;
							offeredProperties = receivedProperties;
							receivedProperties = holdingProperties;
							int holdingMoney = offeredMoney;
							offeredMoney = receivedMoney;
							receivedMoney = holdingMoney;
							System.out.println("Ok " + receivingPlayer.getName() + ", make your counter offer.");
							Player holdingPlayer = offeringPlayer;
							offeringPlayer = receivingPlayer;
							receivingPlayer = holdingPlayer;
							continue;
						case 3: return;
					}
					break;
				case 8: return;
				
			}
		}
	}
	
	public static void printPropertyList(List<Property> propertyList){
		if(propertyList.size() == 0){
			System.out.println("None");
			return;
		}
		Iterator<Property> itr = propertyList.iterator();
		int num = 1;
		while(itr.hasNext()){
			Property thisProperty = itr.next();
			System.out.print(num + ". ");
			thisProperty.printSquare(false);
			num++;
		}
	}
	
	public static void housingOptions(Property property, Player player){
		if(!(property instanceof standardProperty)){
			System.out.println("You can't add houses to this type of property!");
			return;
		}
		else{
			standardProperty buildingProperty = (standardProperty)property;
			int choice;
			if(buildingProperty.isMonopoly()){
				while(true){
					int houseNum = buildingProperty.getHouseNum();
					int housePrice = buildingProperty.getHousePrice();
					System.out.println("Number of buyable Houses: " + numHouses);
					System.out.println("Number of buyable Hotels: " + numHotels);
					System.out.println("It costs $" + housePrice + " to buy a house/hotel. You will get $" + (housePrice/2) + " back if sold.");
					if(houseNum == 5){
						System.out.println("This property has a hotel. You can not buy anymore buildings for this property.");
					}
					System.out.println("This property has " + houseNum + " house(s).");
					System.out.println("What would you like to do?");
					boolean buyable = true;
					boolean sellable = true;
					switch(houseNum){
						case 0: System.out.println("1. Buy house.\n3. Cancel."); sellable = false; break;
						case 5: System.out.println("2. Sell Hotel\n3. Cancel."); buyable = false; break;
						case 4: System.out.println("1. Buy Hotel.\n2. Sell house.\n3. Cancel."); break;
						default: System.out.println("1. Buy house.\n2. Sell house.\n3. Cancel."); break;
					}
					choice = inputCheck("What do you want to do? ", 1, 3);
					switch(choice){
						case 1:
							if(!buyable){
								System.out.println("Please enter number 2 or 3.");
								continue;
							}
							else if(houseNum == 4 && numHotels == 0){
								System.out.println("There are no hotels to buy!");
								continue;
							}
							else if(numHouses == 0){
								System.out.println("There are no houses to buy!");
								continue;
							}
							else{
								player.takeCash(housePrice);
								buildingProperty.addHouse();
								if(houseNum == 4){
									numHotels--;
									numHouses = numHouses + 4;
								}
								else
									numHouses--;
								continue;
							}
						case 2:
							if(!sellable){
								System.out.println("Please enter number 2 or 3");
								continue;
							}
							else{
								player.giveCash(housePrice/2);
								buildingProperty.removeHouse();
								if(houseNum == 5){
									numHotels++;
									if(numHouses < 4){
										player.giveCash((housePrice/2)*(4-numHouses));
										buildingProperty.setHouses(numHouses);
										numHouses = 0;
									}
									numHouses = numHouses + 4;
								}
								else
									numHouses++;
							}
							break;
							
						case 3: return;
					}
				}
				
			}
			else
				System.out.println("You can't add houses without a monopoly!");
		}
	}
	
	public static boolean bankruptcy(Player player){
		System.out.println("Are you sure you want to declare bankruptcy? Type \"yes\" to confirm.");
		String confirmation = input.nextLine().toLowerCase();
		if(confirmation.equals("yes")){
			System.out.println(player.getName() + " has declared bankruptcy!");
			PlacementList.add(player);
			return true;
		}
		else
			return false;
		
	}
	
	public static boolean playerJailActions(Player player){
		//0 = pay
		//1 = roll dice
		//2 = before end of turn

		int choice = 0;
		while(true){
			choice = 0;
			System.out.println("1. Roll (" + player.getJailTime() + " more chances)"
					+ "\n2. Pay $50"
					+ "\n3. Use \"Get out of Jail Free\" Card");
			choice = inputCheck("What do you want to do? ", 1, 3);
			switch(choice){
				case 1: 
					if(jailRoll(player)){
						player.freePlayer();
						//The player moves if they roll doubles, as such they do not do a normal roll.
						return false;
					}
			
					else{
						player.jailTick();
						if(player.getJailTime() == 0){
							System.out.println("Out of Time");
							
							if(player.getJailCards() < 1){
								choice = 1;
							}
							
							else{
								System.out.println("1. Pay $50"
									+ "\n2. Use \"Get out of Jail Free\" Card");
								choice = inputCheck("What do you want to do? ", 1, 2);
							}
							
							if(choice == 1){
								System.out.println("You must pay $50 to leave jail.");
								if(player.getCash() >= 50){
									player.takeCash(50);
									return true;
								}
								else{
									if(MonopolyMain.playerActions(0, player, 50))
										return true;
									else
										return false;
								}
							}
							else{
								player.useJailCard();
								return true;
							}
						}
						else
							return false;
					}

				case 2: 
					System.out.println("The bank will take $50 and you will take your turn once you pay.");
					if(player.getCash() >= 50){
						player.takeCash(50);
						player.freePlayer();
						return true;
					}					
					else if(playerActions(0, player, 50)){
						player.freePlayer();
						return true;
					}
					//The player declared bankruptcy
					else
						return false;
					
				case 3: 
					if(player.getJailCards() <= 0){
						System.out.println("You don't have card to use!");
						continue;
					}
					player.useJailCard();
					player.freePlayer();
					return true;
			}
		}
	}
	
	public static boolean jailRoll(Player player){
		Random rand = new Random();
		int dice1 = rand.nextInt(6) + 1;
		int dice2 = rand.nextInt(6) + 1;
		int result = dice1 + dice2;
		System.out.println(dice1 + " + " + dice2 + " = " + result);
		if(dice1 != dice2){
			System.out.println("Miss!");
			return false;
		}
		else{
			System.out.println("Got it! Move what you rolled now!");
			player.move(result);
			return true;
		}
	}
	
//	public static Player[] getPlayerList(){
//		return PlayerList;
//	}
	
	public static void auctionProperty(Property auctionedProperty, Player initiatingPlayer){
		int index = -1;
		for(int i = 0; i < numberOfPlayers; i++){
			if(initiatingPlayer == PlayerList[i])
				index = i;
		}
		int numberOfAuctioneers = numberOfPlayers;
		Player[] auctionList = new Player[numberOfAuctioneers];
		for(int i = 0; i < numberOfAuctioneers; i++){
			auctionList[i] = PlayerList[index];
			index++;
			if(index >= numberOfAuctioneers)
				index = 0;
		}

		int currentBid = 0;
		System.out.println("Auction Order for " + auctionedProperty.getName());
		int count = 0;
		for(int i = 0; i < numberOfAuctioneers; i++){
			count++;
			System.out.println(count + ". " + auctionList[i].getName() + "   $" + auctionList[i].getCash());
		}

		while(true){
			for(int i = 0; i < numberOfAuctioneers; i++){
				System.out.println(i);
				Player thisPlayer = auctionList[i];
				System.out.println(thisPlayer.getName() + "   $" + thisPlayer.getCash());
				System.out.println(Config.AUCTION_OPTIONS);
				while(true){
					int choice = inputCheck("What do you want to do? ", 1, 4);
					int updatedBid = 0;
					switch(choice){
						case 1: updatedBid = updateBid(thisPlayer, currentBid, 1); break;
		
						case 2: updatedBid = updateBid(thisPlayer, currentBid, 10); break;

						case 3: updatedBid = updateBid(thisPlayer, currentBid, 100); break;

						case 4: 
							System.out.println(thisPlayer.getName() + " withdraws!");
							if(i == (numberOfAuctioneers-1)){
								auctionList[i] = null;
								numberOfAuctioneers--;
							}
							else{
								for(int j = i; j < numberOfAuctioneers-1; j++){
									auctionList[j] = auctionList[j+1];
								}
								numberOfAuctioneers--;
								auctionList[numberOfAuctioneers] = null;
								i--;
							}
							break;
					}
					if(numberOfAuctioneers == 1){
						System.out.println(auctionList[0].getName() + " wins the auction for " + auctionedProperty.getName() + "!");
						auctionList[0].takeCash(currentBid);
						auctionList[0].acquireProperty(auctionedProperty);
						return;
					}
					if(updatedBid == -1)
						continue;
					else{
						if(updatedBid != 0)
							currentBid = updatedBid;
						break;
					}
				}
			}

		}
	}
	
	public static int updateBid(Player player, int bid, int increase){
		if(player.getCash() < (bid + increase)){
			System.out.println("You can't afford this options!");
			return -1;
		}
		else{
			bid = bid + increase;
			System.out.println(player.getName() + " has increased the bid to $" + bid + "!");
			return bid;
		}
	}
}
