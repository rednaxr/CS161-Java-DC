package com.graphcreator.util;

import java.awt.Color;
import java.awt.Graphics;

public class Link {
	
	private Node start;
	private Node end;
	private Color color;
	private String label;
	
	public Link(Node Start, Node End, String Label) {
		start = Start;
		end = End;
		label = Label;
		color = Color.ORANGE;
	}
	
	//draws link
	public void paint(Graphics gr) {
		gr.setColor(color);													//draw orange line from node to node
		gr.drawLine(start.getX(), start.getY(), end.getX(), end.getY());
		gr.setColor(color.WHITE);											//draw label in middle of line
		gr.drawString(label, (start.getX() + end.getX())/2, (start.getY()+end.getY())/2);
	}
	
}
