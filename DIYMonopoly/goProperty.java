
public class goProperty extends Property{
	private int goReward;

	public goProperty(int goReward) {
		super("Go", "None");
		this.goReward = goReward;
	}

	@Override
	public void touchProperty(Player player){
		System.out.println("Passed Go! Received $" + this.goReward + "!");
		player.giveCash(this.goReward);
		return;
	}
	
	@Override
	public void printProperty(){
		super.printProperty();
		System.out.println("Passing or landing on this gives you $" + this.goReward + "!");
	}
}
