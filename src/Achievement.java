/********ACHIEVEMENT.JAVA******
 * The class is responsible for storing, diplying and doing general operations with Achievements.
 * 
 */


import java.util.HashMap;
import java.util.Map.Entry;

public class Achievement implements java.io.Serializable {

	/**
	 * think about enums!!!
	 * 
	 * 
	 */
	private static final long serialVersionUID = 5L;
	private HashMap<String, Integer> achievements;
	HashMap<String, Integer> newAchievements;

	public Achievement() {

		achievements = new HashMap<String, Integer>(10);
		achievements.put("Game won as Order: ", 0);
		achievements.put("Game won as Chaos: ", 0);
		achievements.put("You beat AI_Josh: ", 0);
		achievements.put("You beat a fellow human: ", 0);
		achievements.put("Games won in under 10 moves: ", 0);
		achievements.put("Won 20 games as Order: ", 0);
		achievements.put("Won 20 games as Chaos: ", 0);
		achievements.put("Game won under 2 minutes: ", 0);
		achievements.put("Game won under 5 minutes: ", 0);
	}

	public void newAchievementsEarned(int wonAsOrder, int wonAsChaos,
			int beatAI, int gameTime, int numberOfMoves) {
		newAchievements = new HashMap<String, Integer>(
				10);
		newAchievements.put("Game won as Order: ", wonAsOrder);
		newAchievements.put("Game won as Chaos: ", wonAsChaos);
		if (beatAI == 1) {
			newAchievements.put("You beat AI_Josh: ", 1);
		} else {
			newAchievements.put("You beat a fellow human: ", 1);
		}
		if (numberOfMoves < 10) {
			newAchievements.put("Games won in under 10 moves: ", 1);
		}
		if(((achievements.get("Game won as Order: ")+ newAchievements.get("Game won as Order: "))%20) == 0){
		newAchievements.put("Won 20 games as Order: ", 1);
		}
		if(((achievements.get("Game won as Chaos: ")+ newAchievements.get("Game won as Chaos: "))%20) == 0){
		newAchievements.put("Won 20 games as Chaos: ", 1);
		}
		if(gameTime <2){
			newAchievements.put("Game won under 2 minutes: ", 1);
		}
		else if(gameTime <2){
			newAchievements.put("Game won under 5 minutes: ", 1);
		}
		update();

	}
	
	public void update() {
		for (Entry<String, Integer> entry : newAchievements.entrySet()) {	
			achievements.put(entry.getKey(), achievements.get(entry.getKey())+entry.getValue());
		
		}
	
	}
	
	public String printAllAchievements(){
		return printStats(achievements);
	}
	

	public String printNewAchievements(){
		return printStats(newAchievements);
	}

	public String printStats(HashMap<String, Integer> toPrint) {
		String AllAchievements = "";
		for (Entry<String, Integer> entry : toPrint.entrySet()) {
			
			if (entry.getValue()>0 ){
				AllAchievements = AllAchievements + entry.getKey() + entry.getValue() + "\n";	
			
			}
		}
		if (AllAchievements.equals("")){
			return "No achievements gained";
		}
		return AllAchievements;
	}

	/******** _GET METHODS *******/
	public int getGamesWonOrder() {
		return achievements.get("Game won as Order: ");
	}

	public int getGamesWonChaos() {
		return achievements.get("Game won as Chaos: ");
	}

	public int getWon10Moves() {
		return achievements.get("Games won in under 10 moves: ");
	}

	public int getWon20Order() {
		return achievements.get("Won 20 games as Order: ");
	}

	public int getWon20Chaos() {
		return achievements.get("Won 20 games as Chaos: ");
	}

	public int getBeatAI() {
		return achievements.get("You beat AI_Josh: ");
	}

	public int getBeatHuman() {
		return achievements.get("You beat a fellow human: ");
	}

	/**** SET METHODS ****/

	public void setWonOrder(int value) {
		achievements.put("gamesWonOrder", value);
	}

	public void setWonChaos(int value) {
		achievements.put("gamesWonChaos", value);
	}

	public void setWon10Moves(int value) {
		achievements.put("won10Moves", value);
	}

	public void setWon20Order(int value) {
		achievements.put("won20Order", value);
	}

	public void setWon20Chaos(int value) {
		achievements.put("won20Chaos", value);
	}

	public void setBeatAI(int value) {
		achievements.put("beatAI", value);
	}

	public void setBeatHuman(int value) {
		achievements.put("beatHuman", value);
	}


	/*
	 * public ArrayList<String> FlushAllAchievements(ArrayList<String>
	 * OldAchievements, int playerIndex) { OldAchievements.set(playerIndex+1,
	 * Integer.toString(getGamesPlayedOrder()));
	 * OldAchievements.set(playerIndex+2,
	 * Integer.toString(getGamesPlayedChaos()));
	 * OldAchievements.set(playerIndex+3, Integer.toString(getGamesWonOrder()));
	 * OldAchievements.set(playerIndex+4, Integer.toString(getGamesWonChaos()));
	 * OldAchievements.set(playerIndex+5, Integer.toString(getWon10Moves()));
	 * OldAchievements.set(playerIndex+6, Integer.toString(getWon20Order()));
	 * OldAchievements.set(playerIndex+7, Integer.toString(getWon20Chaos()));
	 * OldAchievements.set(playerIndex+8, Integer.toString(getBeatAI()));
	 * OldAchievements.set(playerIndex+9, Integer.toString(getBeatHuman()));
	 * 
	 * return OldAchievements; }
	 */

}
