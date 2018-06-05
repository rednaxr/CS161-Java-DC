//File Reading and Writing Class of XOR Cipher
//by: Alexander Dyall & Raveena Manivannan
//4 June 2018

package com.xor.ci;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.OutputStream;

public class PaperWork {

	public PaperWork() {
		
	}
	
	
	public boolean checkFile(String path){
		
	       //Create an instance of the file with the path given
	       File f = new File(path);
	      
	       //Check if the file exists and is not a directory/folder
	       if(f.exists() && !f.isDirectory()) { 
	           return true;
	       }
	       return false;
	    }
	    
	    public byte[] readFile(String path){
	        
	        FileInputStream myInputStream = null;

	      	//Create an instance of the file with the path given
	        File myFile = new File(path);

	      
	        //Create and array of bytes of size = the size of the file
	        byte[] myByteStream = new byte[(int) myFile.length()];

	        try {

	          
	            myInputStream = new FileInputStream(myFile);
	            myInputStream.read(myByteStream);
	            myInputStream.close();

	          	//Iterate each position in the array of bytes, and print that position.
	       /*
	            for (int counter = 0; counter < myByteStream.length; counter++) {
	                System.out.print((char) myByteStream[counter]);
	            }
	            System.out.println("\nReading Complete");
	            */
	        } catch (Exception e) {
	            System.out.println("Error: " + e);
	        }
	        
	        return myByteStream;
	        
	    }
	    
	    public void writeFile(byte[] bytes, String path){
	        
	        //Convert the String given to an array of bytes
	        //byte[] bytes = text.getBytes();
	    	
	        File file = new File(path);
	 
	        try {
	 			//Create an instance of the output stream to write to the file
	            OutputStream os = new FileOutputStream(file);
	          	//Write the array of bytes to the file
	            os.write(bytes);
	            os.close();
	        } catch (Exception e) {
	            e.printStackTrace();
	        }
	    }
	
}
