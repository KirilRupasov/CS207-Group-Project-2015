/*******BOARD.JAVA*****
 * 
 * This is the board - two-dimensional, additional data and methods which help to manage it.
 */

import java.io.*;
import java.util.ArrayList;
import java.util.Observable;

public class Board extends Observable implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2L;
	private int n;
	private char[][] grid;
	private int moveCount;
	private MoveDetails beforeLastMove;
	private MoveDetails lastMove = new MoveDetails(0, 0, 'O');
	private ArrayList<MoveDetails> pos_moves = new ArrayList<MoveDetails>();

	public Board() {
		n = 6;
		grid = new char[n][n];
		for (int i = 0; i < 6; i++) {

			for (int j = 0; j < 6; j++) {

				pos_moves.add(new MoveDetails(i, j, '\u0000'));
			}
		}
	}

	public MoveDetails getLastMove() {
		return lastMove;

	}

	public MoveDetails getBeforeLastMove() {

		return beforeLastMove;
	}

	public char[][] getBoard() {
		return grid;
	}

	public int getBoardSize() {
		return n;
	}

	public MoveDetails getFreeMove() {
		return pos_moves.get(0);
	}

	public boolean Move(MoveDetails movement) {
		int x = movement.getX();
		int y = movement.getY();
		char s = movement.getState();
		
		if (x == 6 || y == 6){
			System.out.println("X !!!!" + x);
			System.out.println("Y !!!!" + y);
		}
		if (grid[x][y] == '\u0000') {
			
			
			for( int i =0; i < pos_moves.size(); i++){
			
				if (pos_moves.get(i).equals(movement)){
					pos_moves.remove(pos_moves.get(i));
					break;
				}
				
			}
			grid[x][y] = s;
			updateLastMove(movement);

			/* setChanged is a method that works together with notifyObservers */
			setChanged();
			notifyObservers(); /*
								 * validation here means it wont work unless
								 * 'setChanged' ran before
								 */

			moveCount++;
			return true;
		} else {
			return false;
		}

	}

	private void updateLastMove(MoveDetails movement) {
		beforeLastMove = lastMove;
		lastMove = movement;
	}

	public void undo() {
		int x = lastMove.getX();
		int y = lastMove.getY();
		grid[x][y] = '\u0000';

		x = beforeLastMove.getX();
		y = beforeLastMove.getY();
		grid[x][y] = '\u0000';

		moveCount -= 2;

	}

	public boolean checkWinOrder(MoveDetails movement) {
		int x = movement.getX();
		int y = movement.getY();
		char s = movement.getState();

		// check col
		for (int i = 1; i < (n - 1); i++) {
			if (grid[x][i] != s) {
				break;
			}
			if (i == n - 2) {
				// check 1st and last
				if (grid[x][0] == s || grid[x][n - 1] == s) {

					return true;
				}

			}
		}

		// check row
		for (int i = 1; i < (n - 1); i++) {
			if (grid[i][y] != s)
				break;
			if (i == n - 2) {
				// check 1st and last
				if (grid[0][y] == s || grid[n - 1][y] == s) {
					return true;
				}

			}
		}

		// check anti-diag

		int tester = x + y;
		switch (tester) {
		case 4:
			for (int i = 0; i < (n - 1); i++) {
				if (grid[i][(n - 2) - i] != s)
					break;
				// check 1st and last
				if (i == 4) {
					return true;
				}
			}
			break;

		case 5:
			for (int i = 1; i < (n - 1); i++) {
				if (grid[i][(n - 1) - i] != s)
					break;
				if (i == n - 2) {
					// check 1st and last
					if (grid[0][(n - 1)] == s || grid[n - 1][0] == s) {
						return true;

					}

				}
			}
			break;
		case 6:
			for (int i = 1; i < (n); i++) {
				if (grid[i][(n) - i] != s) {
					break;
				}
				// check 1st and last
				if (i == 5) {

					return true;

				}

			}
			break;
		default:
			break;

		}

		// check diag
		if (x == y) {
			for (int i = 1; i < (n - 1); i++) {
				if (grid[i][i] != s)
					break;
				if (i == n - 2) {
					// check 1st and last
					if (grid[0][0] == s || grid[n - 1][n - 1] == s) {

						return true;

					}

				}
			}

		} else if ((x - y) == 1) {
			for (int i = 1; i < (n); i++) {
				if (grid[i][i - 1] != s) {
					break;
				}
				// check 1st and last
				if (i == 5) {

					return true;

				}

			}

		} else if ((y - x) == 1) {
			for (int i = 0; i < (n - 1); i++) {

				if (grid[i][i + 1] != s) {
					break;
				}
				// check 1st and last
				if (i == 4) {

					return true;

				}

			}
		}
		return false;

	}

	public boolean checkWinChaos() {
		// check draw
		if (moveCount == (n * n)) {
			return true;
		}
		return false;
	}

	public int getMoveCount() {
		return moveCount;
	}

	
}
