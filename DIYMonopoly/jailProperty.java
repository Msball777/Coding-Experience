
public class jailProperty extends Property{

	public jailProperty() {
		super("Jail", "None");
	}

	@Override
	public void printProperty(){
		super.printProperty();
		System.out.println("Landing here is a free space, however you will be moved here if jailed!");
	}
}
