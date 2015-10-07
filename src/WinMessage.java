import javax.swing.*;


public class WinMessage {
	
	JFrame f;
	
	private JTextArea text;
	private JPanel displayAchievements;
	private Player winner;

	
	public WinMessage(Player winner){
		this.winner = winner;
		init();
	}
	
	public JPanel createTableOfAchievements(){
		displayAchievements = new JPanel();
		text = new JTextArea(30, 40);
		displayAchievements.add(text);
		text.setText(winner.getAchievement().printNewAchievements());
		return displayAchievements;
	}
	
	public void init() {

		f = new JFrame("Congratulations "+winner.getName()+" ,you won!");
		JPanel main = new JPanel();
		f.add(main);
		main.add(createTableOfAchievements());
		f.pack();
		f.setLocationRelativeTo(null);
		f.setVisible(true);
	}

}
