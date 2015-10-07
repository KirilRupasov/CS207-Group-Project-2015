/*****GAMEBEHAVIOUR.JAVA***
 * 
 * interface that is used for AI_Josh_Chaos/OrderBehaviour classes, as well as HumanBehaviour
 *
 */
public interface GameBehaviour {
	
boolean startTurn(); 

void playTurn(MoveDetails movement);

void setBoard(Board board);

}
