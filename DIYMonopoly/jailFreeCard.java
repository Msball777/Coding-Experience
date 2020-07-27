
public class jailFreeCard extends Card{

	public jailFreeCard(String message) {
		super(message);
	}
	
	@Override
	public boolean activateCard(Player player){
		super.activateCard(player);
		player.giveJailCard();
		return true;
	}

}
