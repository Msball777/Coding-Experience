
public class TournamentNode {
	private int layer;
	private TrainerInfo Competitor1;
	private TrainerInfo Competitor2;
	private TournamentNode parent;
	
	public TournamentNode(int layer, TrainerInfo Competitor1, TrainerInfo Competitor2, TournamentNode parent){
		this.layer = layer;
		this.Competitor1 = Competitor1;
		this.Competitor2 = Competitor2;
		this.parent = parent;
	}

	public void printBattle(){
		if(this.Competitor1 == null && this.Competitor2 == null)
			System.out.print("x/x;");
		else if(this.Competitor1 != null && this.Competitor2 == null)
			System.out.print(this.Competitor1.getTrainerName() +"/x;");
		else if(this.Competitor1 == null && this.Competitor2 != null)
			System.out.print("x/" + this.Competitor2.getTrainerName() + ";");
		else
			System.out.print(Competitor1.getTrainerName() + "/" + Competitor2.getTrainerName() + ";");
	}
	
	public TrainerInfo getCompetitor(int comp){
		if(comp == 1)
			return this.Competitor1;
		else
			return this.Competitor2;
	}
	
	public void setCompetitor1(TrainerInfo trainer){
		this.Competitor1 = trainer;
	}
	
	public void setCompetitor2(TrainerInfo trainer){
		this.Competitor2 = trainer;
	}
	
	public void advancedWinner(int winner){
		if(winner == 1){
			if(parent.Competitor1 == null)
				parent.Competitor1 = this.Competitor1;
			else
				parent.Competitor2 = this.Competitor1;
		}
		else{
			if(parent.Competitor1 == null)
				parent.Competitor1 = this.Competitor2;
			else
				parent.Competitor2 = this.Competitor2;
		}
	}
	
	public void devancedTwo(boolean one){
		if(one){
			//System.out.println("Moving down: " + parent.Competitor1);
			this.Competitor2 = parent.Competitor1;
			parent.Competitor1 = null;
		}
		else{
			//System.out.println("Moving down: " + parent.Competitor2);
			this.Competitor2 = parent.Competitor2;
			parent.Competitor2 = null;
		}
	}
	
	public int getLayer(){
		return this.layer;
	}
	
	public void setLayer(int layer){
		this.layer = layer;
	}
}
