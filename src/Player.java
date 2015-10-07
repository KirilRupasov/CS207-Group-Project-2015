import java.io.Serializable;

public class Player implements Serializable{

	private static final long serialVersionUID = 1L;
	private String name;
	private Achievement playerAchievements;
	private GameBehaviour gamePlan;
	private Board board;

	

	public Player(String name) {
		this.name = name;
		gamePlan = new HumanBehaviour();
		playerAchievements = new Achievement();
	}
	
	public void setBoard(Board board){
		this.board = board;
		gamePlan.setBoard(board);
	}
	
	public void newAchievementsEarned(int wonAsOrder, int wonAsChaos, int beatAI, int gameTime){
		
		playerAchievements.newAchievementsEarned(wonAsOrder, wonAsChaos, beatAI, gameTime, board.getMoveCount());
	}
	
	public String printAchievements(){
		String allInformation = name + ", these are your achievements to date:\n"+
		playerAchievements.printAllAchievements();
		
		return allInformation;
	}
		
	public String getName() {
		return name;
	}
	
	public Achievement getAchievement(){
		return playerAchievements;
	}
	
	public void setAchievement(Achievement achievement){
		playerAchievements = achievement;
	}
	
	//behaviour that differs for AI and Human players
	
	public void setBehaviour(GameBehaviour gamePlan){
		this.gamePlan = gamePlan;
		
	}
	
	
	public boolean startTurn(){
		return gamePlan.startTurn();
	}
	public void makeMove(MoveDetails movement){
		
		gamePlan.playTurn(movement);
	}

}
