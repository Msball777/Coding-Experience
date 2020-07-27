import java.util.Iterator;

public class railroadProperty extends Property{
	
	private int buyPrice;
	private int rentPrice1;
	private int rentPrice2;
	private int rentPrice3;
	private int rentPrice4;
	private Player owner;
	private boolean monopoly;
	private boolean mortgaged;

	public railroadProperty(String propertyName, int buyPrice, int rentPrice1, int rentPrice2, int rentPrice3, int rentPrice4) {
		super(propertyName, "Railroad");
		this.buyPrice = buyPrice;
		this.rentPrice1 = rentPrice1;
		this.rentPrice2 = rentPrice2;
		this.rentPrice3 = rentPrice3;
		this.rentPrice4 = rentPrice4;
		this.owner = null;
		this.mortgaged = false;
		this.monopoly = false;
	}
	
	@Override
	public boolean activateProperty(Player player, int moveNum){
		super.activateProperty(player, moveNum);
		System.out.println();this.printProperty();System.out.println();player.playerStatus();System.out.println();
		if(this.owner == null){
			boolean ok = false;
			while(!ok){
				System.out.println("This property is available for purchase! You must either buy it or auction it.");
				int choice = MonopolyMain.inputCheck("1. Buy\n2. Auction\nWhat do you want to do? ", 1, 2);
				if(choice == 1 && player.getCash() > this.buyPrice){
					System.out.println("Congrats! You now own this property!");
					player.takeCash(this.buyPrice);
					player.acquireProperty(this);
					ok = true;
				}
				else{
					System.out.println("You can't afford this! Choose Auction!");
				}
				if(choice == 2){
					MonopolyMain.auctionProperty(this, player);
					ok = true;
				}
			}
			return true;
		}
		else if(this.owner != player && !this.mortgaged){
			int cost = 0;
			int ownedRailNum = 1;
			Iterator<Property> itr = this.getJointPropertyList().iterator();
			while(itr.hasNext()){
				railroadProperty thisRailroad = (railroadProperty)itr.next();
				//System.out.println("Entered rail: " + thisRailroad.getName());
				if(thisRailroad.getOwner() == null){
					//System.out.println("No owner");
					continue;
				}
				else if(thisRailroad.getOwner().equals(this.owner)){
					//System.out.println("Owned by this person.");
					ownedRailNum++;
				}
			}
			switch(ownedRailNum){
				case 1: cost = rentPrice1; break;
				case 2: cost = rentPrice2; break;
				case 3: cost = rentPrice3; break;
				case 4: cost = rentPrice4; break;
			}
			System.out.println("You must pay " + this.owner.getName() + " $" + cost + " in rent!");
			boolean stillPlaying = MonopolyMain.playerActions(0, player, cost);
			if(stillPlaying)
				this.owner.giveCash(cost);
			else
				this.owner.takeOverPlayer(player);
			return stillPlaying;
		}
		else if(this.mortgaged && this.owner != player){
			System.out.println("This property is mortaged. You don't need to pay!");
			return true;
		}
		else{
			System.out.println("You own this property!");
			return true;
		}
	}
	
	public void printProperty(){
		super.printProperty();
		if(this.owner == null){
			System.out.println("This property is unowned.");
		}
		else{
			System.out.println("This property is owned by " + this.owner.getName() + ".");
		}
		System.out.println("This property costs $" + this.buyPrice
				+ "\n---Rent---"
				+ "\n1 Railroad: $" + this.rentPrice1
				+ "\n2 Railroad: $" + this.rentPrice2
				+ "\n3 Railroad: $" + this.rentPrice3
				+ "\n4 Railroad: $" + this.rentPrice4);
		int mortgageValue = this.buyPrice/2;
		int unmortgageValue = mortgageValue + (mortgageValue/10);
		System.out.println("Mortgaging will give you $" + mortgageValue);
		System.out.println("Unmortgaging will cost then $" + unmortgageValue);
		if(!mortgaged){
			System.out.println("This property is not mortgaged.");
		}
		else{
			System.out.println("This property is currently mortgaged and can not collect rent.");
		}
	}
	
	@Override
	public void printSquare(boolean showPlayers){
		if(!(this.owner == null) && showPlayers){
			if(this.monopoly)
				System.out.print("[M] ");
			if(this.mortgaged)
				System.out.print("X ");
			System.out.print(this.owner.getName() + " > ");
		}
		super.printSquare(showPlayers);
	}
	
	@Override
	public void setOwner(Player player){
		this.owner = player;
	}
	
	@Override
	public Player getOwner(){
		return this.owner;
	}
	
	@Override
	public void monopolize(boolean setting){
		this.monopoly = setting;
	}

	@Override
	public boolean isMortgaged(){
		return this.mortgaged;
	}
	
	@Override
	public int getCost(){
		return this.buyPrice;
	}
	
	@Override
	public void setMortgage(boolean setting){
		this.mortgaged = setting;
	}
	
	@Override
	public boolean isMonopoly(){
		return this.monopoly;
	}
	
	@Override
	public int removeOwner(boolean splitMonopoly){
		if(!splitMonopoly){
			Iterator<Property> itr = this.getJointPropertyList().iterator();
			while(itr.hasNext())
				itr.next().monopolize(false);
		}
		this.owner.removeProperty(this);
		this.owner = null;
		return 0;
	}
}
