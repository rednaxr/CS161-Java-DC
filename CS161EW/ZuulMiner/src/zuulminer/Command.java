//Command Class of Text Adventure based on Zuul Example Code (https://drive.google.com/file/d/0B_WVGgU46DSKVHBySGN4a1NFWlk/view?usp=sharing)
//holds information about a command inputted by user
//by: Alexander Dyall
//2 Jan. 2018

package zuulminer;

class Command {
    private String commandWord;
    private String qualifierWord;

    /*
     * Create a command object. First and second word must be supplied, but
     * either one (or both) can be null. The command word should be null to
     * indicate that this was a command that is not recognised by this game.
     */
    public Command(String firstWord, String secondWord) {
        commandWord = firstWord;
        this.qualifierWord = secondWord;
    }

    //Return the command word (the first word) of this command. If the command was not understood, the result is null.
    public String getCommandWord() {
        return commandWord;
    }

     //Return the second word of this command. Returns null if there was no
     //second word.

    public String getQualifierWord() {
        return qualifierWord;
    }

   //Return true if this command was not understood.

    public boolean isUnknown() {
        return (commandWord == null);
    }

    //Return true if the command has a second word.
    public boolean hasQualifierWord() {
        return (qualifierWord != null);
    }

  //returns amount of items specified by a command
    public int getAmount() {
    	int amount = 1;																		//default amount is one
    	if(this.hasQualifierWord()) {
    		try {
    			amount = Integer.parseInt(this.getQualifierWord().split(" ")[0]);			//if first word can be parsed into a number, amount is that number
    		}
    		catch(Exception ouch) {
    			if(this.getQualifierWord().split(" ")[0].equals("all")) {					//if parsing fails and first word is all, denote as amount = -1
    				amount = -1;
    			}
    		}
    	}
    	return amount;
    }
    
    //returns name of the item specified by a command
    public String getItemName() {
    	String reString = "";
    	if(this.hasQualifierWord()) {
    		String[] qualifierWord = this.getQualifierWord().split(" ");
	    	if(qualifierWord.length > 1) {									//If the qualifier is multi-word:	
	    		try {														//try parsing 1st word into an int
	        		Integer.parseInt(qualifierWord[0]);						//if successful, set itemName to the rest of the qualifier
	        		for(int i = 1; i < qualifierWord.length - 1; i++) {
	        			reString += qualifierWord[i] + " ";
	        		}
	        		reString += qualifierWord[qualifierWord.length - 1];
	        	}
	        	catch(NumberFormatException ouch) {								//if parsing fails...
	        		if(qualifierWord[0].equals("all")) {						//if the 1st word is all, set itemName to the rest of the qualifier
		        		for(int i = 1; i < qualifierWord.length - 1; i++) {
		        			reString += qualifierWord[i] + " ";
		        		}
		        		reString += qualifierWord[qualifierWord.length - 1];
	        		}
	        		else {														//otherwise, set itemName to the qualifier
		        		for(int i = 0; i < qualifierWord.length - 1; i++) {
		        			reString += qualifierWord[i] + " ";
		        		}
		        		reString += qualifierWord[qualifierWord.length - 1];
	        		}
	        	}
	    	}
	    	else {															//If the qualifier is one word, return the qualifier
	    		reString = qualifierWord[0];
	    	}
    	}
    	return reString;
    }
    
}