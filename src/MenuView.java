import java.awt.*;
import java.awt.event.*;

import javax.swing.*;

public class MenuView { // implements ActionListener {
	private JFrame window;
	private MenuController menuController;

	// do we really need all these to be local?
	private JTextField t;
	private JTextArea text;
	private JPanel displayAchievements;
	private boolean multi = false;

	// assuming this is for networking?


	/*
	 * Because I have put 3 GUIs into one (GUI for displaying Achievements: Main
	 * Menu->Player Profiles, GUI for Single Player choosing: Main Menu->Start
	 * Single Player, GUI for MultiPlayer choosing: Main Menu->Start
	 * MultiPlayer), I have decided to include a string as an argument. String
	 * should be either "noPlayer", "singlePlayer" or "multiPlayer". Other
	 * strings will not be accepted.
	 */

	public MenuView(MenuController menuController) {

		this.menuController = menuController;
		displayMainMenu();
	}
	
	public void setMulti(boolean multi){
		this.multi = multi;
	}
	
	public boolean getMulti(){
		return multi;
	}

	public void displayMainMenu() {
		/* check for when the user is navigating BACK to the main menu */
		if (window != null)
			window.dispose();

		window = new JFrame("Order and Chaos");
		Container contentPane = window.getContentPane();
		ImagePanel background = new ImagePanel(
				new ImageIcon("tic_tac_toe.png").getImage());

		JLabel welcome = new JLabel("Welcome to THE GAME...");
		welcome.setAlignmentX(Component.CENTER_ALIGNMENT);

		contentPane.add(background, BorderLayout.CENTER);

		background.setLayout(new BoxLayout(background, BoxLayout.Y_AXIS));
		background.add(welcome);
		background.add(Box.createRigidArea(new Dimension(0, 15)));

		/* Creating the 'Play on the same computer button */
		JButton localGame = new JButton("Play on the same computer");
		localGame.setAlignmentX(Component.CENTER_ALIGNMENT);
		localGame.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				menuController.startLocalGame();
			}
		});
		background.add(localGame);
		background.add(Box.createRigidArea(new Dimension(0, 15)));

		/* Creating the 'Play over network' button */
		JButton networkGame = new JButton("Play over the network");
		networkGame.setAlignmentX(Component.CENTER_ALIGNMENT);
		networkGame.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				menuController.startMultiGame();
			}
		});
		background.add(networkGame);
		background.add(Box.createRigidArea(new Dimension(0, 15)));

		/* Creating the 'Resume Game' button */
		JButton resumeGame = new JButton("Resume Game");
		resumeGame.setAlignmentX(Component.CENTER_ALIGNMENT);
		resumeGame.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				menuController.resumeGame();
			}
		});
		background.add(resumeGame);
		background.add(Box.createRigidArea(new Dimension(0, 15)));

		/* Creating the 'Player Profiles' button */
		JButton playerProfiles = new JButton("Player profiles");
		playerProfiles.setAlignmentX(Component.CENTER_ALIGNMENT);
		playerProfiles.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				menuController.viewProfiles();
			}
		});
		background.add(playerProfiles);
		background.add(Box.createRigidArea(new Dimension(0, 15)));

		/* Creating the 'Quit' button */
		JButton quit = new JButton("Quit");
		quit.setAlignmentX(Component.CENTER_ALIGNMENT);
		quit.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				menuController.quit();
			}
		});
		background.add(quit);
		background.add(Box.createRigidArea(new Dimension(0, 15)));

		window.setSize(300, 300);
		window.setLocationRelativeTo(null);
		window.setVisible(true);
	}

	class ImagePanel extends JPanel {

		private Image img;
		private static final long serialVersionUID = 2;

		public ImagePanel(Image img) {
			this.img = img;
			Dimension size = new Dimension(img.getWidth(null),
					img.getHeight(null));
			setSize(size);
		}

		public void paintComponent(Graphics g) {
			g.drawImage(img, 0, 0, getWidth(), getHeight(), this);
		}
	}

	public void displayPlayerList() {
		window.dispose();
		window = new JFrame("Choose player profiles");
		JPanel main = new JPanel(new BorderLayout());

		window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		window.add(main);
		main.add(createPlayerList(1), BorderLayout.WEST);

		main.add(createNewPlayer(), BorderLayout.EAST);

		main.add(createMenuButton(), BorderLayout.SOUTH);
		window.pack();
		window.setLocationRelativeTo(null);
		window.setVisible(true);
	}

	public JPanel createNewPlayer() {
		JPanel createPlayer = new JPanel();

		JButton b = new JButton("Create new player");
		createPlayer.add(b);
		t = new JTextField("Enter name here", 20);
		createPlayer.add(t);

		b.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {

				menuController.addNewPlayer(t.getText());

			}
		});

		return createPlayer;
	}

	public JButton createMenuButton() {
		JButton menu = new JButton("Go to Menu");
		menu.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				menuController.goToMenu();
			}
		});

		return menu;
	}

	public PlayerButton createPlayerButton(Player p, int mode) {
		PlayerButton b = new PlayerButton(p);
		b.setPreferredSize(new Dimension(80, 80));
		if (mode == 1)
			addAL1forButtons(b);
		else if (mode ==2)
			addAL2forButtons(b);
			

		return b;
	}

	public JPanel createPlayerList(int mode) {
		JPanel playersPanel = new JPanel();
		playersPanel.setPreferredSize(new Dimension(300, 300));

		Box box = Box.createVerticalBox();

		playersPanel.setLayout(new BoxLayout(playersPanel, BoxLayout.Y_AXIS));
		box.add(new JLabel("             First choose a player profile "));
		box.add(new JLabel("             for 'Order' & then for Chaos"));
		for (Player p : menuController.getPlayersList()) {
			
			box.add(createPlayerButton(p, mode));
			box.add(Box.createVerticalStrut(20));

		}
		playersPanel.add(box);
		return playersPanel;
	}

	public void displayViewProfiles() {

		window.dispose();
		window = new JFrame("View player profiles");
		JPanel main = new JPanel(new BorderLayout());

		window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		window.add(main);
		main.add(createPlayerList(2), BorderLayout.WEST);
		main.add(createTableOfAchievements(), BorderLayout.EAST);
		main.add(createMenuButton(), BorderLayout.SOUTH);
		window.pack();
		window.setLocationRelativeTo(null);
		window.setVisible(true);

	}

// could create a 'private class' of an action listener maybe? 
	public void addAL1forButtons(final PlayerButton player) {

		player.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				if(multi)
					menuController.sendPlayer(player.getPlayer());
					
				menuController.setChoice(player.getPlayer());
			}
		});

	}

	public void addAL2forButtons(final PlayerButton player) {
		player.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				text.setText(player.getPlayer().printAchievements());
			}
		});
	}

	public JPanel createTableOfAchievements() {
		displayAchievements = new JPanel();
		text = new JTextArea(12, 30);
		displayAchievements.add(text);
		return displayAchievements;
	}


	private class PlayerButton extends JButton {

		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;
		private Player player;

		public PlayerButton(Player player) {
			this.player = player;
			this.setText(player.getName());
		}

		public Player getPlayer() {
			return player;
		}

	}

}
