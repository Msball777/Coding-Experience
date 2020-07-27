
public abstract class Card {
	protected String message;

	public Card(String message){
		this.message = message;
	}
	
	public boolean activateCard(Player player){
		System.out.println(this.message);
		return true;
	}
	
	public void debugPrint(){
		return;
	}

}
