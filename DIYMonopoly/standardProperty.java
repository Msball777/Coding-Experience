import java.util.Iterator;

public class standardProperty extends Property{
	
	private int buyPrice;
	private int rentPrice;
	private int rentPrice1;
	private int rentPrice2;
	private int rentPrice3;
	private int rentPrice4;
	private int rentPriceHotel;
	private int houseNum;
	private int housePrice;
	boolean mortgaged;
	private Player owner;
	private boolean monopoly;

	public standardProperty(String propertyName, String color, int buyPrice,
			int rentPrice, int rentPrice1, int rentPrice2, int rentPrice3, int rentPrice4, int rentPriceHotel, int housePrice) {
		super(propertyName, color);
		this.buyPrice = buyPrice;
		this.rentPrice = rentPrice;
		this.rentPrice1 = rentPrice1;
		this.rentPrice2 = rentPrice2;
		this.rentPrice3 = rentPrice3;
		this.rentPrice4 = rentPrice4;
		this.rentPriceHotel = rentPriceHotel;
		this.houseNum = 0;
		this.housePrice = housePrice;
		this.monopoly = false;
		mortgaged = false;
		this.owner = null;
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
		}
		else if(this.owner != player && !this.mortgaged){
			int cost = 0;
			if(this.monopoly){
				switch(this.houseNum){
					case 0: cost = rentPrice*2; break;
					case 1: cost = rentPrice1; break;
					case 2: cost = rentPrice2; break;
					case 3: cost = rentPrice3; break;
					case 4: cost = rentPrice4; break;
					case 5: cost = rentPriceHotel; break;
				}
			}
			else{
				cost = rentPrice;
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
		return true;
	}
	
	@Override
	public void printProperty(){
		super.printProperty();
		if(this.owner == null){
			System.out.println("This property is unowned.");
		}
		else{
			System.out.println("This property is owned by " + this.owner.getName() + ".");
			if(this.houseNum < 5 && this.houseNum > 1){
				System.out.println("There are " + this.houseNum + " house on this property.");
			}
			else if(this.houseNum == 1){
				System.out.println("There is 1 house on this property.");
			}
			else if(this.houseNum == 5)
				System.out.println("There is a hotel on this property.");
		}
		System.out.println("This property costs $" + this.buyPrice
				+ "\n---Rent---"
				+ "\n0 Houses: $" + this.rentPrice
				+ "\n1 Houses: $" + this.rentPrice1
				+ "\n2 Houses: $" + this.rentPrice2
				+ "\n3 Houses: $" + this.rentPrice3
				+ "\n4 Houses: $" + this.rentPrice4
				+ "\nHotel: $" + this.rentPriceHotel
				+ "\nHouses cost $" + this.housePrice + ".");
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
			if(this.houseNum < 4 && this.houseNum > 1){
				for(int i = 0; i < this.houseNum; i++)
					System.out.print("h ");
			}
			else if(this.houseNum == 5)
				System.out.print("H ");
			if(this.mortgaged)
				System.out.print("X ");
			System.out.print(this.owner.getName() + " > ");
		}
		super.printSquare(showPlayers);
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
		this.owner.removeProperty(this);
		this.owner = null;
		int returnedHouses = this.houseNum;
		if(!splitMonopoly){
			this.houseNum = 0;
			Iterator<Property> itr = this.getJointPropertyList().iterator();
			while(itr.hasNext())
				itr.next().monopolize(false);
			return returnedHouses;
		}

		return 0;
	}
	
	@Override
	public void setOwner(Player player){
		this.owner = player;
	}
	
	@Override
	public int getHousePrice(){
		return this.housePrice;
	}
	
	@Override
	public int getHouseNum(){
		return this.houseNum;
	}
	
	public void addHouse(){
		this.houseNum++;
	}
	
	public void removeHouse(){
		this.houseNum--;
	}
	
	public void setHouses(int num){
		this.houseNum = num;
	}

}