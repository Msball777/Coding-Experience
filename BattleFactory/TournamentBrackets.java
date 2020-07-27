
public class TournamentBrackets {
	private LinkedList<TournamentList> fullBrackets;
	private int numLayers;
	private int players;
	private boolean remainder;
	
	public TournamentBrackets(int players){
		this.players = players;
		this.numLayers = (int)(Math.log(this.players) / Math.log(2));
		this.remainder = false;	
		if(((Math.pow(2, numLayers)) - players) != 0)
			this.remainder = true;
		this.fullBrackets = new LinkedList<TournamentList>();
	}
	
	public LinkedListIterator<TournamentList> iterator() {
		LinkedListIterator<TournamentList> iterator = fullBrackets.iterator(); //We use our created link list iterator to iterate
		return iterator;
	}
	
	public void createLayers(TrainerList randomizedList){
		this.fullBrackets.add(new TournamentList(0));
		this.fullBrackets.get(0).createTopLayer();
		for(int i = 1; i < this.numLayers-1; i++){
			this.fullBrackets.add(new TournamentList(i));
			fullBrackets.get(i).createEmptyLayer(fullBrackets.get(i-1));
		}
		this.fullBrackets.add(new TournamentList(this.numLayers-1));
		LinkedListIterator<TrainerInfo> trainerItr = randomizedList.iterator();
		fullBrackets.get(this.numLayers-1).createMainStartLayer(trainerItr, fullBrackets.get(this.numLayers-2));
		if(remainder){
			this.fullBrackets.add(new TournamentList(this.numLayers));
			fullBrackets.get(this.numLayers).createRemainderStartLayer(trainerItr, fullBrackets.get(this.numLayers-1));
		}
		int count = this.numLayers-1;
		if(remainder)
			count++;
		for(int i = count; i >= 0; i--){
			fullBrackets.add(i, fullBrackets.remove(0));
		}
	}
	
	public void printBrackets(){
		TournamentNode currNode;
		for(int i = 0; i < numLayers; i++){
			LinkedListIterator<TournamentNode> itr = this.fullBrackets.get(i).iterator();
			while(itr.hasNext()){
				currNode = itr.next();
				currNode.printBattle();
			}
			System.out.println();
		}
		if(remainder){
			LinkedListIterator<TournamentNode> itr = this.fullBrackets.get(numLayers).iterator();
			while(itr.hasNext()){
				currNode = itr.next();
				currNode.printBattle();
			}
			System.out.println();
		}
	}
}
