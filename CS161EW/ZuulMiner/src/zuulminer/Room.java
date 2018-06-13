//Room Class of Text Adventure based on Zuul Example Code (https://drive.google.com/file/d/0B_WVGgU46DSKVHBySGN4a1NFWlk/view?usp=sharing)
//stores information for various rooms
//by: Alexander Dyall
//2 Jan. 2018

package zuulminer;

import java.util.ArrayList;

class Room {
	
	//Room Attributes (Global Variables)
    private String description;						//Description of Room
    private ArrayList<Room> exitRooms;				//Rooms that player can exit to from room
    private ArrayList<String> exitStrings;			//Strings associated with exits
    private ArrayList<Item> items;					//Items in the room
    private ArrayList<Integer> itemQuantities;		//Quantities of each item

    //Create a room described by "description," initially w/o exits.
    public Room(String description) {
        this.description = description;
        exitRooms = new ArrayList<Room>();
        exitStrings = new ArrayList<String>();
        items = new ArrayList<Item>();
        itemQuantities = new ArrayList<Integer>();
        }

    //gets the number of exits the room has
    public int getExitCount() {
    	return exitRooms.size();
    }
    
    //Define an exit from this room.
    public void setExit(String direction, Room neighbor) {
    	if(!exitStrings.contains(direction)) {
        	exitRooms.add(neighbor);
        	exitStrings.add(direction);
    	}
    }
    
    //Return Room associated with inputted String direction
    public Room getExit(String direction) {
    	Room returnRoom = null;
    	for(int i = 0; i < exitStrings.size(); i++) {
	        if(exitStrings.get(i).equals(direction)) {
	        	returnRoom = exitRooms.get(i);
	        }
        }
        return returnRoom;
    }
    
    //removes the exit to a specified room
    public void removeExit(Room room) {
    	if(exitRooms.contains(room)) {					//if the room in question is on the exit list
    		int index = exitRooms.indexOf(room);		
    		exitRooms.remove(index);					//remove it from the exit list
    		exitStrings.remove(index);
    	}
    }
    
    //blocks a random exit
    public void blockAnExit() {
    	if(exitStrings.size() > 0) {											//if there are any exits
        	int index = (int)Math.round((exitRooms.size()-1)*Math.random());	//pick a random index of exit to remove
        	String direction = exitStrings.get(index);							//store name of exit at index
        	getExit(direction).removeExit(this);								//remove this room from the exit list of the room connected by the exit to be removed
        	exitStrings.remove(index);											//remove the the connected room from this room's exit list.
        	exitRooms.remove(index);
    	}
    }
    
    //puts the entered quantity of items in the room
    public void putItem(Item item, int quantity) {
    	if(quantity > 0) {
	    	if(!items.contains(item)) {		//If item not already present in room
	    		items.add(item);							//add item to room's item list
	    		itemQuantities.add(quantity);				//add quantity to room's quantity list
	    	}
	    	else {							//otherwise, update quantity of item
	    		itemQuantities.set(items.indexOf(item), itemQuantities.get(items.indexOf(item)) + quantity);
	    	}
    	}
    }
    
    //removes an item form the room
    public void takeItem(Item item, int quantity) {
    	int index = -1;
    	for(int i = 0; i < items.size(); i++) {		//if item is in Room, set index equal to said item's index (in ArrayList items)
    		if(items.get(i).equals(item)) {
    			index = i;
    		}
    	}
    	if(index != -1) {									//if item is present in Room...
        	if(quantity == itemQuantities.get(index)) {			//if quantity being removed is the same as number of that item present
        		items.remove(index);							//remove item entirely
        		itemQuantities.remove(index);
        	}
        	else {											//otherwise, remove specified quantity of that item
        		itemQuantities.set(index, itemQuantities.get(index) - quantity);
        	}
    	}
    	else{												//if item is not present, report
    		System.out.println("There's no " + item.getName(2) + " here!");
    	}
    }
    
    //returns the quantity of the inputted item (associated by same ArrayList index)
    public int getItemQuantity(Item item) {
    	int reint = 0;
    	if(items.contains(item)) {
    		reint = itemQuantities.get(items.indexOf(item));
    	}
    	return reint;
    }

    //Return the description of the room (defined in constructor)
    public String getShortDescription() {
        return description;
    }

    /*
     * Return a long description of this room, in the form:
     *     You are in the kitchen.
     *     Exits: N, W, to the Dining Room
     *     Items: Frying Pan, Banana, Oven Gloves
     */
    public String getLongDescription() {
        return "You are " + description + ".\n" + getExitString() + "\n" + getItemString();
    }

    //Return a String listing the Exits from this Room
    private String getExitString() {
        String reString = "Exits: ";								//label as "Exits: "
        for(int i = 0; i < exitStrings.size(); i++) {				//add all exit strings, spaced out
        	if(!exitStrings.get(i).equals("die in a hole")) {		//(hide exit "die in a hole)
        	reString += "   " + exitStrings.get(i);
        	}
        }
        return reString;
    }
    
    //Return String listing the items in this Room
    private String getItemString() {
    	String reString = "Items: ";		//label as "Items: "
    	if(!items.isEmpty()) {				//add the name of each item to the list, space out
	        for(int i = 0; i < items.size(); i++) {
	        	reString += "   " + itemQuantities.get(i) + " " + items.get(i).getName(itemQuantities.get(i));
	        }
    	}
    	return reString;
    }


}