//Main Class (Interface and Threads) - Traffic Simulation
//by: Alexander Dyall
//30 Apr. 2018

package com.traffic.sim;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;

public class Main implements ActionListener, Runnable {
    
    //Global Variable(s)
    boolean simRunning = false;
    
    //Graphical Components
    JFrame simFrame = new JFrame("Traffic Simulation");
    Highway highway = new Highway();
    Container btnZone = new Container();
    JButton semiBtn = new JButton("Add Semi Truck");
    JButton SUVBtn = new JButton("Add SUV");
    JButton sportsBtn = new JButton("Add Sports Car");
    JButton clearBtn = new JButton("CLEAR");
    JButton startBtn = new JButton("START");
    JButton stopBtn = new JButton("STOP");
    JLabel rateLbl = new JLabel("Throughput: 0 vehicles/sec");
    
    public Main() {
        
        //Window
        double screenWidth = Toolkit.getDefaultToolkit().getScreenSize().getWidth();            //methods for obtaining screen width and screen height from: Colin Hebert and Devon C. Miller at https://stackoverflow.com/questions/3680221/how-can-i-get-screen-resolution-in-java
        double screenHeight = Toolkit.getDefaultToolkit().getScreenSize().getHeight();
        simFrame.setSize((int)Math.round(screenWidth*2/3), (int)Math.round(screenHeight*2/3));
        simFrame.setLocation((int)Math.round(screenWidth/6), (int)Math.round(screenHeight/6));
        simFrame.setVisible(true);
        simFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        simFrame.setLayout(new BorderLayout());
        simFrame.add(highway, BorderLayout.CENTER);
        simFrame.add(btnZone, BorderLayout.SOUTH);
        simFrame.add(rateLbl, BorderLayout.NORTH);
        
        //Buttons (at bottom of window)
        btnZone.setLayout(new GridLayout(2, 3));
        btnZone.add(semiBtn);
        btnZone.add(SUVBtn);
        btnZone.add(sportsBtn);
        btnZone.add(startBtn);
        btnZone.add(stopBtn);
        btnZone.add(clearBtn);
        semiBtn.addActionListener(this);
        SUVBtn.addActionListener(this);
        sportsBtn.addActionListener(this);
        startBtn.addActionListener(this);
        stopBtn.addActionListener(this);
        clearBtn.addActionListener(this);
        
    }
    
    public static void main(String[] args) {
        new Main();
    }
    
    public void actionPerformed(ActionEvent ae) {
        if(ae.getSource().equals(startBtn)){            //if "START" is pushed, start simulation
            if(simRunning == false) {
                simRunning = true;
                Thread t = new Thread(this);
                t.start();
            }

        }
        else if(ae.getSource().equals(stopBtn)) {        //if "STOP" is pushed, pause simulation
            simRunning = false;
        }
        else if(ae.getSource().equals(semiBtn)) {        //if "Add Semi Truck" is pushed, add a semi truck
            highway.add(new Semi(6.5, 0));
            simFrame.repaint();
        }
        else if(ae.getSource().equals(SUVBtn)) {        //if "Add SUV" is pushed, add an SUV
            highway.add(new SUV(4, 0));
            simFrame.repaint();
        }
        else if(ae.getSource().equals(sportsBtn)) {        //if "Add Sports Car" is pushed, add a Sports Car
            highway.add(new SportsCar(3, 0));
            simFrame.repaint();
        }
        else if(ae.getSource().equals(clearBtn)) {        //if "Clear" is pushed, remove all vehicles and stop simulation
            highway.clear();
            simRunning = false;
            simFrame.repaint();
        }
    }

    //Runs simulation, updating throughput every 5 sec
    public void run() {
        double time = 0;								//Stores the time elapsed during a given increment
    	int totalCount = 0;								//Stores the number of vehicles that have looped since the last reset
    	float totalTime = 0;							//Stores the total time elapsed since the last reset.
    	float throughput;								//Stores throughput
        while(simRunning == true) {
        	time = System.currentTimeMillis();			//record the current time
            totalCount += highway.increment();			//run one increment, adding the number of vehicles that pass in that increment to the total count
            simFrame.repaint();
            try {										//try waiting for .01 sec
                Thread.sleep(10);
            }
            catch(InterruptedException ex) {
            }
            totalTime += System.currentTimeMillis() - time;							//add the time passed in that increment (plus the sleep) to the total time
            if(totalTime > 5000) {													//When the total time exceeds 5 sec
            	throughput = totalCount/totalTime*1000;								//calculate the number of vehicles that lopped per sec during the last 5ish sec
            	rateLbl.setText("Throughput: " + throughput + " vehicles/sec");		//report the calculated throughput on the associated JLabel
            	totalCount = 0;														//RESET the total vehicle count and total time
            	totalTime = 0;
            }
        }
    }

}
