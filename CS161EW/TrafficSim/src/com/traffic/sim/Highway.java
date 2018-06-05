//Drawing and Controller Class - Traffic Simulation
//by: Alexander Dyall
//30 Apr. 2018

package com.traffic.sim;

import java.awt.Color;
import java.awt.Graphics;
import java.util.ArrayList;

import javax.swing.JPanel;

public class Highway extends JPanel {

    //Global Variables
    ArrayList<Vehicle> traffic = new ArrayList<Vehicle>();        	//Stores vehicles on highway
    double w;                    									//Use width and height of Highway Panel for drawings
    double h;
    final double[] lanes = {13.2, 20.4, 27.6, 34.8};					//Stores the different lane y-values
    
    public Highway() {
        super();
    }
    
    //Adds a vehicle to the highway
    public void add(Vehicle v) {
        ArrayList<Double> laneOptions = new ArrayList<Double>();    //create an ArrayList storing each of the lane y's
        for(byte i = 0; i < lanes.length; i++) {
        	laneOptions.add(lanes[i]);
        }
        boolean proceed = false;
        int laneIndex;
        while(proceed == false) {
            laneIndex = (int)(laneOptions.size()*Math.random());    //pick a pseudorandom lane height and give it to the vehicle
            v.setY(laneOptions.get(laneIndex));
            v.setYTarget(v.getY());
            if(overlap(v, v.getX(), v.getY()) == false) {                //if the vehicle won't overlap with another, add it at the beginning of that lane
                traffic.add(v);
                proceed = true;
            }
            else if(laneOptions.size() > 1) {                        //if it will overlap, pseudorandomly pick another random lane
                laneOptions.remove(laneIndex);
            }
            else {                                            //if out of lanes, don't add another vehicle
                proceed = true;
            }
        }
    }
    
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        w = this.getWidth();                        //use the width and height of the panel
        h = this.getHeight();
        g.setColor(Color.GRAY);                        //Gray Background
        g.fillRect(0, 0, (int)Math.round(w), (int)Math.round(h));
        g.setColor(Color.BLACK);                    //Black Road
        g.fillRect(0, (int)Math.round(h/6), (int)Math.round(w), (int)Math.round(h*2/3));
        g.setColor(Color.WHITE);                    //White Side Lines
        g.fillRect(0, (int)Math.round(95*h/480), (int)Math.round(w), (int)Math.round(h/240));
        g.fillRect(0, (int)Math.round(383*h/480), (int)Math.round(w), (int)Math.round(h/240));
        for(int line = 0; line < 3; line++) {        //Dashed lines separating lanes
            for(int dash = 0; dash < 30; dash++) {
                g.fillRect((int)Math.round(dash*w/30), (int)Math.round((167+72*line)*h/480), (int)Math.round(w/60), (int)Math.round(h/240));
            }
        }
        for(int i = 0; i < traffic.size(); i++) {        //Draw all the vehicles
            traffic.get(i).paint(g, w, h);
        }
    }
    
    //runs one increment of the simulation
    public int increment() {
        Vehicle v;
        Vehicle w = new Vehicle(0, 0);
        int loopCount = 0;											//Stores number of vehicles who have looped in this increment
        for(int i = 0; i < traffic.size(); i++) {                    //for each vehicle:
            v = traffic.get(i);
            
            //Lane changing movement
            if(v.getY() != v.getYTarget()) {                                                                   //if vehicle is not in its target lane, proceed w/ it's lane change
                double step = v.getV()/40*(v.getYTarget() - v.getY())/Math.abs(v.getYTarget() - v.getY());
                if(!overlap(v, v.getX(), v.getYTarget())){															//If the vehicle won't collide
                    if(Math.abs(v.getYTarget() - v.getY()) > Math.abs(step)) {                                           //move a step towards the right lane if further than a step away
                        v.setY(v.getY() + step);
                    }
                    else {
                        v.setY(v.getYTarget());                                                                          //if less than a step away, go exactly to the target lane's y
                    }
                }
                else {
                	v.setWait(v.getWait() + 1);								//if vehicle will collide making lane change, add one increment to the wait count
                	if(v.getWait() == 100) {								//if the vehicle has waited to make a lane change for 100 increments
                		v.setWait(0);											//stop waiting (reset wait count)
                		if(v.getYTarget() > v.getY()) {							//if target lane is higher y than current position, set yTarget to return to lane below
                			v.setYTarget(nearestLane(v.getY(), -1));
                		}
                		else {													//if target is lower y than current position, set yTarget return to lane above
                			v.setYTarget(nearestLane(v.getY(), 1));
                		}
                	}
                }
            }
            
            //moving forward and looping
            if(!overlap(v, v.getX() + 1, v.getY())) {                //if the vehicle is not too close to colliding
                v.setX(v.getX() + v.getV()/40);                        //move the vehicle forward
                if(v.getX() + v.getL()/2 > 60 && !v.isGhost()) {    //if the vehicle has nosed over x = 60 and is not a ghost
                    if(v.getL() == 12) {                            //if it's a semi, make a new semi at the beginning (x = -20) (to simulate looping)
                        w = new Semi(-20, v.getY());
                    }
                    else if(v.getL() == 7) {                        //make an SUV at the beginning if it's an SUV
                        w = new SUV(-20, v.getY());
                    }
                    else if(v.getL() == 5) {                        //likewise if it's a SportsCar
                        w = new SportsCar(-20, v.getY());
                    }
                    if(!overlap(w, w.getX(), w.getY())){                 //If w will not be overlapping w/ another vehicle:
                        traffic.add(w);                                  //add w to the flow of traffic
                        v.setGhost(true);                                //make the original vehicle a ghost
                        loopCount++;									 //count that one vehicle has looped
                    }

                }
            }
            
            //deciding whether or not to lane change
            else if(v.getY() == v.getYTarget()) {                                                                                                            //if vehicle is not currently lane changing, and
                if(v.getY() > 14 && !overlap(v, v.getX() + v.getV()/5, v.getY() - 7.2) && !overlap(v, v.getX() - v.getV()/5, v.getY() - 7.2)) {            //if going to collide and an open spot in the lane to the left
                	v.setYTarget(nearestLane(v.getY(), -1));                                                                                                              //start lane changing left
                	
                }
                else if(v.getY() < 34 && !overlap(v, v.getX() + v.getV()/5, v.getY() + 7.2) && !overlap(v, v.getX() - v.getV()/5, v.getY() + 7.2)) {        //if going to collide and an open spot in the lane to the right
                	v.setYTarget(nearestLane(v.getY(), 1));                                                                                                              //start lane changing left
                }
            }
            
            //destroying ghost vehicles (used to make sure vehicles behind looped vehicles don't speed up)
            if(v.getX() - v.getL()/2 > 120 && v.isGhost()) {                    //if the rear of a ghost vehicle passes x = 120, destroy it.
                traffic.remove(v);
            }
        }
        return loopCount;

    }
    
    //removes all vehicles from the highway
    public void clear() {
        for(int i = traffic.size() - 1; i > -1; i--) {
            traffic.remove(i);
        }
    }
    
    //checks if vehicle will overlap with another, at a particular x & y
    public boolean overlap(Vehicle v, double x, double y) {
        boolean overlap = false;
        Vehicle w;
        for(int i = 0; i < traffic.size(); i++) {        //check each vehicle
            w = traffic.get(i);
            if(!v.equals(w) &&																			//if v & w are not the same vehicle
            	(w.getY() + w.getW()/2 > y - v.getW()/2 && w.getY() - w.getW()/2 < y + v.getW()/2)      //and v & w are at at overlapping y levels
                && ( (x <= w.getX() && (x + v.getL()/2 >= w.getX() - w.getL()/2))        				//and either v is behind w and v's front end is in front of  w's back end
                || (x >= w.getX() && (x - v.getL()/2 <= w.getX() + w.getL()/2))) ) {     				//or w is behind v and w's front end is in front of v's back end
                overlap = true;                                                          				//w & v overlap
            }
        }
        return overlap;
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
