
public class jailCard extends Card{
	private Property jailLocation;

	public jailCard(String message, Property jailProperty) {
		super(message);
		this.jailLocation = jailProperty;
	}
	
	@Override
	public boolean activateCard(Player player){
		super.activateCard(player);
		if(this.jailLocation == null){
			System.out.println("No jail set!");
		}
		player.goToJail(this.jailLocation);
		return true;
	}
	
	@Override
	public void debugPrint(){
		System.out.println("Message: " + this.message);
		System.out.println("This card sends you to jail.");
	}
}
