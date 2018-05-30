//Class for Vehicle Object - Traffic Simulation
//by: Alexander Dyall
//30 Apr. 2018

package com.traffic.sim;

import java.awt.Color;
import java.awt.Graphics;

public class Vehicle {
    
    public Vehicle(double X, double Y) {
        x = X;
        y = Y;
        yTarget = y;
    }
    
    //Vehicle Attributes:
    double x;            //X position
    double y;            //y position
    double l;            //length
    double w;            //width
    double v;            //speed
    double yTarget;        //which lane Y-value vehicle is trying to go to
    boolean ghost;        //whether or not the vehicle is a ghost representation of a vehicle that has already looped
    
    public void paint(Graphics g, double screenWidth, double screenHeight) {
        g.setColor(Color.WHITE);                                                                    //paint vehicle more towards red the slower it goes
        g.fillRect((int)Math.round((x - l/2)*screenWidth/48), (int)Math.round((y - w/2)*screenHeight/48), (int)Math.round(l*screenWidth/48), (int)Math.round(w*screenHeight/48));        //paint vehicle with (x, y) at center
    }
    
    public double getX() {
        return x;
    }
    
    public void setX(double X) {
        x = X;
    }
    
    public double getY() {
        return y;
    }
    
    public void setY(double Y) {
        y = Y;
    }
    
    public double getL() {
        return l;
    }
    
    public double getW() {
        return w;
    }
    
    public double getV() {
        return v;
    }
    
    public boolean isGhost() {
        return ghost;
    }
    
    public void setGhost(boolean G) {
        ghost = G;
    }
    
    public double getYTarget() {
        return yTarget;
    }
    
    public void setYTarget(double yt) {
        yTarget = yt;
    }
    
}