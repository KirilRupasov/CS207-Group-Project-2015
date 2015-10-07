/****MAKECONNECTION.JAVA
 * 
 * The actual GUI for connecting/creating server.
 */
import javax.swing.*;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;


public class MakeConnection {
	private JFrame f;
	private MenuController controllerMenu;
	
	private ObjectOutputStream oos;	
	private ObjectInputStream ois;
	private JTextField state;
	
	
	public MakeConnection(MenuController controllerMenu){
		this.controllerMenu = controllerMenu;
		init();
	}
	
	public void setState(){
		state.setText("Waiting for client... Current IP address : ");
	}
	
	
	public JPanel createOptions(){
		state = new JTextField();
		JPanel Options = new JPanel();
		Options.setPreferredSize(new Dimension(200, 200));
		Box box = Box.createVerticalBox();
		Options.setLayout(new BoxLayout(Options, BoxLayout.Y_AXIS));		
		JButton serverConnect = new JButton("Create Server");
		serverConnect.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
            	try{
           		
            		controllerMenu.createServer(f);
            		
            	} catch (IOException io){
            		System.out.println("Failed to create server, quitting...");
            		controllerMenu.quit();
            	}
            }
        });

		
		box.add(serverConnect);
		box.add(Box.createVerticalStrut(20));
		
		
		JButton clientConnect = new JButton("Connect to Server");
		clientConnect.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
            	try{
            		controllerMenu.connToServer();
            	} catch (IOException io){
            		io.printStackTrace();
            		System.out.println("Failed to connect to server, quitting...");
            		controllerMenu.quit();
            	}
            }
        });
		box.add(clientConnect);
		box.add(Box.createVerticalStrut(20));
		box.add(state);		
		box.add(Box.createVerticalStrut(20));		
		
		Options.add(box);
		return Options;
	}
	
	public String getIP(){
		return state.getText();
	}
	
	

	public void sendMovement(MoveDetails movement){
		try{
			oos.writeObject(movement);
			oos.flush();
		}catch(IOException e){

		}
	}
	
	
	public MoveDetails receiveMovement(){
		MoveDetails movement = new MoveDetails(0, 0, 'O');;
		try{
			movement = (MoveDetails) ois.readObject();
		}catch(ClassNotFoundException e){
			
		}catch(IOException e){
			
		}
		return movement;
	}
	
	
	public void init(){
		f = new JFrame("Initializing Network");
		JPanel main = new JPanel();
		main.add(createOptions());
		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		f.add(main);
		f.pack();
		f.setLocationRelativeTo(null);
		f.setVisible(true);
	}

}
