import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.util.ArrayList;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.SwingWorker;

public class MenuController {
	private MenuView menuView;
	private ArrayList<Player> playersList;
	private Player order;
	private Player chaos;
	@SuppressWarnings("unused")
	private Player AI;
	private Serializer serializer;
	private String mode = "Player vs Player";
	private MakeConnection connect;

	// this is for networking
	private Socket socket;

	private ObjectOutputStream oos;
	private ObjectInputStream ois;

	/*********/

	public void createServer(JFrame frame) throws IOException {

		SwingWorker<Void, Void> worker = new SwingWorker<Void, Void>() {

			@Override
			protected Void doInBackground() throws Exception {

				final int portNumber = 1030;
				final ServerSocket serverSocket = new ServerSocket(portNumber);
				try {
					connect.setState();

					socket = serverSocket.accept();

					menuView.setMulti(true);
					menuView.displayPlayerList();
				} catch (SocketException se) {
					quit();
				}
				oos = new ObjectOutputStream(socket.getOutputStream());
				ois = new ObjectInputStream(socket.getInputStream());

				chaos = receivePlayer();

				/*****/
				Board board = new Board();
				Controller controller = new Controller(board, order, chaos,
						playersList, "MultiPlayer");

				controller.setServerSocket(serverSocket);
				controller.setOOS(oos);
				controller.setOIS(ois);
				controller.setServer();

				/*****/
				return null;
			}
		};
		worker.execute();

	}

	public void sendPlayer(Player player) {
		try {
			oos.writeObject(player);
			oos.flush();
		} catch (IOException e) {

		}
	}

	public Player receivePlayer() {
		Player player = new Player("");
		;
		try {
			player = (Player) ois.readObject();
		} catch (ClassNotFoundException e) {

		} catch (IOException e) {

		}
		return player;
	}

	public void connToServer() throws IOException {

		final String host = JOptionPane.showInputDialog(null,
				"Enter an IP of server");

		if (host == null)
			return;

		final int portNumber = 1030;
		System.out.println("Creating socket to '" + host + "' on port "
				+ portNumber);
		socket = new Socket(connect.getIP(), portNumber);
		oos = new ObjectOutputStream(socket.getOutputStream());
		ois = new ObjectInputStream(socket.getInputStream());
		order = receivePlayer();

		menuView.setMulti(true);
		mode = "MultiPlayer";
		menuView.displayPlayerList();
	}

	public static void main(String[] args) {
		new MenuController();
	}

	public MenuController() {
		menuView = new MenuView(this);
		serializer = new Serializer();
		playersList = serializer.loadPlayers();
	}

	public ArrayList<Player> getPlayersList() {
		return playersList;
	}

	public void startLocalGame() {
		menuView.displayPlayerList();
	}


	public void loadPlayerProfilesMulti(Player playerReserved) {

		setChoice(playerReserved);
	}

	public void setChoice(Player p) {

		if (order == null) {

			if (p.getName().equals("AI_Josh")) {
				mode = "Player vs AI";
				p.setBehaviour(new AI_Josh_OrderBehaviour());
			}
			order = p;
			playersList.remove(p);

			if (!menuView.getMulti())
				menuView.displayPlayerList();
		}

		else if (chaos == null) {
			if (p.getName().equals("AI_Josh")) {
				mode = "Player vs AI";
				p.setBehaviour(new AI_Josh_ChaosBehaviour());
			}
			chaos = p;
			playersList.remove(p);
			
			Board board = new Board();

			Controller controller = new Controller(board, order, chaos,
					playersList, mode);

			if (menuView.getMulti()) {
				controller.setSocket(socket);
				controller.setOOS(oos);
				controller.setOIS(ois);
				controller.pause();
			}

		}
	}

	public void startMultiGame() {
		AI = playersList.get(0);
		playersList.remove(0);
		connect = new MakeConnection(this);
	}

	public void addNewPlayer(String newPlayerName) {
		playersList.add(new Player(newPlayerName));
		serializer.savePlayerProfiles(playersList);
		/* reload the list */
		menuView.displayPlayerList();
	}

	public void resumeGame() {
		order = serializer.loadOrder();
		chaos = serializer.loadChaos();
		Board board = serializer.loadBoard();

		if ((order != null) && (chaos != null) && (board != null)) {
			new Controller(board, order, chaos, playersList, mode);
		} else {
			startLocalGame();
			
		}

	}

	public void viewProfiles() {
		menuView.displayViewProfiles();

	}

	public void quit() {
		System.exit(0);
	}

	public void goToMenu() {
		if (order != null) {
			playersList.add(order);
			order = null;
		}
		menuView.displayMainMenu();
	}


}
