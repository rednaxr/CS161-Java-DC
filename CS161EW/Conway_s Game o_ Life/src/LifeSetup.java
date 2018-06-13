//Class for setup window for Conway's Game o' Life Simulator
//By: Alexander Dyall
//15 Dec 2017

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;

public class LifeSetup implements ActionListener {
	
	//Global Variables
	int scale = 0;
	
	//Graphical Components - Setup Window
	JFrame setupWindow = new JFrame("Conway's Game o' Life - Setup");
	JButton startBtn = new JButton("START");
	JTextField inscaleField = new JTextField();
	JLabel setupInstructions = new JLabel("Please Enter Grid Size");
	
	//Constructor
	public LifeSetup() {
		setupWindow.setSize(150, 150);
		setupWindow.setLocation(50, 50);
		setupWindow.setVisible(true);
		setupWindow.setDefaultCloseOperation(setupWindow.EXIT_ON_CLOSE);
		setupWindow.setLayout(new GridLayout(3, 1));
		setupWindow.add(setupInstructions);
		setupWindow.add(inscaleField);
		setupWindow.add(startBtn);
		startBtn.addActionListener(this);
	}
	
	//If START is pressed
	public void actionPerformed(ActionEvent interaction) {
		if(interaction.getSource().equals(startBtn)) {
			try {
				scale = (int)Long.parseLong(inscaleField.getText());	//Start simulation with entered scale
				if(scale > 0 && scale < 101) {								//...if within the valid range
					setupWindow.setVisible(false);
					new LifeSimulator(scale);
				}
			}catch(Exception ouch) {
			}
		}
	}
}
