
public class TrainerList {
	private LinkedList<TrainerInfo> TrainerList;
	
	public TrainerList(){
		this.TrainerList = new LinkedList<TrainerInfo>();
	}
	
	public LinkedListIterator<TrainerInfo> iterator() {
		LinkedListIterator<TrainerInfo> iterator = TrainerList.iterator(); //We use our created link list iterator to iterate
		return iterator;
	}
	
	public void addTrainer(String name, int BFID){
		TrainerInfo trainer = new TrainerInfo(name,BFID);
		TrainerList.add(trainer);
	}
	
	public void addTrainer(TrainerInfo trainer){
		TrainerList.add(trainer);
	}
	
	public int getSize(){
		return TrainerList.size();
	}
	
	public TrainerInfo getTrainer(int index){
		return TrainerList.get(index);
	}
	
	public boolean isEmpty(){
		return TrainerList.isEmpty();
	}
	
	public TrainerInfo removeTrainer(int index){
		return TrainerList.remove(index);
	}
	
	public boolean BFIDexist(int BFID){
		LinkedListIterator<TrainerInfo> itr = TrainerList.iterator(); //Our iterator to look through the train
		TrainerInfo currTrainer; //Current car we are looking at;
		while(itr.hasNext()){
			currTrainer = itr.next();
			if(currTrainer.getBFID() == BFID){
				return true;
			}
		}
		return false;
	}
	
	public void allTrainers(){
		LinkedListIterator<TrainerInfo> itr = TrainerList.iterator();
		if(TrainerList.isEmpty()){
			System.out.println("There are no trainers to display!");
			return;
		}
		TrainerInfo currTrainer = itr.next();
		int counter = 1;
		System.out.println(counter + ") " + currTrainer.getTrainerName() + "   BFID: " + String.format("%05d",currTrainer.getBFID()));
		while(itr.hasNext()){
			counter++;
			currTrainer = itr.next();
			System.out.println(counter + ") " + currTrainer.getTrainerName() + "   BFID: " + String.format("%05d",currTrainer.getBFID()));
		}
	}
}
