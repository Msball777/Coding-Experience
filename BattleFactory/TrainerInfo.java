
public class TrainerInfo {
	private String name = "";
	private int wins = 0;
	private int losses = 0;
	private int BFID = 0;
	private LinkedList<PokeInfo> Team;
	
	public TrainerInfo(String name, int BFID){
		this.name = name;
		this.wins = 0;
		this.losses = 0;
		this.BFID = BFID;
		Team = new LinkedList<PokeInfo>();
	}

	public void setTrainerName(String setName){
		this.name = setName;
	}
	
	public void setWins(int setWin){
		this.wins = setWin;
	}
	
	public void setLosses(int setLoss){
		this.losses = setLoss;
	}
	
	public String getTrainerName(){
		return this.name;
	}
	
	public int getBFID(){
		return this.BFID;
	}
	
	public int getWins(){
		return this.wins;
	}
	
	public int getLosses(){
		return this.losses;
	}
	
	public void givePoke(PokeInfo pokemon){
		Team.add(pokemon);
	}
	
	public void expandPoke(int index){
		Team.get(index).printPokemon();
	}
	
	public PokeInfo removePoke(String pokeName){
		LinkedListIterator<PokeInfo> itr = Team.iterator(); //Our iterator to look through the train
		PokeInfo currPoke; //Current car we are looking at
		int counter = 0; //A counter to tell us where to remove when we find it
		while(itr.hasNext()){
			currPoke = itr.next();
			if(currPoke.getName().equals(pokeName)){
				return Team.remove(counter);
			}
			else
				counter++;
		}
		return null;
	}
	
	public PokeInfo removePoke(int index){
		return Team.remove(index);
	}
	
	public LinkedListIterator<PokeInfo> iterator() {
		LinkedListIterator<PokeInfo> iterator = Team.iterator(); //We use our created link list iterator to iterate
		return iterator;
	}
	
	public void printTrainer(){
		System.out.println("Name: " + this.name);
		System.out.println("Wins: " + this.wins);
		System.out.println("Losses: " + this.losses);
		LinkedListIterator<PokeInfo> itr = Team.iterator();
		PokeInfo currPoke = itr.next();
		System.out.print(currPoke.getName());
		while(itr.hasNext()){
			currPoke = itr.next();
			System.out.print(";" + currPoke.getName());
		}
		System.out.println();
//		if(this.ranking != 0){
//			System.out.println("Currently participating in a tournament.");
//			System.out.println("Rank: " + this.ranking);
//			System.out.print("Team: ");
//			LinkedListIterator<PokeInfo> itr = Team.iterator();
//			PokeInfo currPoke = itr.next();
//			System.out.print(currPoke.getName());
//			while(itr.hasNext()){
//				currPoke = itr.next();
//				System.out.print(" ; " + currPoke.getName());
//			}
//		}
	}
}
