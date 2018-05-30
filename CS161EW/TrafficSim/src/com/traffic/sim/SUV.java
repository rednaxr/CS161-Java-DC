//Class for SUV Object -  Traffic Simulation
//by: Alexander Dyall
//30 Apr. 2018

package com.traffic.sim;

import java.awt.Color;
import java.awt.Graphics;

public class SUV extends Vehicle{

    public SUV(double X, double Y) {
        super(X, Y);
        w = 5;
        l = 7;
        v = 2.5;
        ghost = false;
    }
    
    
    public void paint(Graphics g, double screenWidth, double screenHeight) {
        g.setColor(new Color(250, 220, 50));                                                                    //paint SUV yellow
        g.fillRect((int)Math.round((x - l/2)*screenWidth/48), (int)Math.round((y - w/2)*screenHeight/48), (int)Math.round(l*screenWidth/48), (int)Math.round(w*screenHeight/48));        //paint vehicle with (x, y) at center
    }
}
