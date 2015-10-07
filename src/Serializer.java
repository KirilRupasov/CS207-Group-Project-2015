import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

public class Serializer {

	public Object load(String filename) {

		Object o = null;
		try {
			FileInputStream fileIn = new FileInputStream(filename);
			ObjectInputStream in = new ObjectInputStream(fileIn);
			o = in.readObject();
			in.close();
			fileIn.close();
			return o;
		} catch (IOException i) {
			System.out.println(filename);
			return o;
		} catch (ClassNotFoundException c) {
			System.out.println("not found");
			c.printStackTrace();
			return o;
		}
	}

	public Player loadChaos() {
		return (Player) load("ChaosPlayer.txt");
	}

	public Player loadOrder() {
		return (Player) load("OrderPlayer.txt");
	}

	public Board loadBoard() {
		return (Board) load("Board.txt");
	}

	public ArrayList<Player> loadPlayers() {
		// don't know how to check cast?
		@SuppressWarnings("unchecked")
		ArrayList<Player> playersList = (ArrayList<Player>) load("Players.txt");
		
		if (playersList != null)
			return playersList;
		else {
			System.out.println("asdadasdasdasdasd!!");
			Player josh = new Player("AI_Josh");
			ArrayList<Player> sample =  new ArrayList<Player>();
			sample.add(josh);
			return sample;
		}
		
	}

	public void save(Object o, String filename) {
		try {
			FileOutputStream fileOut = new FileOutputStream(filename);
			ObjectOutputStream out = new ObjectOutputStream(fileOut);
			out.writeObject(o);
			out.close();
			fileOut.close();
			System.out.printf("Serialized data is saved ");
		} catch (IOException i) {
			i.printStackTrace();
		}
	}

	public void savePlayerProfiles(ArrayList<Player> playersList) {
		save(playersList, "Players.txt");
	}

	public void saveBoard(Board board) {
		save(board, "Board.txt");
	}

	public void saveOrder(Player order) {
		save(order, "OrderPlayer.txt");
	}

	public void saveChaos(Player chaos) {
		save(chaos, "ChaosPlayer.txt");
	}

}
