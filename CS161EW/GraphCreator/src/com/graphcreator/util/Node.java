package com.graphcreator.util;

import java.awt.Color;
import java.awt.Graphics;
import java.util.ArrayList;

public class Node {
	
	//Node Attributes:
	private int x;							//x position on graph
	private int y;							//y position on graph
	private Color color;					//color of node
	private String label;					//label of node
	private ArrayList<Link> linkList;		//list of links connected to node
	
	//Constructor
	public Node(int X, int Y, String L) {
		x = X;
		y = Y;
		label = L;
		linkList = new ArrayList<Link>();
		color = Color.BLUE;
	}
	
	//draws Node
	public void paint(Graphics gr) {
		gr.setColor(color);						//draw circle centered on x, y
		gr.fillOval(x - 10, y - 10, 20, 20);
		gr.setColor(Color.WHITE);
		gr.drawString(label, x - 2, y + 5);		//draw label near x, y
	}
	
	public int getX() {
		return x;
	}

	public void setX(int x) {
		this.x = x;
	}

	public int getY() {
		return y;
	}

	public void setY(int y) {
		this.y = y;
	}

	public String getLabel() {
		return label;
	}

	public void setLabel(String label) {
		this.label = label;
	}
	
	public Color getColor() {
		return color;
	}
	
	public void setColor(Color color) {
		this.color = color;
	}

	public ArrayList<Link> getLinkList() {
		return linkList;
	}
	
}
