import java.util.Date;
import java.sql.Timestamp;
import java.text.NumberFormat;
import java.util.ArrayList;

/**
 * Order aggregates Items in a particular order. It additionally provides total
 * price and logWrite String format
 * 
 * @author Chae Jubb
 * @version 1.0
 * 
 */
public class Order {

	private ArrayList<Item> foodList;
	private double totalPrice;

	/**
	 * Constructor. Defaults to no items at zero price
	 */
	public Order() {
		this.foodList = new ArrayList<Item>();
		this.totalPrice = 0;
	}

	/**
	 * Adds item to order. Updates total price
	 * 
	 * @param itm
	 *            Item to be added to Order
	 */
	public void addItem(Item itm) {
		this.foodList.add(itm);
		this.totalPrice += itm.getPrice();
	}

	/**
	 * Resets order to defaults (Blank order and no price)
	 */
	public void clearItems() {
		this.foodList.clear();
		this.totalPrice = 0;
	}

	/**
	 * Formats a string representation of the Item array in such a way that it
	 * is readable in a logfile.
	 * 
	 * @return String to be written to log file
	 */
	public String logWrite() {
		String output = "";

		for (Item itm : this.foodList) {
			output += itm.toString() + "\n";
		}

		NumberFormat formatter = NumberFormat.getCurrencyInstance();

		output += "\n TOTAL: " + formatter.format(this.totalPrice);
		output += "\n" + new Timestamp(new Date().getTime()) + "\n \n";

		return output;
	}

	/**
	 * Getter method for running total price
	 * 
	 * @return running total price
	 */
	public double getTotalPrice() {
		return this.totalPrice;
	}
}