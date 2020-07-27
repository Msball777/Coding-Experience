import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

public abstract class Property {
	private String propertyName;
	private String color;
	private List<Property> jointProperties;
	private Property forwardProperty;
	private List<Player> occupiers;

	public Property(String propertyName, String color){
		this.propertyName = propertyName;
		this.color = color;
		this.jointProperties = new ArrayList<Property>();
		this.forwardProperty = null;
		this.occupiers = new ArrayList<Player>();
	}
	
	public String getName(){
		return this.propertyName;
	}
	
	public String getColor(){
		return this.color;
	}
	
	public boolean activateProperty(Player player, int moveNum){
		System.out.println("Moved " + moveNum + " spaces to " + this.propertyName);
		return true;
	}
	
	public void printProperty(){
		System.out.println(this.propertyName);
		if(!this.jointProperties.isEmpty()){
			System.out.print("Connected Properties: ");
			Iterator<Property> itr2 = this.jointProperties.iterator();
			while(itr2.hasNext()){
				System.out.print(itr2.next().getName() + ";");
			}
			System.out.println();
		}
		if(!this.color.equals("None"))
			System.out.println(this.color);
	}
	
	public void printSquare(boolean showPlayers){
		System.out.print(this.propertyName);
		if(!this.color.equals("None"))
			 System.out.print(" [" + this.color + "] ");
		if(showPlayers){
			Iterator<Player> itr = this.occupiers.iterator();
			while(itr.hasNext()){
				System.out.print("<" + itr.next().getName());
			}
		}
		System.out.println();
	}
	
	public void debugPrint(){
		System.out.println("Name: " + this.propertyName);
		System.out.println("Color: " + this.color);
		System.out.println("Joint properties: " + this.jointProperties.toString());
	}
	
	public void moveOffProperty(Player player){
		this.occupiers.remove(player);
	}
	
	public void occupyProperty(Player player){
		this.occupiers.add(player);
	}
	
	public Property advanceOne(){
		return this.forwardProperty;
	}
	
	public void touchProperty(Player player){
		return;
	}
	
	public void addJointProperty(Property property){
		if(!this.jointProperties.contains(property))
			this.jointProperties.add(property);
		List<Property>theirProperties = property.getJointPropertyList();
		if(!theirProperties.contains(this))
			property.getJointPropertyList().add(this);
	}
	
	public List<Property> getJointPropertyList(){
		return this.jointProperties;
	}
	
	public void setNextProperty(Property property){
		this.forwardProperty = property;
	}
	
	public void monopolize(boolean setting){
		return;
	}
	
	public Player getOwner(){
		System.out.println("Doesn't work here!");
		return null;
	}
	
	public void setOwner(Player player){
		System.out.println("Doesn't work here!");
		return;
	}
	
	public boolean isMortgaged(){
		System.out.println("This property can't be mortgaged!");
		return false;
	}
	
	public void setMortgage(boolean setting){
		return;
	}
	
	public int getCost(){
		return 0;
	}
	
	public boolean isMonopoly(){
		return false;
	}
	
	public int removeOwner(boolean splitMonopoly){
		return 0;
	}
	
	public int getHousePrice(){
		return 0;
	}
	
	public int getHouseNum(){
		return 0;
	}
}
