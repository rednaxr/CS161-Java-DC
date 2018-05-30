//Class for Drawing Things & Physics - Traffic Simulation
//by: Alexander Dyall
//30 Apr. 2018

package com.traffic.sim;

import java.awt.Color;
import java.awt.Graphics;
import java.util.ArrayList;

import javax.swing.JPanel;

public class Highway extends JPanel {

    //Global Variables
    ArrayList<Vehicle> traffic = new ArrayList<Vehicle>();        //Stores vehicles on highway
    double w;                    //Use width and height of Highway Panel for drawings
    double h;
    
    public Highway() {
        super();
    }
    
    //Adds a vehicle to the highway
    public void add(Vehicle v) {
        ArrayList<Double> lanes = new ArrayList<Double>();    //create an ArrayList storing each of the lane y's
        lanes.add(13.2);
        lanes.add(20.4);
        lanes.add(27.6);
        lanes.add(34.8);
        boolean proceed = false;
        int laneIndex;
        while(proceed == false) {
            laneIndex = (int)(lanes.size()*Math.random());    //pick a pseudorandom lane height
            v.setY(lanes.get(laneIndex));
            if(overlap(v, v.getX(), v.getY()) == false) {                //if the vehicle won't overlap with another, add it at the beginning of that lane
                traffic.add(v);
                proceed = true;
            }
            else if(lanes.size() > 1) {                        //if it will overlap, pseudorandomly pick another random lane
                lanes.remove(laneIndex);
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
    public void increment() {
        Vehicle v;
        Vehicle w = new Vehicle(0, 0);
        int y;
        for(int i = 0; i < traffic.size(); i++) {                    //for each vehicle:
            v = traffic.get(i);
            if(!overlap(v, v.getX() + v.getV()/5, v.getY())) {                //if the vehicle is not too close to colliding
                v.setX(v.getX() + v.getV()/40);                        //move the vehicle forward
                if(v.getX() + v.getL()/2 > 60 && !v.isGhost()) {    //if the vehicle has nosed over x = 60 and is not a ghost
                    if(v.getL() == 12) {                            //if it's a semi, make a new semi at the beginning (to simulate looping)
                        w = new Semi(v.getX() - 72, v.getY());
                    }
                    else if(v.getL() == 7) {                        //likewise if it's an SUV
                        w = new SUV(v.getX() - 72, v.getY());
                    }
                    else if(v.getL() == 5) {                        //likewise if it's a SportsCar
                        w = new SportsCar(v.getX() - 72, v.getY());
                    }
                    if(!overlap(w, w.getX(), w.getY())){                        //If w will not be overlapping w/ another vehicle:
                        traffic.add(w);                                    //add w to the flow of traffic
                        v.setGhost(true);                                //make the original vehicle a ghost
                    }

                }
                if(v.getX() - v.getL()/2 > 120 && v.isGhost()) {                    //if the rear of a ghost vehicle passes x = 70, destroy it.
                    traffic.remove(v);
                }
            }
            else if(v.getY() == v.getYTarget()) {                                                                                                            //if vehicle is not currently lane changing, and
                if(v.getY() > 14 && !overlap(v, v.getX() + v.getV()/5, v.getY() - 7.2) && !overlap(v, v.getX() - v.getV()/5, v.getY() - 7.2)) {            //if going to collide and an open spot in the lane to the left
                	v.setYTarget(v.getY() - 7.2);                                                                                                                //start lane changing left
                }
                else if(v.getY() < 34 && !overlap(v, v.getX() + v.getV()/5, v.getY() + 7.2) && !overlap(v, v.getX() - v.getV()/5, v.getY() + 7.2)) {        //if going to collide and an open spot in the lane to the right
                    v.setYTarget(v.getY() + 7.2);                                                                                                            //start lane changing right
                }
            }

            if(v.getY() != v.getYTarget()) {                                                                                                            //if vehicle is not in its target lane, proceed w/ lane change
                double step = v.getV()/40*(v.getYTarget() - v.getY())/Math.abs(v.getYTarget() - v.getY());
                if(!overlap(v, v.getX() + v.getV()/5, v.getYTarget()) && !overlap(v, v.getX() - v.getV()/5, v.getYTarget())){
                    if(Math.abs(v.getYTarget() - v.getY()) > Math.abs(step)) {                                                                                    //move a step towards the right lane if further than a step away
                        v.setY(v.getY() + step);
                    }
                    else {
                        v.setY(v.getYTarget());                                                                                                                    //if less than a step away, go exactly to the target lane's y
                    }
                }
            }
        }

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
            if(!v.equals(w) && (w.getY() + 6 > y && w.getY() - 6 <  y)                                                        //if v & w are not the same vehicle and at overlapping y levels
                && ( (x <= w.getX() && (x + v.getL()/2 >= w.getX() - w.getL()/2))                                             //and either v is behind w and v's front end is in front of  w's back end
                || (x >= w.getX() && (x - v.getL()/2 <= w.getX() + w.getL()/2))) ) {                                        //or w is behind v and w's front end is in front of v's back end
                overlap = true;                                                                                                //w & v overlap
            }
        }
        return overlap;
    }
    
}
