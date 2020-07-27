import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

public class Player {
	private String name;
	private int cash;
	private int jailCards;
	private int jailTime;
	private Property currentLocation;
	private List<Property> ownedProperties;
	
	public Player(String name, int cash, Property currentLocation){
		this.name = name;
		this.cash = cash;
		this.jailTime = 0;
		this.jailCards = 0;
	    this.currentLocation = currentLocation;
		this.ownedProperties = new ArrayList<Property>();
	}
	
	public void playerStatus(){
		System.out.println("Player " + this.name);
		System.out.println("Cash: " + this.cash);
		if(jailCards != 0){
			System.out.println("Get Out of Jail Free Cards: " + this.jailCards);
		}
	}
	
	public String getName(){
		return this.name;
	}
	
	public int getCash(){
		return this.cash;
	}
	
	public void freePlayer(){
		this.jailTime = 0;
	}
	
	public void giveJailCard(){
		System.out.println("Giving jail card?");
		if(this.jailCards < 2){
			System.out.println("Given!");
			this.jailCards++;
		}
	}
	
	public int getJailCards(){
		return this.jailCards;
	}
	
	public void useJailCard(){
		this.jailCards = this.jailCards - 1;
	}
	
	public int getJailTime(){
		return this.jailTime;
	}
	
	public void jailTick(){
		this.jailTime = this.jailTime - 1;
	}
	
	public void takeCash(int takenCash){
		this.cash = this.cash - takenCash;
	}
	
	public void giveCash(int givenCash){
		this.cash = this.cash + givenCash;
	}

	public boolean move(int moveNum){
		this.currentLocation.moveOffProperty(this);
		for(int i = 0; i < moveNum; i++){
			this.currentLocation = this.currentLocation.advanceOne();
			this.currentLocation.touchProperty(this);
		}
		this.currentLocation.occupyProperty(this);
		if(!this.currentLocation.activateProperty(this, moveNum)){
			this.eliminateThisPlayer();
			return false;
		}
		else
			return true;
	}
	
	public boolean moveName(String propertyName){
		int moveNum = 0;
		this.currentLocation.moveOffProperty(this);
		while(true){
			moveNum++;
			this.currentLocation = this.currentLocation.advanceOne();
			this.currentLocation.touchProperty(this);
			//System.out.println(this.currentLocation.getName() + " = " + propertyName);
			if(this.currentLocation.getName().equals(propertyName))
				break;
		}
		this.currentLocation.occupyProperty(this);
		return this.currentLocation.activateProperty(this, moveNum);
	}
	
	public boolean moveColor(String color){
		int moveNum = 0;
		this.currentLocation.moveOffProperty(this);
		while(true){
			moveNum++;
			this.currentLocation = this.currentLocation.advanceOne();
			this.currentLocation.touchProperty(this);
			//System.out.println(this.currentLocation.getColor() + " = " + color);
			if(this.currentLocation.getColor().equals(color))
				break;
		}
		this.currentLocation.occupyProperty(this);
		return this.currentLocation.activateProperty(this, moveNum);
	}
	
	public void eliminateThisPlayer(){
		System.out.println("Doh! I lost!");
		this.currentLocation.moveOffProperty(this);
		this.currentLocation = null;
		//If in debt to an opponent, the properties should already be given to the opponent.
		if(this.cash != -1){
			System.out.println("Since the player is in debt to the bank, the properties and cash return to the bank.");
			this.cash = -1;
			if(!this.ownedProperties.isEmpty())
				return;
			List<Property> removingProperties = new ArrayList<Property>(this.ownedProperties);
			Iterator<Property> itr = removingProperties.iterator();
			Property thisProperty;
			int returnedHouses;
			while(itr.hasNext()){
				thisProperty = itr.next();
				returnedHouses = thisProperty.removeOwner(true);
				System.out.println("returned houses: " + returnedHouses);
				if(returnedHouses > 0){
					if(returnedHouses == 5){
						MonopolyMain.numHotels++;
					}
					else{
						MonopolyMain.numHouses += returnedHouses;
					}
				}
			}
		}
	}
	
	public void acquireProperty(Property property){
		Iterator<Property> itr = this.ownedProperties.iterator();
		property.setOwner(this);
		boolean connected = false;
		int index = 0;
		
		while(itr.hasNext()){
			Property thisProperty = itr.next();
			//System.out.println(thisProperty.getName() + " " + thisProperty.getColor() + " = " + property.getColor() + "?");
			if(thisProperty.getColor().equals(property.getColor())){
				connected = true;
				this.ownedProperties.add(index, property);
				break;
			}
			else
				index++;
		}
		if(!connected)
			this.ownedProperties.add(property);
		
		if(this.ownedProperties.containsAll(property.getJointPropertyList())){
			System.out.println("Congrats! You have a monopoly of " + property.getColor() + " properties!");
			property.monopolize(true);
			Iterator<Property> itr2 = property.getJointPropertyList().iterator();
			while(itr2.hasNext()){
				itr2.next().monopolize(true);
			}
		}
	}
	
	public void showOwnedProperties(){
		Iterator<Property> itr = this.ownedProperties.iterator();
		int num = 1;
		String currentColor = "";
		while(itr.hasNext()){
			Property thisProperty = itr.next();
			if(!currentColor.equals(thisProperty.getColor())){
				currentColor = thisProperty.getColor();
				System.out.println("-----" + currentColor + "-----");
			}
			System.out.print(num + ". ");
			thisProperty.printSquare(false);
			num++;
		}
	}
	
	public int propertyListLength(){
		return this.ownedProperties.size();
	}
	
	public Property getProperty(int choice){
		return this.ownedProperties.get(choice);
	}
	
	public void removeProperty(Property property){
		System.out.println("Removing " + property.getName());
		this.ownedProperties.remove(property);
	}
	
	public void goToJail(Property jailProperty){
		this.jailTime = 3;
		this.currentLocation.moveOffProperty(this);
		jailProperty.occupyProperty(this);
		this.currentLocation = jailProperty;
	}
	
	public void takeOverPlayer(Player opponent){
		List<Property> opponentProperties = new ArrayList<Property>(opponent.getAllProperties());
		Iterator<Property> itr = opponentProperties.iterator();
		this.giveCash(opponent.getCash());
		opponent.takeCash(opponent.getCash()+1);
		System.out.println("Om nom nom!");
		Property thisProperty;
		while(itr.hasNext()){
			 thisProperty = itr.next();
			 thisProperty.removeOwner(false);
			 this.acquireProperty(thisProperty);
		}
	}
	
	public List<Property> getAllProperties(){
		return this.ownedProperties;
	}
}
