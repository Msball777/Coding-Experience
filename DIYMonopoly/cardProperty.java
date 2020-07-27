import java.util.List;
import java.util.Random;
import java.util.concurrent.TimeUnit;

public class cardProperty extends Property{
	private int type;
	private List<Card> cardList;

	public cardProperty(String type, int typeNum, List<Card> cardList) {
		super(type, "None");
		this.type = typeNum;
		this.cardList = cardList;
	}
	
	@Override 
	public boolean activateProperty(Player player, int moveNum){
		super.activateProperty(player, moveNum);
		System.out.println("Drawing your card!");
		try {
			TimeUnit.SECONDS.sleep(1);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		Random rand = new Random();
		int chosenCard = rand.nextInt(cardList.size());
		System.out.println("#" + chosenCard + " has been chosen!");
		if(!cardList.get(chosenCard).activateCard(player)){
			return false;
		}
		return true;
	}
	
	public void setCardList(List<Card> cardList){
		this.cardList = cardList;
	}
	
	@Override
	public void printProperty(){
		super.printProperty();
		System.out.println("You will draw a " + this.getName() + " card if you land here!");
	}

}
