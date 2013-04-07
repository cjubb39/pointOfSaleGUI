/**
 * Item object contains short name (to be displayed on button), long name
 * (everywhere else) and pricing information. These properties are immutable for
 * given Item object
 * 
 * @author Chae Jubb
 * @version 1.0
 */
public class Item {

	private final String shortName, fullName;
	private final double price;

	/**
	 * Constructor
	 * 
	 * @param shortName
	 *            Name for button
	 * @param fullName
	 *            Name for other uses
	 * @param price
	 *            Price of item
	 */
	public Item(String shortName, String fullName, double price) {
		this.shortName = shortName;
		this.fullName = fullName;
		this.price = price;
	}

	/**
	 * Constructor. Allows object to be created using only short name. (Used
	 * when comparing button to Menu items)
	 * 
	 * @param shortName
	 *            Name for button
	 */
	public Item(String shortName) {
		this.shortName = shortName;
		this.fullName = null;
		this.price = 0;
	}

	/**
	 * Getter method for short name
	 * 
	 * @return Short Name
	 */
	public String getShortName() {
		return this.shortName;
	}

	/**
	 * Getter method for full name
	 * 
	 * @return Full Name
	 */
	public String getFullName() {
		return this.fullName;
	}

	/**
	 * Getter method for price
	 * 
	 * @return Price
	 */
	public double getPrice() {
		return this.price;
	}

	/**
	 * Concatenates full name with price for String representation
	 * 
	 * @return full name concatentated with price
	 */
	public String toString() {
		return this.fullName + " " + this.price;
	}

	/**
	 * Checks if short names are equal
	 * 
	 * @param otherItem
	 *            Item to be compared against implicit parameter
	 * @return true if short names equivalent; false otherwise
	 */
	public boolean isEqual(Item otherItem) {
		if (this.shortName.equals(otherItem.getShortName())) {
			return true;
		} else {
			return false;
		}
	}
}
