package com.sorting.util;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Scanner;

public class Sorting {
	
	//Global Variables
	Scanner sysIn = new Scanner(System.in);				//Scanner for reading typed input

	
	//CONSTRUCTOR
	public Sorting() {
		
		//Local Variables:
		Scanner fileIn = null;								//Scanner for reading file input
		String sysInput;									//String for storing file input
		int errorCount = 0;									//int storing the number of errors thrown, to report in System Exit
		boolean cont = false;								//boolean for: should the program continue (exit while loop) ?
		long time = 0;										//long for storing time taken by sorting process
		
		//Read in file name
		System.out.println("Please type the name of the file you would like sorted.");		//Ask user for file name
		while(cont == false) {						//until a valid file name is given, read system inputs
			System.out.print("> ");
			sysInput = sysIn.nextLine();
			try {
				fileIn = new Scanner(new File(sysInput));
				cont = true;
			}
			catch(FileNotFoundException ouch) {		//for each not-found file, report and add to error count
				System.out.println("File not found. Please try again.");
				errorCount++;
			}
		}
		
		//Store comma-separated input from file in String array stringInput
		String[] stringData= fileIn.nextLine().split(",");		
		int[] data = new int[stringData.length];
		for(int i = 0; i < stringData.length; i++) {
			data[i] = Integer.parseInt(stringData[i]);
		}
		
		//Ask for how data should be sorted
		String[] sortQ = {
			"How would you like the data sorted?",
			"bubble",		//this answer returns 1
			"selection",	//" 2
			"table"			//" 3
		};
		int sortType = question(sortQ);	
		System.out.println("...");
		
		//record start time
		time = System.currentTimeMillis();
		
		//sort according to specified type
		if(sortType == 1) {
			data = bubbleSort(data);
		}
		else if(sortType == 2) {
			data = selectionSort(data);
		}
		else if(sortType == 3) {
			data = tableSort(data);
		}
		
		//report completion and calculate and report time taken
		time = System.currentTimeMillis() - time;
		System.out.println("Sorting Completed.");
		System.out.println("time: " + time + " ms.");
		
		//try sending to the processed data to output file: output.txt
		try {
			PrintWriter outputter = new PrintWriter(new FileWriter(new File("output.txt")));		//Create a new printWriter to a new File
			String output = "";																		//put sorted data points in a comma separated list (String)
			for(int i = 0; i < data.length - 1; i++) {
				output += data[i]+",";
			}
			output += data[data.length-1];
			output += "\n   time: " + time + " ms";													//record time taken to sort after the list, in the String
			outputter.write(output);																//write and save output file
			outputter.close();
		}
		catch(IOException ouch) {																	//If outputting fails
			System.out.println("Output failed.");													//report and exit
			ouch.printStackTrace();
			errorCount += 1;
			System.exit(errorCount);
		}

		sysIn.close();																				//close Scanner and exit
		System.exit(errorCount);
	}
	
	//MAIN
	public static void main(String[] args) {
		new Sorting();
	}
	
	//PRIVATE METHODS:
	
	//Asks a question based on an inputted array of a question (at index 0) and answers (indexes 1+)
	private int question(String[] QnA) {
		int reint = 0;
		String answer;
		String output = QnA[0] + " (";			//Print out question QnA[0] w/ valid answers following in parenthesis
		for(int i = 1; i < QnA.length - 1; i++) {
			output += QnA[i] + ", ";
		}
		output += QnA[QnA.length-1] + ")";
		System.out.println(output);
		boolean cont = false;						//Until a valid answer is given...
		while(cont == false) {
			System.out.print("> ");					//print prompt and read input
			answer = sysIn.nextLine();
			for(int i = 1; i < QnA.length; i++) {	//if the input equals any of the answers, return the index of that answer
				if(QnA[i].equals(answer)) {
					reint = i;
					cont = true;
				}
			}
			if(cont == false) {						//if input was not one of the answers, report and print out valid answers:
				output = "Invalid Input. Valid Answers: ";
				for(int i = 1; i < QnA.length-1; i++) {
					output += QnA[i] + ", ";
				}
				output += QnA[QnA.length-1];
				System.out.println(output);
			}
		}
		sysIn.close();
		return reint;
	}
	
	//SORTING METHODS:		(algorithms obtained from Wikipedia)
	
	//Bubble Sorts Data
	private int[] bubbleSort(int[] data) {
		int smallInt;
		int bigInt;
		int switchCount = 1;
		while(switchCount > 0) {							//Until having gone through data without switching anything
			switchCount = 0;								//reset switch count
			for(int i = 0; i < data.length-1; i++) {		//go through each data point (except the last)
				if(data[i] > data[i+1]) {					//if data point is larger than the next one...
					smallInt = data[i+1];					//...switch the two
					bigInt = data[i];
					data[i] = smallInt;
					data[i+1] = bigInt;
					switchCount++;							//...and record one switch
				}
			}	
		}
		return data;
	}
	
	//Selection Sorts Data
	private int[] selectionSort(int[] data) {
		int smallInt;
		int bigInt;
		int smallIndex;
		for(int i = 0; i < data.length-1; i++) {			//go through each data index (except the last):
			smallInt = data[i];								//start the smallest integer as the one at the current index (set it's index accordingly)
			smallIndex = i;
			for(int j = i+1; j < data.length; j++) {		//go through each data point after the index in question
				if(data[j] < smallInt) {					//if the data point is smaller than the current smallest integer
					smallInt = data[j];						//set the smallest integer to the current data point and record its index
					smallIndex = j;
				}
			}
			bigInt = data[i];								//switch the integer in the index in question with the smallest integer
			data[i] = smallInt;
			data[smallIndex] = bigInt;
		}
		return data;
	}
	
	//Table Sorts Data
	private int[] tableSort(int[] input) {
		int[] output = new int[input.length];
		ArrayList<Integer> keys = new ArrayList<Integer>();				//Stores one of each number (key) in data set
		ArrayList<Integer> keyCounts = new ArrayList<Integer>();		//Stores the number of times each key occurs
		ArrayList<Integer> underKey = new ArrayList<Integer>();			//stores the number of data points with a lower key value
		for(int i = 0; i < input.length; i++) {					//go through the data set
			if(!keys.contains(input[i])) {							//...recording which keys are present
				keys.add(input[i]);
				keyCounts.add(1);
				underKey.add(0);
			}
			else {													//...and counting the number of each key
				keyCounts.set(keys.indexOf(input[i]), keyCounts.get(keys.indexOf(input[i])) + 1);
			}
		}
		for(int i = 0; i < keys.size(); i++) {					//go through the keys
			for(int j = 0; j < input.length; j++) {					//count the number of data points with a lower key value
				if(input[j] < keys.get(i)) {
					underKey.set(i, underKey.get(i) + 1);
				}
			}
			for(int j = underKey.get(i); j < underKey.get(i)+keyCounts.get(i); j++) {		//place all data points with given key value in position, after the spaces for data points of a lower key
				output[j] = keys.get(i);
			}
		}
		return output;
	}
	
}

