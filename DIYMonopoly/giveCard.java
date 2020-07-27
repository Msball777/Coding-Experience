
public class giveCard extends Card {
	private int givenCash;

	public giveCard(String message, int givenCash){
		super(message);
		this.givenCash = givenCash;
	}

	@Override
	public boolean activateCard(Player player){
		System.out.println(this.message);
		player.giveCash(this.givenCash);
		return true;
	}
	
	@Override
	public void debugPrint(){
		System.out.println("Message: " + this.message);
		System.out.println("This card gives you $" + this.givenCash);
	}
}
