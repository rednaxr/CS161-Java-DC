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
	Node startNode = null;
	Node endNode = null;

	
	//Graphical Components
	JFrame window = new JFrame("Graph Creator");
	GraphPanel graph = new GraphPanel();
	Container controlC = new Container();
	JLabel labelLbl = new JLabel("Component Label:  ");
	JTextField labelTF = new JTextField();
	JButton nodeBtn = new JButton("Node");
	JButton linkBtn = new JButton("Link");
	JButton clearBtn = new JButton("Clear");
	JButton deleteBtn = new JButton("Delete");
	
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
		window.add(graph, BorderLayout.CENTER);
		graph.addMouseListener(this);
		graph.setBackground(Color.BLACK);
		window.add(controlC, BorderLayout.SOUTH);
		controlC.setLayout(new GridLayout(1,6));
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
		
	}
	
	public static void main(String[] args) {
		new GCMain();
	}
	
	//on pushing a BUTTON
	public void actionPerformed(ActionEvent ae) {
		if(ae.getSource().equals(nodeBtn)) {
			nodeBtn.setBackground(Color.GREEN);
			linkBtn.setBackground(Color.LIGHT_GRAY);
			deleteBtn.setBackground(Color.LIGHT_GRAY);
			mouseTool = ADD_NODE;
		}
		else if(ae.getSource().equals(linkBtn)) {
			nodeBtn.setBackground(Color.LIGHT_GRAY);
			linkBtn.setBackground(Color.GREEN);
			deleteBtn.setBackground(Color.LIGHT_GRAY);
			mouseTool = START_LINK;
		}
		else if(ae.getSource().equals(deleteBtn)) {
			nodeBtn.setBackground(Color.LIGHT_GRAY);
			linkBtn.setBackground(Color.LIGHT_GRAY);
			deleteBtn.setBackground(Color.RED);
			mouseTool = DELETE;
		}
		else if(ae.getSource().equals(clearBtn)){
			graph.clear();
			window.repaint();
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
			endNode = graph.getNode(me.getX(), me.getY());
			if(endNode != null) {
				graph.addLink(startNode, endNode, labelTF.getText());
				startNode.setColor(Color.BLUE);
				startNode = null;
				mouseTool = START_LINK;
			}
		}
		else {
			if(startNode != null) {
				startNode.setColor(Color.BLUE);					//reset & forget a saved start node if not completing a link
			}
			startNode = null;								
			if(mouseTool == ADD_NODE) {							//if mouse tool is Add Node, add a node at x and y of mouse
				graph.addNode(me.getX(), me.getY(), labelTF.getText());
			}
			else if(mouseTool == START_LINK) {					//if mouse tool is start link, save node clicked on as start node
				startNode = graph.getNode(me.getX(), me.getY());
				if(startNode != null) {						//...and if start node is extant, switch mouse tool to End Link and mark node
					startNode.setColor(Color.GREEN);
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
