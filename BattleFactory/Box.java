import java.util.Iterator;

public class Box implements Iterable<PokeInfo> {
	private LinkedList<PokeInfo> BoxList;
	private String Attribute;
	private boolean used;
	private int numUsed;

	public Box(String Attribute){
		BoxList = new LinkedList<PokeInfo>();
		this.Attribute = Attribute;
		boolean used = false;
		int numUsed = 0;
	}
	
	public void setAttribute(String setAttribute){
		this.Attribute = setAttribute;
	}
	
	public String getAttribute(){
		return this.Attribute;
	}
	
	public boolean isEmpty(){
		return BoxList.isEmpty();
	}
	
	public void resetNumUsed(){
		this.numUsed = 0;
	}
	
	public void incrementUsed(int pokeNum){
		this.numUsed++;
		this.BoxList.add(0, this.BoxList.remove(pokeNum));
	}
	
	public int getNumUsed(){
		return this.numUsed;
	}

	public boolean store(PokeInfo pokemon){
		if(!pokemon.getType1().toUpperCase().equals(this.Attribute) && !pokemon.getType2().toUpperCase().equals(this.Attribute)
				&& !this.Attribute.equals("ALL")){
			System.out.println("Error: Pokemon does not fit box's attribute.");
			return false;
		}
		BoxList.add(pokemon);
		return true;
	}
	
	public boolean store(PokeInfo pokemon, int index){
		if((pokemon.getType1().toUpperCase().equals(this.Attribute) || pokemon.getType2().toUpperCase().equals(this.Attribute))){
			System.out.println("Error: Pokemon does not fit box's attribute.");
			return false;
		}
		BoxList.add(index, pokemon);
		return true;
	}
	
	public void release(int index){
		BoxList.remove(index);
	}
	
	public PokeInfo release(String pokeName){
		LinkedListIterator<PokeInfo> itr = BoxList.iterator(); //Our iterator to look through the train
		PokeInfo currPoke; //Current car we are looking at
		int counter = 0; //A counter to tell us where to remove when we find it
		while(itr.hasNext()){
			currPoke = itr.next();
			if(currPoke.getName().equals(pokeName)){
				return BoxList.remove(counter);
			}
			else
				counter++;
		}
		return null;
	}
	
	public boolean getUsage(){
		return used;
	}
	
	public void setUsage(boolean used){
		this.used = used;
	}
	
	public PokeInfo getPoke(int index){
		return BoxList.get(index);
	}
	
	public PokeInfo getPoke(String pokeName){
		LinkedListIterator<PokeInfo> itr = BoxList.iterator(); //Our iterator to look through the train
		PokeInfo currPoke; //Current car we are looking at
		int counter = 0; //A counter to tell us where to remove when we find it
		while(itr.hasNext()){
			currPoke = itr.next();
			if(currPoke.getName().equals(pokeName)){
				return BoxList.get(counter);
			}
			else
				counter++;
		}
		return null;
	}
	
	public LinkedListIterator<PokeInfo> iterator() {
		LinkedListIterator<PokeInfo> iterator = BoxList.iterator(); //We use our created link list iterator to iterate
		return iterator;
	}
	
	public void allBoxPoke(){
		LinkedListIterator<PokeInfo> itr = BoxList.iterator();
		PokeInfo currPoke = itr.next();
		int counter = 1;
		System.out.println(counter + ") " + currPoke.getName());
		while(itr.hasNext()){
			counter++;
			currPoke = itr.next();
			System.out.println(counter + ") " + currPoke.getName());
		}
	}
	
	public int boxSize(){
		return BoxList.size();
	}
	
}
