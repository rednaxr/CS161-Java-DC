//Main (GUI & Overall Control) Class of Graph Creator
//by: Alexander Dyall
//5 June 2018

package com.graphcreator.util;

import javax.swing.JFrame;
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
	final int NONE = 0;
	final int ADD_NODE = 1;
	final int START_EDGE = 2;
	final int END_EDGE = 3;
	
	//Graphical Components
	JFrame window = new JFrame("Graph Creator");
	GraphPanel graph = new GraphPanel();
	Container controlC = new Container();
	JButton nodeBtn = new JButton("Node");
	JButton edgeBtn = new JButton("Edge");
	JTextField labelTF = new JTextField();
	
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
		window.add(controlC, BorderLayout.SOUTH);
		controlC.setLayout(new GridLayout(1,3));
		controlC.add(nodeBtn);
		nodeBtn.addActionListener(this);
		nodeBtn.setBackground(Color.LIGHT_GRAY);
		controlC.add(edgeBtn);
		edgeBtn.addActionListener(this);
		edgeBtn.setBackground(Color.LIGHT_GRAY);
		controlC.add(labelTF);
		
	}
	
	public static void main(String[] args) {
		new GCMain();
	}
	
	public void actionPerformed(ActionEvent ae) {
		if(ae.getSource().equals(nodeBtn)) {
			nodeBtn.setBackground(Color.GREEN);
			edgeBtn.setBackground(Color.LIGHT_GRAY);
			mouseTool = ADD_NODE;
		}
		if(ae.getSource().equals(edgeBtn)) {
			edgeBtn.setBackground(Color.GREEN);
			nodeBtn.setBackground(Color.LIGHT_GRAY);
			mouseTool = START_EDGE;
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

	public void mouseReleased(MouseEvent me) {		
		System.out.println();
	}

}
