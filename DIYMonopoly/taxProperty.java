
public class taxProperty extends Property{
	private int taxPrice;

	public taxProperty(String propertyName, int taxPrice) {
		super(propertyName,"None");
		this.taxPrice = taxPrice;
	}

	@Override
	public boolean activateProperty(Player player, int moveNum){
		System.out.println("You must pay " + this.taxPrice + " in taxes!");
		return MonopolyMain.playerActions(0, player, this.taxPrice);
	}
	
	@Override
	public void printProperty(){
		super.printProperty();
		System.out.println("Landing on this space will force you to pay $" + this.taxPrice + "!");
	}
}
