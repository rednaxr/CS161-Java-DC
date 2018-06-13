//Drawing Class of Graph Creator
//by: Alexander Dyall
//5 June 2018

package com.graphcreator.util;

import java.awt.Color;
import java.awt.Graphics;
import java.util.ArrayList;
import java.util.regex.MatchResult;

import javax.swing.JPanel;

public class GraphPanel extends JPanel {
	
	//Global Variables
	private ArrayList<Node> nodes = new ArrayList<Node>();		//Stores list of nodes on GraphPanel
	private ArrayList<Link> links = new ArrayList<Link>();		//Stores list of links on GraphPanel
	private ArrayList<ArrayList<Boolean>> adjMatrix = new ArrayList<ArrayList<Boolean>>();
	
	//Constructor
	public GraphPanel() {
		super();
	}

	public void paintComponent(Graphics gr) {
		super.paintComponent(gr);
		if(links.size() > 0) {								//Draw each link with its label
			for(int i = 0; i < links.size(); i++) {
				links.get(i).paint(gr);
			}
		}
		if(nodes.size() > 0) {
			for (int i = 0; i < nodes.size(); i++) {		//Draw each node with its label
				nodes.get(i).paint(gr);
			}
		}

	}
	
	//returns the node (if extant) at a given x and y
	public Node getNode(int x, int y) {
		Node output = null;
		for(int i = 0; i < nodes.size(); i++) {					//check if the click is within the hitbox of each node
			if(Math.abs(nodes.get(i).getX() - x) < 10 && Math.abs(nodes.get(i).getY() - y) < 10) {
				output = nodes.get(i);							//return the last-created node that was clicked in its hitbox
			}
		}
		return output;
	}
	
	//returns the first-created node (if extant) with a given label
	public Node getNode(String label) {
		Node output = null;
		if(nodes.size() > 0) {										//if there are any nodes
			for(int i = nodes.size() - 1; i > -1; i--) {			//check if each node matches the given label, prioritizing the last
				if(nodes.get(i).getLabel().equals(label)) {
					output = nodes.get(i);							//return the last node that matches that label (1st created going backwards through array)
				}
			}
		}
		return output;
	}
	
	//adds a node at a x and y
	public void addNode(int x, int y, String label) {	
		nodes.add(new Node(x, y, label));				//add a new node to list(<-)...
		if(adjMatrix.size() > 0) {						//...and add a new row and column to the adjacency matrix for that node
			for(int i = 0; i < adjMatrix.size(); i++) {		//add a new column to each currently existing row
				adjMatrix.get(i).add(false);
			}
		}
		ArrayList<Boolean> intermediary = new ArrayList<Boolean>();		//add a new row
		for(int i = 0; i < adjMatrix.size() + 1 ; i++) {
			intermediary.add(false);
		}
		adjMatrix.add(intermediary);
		printMatrix();			//TODO debug code
	}
	
	//Print out adjacency matrix TODO: Debug code
	public void printMatrix() {
		for(int x = 0; x < adjMatrix.size(); x++) {					//print out each row, tab separated, new line for each row
			for(int y = 0; y < adjMatrix.size(); y++) {
				System.out.print(adjMatrix.get(x).get(y) + "\t");
			}
			System.out.println();
		}
		System.out.println();
	}
	
	//removes a node from the graph and the adjacency matrix
	public void removeNode(Node node) {
		if(node != null) {											//if the node exists
			if(node.getLinkList().size() > 0) {							//remove all links connected to node
				for(int i = 0; i < node.getLinkList().size(); i++) {
					links.remove(node.getLinkList().get(i));
				}
			}
			for(int x = 0; x < adjMatrix.size(); x++) {					//remove node's column and row from adjacency matrix
				adjMatrix.get(x).remove(nodes.indexOf(node));
			}
			adjMatrix.remove(nodes.indexOf(node));
			node.getLinkList().clear();
			nodes.remove(node);											//remove node from GraphPanel
			printMatrix();		//TODO debug code
		}
	}
	
	//returns true if two given nodes are connected
	public boolean getConnectivity(Node node1, Node node2) {			//return adjacency matrix value for row node1 and column node2, which indicates connectivity
		return adjMatrix.get(nodes.indexOf(node1)).get(nodes.indexOf(node2));
	}
	
	//adds a link (aka edge) between 2 nodes
	public void addLink(Node start, Node end, String label) {
		Link link = new Link(start, end, label);								//Create link
		start.getLinkList().add(link);											//add link to link list of each node
		end.getLinkList().add(link);
		adjMatrix.get(nodes.indexOf(start)).set(nodes.indexOf(end), true);		//represent link in adjacency matrix
		adjMatrix.get(nodes.indexOf(end)).set(nodes.indexOf(start), true);
		printMatrix();		//TODO debug code
		links.add(link);
	}
	
	//clears all nodes and links from graph
	public void clear() {
		nodes.clear();
		links.clear();
		adjMatrix.clear();
	}
	
}
