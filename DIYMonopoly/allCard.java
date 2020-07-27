import java.util.List;

public class allCard extends Card{
	private int individualCost;

	public allCard(String message, int individualCost){
		super(message);
		this.individualCost = individualCost;
	}

	@Override
	public boolean activateCard(Player player){
		System.out.println(this.message);
		Player[] playerList = MonopolyMain.PlayerList;
		int paySum = 0;
		//pay = (numPlayers-1) * inidividualCost
		if(!MonopolyMain.playerActions(0, player, paySum))
			return false;
		else{
			//Pay all other players
			return true;
		}
	}
	
	@Override
	public void debugPrint(){
		System.out.println("Message: " + this.message);
		System.out.println("This card has you receive $" + this.individualCost + " from each player.");
	}
}
