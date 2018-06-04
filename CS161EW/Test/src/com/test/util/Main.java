package com.test.util;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JTextField;

public class Main implements ActionListener {
	
	String input;
	
	Scanner fileReader;
	JFrame window = new JFrame("Sorty");
	JButton btn = new JButton("Sort!");
	JTextField tf = new JTextField();
	
	public Main() {
		window.setSize(300,100);
		window.setLocation(100, 100);
		window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		window.setVisible(true);
		window.setLayout(new GridLayout(2, 1));
		window.add(tf);
		window.add(btn);
		btn.addActionListener(this);
	}
	
	public static void main(String[] args) {
		new Main();
	}

	public void actionPerformed(ActionEvent ae) {
		if(ae.getSource().equals(btn)) {
			try {
				fileReader = new Scanner(new File(tf.getText()));
				
			}
			catch(FileNotFoundException ex) {
				ex.printStackTrace();
			}
		}
	}
	
}
