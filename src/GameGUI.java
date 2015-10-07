/*****GAMEGUI.JAVA
 * This is the GUI for the actual game running.
 * 
 */

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Observable;
import java.util.Observer;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;

public class GameGUI implements Observer, ActionListener, Serializable {

	private static final long serialVersionUID = 4L;
	private static final int N = 6;
	private JRadioButton Obutton;
	private JRadioButton Xbutton;
	private final List<JButton> list = new ArrayList<JButton>();
	private JLabel errorMsg;
	private JLabel playerMsg;
	private Board board;
	private Controller controller;
	private JFrame f;
	private boolean isOrdersTurn;
	
	/*The _mode_ string controls the functionality of provided control panel.
	 * It can be "MultiPlayer", "Player vs AI", "Player vs Player" */
	

	public GameGUI(Board board, Controller controller) {
		this.board = board;
		this.controller = controller;
		board.addObserver(this);
	}

	public boolean isOrdersTurn(){
		return isOrdersTurn;
	}
	
	public void setIsOrdersTurn(boolean newValue){
		isOrdersTurn = newValue;
	}

	private JButton getGridButton(int r, int c) {
		int index = r * N + c;
		return list.get(index);
	}

	private JButton createGridButton(final int row, final int col,
			final char symbol) {
		final JButton b = new JButton();
		b.setText(symbol + "");
		b.setPreferredSize(new Dimension(80, 80));
		b.addActionListener(this);
		return b;
	}

	private JPanel createGridPanel() {
		JPanel p = new JPanel(new GridLayout(N, N));
		for (int i = 0; i < N * N; i++) {
			int row = i / N;
			int col = i % N;
			char[][] grid = board.getBoard();
			JButton gb = createGridButton(row, col, grid[row][col]);
			list.add(gb);
			p.add(gb);
		}
		return p;
	}

	private JPanel createControls() {
		JPanel p = new JPanel();
		p.setLayout(new BoxLayout(p, BoxLayout.Y_AXIS));
		p.setBorder(BorderFactory.createEmptyBorder(0, 20, 20, 20));
		p.add(Box.createVerticalGlue());
		Obutton = new JRadioButton("O");
		Xbutton = new JRadioButton("X");
		ButtonGroup group = new ButtonGroup();
		group.add(Obutton);
		group.add(Xbutton);

		p.add(Obutton);
		p.add(Xbutton);

		p.add(Box.createVerticalGlue());

		JButton undo = new JButton("Undo");
		if(controller.getMode().equals("Player vs AI")){
				undo.addActionListener(new ActionListener() {

					@Override
					public void actionPerformed(ActionEvent e) {
						controller.undoMove();
					}
				});
		} else {
			undo.addActionListener(new ActionListener() {

				@Override
				public void actionPerformed(ActionEvent e) {
					setErrorMsg("You are in "+controller.getMode()+ " mode! Not a possible action");
				}
			});
		}
		

		p.add(undo);
		p.add(Box.createVerticalGlue());
		
		JButton save = new JButton("Save Game");
		
		if(!controller.getMode().equals("MultiPlayer"))
		{
		
		save.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				controller.saveGame();
			}
		});
		} else {
			undo.addActionListener(new ActionListener() {

				@Override
				public void actionPerformed(ActionEvent e) {
					setErrorMsg("You are in "+controller.getMode()+ " mode! Not a possible action");
				}
			});	
		}

		//JButton menu = controller.MenuButton(f);

		//p.add(menu);
		p.add(Box.createVerticalGlue());

		p.add(save);
		p.add(Box.createVerticalGlue());

		JButton quit = new JButton("Quit Game (without saving)");
		quit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				controller.Quit();
			}
		});

		p.add(quit);
		p.add(Box.createVerticalGlue());
		return p;

	}

	public void setErrorMsg(String s) {
		errorMsg.setText(s);
		errorMsg.setForeground(Color.red);
	}

	public void setPlayerMsg(String s) {
		playerMsg.setText(s);
	}

	public void display() {
		f = new JFrame("Order VS Chaos");
		JPanel main = new JPanel(new BorderLayout());
		main.setBorder(BorderFactory.createEmptyBorder(30, 30, 30, 30));
		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		f.add(main);
		main.add(createGridPanel(), BorderLayout.CENTER);
		main.add(createControls(), BorderLayout.LINE_END);
		playerMsg = new JLabel("");
		main.add(playerMsg, BorderLayout.NORTH);
		errorMsg = new JLabel("");
		main.add(errorMsg, BorderLayout.SOUTH);
		f.pack();
		f.setLocationRelativeTo(null);
		f.setVisible(true);


	}

	public void setButtonText(char s, int row, int col) {
		JButton b = getGridButton(row, col);
		b.setText(s + "");
	}

	@Override
	public void update(Observable arg0, Object last) {
		MoveDetails lastMove = board.getLastMove();
		if (lastMove != null) {
			int row = lastMove.getX();
			int col = lastMove.getY();
			char s = lastMove.getState();
			setButtonText(s, row, col);
		}
	}

	@Override
	public void actionPerformed(ActionEvent event) {
// to add the instanceOf validation? 
		

		
		if(!controller.isWaiting())
		{
		JButton gb = (JButton) event.getSource();
		
		char s = '\u0000';
		if (Xbutton.isSelected()) {
			s = 'X';

		} else if (Obutton.isSelected()) {
			s = 'O';
		}
		
		int i = list.indexOf(gb);
		
		if (s == '\u0000') {
			setErrorMsg("You must select either 'O' or 'X' before clicking the cell");
			return;
		} else if (gb.getText().equals("X") || gb.getText().equals("O") ) {
			setErrorMsg("That cell is already occupied... choose another one :p");
			return;
		} else {
		
		controller.play(i / N, i%N , s );		
		}
		
		if (controller.getMode().equals("MultiPlayer")) {
			
			controller.sendMovement(new MoveDetails(i / N, i%N , s ));
			controller.pause();
		}
		
		}


		
	}
	
	/*if (multiPlayer) {
	controller.sendMovement(lastMove);
	pause();
}

if (((board.getMoveCount() % 2) == 0) && (!waiting)) {
	if (controller.getOrder().getName().equals("AI_Josh")) {
		AIMove = AIMoves.playOrder(board, lastMove);
		controller.checkWin(AIMove);
	} else if (controller.getWinner() == null) {
		setPlayerMsg("Order - "
				+ controller.getOrder().getName()
				+ " ,your turn .Please choose 'X' or 'O' and click on the square");
	}
} else if ((!waiting)) {

	if (controller.getChaos().getName().equals("AI_Josh")) {
		AIMove = AIMoves.playChaos(board, lastMove);
		controller.checkWin(AIMove);
	} else if (controller.getWinner() == null) {
		setPlayerMsg("Chaos - "
				+ controller.getChaos().getName()
				+ " ,your turn .Please choose 'X' or 'O' and click on the square");
	}
}*/
//}

}
