
public class buildingPayCard extends Card{
	private int houseCost;
	private int hotelCost;

	public buildingPayCard(String message, int houseCost, int hotelCost){
		super(message);
		this.houseCost = houseCost;
		this.hotelCost = hotelCost;
	}

	@Override
	public boolean activateCard(Player player){
		System.out.println(this.message);
		int paySum = 0;
		//total up building costs
		if(paySum == 0)
			return true;
		else
			return MonopolyMain.playerActions(0, player, paySum);
	}
	
	@Override
	public void debugPrint(){
		System.out.println("Message: " + this.message);
		System.out.println("This card expects you to pay $" + this.houseCost + " for each house and $" + this.hotelCost + " for each hotel.");
	}
}
