//Main Class of Text Adventure based on Zuul Example Code (https://drive.google.com/file/d/0B_WVGgU46DSKVHBySGN4a1NFWlk/view?usp=sharing)
//initializes all other classes and contains the central mechanics of the program
//by: Alexander Dyall
//2 Jan. 2018

package zuulminer;

import java.util.ArrayList;
import java.util.Scanner;

class Game {
	//Global Variables
	int mineScale = 25;			//Size mine (side length of cubic array of rooms)
	String input;				//stores raw commands inputted by player
    public Parser parser;		//creates parser
    Room currentRoom;			//Room that player is currently in
    Room mineEntrance, mainStreet, tavern, store, trainStation, dead;		//Non-mine rooms
    Room[][][] mine = new Room[mineScale][mineScale][mineScale];			//3D array of mine rooms
    Item dollar, ironOre, goldOre, iron, gold, basicPick, standardPick, epicPick, satchel, ale, pomegranate, ticket;		//game items
    public ArrayList<Item> itemList;		//Master list of all items
    Player player;							//creates player
    
    //Create the game and initialize its internal map
    public Game() {
    	player = new Player(0, 20);		//initialize player
        createItems();									//initialize items
        createRooms();									//initialize rooms
        parser = new Parser();							//initialize parser
    }
    
    //Create all ITEMS
    private void createItems() {
    	//initialize items
    	dollar = new Item("dollar", 1, true);
    	iron = new Item("iron", 2, false);
    	gold = new Item("gold", 5, false);
    	ironOre = new Item("iron ore", 1, false);
    	goldOre = new Item("gold ore", 5, false);
    	basicPick = new Item("basic pickaxe", 3, true);
    	standardPick = new Item("standard pickaxe", 40, true);
    	epicPick = new Item("epic pickaxe", 100, true);
    	satchel = new Item("satchel", 30, true);
    	ale = new Item("ale", 3, true);
    	pomegranate = new Item("pomegranate", 0, true);
    	ticket = new Item("train ticket", 500, true);
    	
    	//add items to a list (to allow checking if an item exists)
    	itemList = new ArrayList<Item>();
    	itemList.add(dollar);
    	itemList.add(iron);
    	itemList.add(gold);
    	itemList.add(ironOre);
    	itemList.add(goldOre);
    	itemList.add(basicPick);
    	itemList.add(standardPick);
    	itemList.add(epicPick);
    	itemList.add(ale);
    	itemList.add(pomegranate);
    	itemList.add(satchel);
    	itemList.add(ticket);
    }
 
    //Create all the ROOMS and link their exits together.
    private void createRooms(){
    	
        //create  mine rooms
        for(int h = 0; h < mineScale; h++) {
        	for(int x = 0; x < mineScale; x++) {
        		for(int y = 0; y < mineScale; y++) {
        			mine[x][y][h] = new Room("in the Mine. Coordinates: " + x + ", " + y + ", " + h);
        		}
        	}
        }
        
        //Create non-mine rooms
    	mainStreet = new Room("on the Main Street of Minertown");
        mineEntrance = new Room("at the entrance of the Mine");
        tavern = new Room("in the Tavern");
        store = new Room("in the General Store");
        trainStation = new Room("at the Train Station");
        dead = new Room("in the Realm of the Dead");

        //initializes exits and items of mine rooms
        for(int h = 0; h < mineScale; h++) {								//Set horizontal (N E W S) for all mine rooms
        	for(int x = 0; x < mineScale; x++) {
        		for(int y = 0; y < mineScale; y++) {
	        		mine[x][y][h].setExit("die in a hole", dead);			//Add exit to "The Realm of the Dead" for all rooms (easter egg)
	        		if(y != mineScale-1) {									//Only set a North Exit if not on the North Edge
	        			mine[x][y][h].setExit("N", mine[x][y+1][h]);
	        		}
	        		if(x != mineScale-1) {									//Only set an East Exit if not on the East Edge
	        			mine[x][y][h].setExit("E", mine[x+1][y][h]);
	        		}
	        		if(y != 0) {											//Only set a West Exit if not on the West Edge
	        			mine[x][y][h].setExit("S", mine[x][y-1][h]);
	        		}
	        		if(x != 0) {											//Only set a South Exit if not on the South Edge
	        			mine[x][y][h].setExit("W", mine[x-1][y][h]);
	        		}
        		}
        	}
        }
        for(int h = 1; h < mineScale; h++) {								//In each layer except top
	    	for(int i = 0; i < (int)(Math.pow(mineScale, 2)/5); i++) {		//Set about 1 way up per 5 rooms, with a corresponding way down from the room above, in each flat layer...
	    		int upX = (int)((mineScale)*Math.random());					//...at random X's and Y's
	    		int upY = (int)((mineScale)*Math.random());
	    		mine[upX][upY][h].setExit("up", mine[upX][upY][h-1]);
	    		mine[upX][upY][h-1].setExit("down", mine[upX][upY][h]);
	    	}
	    	for(int x = 0; x < mineScale; x++) {							//for each mine room (except top layer):
        		for(int y = 0; y < mineScale; y++) {
        			mine[x][y][h].putItem(ironOre, (int)Math.round(3*Math.pow(h, .5)*Math.random()));			//add random amount of iron ore, more (on avg) deeper down
        			mine[x][y][h].putItem(goldOre, (int)Math.round(.5*Math.pow(h, .5)*Math.random()));			//add (smalller) random amount of gold ore, more (on avg) deeper down
        			if(Math.random() > .35) {
        				mine[x][y][h].blockAnExit();
        			}
        		}
	    	}
    	}
        
        //initialize exits of non-mine rooms
        mainStreet.setExit("to the Mine", mineEntrance);					//Connect the 4 main locations to Main Street
        mainStreet.setExit("to the Tavern", tavern);
        mainStreet.setExit("to the Store", store); 
        mainStreet.setExit("to the Train Station", trainStation);
        tavern.setExit("to Main Street", mainStreet);
        store.setExit("to Main Street", mainStreet);
        trainStation.setExit("to Main Street", mainStreet);
        mineEntrance.setExit("to Main Street", mainStreet);
		int enterX = (int)((mineScale)*Math.random());						//Put mine entrance at random location on first level
		int enterY = (int)((mineScale)*Math.random());
        mineEntrance.setExit("into the Mine", mine[enterX] [enterY][0]);
        mine[enterX][enterY][0].setExit("up", mineEntrance);
        
        //place items in non-mine rooms
        trainStation.putItem(dollar, 2);
        mineEntrance.putItem(dollar, 1);
        dead.putItem(pomegranate, 1);
        tavern.putItem(ale, 1000);
        store.putItem(basicPick, 5);
        store.putItem(standardPick, 3);
        store.putItem(epicPick, 1);
        store.putItem(satchel, 9);
        store.putItem(ticket, 1);
        
        currentRoom = mainStreet;  // start game on Main street
    }

    //MAIN PLAY ROUTINE:
    public void play() {            
        printWelcome();										//Start game by showing welcome text
        Scanner reader = new Scanner(System.in);			//initialize scanner for reading inputs

        //Continue main command loop until quit received 
        boolean finished = false;
        while (! finished) {								//until player wins, loses, or quits:
        	System.out.print("> ");     					// print prompt
        	input = reader.nextLine();						//read input line
            Command command = parser.getCommand(input);		//parse input into command (see PARSER)
            finished = processCommand(command);				//process command (see below)
        }
        System.out.println("Thank you for playing.  Good bye!");	//At end, thank player for playing
        reader.close();												//(and close scanner)
    }

    //MAIN
    public static void main(String[] args) {
    	Game zuulminer = new Game();
    	zuulminer.play();
    }
   
    //Print opening message
    private void printWelcome() {
        System.out.println();
        System.out.println("Welcome to Zuul Miner!");
        System.out.println("Miner is a text-based adventure game in which you can mine ore to make money, amongst other things.");
        System.out.println("In this game, you are stranded in Minertown. Your goal is to earn enough money to buy a train ticket out.");
        System.out.println("\t It is rumored that there is more and better ore deeper in the mine...");
        System.out.println("Type 'help' if you need help.");
        System.out.println();
        System.out.println(currentRoom.getLongDescription());
    }

    //processes inputted commands. returns true if quitting time
    private boolean processCommand(Command command) {
        boolean quitOrNot = false;
	        if(command.isUnknown()) {									//if the command is unknown report
	            System.out.println("I don't know what you mean...");
	        }
	        else{
		        String commandWord = command.getCommandWord();			//if the command is valid, enact the inputted command
		        if(commandWord.equals("go")) {
		            goRoom(command);
		        }
		        else if(commandWord.equals("mine")) {
		        	mine(command);
		        }
		        else if(commandWord.equals("tunnel")) {
		        	quitOrNot = tunnel(command);
		        }
		        else if(commandWord.equals("grab")) {
		        	grabItem(command);
		        }
		        else if(commandWord.equals("drop")) {
		        	dropItem(command);
		        }
		        else if(commandWord.equals("buy")) {
		        	buyItem(command);
		        }
		        else if(commandWord.equals("sell")) {
		        	sellItem(command);
		        }
		        else if(commandWord.equals("show")) {
		        	show(command);
		        }
		        else if(commandWord.equals("help")) {
		            printHelp();
		        }
		        else if (commandWord.equals("quit")) {
		            quitOrNot = quit(command);
		        }
		        else if(commandWord.equals("shout")) {
		        	quitOrNot = shout();
		        }
		        else if(commandWord.equals("board")) {
		        	quitOrNot = board(command);
		        }
	        }
        return quitOrNot;
    }

    //implementations of user commands:

    //go to a new room
    private void goRoom(Command command) {
        if(!command.hasQualifierWord()) {							//If user does not give direction, ask for direction
            System.out.println("Go where?");
            return;
        }
        else {													//If user gives direction, check if that exit exists
	        String direction = command.getQualifierWord();
	        Room nextRoom = currentRoom.getExit(direction);
	        if (nextRoom == null) {
	            System.out.println("You can't go that way!");			//If not, tell the user
	        }
	        else {													//If so, go to said room
	            currentRoom = nextRoom;
	            System.out.println(currentRoom.getLongDescription());
	        }
        }
    }
    
    //mines specified amount of specified ore
    private void mine(Command command) {
    	Item item = getItem(command);				//find item given by command
    	if(item != null) {							//If the item exists...
    		int quantity = command.getAmount();		//find quantity given by command
        	if(item.equals(iron) || item.equals(ironOre)){		//...and is iron ore...
        		if(player.getInventoryQuantity(basicPick) + player.getInventoryQuantity(standardPick) + player.getInventoryQuantity(epicPick) > 0) {	//...and the player has a pickaxe...
        			quantity = fixQuantityUp(quantity, ironOre);				//fix the specified quantity to avoid overfilling inventory or taking more from room than there is
        			player.addItem(iron, quantity);								//add that much iron to the player's inventory
        			currentRoom.takeItem(ironOre, quantity);					//remove that much iron from the room
        			System.out.println("Mined " + quantity + " iron.");			//report
        		}
        		else {
        			System.out.println("You need a pickaxe!");					//If player lacks a pick, report
        		}
        	}
        	else if(item.equals(gold) || item.equals(goldOre)) {				//if item to mine is gold ore...
        		if(player.getInventoryQuantity(standardPick) + player.getInventoryQuantity(epicPick) > 0) {		//and the player has a non-basic pick
        			quantity = fixQuantityUp(quantity, goldOre);			//fix the specified quantity to avoid overfilling inventory or taking nonexistant items
        			player.addItem(gold, quantity);							//add the items to the player's inventory
        			currentRoom.takeItem(goldOre, quantity);				//remove said items from the room
        			System.out.println("Mined " + quantity + " gold.");		//report
        		}
        		else if(player.getInventoryQuantity(basicPick) + player.getInventoryQuantity(standardPick) + player.getInventoryQuantity(epicPick) == 0) {
        			System.out.println("You need a pickaxe");				//If the player lacks a pickaxe, report
        		}
        		else {
        			System.out.println("You need a better pickaxe!");		//If the player only has a basic pick, report
        		}
        	}
        	else {
        		System.out.println("You can't mine that!");					//If the specified item is unminable, report
        	}
    	}
    	else {
    		System.out.println("You can't mine that!");						//If specified item does not exist, report as unminable
    	}
    }
    
    private boolean tunnel(Command command) {
    	String direction = command.getQualifierWord();
    	boolean quitOrNot = false;
    	int X = -1;											//find the coordinates of the current room
    	int Y = -1;
    	int H = -1;
    	for(int h = 0; h < mineScale; h++) {
    		for(int x = 0; x < mineScale; x++) {
    			for(int y = 0; y < mineScale; y++) {
    				if(currentRoom.equals(mine[x][y][h])) {
    					X = x;
    					Y = y;
    					H = h;
    				}
    			}
    		}
    	}
    	if(H!=-1) {						//if not in the mine...
        	Room nextRoom = currentRoom.getExit(direction);
        	if(player.getInventoryQuantity(epicPick) > 0) {		//and if the player has an epic pickaxe
	    		if(nextRoom == null) {			//...and if there is no exit in the specified direction
	    			if(direction.equals("up") && H>0) {			//using the direction specified and avoiding digging in an impossible direction...
	    				String direction2 = "down";
	    				dig(command, direction2, X, Y, H-1);					//add a set of exits between the current room  and the room in the exit specified, and go to that room
	    			}
	    			else if(direction.equals("down") && H < mineScale - 1) {
	    				String direction2 = "up";
	    				dig(command, direction2, X, Y, H+1);
	    			}
	    			else if(direction.equals("N") && Y < mineScale - 1) {
	    				String direction2 = "S";
	    				dig(command, direction2, X, Y+1, H);
	    			}
	    			else if(direction.equals("E") && X < mineScale - 1) {
	    				String direction2 = "W";
	    				dig(command, direction2, X+1, Y, H);
	    			}
	    			else if(direction.equals("W") && X > 0) {
	    				String direction2 = "E";
	    				dig(command, direction2, X-1, Y, H);
	
	    			}
	    			else if(direction.equals("S") && Y > 0) {
	    				String direction2 = "N";
	    				dig(command, direction2, X, Y-1, H);
	    			}
	    			else {
	    				System.out.println("You can't tunnel that way!");		//if the specified exit to tunnel is invalid, report
	    			}
	    		    if(Math.random() < .1) {												//give an approx. 10% chance of a cave in when tunneling
	    		    	caveIn();
	    		    }
	    		}
	    		else {
	    			System.out.println("The way is already open!");				//if the way to go is already open, report, and go that way
	    			goRoom(command);
	    		}
        	}
        	else {
        		System.out.println("You need a better pickaxe!");
        	}
    	}
    	else {
    		System.out.println("You can't tunnel here!");					//if not in the mine, report
    	}
    
    return quitOrNot;
    }

    //grab a number of items, if available
    private void grabItem(Command command) {
    	Item item = getItem(command);
    	if(item!=null) {
        	if((item.equals(iron) || item.equals(ironOre)) && currentRoom.getItemQuantity(iron) == 0 && currentRoom.getItemQuantity(ironOre) > 0) {			//If all iron ore in room is unmined, tell player it can't just be grabbed
        		System.out.println("It's stuck in the rock!");
        	}
        	else if((item.equals(gold) || item.equals(goldOre)) && currentRoom.getItemQuantity(gold) == 0 && currentRoom.getItemQuantity(goldOre) > 0) {		//If all gold ore in room is unmined, tell player it can't just be grabbed
        		System.out.println("It's stuck in the rock!");
        	}
        	else if(currentRoom.equals(tavern) || currentRoom.equals(store)) {	//If in tavern or store, don't allow stealing
        		System.out.println("No Stealing!");									//(items dropped in store/tavern are claimed by owners)
        	}
        	else {																//If location/item check out...
        		int quantity = command.getAmount();								//set quantity according to command
        		quantity = fixQuantityUp(quantity, item);
        		if(quantity != 0) {														//if grabbing anything, report
            		System.out.println("Picked up " + quantity + " " + item.getName(quantity) + ".");
        		}
        		currentRoom.takeItem(item, quantity);									//remove items from room
        		player.addItem(item, quantity);											//add items to player inventory
        	}
    	}
    	else {																			//if inputted item does not exist, report
    		System.out.println("That's not an item!");
    	}
    }
    
    //drop specified number of items
    private void dropItem(Command command) {
    	Item item = getItem(command);
    	if(item != null) {								//if the inputted item exists...
    		int quantity = command.getAmount();
        	if(quantity != -1) {						//...if all inputted...
        		quantity = player.takeItem(item, quantity);		//...remove all of that item from player's inventory
        		currentRoom.putItem(item, quantity);			//..and add those items to the room
        	}
        	else {										//if a number inputted...
        		quantity = player.takeItem(item, player.getInventoryQuantity(item));	//remove that number of items from the player's inventory (don't take more than is there)
        		currentRoom.putItem(item, player.getInventoryQuantity(item));			//add to the room the number of items taken from the player
        	}
        	System.out.println("Dropped " + quantity + " " + item.getName(quantity) + ".");		//report
    	}
    	else {
    		System.out.println("That's not an item!");		//if the inputted item does not exist, report
    	}
    }
    
    //buy specified number of items
    private void buyItem(Command command) {
    	if(currentRoom.equals(tavern) || currentRoom.equals(store)) {		//if you are in the tavern or the store, allow buying
    		Item item = getItem(command);
    		if(item != null) {												//if the item is actually an item...
	    		int quantity = command.getAmount();								//...fix quantity to avoid overfilling inventory or removing too much from room
	    		
	    		quantity = fixQuantityUp(quantity, item);
	    		System.out.println(quantity + " " + item.getName(quantity) + " will cost $" + quantity*item.getCost() + ".");	//report cost
	    		if(player.getCash() < quantity*item.getCost()) {				//...report if player doen't have enough money
	    			System.out.println("You don't have enough cash!");
	    		}
	    		else {															//otherwise...
	    			player.setCash(player.getCash() - quantity*item.getCost());							//...take the money from the player
	    			currentRoom.takeItem(item, quantity);												//remove the item from the room
	    			player.addItem(item, quantity);														//add the item to the player's inventory
	    			System.out.println("Bought " + quantity + " " + item.getName(quantity) + ".");		//report
	    			System.out.println(player.getInventoryString());									//display the player's inventory
	    		}
	    	}
	    	else {												//report if inputted "item" is not an item
	    		System.out.println("That's not an item!");
	    	}
    	}
    	else {													//report if the player is not in the tavern or store(and thus cannot buy stuff)
    		System.out.println("You can't buy stuff here!");
    	}
    }
    
    //sell specified number of items
    private void sellItem(Command command) {
    	Item item = getItem(command);
    	if(item != null) {								//if the inputted item exists
    		int quantity = command.getAmount();
        	if(currentRoom.equals(store)) {			//if the player is in the store...
        		if(quantity != -1) {					//if inputted quantity is not all(-1)
        			quantity = player.takeItem(item, quantity);		//take the specified number of items from the player's inventory (don't take more than is there)
        			currentRoom.putItem(item, quantity);			//add to the room the number of items taken from the player
        			player.setCash(player.getCash()+item.getCost()*quantity);		//give the player the money for the items they sold
        		}
        		else {																		//if inputted quantity is "all"
        			quantity = player.takeItem(item, player.getInventoryQuantity(item));	//remove all of given item from player's inventory
        			currentRoom.putItem(item, quantity);									//remove that many items from room
        			player.setCash(player.getCash()+item.getCost()*quantity);				//give player the money for the items they sold
        		}
        		System.out.println("Sold " + quantity +" " + item.getName(quantity) + ".");		//report
        		System.out.println(player.getInventoryString());								//show player inventory
        	}
        	else {
        		System.out.println("You can't sell stuff here!");		//if not in the store, report
        	}
    	}
    	else {
    		System.out.println("That's not an item!");					//if inputted "item" does not exist, report
    	}
    }
    
    //print out something the player asks for
    private void show(Command command) {
    	if(!command.hasQualifierWord()) {							//If no qualifier word is given, ask for one
    		System.out.println("Show what?");
    	}
    	else if(command.getQualifierWord().equals("inventory")) {	//If input = "show inventory", print out the player's inventory string
    		System.out.println(player.getInventoryString());
    	}
    	else {
    		System.out.println("Can't show that!");					//If invalid qualifier, report.
    	}
    }
    
    //prints help text, including a list of command words, instructions, and example commands
    private void printHelp() {
        System.out.println("Your command words are:");
        parser.showCommands();
        System.out.println();
        System.out.println("To enter a command and do something in the game, enter one of the above command words...");
        System.out.println("...followed by a number (ex: 12), all, or no number...");
        System.out.println("...and possibly followed by a further qualifier, usually an item name.");
        System.out.println();
        System.out.println("Example Commands:");
        System.out.println("\t go to Main Street \t go N");
        System.out.println("\t grab dollar \t\t mine 3 iron ore");
        System.out.println("\t buy 2 ales \t\t sell all gold");
        System.out.println("\t tunnel W \t\t board train");
        System.out.println("\t show inventory \t quit");
    }
    
    //quit game if "quit" was entered with no second word
    private boolean quit(Command command) {
        if(command.hasQualifierWord()) {
            System.out.println("If you want to quit the game, just input \"quit\"");
            
            return false;
        }
        else
            return true;  // signal that we want to quit
    }
    
    private boolean shout() {
    	boolean quitOrNot = false;
    	boolean inMine = false;									//check if player is in the mine
    	for(int h = 0; h < mineScale; h++) {
        	for(int x = 0; x < mineScale; x++) {
        		for(int y = 0; y < mineScale; y++) {
        			if(currentRoom.equals(mine[x][y][h])) {
        				inMine = true;
        			}
        		}
        	}
        }
    	if(inMine == true) {								//if so, report shouting and trigger a cave in!
    		System.out.println("You shout as loud as you can.");
    		quitOrNot = caveIn();
    	}
    	else {												//if not, report shouting
    		System.out.println("You shout as loud as you can. You get some weird looks.");
    	}
    	return quitOrNot;
    }
    
    //board the train to win the game, if you have a ticket and are at the train station
    private boolean board(Command command) {
    	boolean quitOrNot = false;
    	if(currentRoom.equals(trainStation)) {					//If currently in the train station...
    		if(player.getInventoryQuantity(ticket) > 0) {			//and player has a ticket...
        		System.out.println("You board the train to start a new life in California.");
        		System.out.println("Congradulations! You won the game!");
        		quitOrNot = true;
        	}
    		else {													//If player lacks a ticket, report
    			System.out.println("You need a ticket!");
    		}
    	}
    	else {														//If is not in the train station, report
    		System.out.println("You need to be at the Train Station!");
    	}
    	return quitOrNot;
    }
    
    //fixes quantities for going from room to player's inventory
    private int fixQuantityUp(int quantity, Item item) {
    	if(quantity > currentRoom.getItemQuantity(item) || quantity == -1) {	//if all (-1) inputted or too few items in room...
    		quantity = currentRoom.getItemQuantity(item);							//...set quantity to amount of item in room
    	}
    	if(quantity + player.getInventorySum() > player.getInventorySize()) {	//next, if adding quantity will overfill inventory...
    		quantity = player.getInventorySize() - player.getInventorySum();	//set quantity to merely fill inventory
    	}
    	return quantity;
    }
    
    //adds a set of exits between mine rooms
    private void dig(Command command, String direction2, int X, int Y, int H) {
    	String direction = command.getQualifierWord();
		currentRoom.setExit(direction, mine[X][Y][H]);				//add an exit from the current room to the inputted location
		mine[X][Y][H].setExit(direction2, currentRoom);				//add an exit from the inputted location to the current room
		System.out.println("Tunneled " + direction + ".");			//report
		goRoom(command);											//go to the inputted location
    }
    
    //triggers a cave in
    private boolean caveIn() {
    	boolean shutIn = false;
    	currentRoom.blockAnExit();										//block an exit
    	for(int i = 0; i < (int)Math.round(Math.random()*4); i++) {		//block up to four more exits
    		currentRoom.blockAnExit();
    	}
    	System.out.println("Cave In!");									//report cave in and new room conditions
    	System.out.println(currentRoom.getLongDescription());
    	if(currentRoom.getExitCount() == 0) {							//if player is trapped, report and end game (return true)
    		shutIn = true;
    		System.out.println("You are Trapped! You die.");
    	}
    	return shutIn;
    }
    
    //return the item specified by a command
    public Item getItem(Command command) {
    	Item item = null;
    	String itemName = command.getItemName();
    	for(int i = 0; i < itemList.size(); i++) {		//checking the entire itemlist...
    		if(itemList.get(i).getName(1).equals(itemName) || itemList.get(i).getName(2).equals(itemName)) {		//if the inputted itemName is the same as the name of an item (singular or plural)...
    			item = itemList.get(i);																				//return that item
    		}
    	}
    	return item;
    }
    
    //returns whether something is an item or not
    public boolean isItem(Command command) {
    	if(!getItem(command).equals(null)) {		//if the item does not exist, returns 'not an item'.
    		return true;
    	}
    	else {
    		return false;
    	}
    }
}
