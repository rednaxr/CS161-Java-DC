//CommandWords Class of Text Adventure based on Zuul Example Code (https://drive.google.com/file/d/0B_WVGgU46DSKVHBySGN4a1NFWlk/view?usp=sharing)
//list of command words that the user can utilize
//by: Alexander Dyall
//2 Jan. 2018

package zuulminer;

/*
 * This class is the main class of the "World of Zuul" application. 
 * "World of Zuul" is a very simple, text based adventure game.
 * 
 * This class holds an enumeration of all command words known to the game.
 * It is used to recognise commands as they are typed in.
 *
 * @author  Michael Kolling and David J. Barnes
 * @version 1.0 (February 2002)
 */

class CommandWords {
    // a constant array that holds all valid command words
    private static final String validCommands[] = {
        "go", "quit", "help", "grab", "drop", "mine", "tunnel", "show", "buy", "sell", "shout", "board"
    };

    //Constructor - initialise the command words.

    public CommandWords() {
        // nothing to do at the moment...
    }

    /**
     * Check whether a given String is a valid command word. 
     * Return true if it is, false if it isn't.
     */
    public boolean isCommand(String aString) {
        for(int i = 0; i < validCommands.length; i++) {
            if(validCommands[i].equals(aString))
                return true;
        }
        // if we get here, the string was not found in the commands
        return false;
    }

   //Print all valid commands to System.out.

    public void showAll() {
        for(int i = 0; i < validCommands.length; i++) {
            System.out.print(validCommands[i] + "  ");
        }
        System.out.println();
    }
}
