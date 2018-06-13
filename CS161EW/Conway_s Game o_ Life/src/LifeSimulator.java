//Conway's Game of Life Simulator     (see LifeDisplay for a necessary component)
//by: Alexander Dyall
//15 Dec 2017

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.FlowLayout;
import java.awt.GridLayout;
//import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;

public class LifeSimulator implements MouseListener, ActionListener, Runnable {

	//Global Variables
	int scale;													//Size of grid
	int waitTime = 500;											//time between steps when running simulation
	boolean[][] grid; //= new boolean[scale + 2][scale + 2];	//Array storing alive/dead for all cells
	boolean running = false;									//Should the simulation be running?

	
	
	//Graphical Components
	JFrame window = new JFrame("Conway's Game o' Life");
	Container btnZone = new Container();
	LifeDisplay simDisplay;
	JButton stepBtn = new JButton("STEP");
	JButton playBtn = new JButton("PLAY");
	JButton pauseBtn = new JButton("PAUSE");
	JButton clearBtn = new JButton("CLEAR");
	
	//Method to run Simulator for one Step
	public void step() {
		byte sum;
		boolean[][]newGrid = new boolean[scale + 2][scale + 2];		//Create a new grid and set it equal to the current grid
		for(int x = 0; x < scale + 2; x++) {
			for(int y = 0; y < scale + 2; y++) {
				newGrid[x][y] = grid[x][y];
			}
		}
		for(int x = 1; x < scale + 1; x++) {					//go through each cell
			for(int y = 1; y < scale + 1; y++) {
				sum = 0;
				for(int a = x - 1; a < x + 2; a++) {		//calculate sum of all alive cells neighboring current cell
					for(int b = y - 1; b < y + 2; b++) {
						if(grid[a][b] == true) {
							sum++;
						}
					}
				}
				if(grid[x][y] == true) {		//(remove the square in question from neighbor count)
					sum--;
				}
				if(sum < 2) {					//if 0 or 1 neighbors, dies of loneliness
					newGrid[x][y] = false;
				}
				else if(sum == 3) {				//if 3 neighbors, populate square
					newGrid[x][y] = true;
				}
				else if(sum > 3) {				//if 4 or more neighbors, dies of overcrowding
					newGrid[x][y] = false;
				}
			}
		}
		for(int x = 1; x < scale + 1; x++) {
			for(int y = 1; y < scale + 1; y++) {
				grid[x][y] = newGrid[x][y];
			}
		}
		window.repaint();
	}
	

	//Constructor
	public LifeSimulator(int inscale){
		scale = inscale;
		grid = new boolean[scale +2][scale+2];					//set grid size based on scale from LifeSetup
		simDisplay = new LifeDisplay(grid);
		
		//Window Settings
		window.setSize(Math.max(350, Math.min(55*scale, 950)), Math.max(350, Math.min(55*scale + 50, 950)));		//set size based on scale from LifeSetup
		window.setLocation(50, 50);
		window.setVisible(true);
		window.setDefaultCloseOperation(window.EXIT_ON_CLOSE);
		window.setLayout(new BorderLayout());
		window.add(simDisplay, BorderLayout.CENTER);
		simDisplay.addMouseListener(this);
		window.add(btnZone, BorderLayout.NORTH);
		
		//Button Settings
		btnZone.setLayout(new FlowLayout());
		btnZone.add(stepBtn);
		btnZone.add(playBtn);
		btnZone.add(pauseBtn);
		btnZone.add(clearBtn);
		stepBtn.addActionListener(this);
		playBtn.addActionListener(this);
		pauseBtn.addActionListener(this);
		clearBtn.addActionListener(this);
		
		//Start the grid out as all false
		for(int x = 0; x < scale + 2; x++) {
			for(int y = 0; y < scale + 2; y++) {
				grid[x][y] = false;
			}
		}
	}
	
	//MAIN
	public static void main(String[] args){
		new LifeSetup();
	}

	public void mouseClicked(MouseEvent interaction) {
	}

	public void mouseEntered(MouseEvent interaction) {
	}

	public void mouseExited(MouseEvent interaction) {
	}

	public void mousePressed(MouseEvent interaction) {
	}
	
	//On clicking the Panel - change alive/dead status of clicked cell
	public void mouseReleased(MouseEvent interaction) {
		int x = (int)Math.max(1, Math.min(scale, Math.round(interaction.getX()/(simDisplay.getWidth()/scale)+1)));
		int y = (int)Math.max(1, Math.min(scale, Math.round(interaction.getY()/(simDisplay.getHeight()/scale)+1)));
		grid[x][y] = !grid[x][y];
		window.repaint();
	}
	
	//On clicking a button:
	public void actionPerformed(ActionEvent interaction) {
		if(interaction.getSource().equals(stepBtn)) {			//run simulation for one step if "STEP" button is pressed
			step();
		}
		else if(interaction.getSource().equals(playBtn)) {		//run simulation continually if "PLAY" button is pressed
			if(running == false) {
				running = true;
				Thread runSim = new Thread(this);					
				runSim.start();
			}
			else {													//(if already playing, halve the time between steps)
				waitTime /= 2;
			}
			System.out.println(running);
			System.out.println(waitTime);
		}
		else if(interaction.getSource().equals(pauseBtn)) {		//pause simulation if "PAUSE" button is pressed
			running = false;
			waitTime = 500;
		}
		else if(interaction.getSource().equals(clearBtn)) {		//If "CLEAR" button is pressed, clear all cells
			for(int x = 0; x < scale + 2; x++) {
				for(int y = 0; y < scale + 2; y++) {
					grid[x][y] = false;
				}
			}
			window.repaint();
		}
	}
	
	public void run() {					//When running the simulation, execute at one step per specified time interval
		while(running == true) {
			step();
			try {
				Thread.sleep(waitTime);
			}
			catch(Exception ouch){	
			}
		}
	}
}
