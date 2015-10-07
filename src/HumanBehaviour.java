/***HUMANBEHAVIOUR.JAVA
 * 
 * This is the class for regular Players. Just places the move made in the board.
 */
import java.io.Serializable;

public class HumanBehaviour implements GameBehaviour, Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Board board;

	public void setBoard(Board board) {
		this.board = board;
	}

	@Override
	public boolean startTurn() {
		return true;
		
	}

	@Override
	public void playTurn(MoveDetails movement) {
		
		 board.Move(movement);
		
	}

}
