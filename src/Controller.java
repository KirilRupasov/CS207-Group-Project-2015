/*******CONTROLLER.JAVA******
 * 
 * This is GAME's controller. It comes into place once the actual game is started.
 */

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import javax.swing.SwingWorker;

public class Controller {

	private Player order;
	private Player chaos;
	private Player winner;
	private Board board;
	private GameGUI view;
	private Serializer serializer;
	private ArrayList<Player> playersList;
	private String mode;
	private long startGame = System.nanoTime();

	//this is for networking
	private Socket socket;
	private ServerSocket serverSocket;
	private boolean isServer;
	private ObjectOutputStream oos;
	private ObjectInputStream ois;
	private boolean waiting = false;
	/******/



	public Controller(Board board, Player order, Player chaos,
			ArrayList<Player> playersList, String mode) {
		this.mode = mode;
		view = new GameGUI(board, this);
		this.board = board;

		serializer = new Serializer();
		this.order = order;
		this.chaos = chaos;
		this.playersList = playersList;
		order.setBoard(board);
		chaos.setBoard(board);
	
		if (mode.equals("MultiPlayer"))
			execOnlineGame();
	
		view.display();
		view.setIsOrdersTurn(false);
		changeTurn();

	}
	public void pause() {
		waiting = true;
	}

	public void go() {
		waiting = false;
	}

	public boolean isWaiting() {
		return waiting;
	}
	
	public void setServer(){
		isServer = true;
	}
	
	/***SWING WORKER HERE***/
	public void execOnlineGame(){
	SwingWorker<Void, Void> worker = new SwingWorker<Void, Void>() {

		@Override
		protected Void doInBackground() throws Exception {
			while (winner == null) {
				if (isWaiting()) {
					try{
						MoveDetails receivedMovement = receiveMovement();
						play(receivedMovement.getX(),
								receivedMovement.getY(),
								receivedMovement.getState());
						go();
					} catch (ClassNotFoundException cnfe) {
						getView().setErrorMsg("Connection lost... Aborting game");
						pause();
					} catch (IOException io) {
						getView().setErrorMsg("Connection lost... Aborting game");
						pause();
					} 

				} else {
					try {
						Thread.sleep(1000);
					} catch (InterruptedException ie) {
					}
				}
			}
			return null;
		}
	};
	
	
	worker.execute();
	}
	
	public Player getWinner(){
		return winner;
	}
	
	public String getMode(){
		return mode;
	}
	
	public void setMode(String mode){
		this.mode = mode;
	}

	public GameGUI getView() {
		return view;
	}

	public void setSocket(Socket socket) {
		this.socket = socket;
	}

	public void setServerSocket(ServerSocket serverSocket) {
		this.serverSocket = serverSocket;
	}

	public void setOOS(ObjectOutputStream oos) {
		this.oos = oos;
	}

	public void setOIS(ObjectInputStream ois) {
		this.ois = ois;
	}

	public void sendMovement(MoveDetails movement){
		try{
			oos.writeObject(movement);
			oos.flush();
		}catch(IOException e){
			getView().setErrorMsg("Connection lost... Game aborted");
			pause();
		}
	}
		
	public MoveDetails receiveMovement() throws ClassNotFoundException, IOException{
		MoveDetails movement = new MoveDetails(0, 0, 'O');

			movement = (MoveDetails) ois.readObject();
		return movement;
	}

	public void undoMove() {
		MoveDetails movement = board.getBeforeLastMove();
		if (movement == null) {
			view.setErrorMsg("Not enough moves on the board to 'Undo'");
		} else {
			board.undo();
			view.setButtonText('\u0000', movement.getX(), movement.getY());
			movement = board.getLastMove();
			view.setButtonText('\u0000', movement.getX(), movement.getY());
		}
	}

	public void changeTurn() {
		getView().setIsOrdersTurn(!getView().isOrdersTurn());
		if (getView().isOrdersTurn()) {
			if (order.startTurn())
				;
			else
				changeTurn();
		} else {

			if (chaos.startTurn())
				;
			else
				changeTurn();
		}
	}

	public void play(int x, int y, char s) {
		MoveDetails movement = new MoveDetails(x, y, s);

		if (getView().isOrdersTurn()) {
			order.makeMove(movement);
			changeTurn();

		} else {
			chaos.makeMove(movement);
			changeTurn();

		}

		if (board.checkWinOrder(movement)) {
			winner = order;
			endGame();
		} else if (board.checkWinChaos()) {
			winner = chaos;
			endGame();
			
		}

	}

	public void saveGame() {
		serializer.saveBoard(board);
		serializer.saveOrder(order);
		serializer.saveChaos(chaos);
	}

	public void Quit() {
		if(mode.equals("MultiPlayer")){
			try {
				if(isServer)
					serverSocket.close();
				else
					socket.close();
				oos.close();
				ois.close();
				} catch (IOException io) {}
			
		}
		System.exit(0);
	}


	public void endGame() {
		long endGame = System.nanoTime();

		int gameTime = (int) ((endGame - startGame) / (60000000000L));

		if (winner == order) {
			int beatJosh = (chaos.getName().equals("AI_Josh")) ? 1 : 0;
			order.newAchievementsEarned(1, 0, beatJosh, gameTime);
			

		} else {
			int beatJosh = (order.getName().equals("AI_Josh")) ? 1 : 0;
			chaos.newAchievementsEarned(0, 1, beatJosh, gameTime);

		}
		playersList.add(order);
		playersList.add(chaos);
		serializer.savePlayerProfiles(playersList);
		
		new WinMessage(winner);

	}

}
