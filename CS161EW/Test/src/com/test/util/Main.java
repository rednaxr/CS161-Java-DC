package com.test.util;

import java.util.ArrayList;
import java.util.Scanner;

public class Main {

	//Global Variables & Objects
    final double[] lanes = {13.2, 20.4, 27.6, 34.8};					//Stores the different lane y-values
    String inputS;
    Scanner reader = null;

	
	public Main() {
		double y;
		int dir;
		Scanner reader = new Scanner(System.in);
		System.out.println("y:");
		System.out.print("> ");
		inputS = reader.nextLine();
		y = Double.parseDouble(inputS);
		System.out.println("dir:");
		System.out.print("> ");
		inputS = reader.nextLine();
		dir = Integer.parseInt(inputS);
		y = nearestLane(y, dir);
		System.out.println("yTarget: " + y);
		reader.close();
	}
	
	public static void main(String[] args) {
		new Main();
	}
	
	
    //Finds nearest lane in a particular direction to the nearest y value
    private double nearestLane(double y, int direction) {
    	double output = 0;
    	ArrayList<Double> laneOptions = new ArrayList<Double>();    //create an ArrayList storing each of the lane y's
        for(byte i = 0; i < lanes.length; i++) {
        	laneOptions.add(lanes[i]);
        }
        for(byte i = 0; i < lanes.length; i++) {								//remove lanes that are below (or at) the current y (if going up in y, direction == 1)...
        	if(Math.pow(y, direction) >= Math.pow(lanes[i], direction)) {		//...or that are above the current y (if going down in y, direction == -1)
        		laneOptions.remove(lanes[i]);
        	}
        }
        if(laneOptions.size() == 0) {										//If no lane options to change into in the given direction, return current y value
        	output = y;
        }
        else if(direction > 0) {											//otherwise, if going up in y, pick lowest lane y value
        	output = laneOptions.get(0);
        }
        else {																//or, if going down in y, pick tbe lowest lane value
        	output = laneOptions.get(laneOptions.size() - 1);
        }
    	return output;
    }
	
}
