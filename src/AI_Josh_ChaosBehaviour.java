/********AI_JOSH_CHAOSBEHAVIOR.JAVA******
 * The class is responsible for calculating the move for AI, called Josh as Chaos
 */

import java.io.Serializable;
import java.util.Random;


public class AI_Josh_ChaosBehaviour implements GameBehaviour, Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Board board;
	private MoveDetails lastMove;

	public void setBoard(Board board) {
		this.board = board;
	}

	@Override
	public boolean startTurn() {
		
		this.lastMove = board.getLastMove();
		playTurn(lastMove);
		return false;
		
	}

	@Override
	public void playTurn(MoveDetails lastMove) {
		MoveDetails movement;

			movement = findPositionChaos(lastMove);
			if(!board.Move(movement)){
				board.Move(board.getFreeMove());
			
		}

	}
	
	
	
	
	public MoveDetails findPositionChaos(MoveDetails lastMove) {

		int x = lastMove.getX();
		int y = lastMove.getY();
		System.out.println(x);
		System.out.println(y);
		char s = lastMove.getState();
		char grid[][] = board.getBoard();
		int n = board.getBoardSize();

		// check column
		int count = 0;

		for (int i = 1; i <= n; i++) {
			if (grid[i%6][y] == s)
				count++;
			if (grid[i%6][y] != s && grid[i%6][y] != '\u0000')
				break;
		}

		if (count > 2) {
			System.out.println("check row");

			int empty_pos = 0;
			for (int i = 1; i <= n; i++) {
				if (grid[i % 6][y] == '\u0000') {
					System.out.println("!Y!! " + y);
					System.out.println("!X!! " + i);
					empty_pos = (i%6);
					break;
				}
			}

			if (s == 'X') {
				return new MoveDetails(empty_pos, y, 'O');
			} else {
				return new MoveDetails(empty_pos, y, 'X');
			}

		}

		// check row
		count = 0;

		for (int i = 1; i <= n; i++) {
			if (grid[x][i%6] == s)
				count++;
			if (grid[x][i%6] != s && grid[x][i%6] != '\u0000')
				break;

		}

		if (count > 2)

		{
			System.out.println("check column");
			int empty_pos = 0;
			for (int i = 1; i <= n; i++) {
				if (grid[x][i % 6] == '\u0000') {
					empty_pos = (i%6);
					break;
				}
			}
			System.out.println("counter");

			if (s == 'X') {
				return new MoveDetails(x, empty_pos, 'O');
			} else {
				return new MoveDetails(x, empty_pos, 'X');
			}

		}

		// check anti-diag

		int tester = x + y;
		count = 0;
		switch (tester) {
		case 4:
			for (int i = 0; i < (n - 1); i++) {
				if (grid[i][(n - 2) - i] == s)
					count++;
				if (grid[i][(n - 2) - i]  != s && grid[i][(n - 2) - i]  != '\u0000')
					break;

			}
			break;

		case 5:
			for (int i = 0; i < n; i++) {
				if (grid[i][n - 1 - i] == s)
					count++;
				if (grid[i][(n - 1) - i]  != s && grid[i][(n - 1) - i]  != '\u0000')
					break;
			}

			break;
		case 6:
			for (int i = 1; i < (n); i++) {
				if (grid[i][(n) - i] == s)
					count++;
				if (grid[i][n - i]  != s && grid[i][n - i]  != '\u0000')
					break;
			}

			break;
		default:
			break;

		}

		if (count > 2) {
			switch (tester) {
			case 4:
				System.out.println("check anti-diag");
				for (int i = 0; i < (n - 1); i++) {
					if (grid[i][(n - 2) - i] == '\u0000') {
						x = i;
						y = n - 2 - i;
						break;
					}
				}
				if (s == 'X') {
					return new MoveDetails(x, y, 'O');
				} else {
					return new MoveDetails(x, y, 'X');
				}
			case 5:

				System.out.println("check anti-diag");
				for (int i = 1; i <= n; i++) {
					int temp = ((n - 1 - i) % 6);
					if (temp < 0)
						temp += n;
					if (grid[i % 6][temp] == '\u0000') {
						x = i % 6;
						y = temp;
						break;
					}
				}
				if (s == 'X') {
					return new MoveDetails(x, y, 'O');
				} else {
					return new MoveDetails(x, y, 'X');
				}

			case 6:
				System.out.println("check anti-diag");
				for (int i = 1; i < (n - 1); i++) {
					if (grid[i][(n) - i] == '\u0000') {
						x = i;
						y = n - i;
						break;
					}
				}
				if (s == 'X') {
					return new MoveDetails(x, y, 'O');
				} else {
					return new MoveDetails(x, y, 'X');
				}

			default:
				break;

			}

		}

		// check diag
		count = 0;
		if (x == y) {
			for (int i = 0; i < (n); i++) {
				if (grid[i][i] == s) {
					count++;
				}
			}

		} else if ((x - y) == 1) {
			for (int i = 1; i < (n); i++) {
				if (grid[i][i - 1] == s) {
					count++;
				}

			}

		} else if ((y - x) == 1) {
			for (int i = 0; i < (n - 1); i++) {

				if (grid[i][i + 1] == s) {
					count++;
				}

			}
		}

		if (count > 2) {
			if (x == y) {
				System.out.println("check diag");
				for (int i = 1; i <= (n); i++) {
					if (grid[i % 6][i % 6] == '\u0000') {
						x = i;
						y = i;
						break;
					}
				}
				if (s == 'X') {
					return new MoveDetails(x, y, 'O');
				} else {
					return new MoveDetails(x, y, 'X');
				}

			} else if ((x - y) == 1) {

				System.out.println("check anti-diag");
				for (int i = 1; i < n; i++) {
					if (grid[i][i - 1] == '\u0000') {
						x = i;
						y = i - 1;
						break;
					}
				}
				if (s == 'X') {
					return new MoveDetails(x, y, 'O');
				} else {
					return new MoveDetails(x, y, 'X');
				}
			} else if ((y - x) == 1) {
				System.out.println("check anti-diag");
				for (int i = 0; i < (n - 1); i++) {
					if (grid[i][i + 1] == '\u0000') {
						x = i;
						y = i + 1;
						break;
					}
				}
				if (s == 'X') {
					return new MoveDetails(x, y, 'O');
				} else {
					return new MoveDetails(x, y, 'X');
				}
			}

		}

		x = new Random().nextInt(n);
		y = new Random().nextInt(n);

		if (s == 'X') {
			return new MoveDetails(x, y, 'O');
		} else {
			return new MoveDetails(x, y, 'X');
		}

	}

	

	public int checkAntiDiag(int loop, int n, int offset, char grid[][], char s) {
		int count = 0;
		for (int i = loop; i < (n - 1); i++) {
			if (grid[i][(n - offset) - i] == s)
				count++;

		}
		return count;
	}

	public int checkDiag(int loop, int n, int offset, char grid[][], char s) {
		int count = 0;
		for (int i = loop; i < (n); i++) {
			if (grid[i][i - offset] == s) {
				count++;
			}
		}
		return count;
	}

	public MoveDetails returnAntiDiag(int loop, int n, int offset, char s,
			char grid[][]) {
		int x = 0;
		int y = 0;
		for (int i = loop; i < (n - 1); i++) {
			if (grid[i][(n) - offset - i] == '\u0000') {
				x = i;
				y = n - offset - i;
				break;
			}
		}
		if (s == 'X') {
			return new MoveDetails(x, y, 'O');
		} else {
			return new MoveDetails(x, y, 'X');
		}
	}

	public MoveDetails returnDiag(int loop, int n, int offset, char grid[][],
			char s) {
		int x = 0, y = 0;
		for (int i = loop; i < n; i++) {
			if (grid[i][i - offset] == '\u0000') {
				x = i;
				y = i - 1;
				break;
			}
		}
		if (s == 'X') {
			return new MoveDetails(x, y, 'O');
		} else {
			return new MoveDetails(x, y, 'X');
		}
	}
}
