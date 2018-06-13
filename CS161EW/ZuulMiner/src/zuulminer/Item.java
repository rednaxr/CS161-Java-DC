package zuulminer;

//import java.lang.reflect.Method;

public class Item {

	//Item Attributes (Global variables)
	private String name;			//Name of Item
	private int cost;				//Cost of item
	private boolean isSPlural;		//is "s" used when referring to multiple of the item?
	
	//Constructor (create item w/ name, cost, and plural type)
	public Item(String name, int cost, boolean isSPlural) {
		this.setName(name);
		this.setCost(cost);
		this.isSPlural = isSPlural;
	}

	//returns the name of the object based on the quantity
	public String getName(int quantity) {
		String reString = name;
			if(quantity != 1 && isSPlural == true) {	//(if plural and item name uses "s" when plural, add s to itemName)
				reString += "s";
			}
		return reString;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getCost() {
		return cost;
	}

	public void setCost(int cost) {
		this.cost = cost;
	}

}
