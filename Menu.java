/**
 * Menu is an aggregation of menu Items. Created in such a way to only allow 1
 * instance, to keep menu constant throughout POS session
 * 
 * @author Chae Jubb
 * @version 1.0
 * 
 */
public class Menu {
	private static volatile boolean indicator = false;
	private static volatile Menu instance = null;

	public final Item[] itemList;

	/**
	 * Constructor. Only able to be called within class. Sets indicitor to
	 * prevent from being called a second time.
	 * 
	 * @param location
	 *            Location of menu source file
	 */
	private Menu(String location) {
		itemList = new IOHandler().getMenu(location);
		indicator = true;
	}

	/**
	 * Returns menu object. (In conjunction with constructor) Only creates Menu
	 * object until first successful completion of method.
	 * 
	 * @param menuLocation
	 *            Location of menu source file
	 * @return Static Menu object
	 */
	public static Menu getMenu(String menuLocation) {
		// create new object only if object has not already been successfully
		// created.
		if (indicator == false) {
			instance = new Menu(menuLocation);
		}
		return instance;
	}

	/**
	 * Find Item in Menu that matches supplied Item
	 * 
	 * @param input
	 *            Item for which match is being sought
	 * @return Item from Menu (complete with namings and price)
	 */
	public static Item findItem(Item input) {
		Menu menu = Menu.getMenu(null);

		for (Item item : menu.itemList) {
			if (item.isEqual(input)) {
				return item;
			}
		}
		return null; // only if no match
	}
}
