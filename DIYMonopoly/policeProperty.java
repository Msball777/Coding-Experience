import java.util.Iterator;

public class policeProperty extends Property{
	private Property jailLocation;

	public policeProperty() {
		super("Go to Jail", "None");
		this.jailLocation = null;
	}
	
	@Override
	public boolean activateProperty(Player player, int moveNum){
		super.activateProperty(player, moveNum);
		System.out.println("Uh oh! You're going to jail!");
		if(this.jailLocation != null)
			player.goToJail(this.jailLocation);
		return true;
	}

	@Override
	public void printProperty(){
		super.printProperty();
		System.out.println("This throws you to " + this.jailLocation.getName() + "!");
	}
	
	@Override
	public void addJointProperty(Property property){
		super.addJointProperty(property);
		this.jailLocation = property;
	}
}
