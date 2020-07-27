
public class payCard extends Card{
	private int paidCash;

	public payCard(String message, int paidCash){
		super(message);
		this.paidCash = paidCash;
	}

	@Override
	public boolean activateCard(Player player){
		System.out.println(this.message);
		return MonopolyMain.playerActions(0, player, this.paidCash);
	}
	
	@Override
	public void debugPrint(){
		System.out.println("Message: " + this.message);
		System.out.println("This card expects you to pay $" + this.paidCash);
	}
}
