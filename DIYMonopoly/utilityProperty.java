import java.util.Iterator;

public class utilityProperty extends Property{
	
	private int buyPrice;
	private int rentPrice;
	private int rentPriceMonopoly;
	private Player owner;
	private boolean mortgaged;
	private boolean monopoly;

	public utilityProperty(String propertyName, int buyPrice, int rentPrice, int rentPriceMonopoly) {
		super(propertyName, "Utility");
		this.buyPrice = buyPrice;
		this.rentPrice = rentPrice;
		this.rentPriceMonopoly = rentPriceMonopoly;
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
			if(this.monopoly){
				cost = moveNum*10;
			}
			else
				cost = moveNum*4;
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
				+ "\nNormal: $" + this.rentPrice + " x Roll Number."
				+ "\nMonopoly: $" + this.rentPriceMonopoly + " x Roll Number.");
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
	public void setOwner(Player player){
		this.owner = player;
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
