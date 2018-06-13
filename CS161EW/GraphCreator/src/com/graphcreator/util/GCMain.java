//Main (GUI & Overall Control) Class of Graph Creator
//by: Alexander Dyall
//5 June 2018

package com.graphcreator.util;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JButton;
import javax.swing.JTextField;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class GCMain implements ActionListener, MouseListener {
	
	//Global Variables
	int mouseTool = 0;								//Stores what a mouse click will do
	final int ADD_NODE = 0;
	final int START_LINK = 1;
	final int END_LINK = 2;
	final int DELETE = -1;
	
	//Global Objects
	Node node1 = null;
	Node node2 = null;

	
	//Graphical Components
	JFrame window = new JFrame("Graph Creator");
	GraphPanel graph = new GraphPanel();
	Container controlC = new Container();
	JLabel labelLbl = new JLabel("Component Label:  ");
	JLabel node1Lbl = new JLabel("Node 1:  ");
	JLabel node2Lbl = new JLabel("Node 2:  ");
	JLabel connectLbl = new JLabel(" - ");
	JTextField labelTF = new JTextField();
	JTextField node1TF = new JTextField();
	JTextField node2TF = new JTextField();
	JButton nodeBtn = new JButton("Node");
	JButton linkBtn = new JButton("Link");
	JButton clearBtn = new JButton("Clear");
	JButton deleteBtn = new JButton("Delete");
	JButton checkConnectBtn = new JButton("Check Connection");
	
	//Constructor
	public GCMain() {
		
		//Base sizes of Graphical Components on size of screen:
		double screenWidth = Toolkit.getDefaultToolkit().getScreenSize().getWidth();
		double screenHeight = Toolkit.getDefaultToolkit().getScreenSize().getHeight();
		
		//Set up window:
		window.setSize((int)(Math.round(screenWidth*2/3)), (int)(Math.round(screenHeight*2/3)));
		window.setLocation((int)(Math.round(screenWidth*1/6)), (int)(Math.round(screenHeight*1/6)));
		window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		window.setVisible(true);
		window.setLayout(new BorderLayout());
		
		//Add in and set up Graphical Components:
		
		//Editing Row
		window.add(graph, BorderLayout.CENTER);
		graph.addMouseListener(this);
		graph.setBackground(Color.BLACK);
		window.add(controlC, BorderLayout.SOUTH);
		controlC.setLayout(new GridLayout(2,6));
		controlC.add(labelLbl);
		labelLbl.setHorizontalAlignment(JLabel.RIGHT);
		controlC.add(labelTF);
		controlC.add(nodeBtn);
		nodeBtn.addActionListener(this);
		nodeBtn.setBackground(Color.GREEN);
		controlC.add(linkBtn);
		linkBtn.addActionListener(this);
		linkBtn.setBackground(Color.LIGHT_GRAY);
		controlC.add(deleteBtn);
		deleteBtn.addActionListener(this);
		deleteBtn.setBackground(Color.LIGHT_GRAY);
		controlC.add(clearBtn);
		clearBtn.addActionListener(this);
		clearBtn.setBackground(Color.CYAN);
		
		//Check Connection Row
		controlC.add(node1Lbl);
		node1Lbl.setHorizontalAlignment(JLabel.RIGHT);
		controlC.add(node1TF);
		controlC.add(node2Lbl);
		node2Lbl.setHorizontalAlignment(JLabel.RIGHT);
		controlC.add(node2TF);
		controlC.add(checkConnectBtn);
		checkConnectBtn.addActionListener(this);
		controlC.add(connectLbl);
		connectLbl.setHorizontalAlignment(JLabel.CENTER);
	}
	
	public static void main(String[] args) {
		new GCMain();
	}
	
	//on pushing a BUTTON
	public void actionPerformed(ActionEvent ae) {
		if(ae.getSource().equals(nodeBtn)) {				//If node button is pushed:
			nodeBtn.setBackground(Color.GREEN);					//Select node button and deselect others
			linkBtn.setBackground(Color.LIGHT_GRAY);
			deleteBtn.setBackground(Color.LIGHT_GRAY);
			mouseTool = ADD_NODE;								//set mouse tool to adding nodes
		}
		else if(ae.getSource().equals(linkBtn)) {			//If link button is pushed
			nodeBtn.setBackground(Color.LIGHT_GRAY);			//Select link button and deselect others
			linkBtn.setBackground(Color.GREEN);
			deleteBtn.setBackground(Color.LIGHT_GRAY);
			mouseTool = START_LINK;								//set mouse tool to starting a link
		}
		else if(ae.getSource().equals(deleteBtn)) {			//If delete button is pushed
			nodeBtn.setBackground(Color.LIGHT_GRAY);			//select delete button and deselect others
			linkBtn.setBackground(Color.LIGHT_GRAY);
			deleteBtn.setBackground(Color.RED);
			mouseTool = DELETE;									//set mouse tool to delete
		}
		else if(ae.getSource().equals(clearBtn)){			//If the clear buttton is pushed
			graph.clear();										//clear the graph
			window.repaint();
		}
		else if(ae.getSource().equals(checkConnectBtn)) {
			boolean connected = false;
			node1 = graph.getNode(node1Lbl.getText());
			node2 = graph.getNode(node2Lbl.getText());
			if(node1 != null && node2 != null) {
				connected = graph.getConnectivity(node1, node2);
			}
		}
	}

	public void mouseClicked(MouseEvent me) {
	}

	public void mouseEntered(MouseEvent me) {
	}

	public void mouseExited(MouseEvent me) {		
	}

	public void mousePressed(MouseEvent me) {		
	}
	
	//On releasing the MOUSE
	public void mouseReleased(MouseEvent me) {		
		if(mouseTool == END_LINK) {							//if mouse tool is end link, create link between saved start node and nod clicked on, if extant
			node2 = graph.getNode(me.getX(), me.getY());
			if(node2 != null) {
				graph.addLink(node1, node2, labelTF.getText());			//add link
				node1.setColor(Color.BLUE);								//deselect start node
				node1 = null;
				mouseTool = START_LINK;									//return to start link mode
			}
		}
		else {
			if(node1 != null) {
				node1.setColor(Color.BLUE);					//reset & forget a saved start node if not completing a link
			}
			node1 = null;								
			if(mouseTool == ADD_NODE) {							//if mouse tool is Add Node, add a node at x and y of mouse
				graph.addNode(me.getX(), me.getY(), labelTF.getText());
			}
			else if(mouseTool == START_LINK) {					//if mouse tool is start link, save node clicked on as start node
				node1 = graph.getNode(me.getX(), me.getY());
				if(node1 != null) {							//...and if start node is extant, switch mouse tool to End Link and mark node
					node1.setColor(Color.GREEN);
					mouseTool = END_LINK;
				}
			}
			else if(mouseTool == DELETE) {						//If mouse tool is Delete, remove node at x and y of mouse
				graph.removeNode(graph.getNode(me.getX(), me.getY()));
			}
		}
		window.repaint();
	}

}
