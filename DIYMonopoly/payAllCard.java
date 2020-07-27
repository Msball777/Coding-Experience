
public class payAllCard extends Card {
	private int payAmount;

	public payAllCard(String message, int payAmount) {
		super(message);
		this.payAmount = payAmount;
	}

	@Override
	public boolean activateCard(Player player){
		Player[] playerList = MonopolyMain.PlayerList;
		
		return true;
	}
	
	@Override
	public void debugPrint(){
		System.out.println("Message: " + this.message);
		System.out.println("This card expects you to pay $" + this.payAmount + " to all other players");
	}
}
