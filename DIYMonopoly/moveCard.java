
public class moveCard extends Card{
	private String placeName;
	private int type;

	public moveCard(String message, String placeName, int type){
		super(message);
		this.placeName = placeName;
		this.type = type;
	}

	@Override
	public boolean activateCard(Player player){
		System.out.println(this.message);
		switch(type){
			case 0: player.moveName(this.placeName); break;
			case 1: player.moveColor(this.placeName); break;
			default: System.out.println("Oops");
		}
		return true;
	}
	
	@Override
	public void debugPrint(){
		System.out.println("Message: " + this.message);
		if(type == 0)
			System.out.println("This card moves you to a property named: " + this.placeName);
		if(type == 1)
			System.out.println("This card moves you to the closest property with the color: " + this.placeName);
	}
}
