/********AI_JOSH_ORDERBEHAVIOUR*****
 * The class is responsible for calculating the move for AI as Order.
 * 
 */
import java.io.Serializable;
import java.util.Random;

public class AI_Josh_OrderBehaviour implements GameBehaviour, Serializable {
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

			movement = findPositionOrder();
			if(!board.Move(movement)){
				board.Move(board.getFreeMove());
			
		}

	}

	public MoveDetails findPositionOrder() {

		char grid[][] = board.getBoard();
		int n = board.getBoardSize();

		int countX;
		int countO;
		int maxCount = 0;
		int maxCountPos = 0;
		char symbol = 'X';
		// check rows!!
		for (int i = 0; i < n; i++) {
			countX = 0;
			countO = 0;
			for (int j = 0; j < n; j++) {
				if (grid[i][j] == 'X') {
					countX++;
				} else if (grid[i][j] == 'O')
					countO++;
			}
			if ((countX == 0) || (countO == 0)) {
				if (maxCount < countX) {
					maxCount = countX;
					maxCountPos = i;
					symbol = 'X';
				}

				if (maxCount < countO) {
					maxCount = countO;
					maxCountPos = i;
					symbol = 'O';
				}
			}
		}
		// check columns!!
		for (int k = 0; k < n; k++) {
			countX = 0;
			countO = 0;
			for (int j = 0; j < n; j++) {
				if (grid[j][k] == 'X')
					countX++;
				else if (grid[j][k] == 'O')
					countO++;
			}
			if (countX == 0 || countO == 0) {
				if (maxCount < countX) {
					maxCount = countX;
					maxCountPos = k + 10;
					symbol = 'X';
				}

				if (maxCount < countO) {
					maxCount = countO;
					maxCountPos = k + 10;
					symbol = 'O';
				}
			}
		}

		// check diag

		countX = 0;
		countO = 0;
		for (int i = 0; i < (n); i++) {
			if (grid[i][i] == 'X')
				countX++;
			else if (grid[i][i] == 'O')
				countO++;
		}
		if (countX == 0 || countO == 0) {
			if (maxCount < countX) {
				maxCount = countX;
				maxCountPos = 30;
				symbol = 'X';
			}

			if (maxCount < countO) {
				maxCount = countO;
				maxCountPos = 30;
				symbol = 'O';
			}
		}
		countX = 0;
		countO = 0;
		for (int i = 1; i < (n); i++) {
			if (grid[i][i - 1] == 'X')
				countX++;
			else if (grid[i][i - 1] == 'O')
				countO++;

		}
		if (countX == 0 || countO == 0) {
			if (maxCount < countX) {
				maxCount = countX;
				maxCountPos = 40;
				symbol = 'X';
			}

			if (maxCount < countO) {
				maxCount = countO;
				maxCountPos = 40;
				symbol = 'O';
			}
		}
		countX = 0;
		countO = 0;
		for (int i = 0; i < (n - 1); i++) {
			if (grid[i][i + 1] == 'X')
				countX++;
			else if (grid[i][i + 1] == 'O')
				countO++;

		}
		if (countX == 0 || countO == 0) {
			if (maxCount < countX) {
				maxCount = countX;
				maxCountPos = 50;
				symbol = 'X';
			}

			if (maxCount < countO) {
				maxCount = countO;
				maxCountPos = 50;
				symbol = 'O';
			}
		}

		// check anti-diag
		countX = 0;
		countO = 0;
		for (int i = 0; i < (n - 1); i++) {
			if (grid[i][(n - 2) - i] == 'X')
				countX++;

			else if (grid[i][(n - 2) - i] == 'O')
				countO++;

		}

		if (countX == 0 || countO == 0) {
			if (maxCount < countX) {
				maxCount = countX;
				maxCountPos = 60;
				symbol = 'X';
			}

			if (maxCount < countO) {
				maxCount = countO;
				maxCountPos = 60;
				symbol = 'O';
			}
		}

		countX = 0;
		countO = 0;
		for (int i = 0; i < n; i++) {
			if (grid[i][n - 1 - i] == 'X')
				countX++;
			else if (grid[i][n - 1 - i] == 'O')
				countO++;

		}

		if (countX == 0 || countO == 0) {
			if (maxCount < countX) {
				maxCount = countX;
				maxCountPos = 70;
				symbol = 'X';
			}

			if (maxCount < countO) {
				maxCount = countO;
				maxCountPos = 70;
				symbol = 'O';
			}
		}

		countX = 0;
		countO = 0;
		for (int i = 1; i < (n); i++) {
			if (grid[i][(n) - i] == 'X')
				countX++;
			else if (grid[i][(n) - i] == 'O')
				countO++;

		}

		if (countX == 0 || countO == 0) {
			if (maxCount < countX) {
				maxCount = countX;
				maxCountPos = 80;
				symbol = 'X';
			}

			if (maxCount < countO) {
				maxCount = countO;
				maxCountPos = 80;
				symbol = 'O';
			}
		}

		if (maxCountPos < 10)
			return new MoveDetails(maxCountPos, new Random().nextInt(n), symbol);
		else if ((10 <= maxCountPos) && (maxCountPos < 20))
			return new MoveDetails(new Random().nextInt(n), maxCountPos - 10,
					symbol);
		else if (maxCountPos == 30) {
			int r = new Random().nextInt(n);
			return new MoveDetails(r, r, symbol);
		} else if (maxCountPos == 40) {
			int r = new Random().nextInt(n - 1);
			return new MoveDetails(r + 1, r, symbol);

		} else if (maxCountPos == 50) {
			int r = new Random().nextInt(n - 1);
			return new MoveDetails(r, r + 1, symbol);
		} else if (maxCountPos == 60) {
			int r = new Random().nextInt(n - 1);
			return new MoveDetails(r, (4 - r), symbol);

		} else if (maxCountPos == 70) {
			int r = new Random().nextInt(n);
			return new MoveDetails(r, (5 - r), symbol);

		} else if (maxCountPos == 80) {
			int r = new Random().nextInt(n - 1);
			return new MoveDetails(r + 1, (6 - r + 1), symbol);

		} else
			return new MoveDetails(new Random().nextInt(n),
					new Random().nextInt(n), 'X');

	}

}
