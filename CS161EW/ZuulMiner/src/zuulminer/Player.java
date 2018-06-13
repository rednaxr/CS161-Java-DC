package zuulminer;

import java.util.ArrayList;

public class Player {
	
	//Global Variables
	private int cash;									//Stores how much money player has
	private int inventorySize;							//Stores maximum number of items allowed in player's inventory
	private ArrayList<Item> inventory;					//Stores items in inventory
	private ArrayList<Integer> inventoryQuantities;		//Stores quantities of each such item
	
	//Constructor (create player with starting cash and inventory size)
	public Player(int cash, int inventorySize) {
		inventory = new ArrayList<Item>();
		inventoryQuantities = new ArrayList<Integer>();
		this.setInventorySize(inventorySize);
		this.setCash(cash);
	}

	//PUBLIC Methods
	public int getCash() {
		return cash;
	}

	public void setCash(int cash) {
		this.cash = cash;
	}

	public int getInventorySize() {
		return inventorySize;
	}
	
	public void setInventorySize(int inventorySize) {
		this.inventorySize = inventorySize;
	}
	
	//returns the quantity of an item in the player's inventory
	public int getInventoryQuantity(Item item) {
		int reint = 0;									//returns 0 by default
		for(int i = 0; i < inventory.size(); i++) {		//checks through the inventory for the item
			if(inventory.get(i).equals(item)){			//if item is present
				reint = inventoryQuantities.get(i);		//returns associated quantity
			}	
		}
		return reint;
	}
	
	//returns a string to describe the contents of the inventory
	public String getInventoryString() {	
		String reString = "Inventory($" + cash + "|"+ getInventorySum() + "/" + inventorySize + "):";		//Label as inventory, including items/total and cash
    	if(!inventory.isEmpty()) {																			//If the inventory isn't empty...
	        for(int i = 0; i < inventory.size(); i++) {														//...for each item in the inventory
	        	reString += "   " + inventoryQuantities.get(i) + " " + inventory.get(i).getName(inventoryQuantities.get(i));	//..add item and its quantity to string
	        }
    	}
		return reString;
	}
	
	//returns the number of items in the inventory
	public int getInventorySum(){
		int contentsSum = 0;
		for(int i = 0; i < inventory.size(); i++) {			//for each item in the inventory...
			contentsSum += inventoryQuantities.get(i);		//...add the quantity of that item to the total
		}
		return contentsSum;
	}
	
	//adds an item to the inventory
	public void addItem(Item item, int quantity) {
		if(item.getName(1).equals("dollar")) {				//If item is dollar, add amount picked up to player's cash
			cash += quantity;
		}
		else if(item.getName(1).equals("satchel")) {		//If item is satchel, add 20 to inventorySize for each satchel picked up
			inventorySize += 20*quantity;
		}
		else if(!inventory.contains(item)) {				//If the item is new, add to the inventory
			inventory.add(item);
			inventoryQuantities.add(quantity);
			}
		else {												//If the item is already in the inventory, update the quantity of that item
			inventoryQuantities.set(inventory.indexOf(item), inventoryQuantities.get(inventory.indexOf(item)) + quantity);
		}
		if(this.getInventorySum() == inventorySize) {		//If the inventory is full, report
			System.out.println("Your inventory is full!");
		}
	}
	
	//takes an item from the inventory
	public int takeItem(Item item, int quantity) {
		int itemsDropped = 0;								//stores number of items that actually leave the inventory (0 by default)
		int index = -1;
		for(int i = 0; i < inventory.size(); i++) {			//find index of the item
			if(inventory.get(i) == item) {
				index = i;
			}
		}
		if(index!=-1) {										//if the index was found...
			if(quantity < inventoryQuantities.get(index)) {										//if dropping/selling less of the item than is in the inventory
				inventoryQuantities.set(index, inventoryQuantities.get(index) - quantity);		//adjust the quantity in the inventory accordingly
				itemsDropped = quantity;
			}
			else {																				//if dropping/selling all of the item present in the inventory
				itemsDropped = inventoryQuantities.get(index);
				inventory.remove(index);														//remove item entirely from the inventory
				inventoryQuantities.remove(index);
			}
		}
		else {																					//If item is not in inventory, report
			System.out.println("You don't have any " + item.getName(2) + "!");
		}
		return itemsDropped;																	//report how many items were dropped
	}
}