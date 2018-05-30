//Class for Sports Car Object - Traffic Simulation
//by: Alexander Dyall
//30 Apr. 2018

package com.traffic.sim;

import java.awt.Color;
import java.awt.Graphics;

public class SportsCar extends Vehicle{

    public SportsCar(double X, double Y) {
        super(X, Y);
        w = 4;
        l = 5;
        v = 3;
        ghost = false;
    }
    
    
    public void paint(Graphics g, double screenWidth, double screenHeight) {
        g.setColor(new Color(5, 200, 50));                                                                    //paint sportscar green
        g.fillRect((int)Math.round((x - l/2)*screenWidth/48), (int)Math.round((y - w/2)*screenHeight/48), (int)Math.round(l*screenWidth/48), (int)Math.round(w*screenHeight/48));        //paint vehicle with (x, y) at center
    }
}
