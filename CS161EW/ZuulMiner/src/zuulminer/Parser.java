//Parser Class of Text Adventure based on Zuul Example Code (https://drive.google.com/file/d/0B_WVGgU46DSKVHBySGN4a1NFWlk/view?usp=sharing)
//parses read input into a command word and qualifier words (outputted as one word)
//by: Alexander Dyall
//2 Jan. 2018

package zuulminer;

class Parser {

    private CommandWords commands;  // holds all valid command words

    public Parser() {
        commands = new CommandWords();
    }
    
    //returns a command based on inputted string
    public Command getCommand(String input) {
        String commandWord= "";						//holds first (command) word
        String qualifierWord = "";					//holds all following (qualifier) words

        commandWord = input.split(" ")[0];							//stores the first word in String commandWord
        if(input.split(" ").length != 1) {							//Stores any following words in String qualifierWord, as one word
  	        for(int i = 1; i < input.split(" ").length - 1; i++) {		
   	        	qualifierWord += input.split(" ")[i] + " ";
    	        }
  	        qualifierWord += input.split(" ")[input.split(" ").length-1];
            }
        else {
        	qualifierWord = null;
        }

        if(commands.isCommand(commandWord)) {						//if commandWord is valid, send on command is normal
            return new Command(commandWord, qualifierWord);
        }
        else {														//otherwise, send on a dummy
            return new Command(null, null);
        }
    }

    //Print out a list of valid command words.

    public void showCommands() {
        commands.showAll();
    }
}
