//Main Class (GUI & Overall Control) of XOR Cipher
//by: Alexander Dyall & Raveena Manivannan
//4 June 2018

package com.xor.ci;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;

public class Main implements ActionListener {
	
	//Global Variables
	byte[] input;
	byte[] output;
	byte[] key;
	boolean proceed;
	
	//Initiate File Reading & Cipher Classes
	PaperWork paperwork = new PaperWork();
	Cipher cipher = new Cipher();
	
	//Graphical Components
	JFrame window = new JFrame("XOR Cipher");
	JButton encryptBtn = new JButton("ENCRYPT");
	JButton decryptBtn = new JButton("DECRYPT");
	JLabel inputFileLbl = new JLabel("Input File:");
	JLabel outputFileLbl = new JLabel("Output File:");
	JLabel keyFileLbl = new JLabel("Key File:");
	JLabel statusLbl = new JLabel("");
	JTextField inputFileTF = new JTextField();
	JTextField outputFileTF = new JTextField();
	JTextField keyFileTF = new JTextField();
	
	
	public Main() {
		
		//Set up window
		window.setSize(400, 200);
		window.setLocation(100, 100);
		window.setLayout(new GridLayout(5, 2));
		window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		window.setVisible(true);
		
		//Set up components & add to window
		window.add(inputFileLbl);
		inputFileLbl.setHorizontalAlignment(JLabel.CENTER);
		window.add(inputFileTF);
		window.add(outputFileLbl);
		outputFileLbl.setHorizontalAlignment(JLabel.CENTER);
		window.add(outputFileTF);
		window.add(keyFileLbl);
		keyFileLbl.setHorizontalAlignment(JLabel.CENTER);
		window.add(keyFileTF);
		window.add(encryptBtn);
		encryptBtn.addActionListener(this);
		window.add(decryptBtn);
		decryptBtn.addActionListener(this);
		window.add(statusLbl);
		statusLbl.setHorizontalAlignment(JLabel.CENTER);
		
	}
	
	public static void main(String[] args) {
		new Main();
	}

	public void actionPerformed(ActionEvent ae) {
		
		if(paperwork.checkFile(inputFileTF.getText()) && paperwork.checkFile(keyFileTF.getText())
				&& paperwork.checkFile(outputFileTF.getText())) {			//only proceed if files in question exist
			proceed = true;
			statusLbl.setText("");
		}
		else {
			proceed = false;
			statusLbl.setText("Errror: Invalid File(s)");					//report if files are invalid
		}
		
		input = paperwork.readFile(inputFileTF.getText());					//read in input file
		
		if(proceed == true) {
			if(ae.getSource().equals(encryptBtn)) {							//If ENCRYPT is pushed:
				key = cipher.newRandomKey();									//create a random key
				output = cipher.XOR(input, key);								//encrypt input using that key
				paperwork.writeFile(output, outputFileTF.getText());			//write output to output file
				paperwork.writeFile(key, keyFileTF.getText());					//write key to key file
			}
			else {															//If Decrypt is pushed:
				key = paperwork.readFile(keyFileTF.getText());					//read in key file
				output = cipher.XOR(input, key);								//encrypt input using key
				paperwork.writeFile(output, outputFileTF.getText());			//write output to output file
			}
			statusLbl.setText("Done");										//Display "Done" upon completion of encryption
		}
		
	}

}
