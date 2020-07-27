
public class PCBox implements Iterable<Box> {
	private LinkedList<Box> Boxes;
	private int numUsed;
	
	public PCBox(){
		this.Boxes = new LinkedList<Box>();
		this.numUsed = 0;
	}
	
	public LinkedListIterator<Box> iterator() {
		LinkedListIterator<Box> iterator = Boxes.iterator(); //We use our created link list iterator to iterate
		return iterator;
	}
	
	public boolean isEmpty(){
		return Boxes.isEmpty();
	}
	
	public int getSize(){
		return Boxes.size();
	}
	
	public void moveThisBox(int pos){
		this.numUsed++;
		Boxes.add(0, Boxes.remove(pos));
	}
	
	public void resetUsed(){
		this.numUsed = 0;
	}
	
	public int getNumUsed(){
		return this.numUsed;
	}
	
	public void createBox(String attribute){
		Box box = new Box(attribute);
		Boxes.add(box);
	}
	
	public void createBox(String attribute, PokeInfo pokemon){
		Box box = new Box(attribute);
		box.store(pokemon);
		Boxes.add(box);
	}
	
	public void createBox(Box box){
		Boxes.add(box);
	}
	
	public Box removeBox(int index){
		return Boxes.remove(index);
	}
	
	public Box getBox(int index){
		return Boxes.get(index);
	}
	
	public void displayAllBoxes(){
		LinkedListIterator<Box> itr = Boxes.iterator();
		if(Boxes.isEmpty()){
			System.out.println("PC is empty!");
			return;
		}
		Box currBox = itr.next();
		int counter = 1;
		System.out.println("#" + counter + " (" + currBox.getAttribute() + ")");
		while(itr.hasNext()){
			counter++;
			currBox = itr.next();
			System.out.println("#" + counter + " (" + currBox.getAttribute() + ")");
		}
	}
	
	public int totalPCPokeNum(){
		LinkedListIterator<Box> itr = Boxes.iterator();
		if(Boxes.isEmpty()){
			return 0;
		}
		int numPoke = 0;
		Box currBox;
		while(itr.hasNext()){
			currBox = itr.next();
			numPoke = numPoke + currBox.boxSize();
		}
		
		return numPoke;
	}

}
