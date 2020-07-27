import java.util.Iterator;

public class TournamentList{

	private LinkedList<TournamentNode> battleList;
	private int layer;
	
	public TournamentList(int layer){
		this.layer = layer;
		this.battleList = new LinkedList<TournamentNode>();
	}
	
	public LinkedListIterator<TournamentNode> iterator() {
		LinkedListIterator<TournamentNode> iterator = battleList.iterator(); //We use our created link list iterator to iterate
		return iterator;
	}
	
	public TournamentNode getNode(int index){
		return this.battleList.get(index);
	}
	
	public int getSize(){
		return this.battleList.size();
	}
	
	public void addNodes(int num){
		for(int i = 0; i < num; i++)
			this.battleList.add(0, new TournamentNode(0,null,null,null));
	}
	
	public int getLayer(){
		return layer;
	}
	
	public void setLayer(int layer){
		this.layer = layer;
	}
	
	public void createTopLayer(){
		TournamentNode newNode = new TournamentNode(1, null, null, null);
		this.battleList.add(newNode);
	}
	
	public void createMainStartLayer(LinkedListIterator<TrainerInfo> trainerItr, TournamentList aboveLayer){
		int numNodes = (int)Math.pow(2, layer);
		for(int i = 0; i < numNodes; i++){
			this.battleList.add(new TournamentNode(1, trainerItr.next(), trainerItr.next(), aboveLayer.getNode(i/2)));
		}
	}
	
	public void createRemainderStartLayer(LinkedListIterator<TrainerInfo> trainerItr, TournamentList aboveLayer){
		int counter = 0;
		while(trainerItr.hasNext()){
			//System.out.println(counter/2);
			TournamentNode thisNode = new TournamentNode(this.layer, trainerItr.next(), null, aboveLayer.getNode(counter/2));
			if(counter%2 == 0)
				thisNode.devancedTwo(true);
			else
				thisNode.devancedTwo(false);
			this.battleList.add(thisNode);
			counter++;
		}
	}
	
	public void createEmptyLayer(TournamentList aboveLayer){
		int numNodes = (int)Math.pow(2, layer);
		for(int i = 0; i < numNodes; i++){
			this.battleList.add(new TournamentNode(1, null, null, aboveLayer.getNode(i/2)));
		}	
	}
	
}
