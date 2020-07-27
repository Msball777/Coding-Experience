
public class PokeInfo {
	private String name = "";
	private int BFID;
	private int[] stats;
	private int level;
	private String[] typing;
	private String[] moves;
	private String ability;
	private String form;
	private String heldItem;
	private String gender;
	
	public PokeInfo(String name, int BFID, int level, int[] stats, String[] moves, String ability, String form, String[] typing, String item, String gender){
		this.name = name;
		this.BFID = BFID;
		this.stats = new int[6];
		this.stats = stats;
		this.moves = new String[4];
		this.moves = moves;
		this.ability = ability;
		this.level = level;
		this.form = form;
		this.heldItem = item;
		this.typing = new String[2];
		this.typing = typing;
		this.gender = gender;
	}
	
	public void setName(String setName){
		this.name = setName;
	}
	
	public void setStats(int[] stats){
		this.stats = stats;
	}
	
	public void setMoves(String[] setMoves){
		this.moves = setMoves;
	}
	
	public void setLosses(String ability){
		this.ability = ability;
	}
	
	public void setLevel(int setLevel){
		this.level = setLevel;
	}
	
	public void setBFID(int setBFID){
		this.BFID = setBFID;
	}
	
	public void setItem(String item){
		this.heldItem = item;
	}
	
	public void setGender(String gender){
		this.gender = gender;
	}
	
	public String getName(){
		return this.name;
	}
	
	public int getBFID(){
		return this.BFID;
	}
	
	public String getType1(){
		return this.typing[0];
	}
	
	public String getType2(){
		return this.typing[1];
	}
	
	public String getItem(){
		return this.heldItem;
	}
	
	public void printPokemon(){
		System.out.println("Name: " + this.name);
		if(this.typing[1].equals("N/A"))
			System.out.println("Type: " + this.typing[0]);
		else
			System.out.println("Type: " + this.typing[0] + "/" + this.typing[1]);
		System.out.println("Ability: " + this.ability);
		System.out.println("Level: " + this.level);
		System.out.println("Gender: " + this.gender);
		System.out.print("Stats: "); for(int i = 0; i < 5; i++){System.out.print(this.stats[i] + "/");}System.out.println(this.stats[5]);
		System.out.print("Moves: ");
		boolean needBracket = false;
		if(!this.moves[0].equals("")){
			System.out.print(this.moves[0]);
			needBracket = true;
		}
		for(int i = 1; i < 4; i++){
			if(this.moves[i].equals("")){
				continue;
			}
			if(needBracket)
				System.out.print("/");
			System.out.print(this.moves[i]);
			needBracket = true;
		}
		System.out.println();
		
		if(!this.form.equals("N/A"))
			System.out.println("Form: " + this.form);
		
		if(!this.heldItem.equals("N/A"))
			System.out.println("Held Item: " + this.heldItem);
		
		System.out.println();
	}
	
	public String writePokemonToFile(){
		String writtenPoke = this.getName();
		String stats = this.stats[0] + "/" + this.stats[1] + "/" + this.stats[2] + "/" + this.stats[3] + "/" + 
				this.stats[4] + "/" + this.stats[5];
		String moves = this.moves[0] + "/" + this.moves[1] + "/" + this.moves[2] + "/" + this.moves[3];
		String item = this.heldItem;
		if(this.heldItem.trim().equals("N/A"))
			item = "";
		String form = this.form;
		if(this.form.trim().equals("N/A"))
			form = "";
		String type2 = "/" + this.typing[1];
		if(this.typing[1].trim().equals("N/A"))
			type2 = "";
			
		writtenPoke = writtenPoke + ";" + this.level + ";" + stats + ";" + moves + ";" + this.ability + ";" + form + ";" + this.typing[0] + type2 +
				";" + item + ";" + this.gender;
		
		return writtenPoke;
		
	}
}
