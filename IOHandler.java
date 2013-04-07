import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Scanner;

import javax.swing.JOptionPane;

/**
 * Handles interaction between outside files and the rest of the program.
 * Namely, reading from menu file and writing to logfile
 * 
 * @author Chae Jubb
 * @version 1.0
 * 
 */
public class IOHandler {

	// max characters on button
	private static final int substringVal = 30;

	private Scanner input;

	/**
	 * Empty Constructor
	 */
	public IOHandler() {
	}

	/**
	 * Reads menu from specified file and converts to Item array
	 * 
	 * @param location
	 *            Location to check for valid menu source file
	 * @return Item array created from menu source file
	 */
	public Item[] getMenu(String location) {

		this.input = null;

		// set-up Scanner
		try {
			this.input = new Scanner(new File(location));

		} catch (FileNotFoundException e) {
			JOptionPane.showMessageDialog(null,
					"File Not Found.  Now Exiting.", "ERROR!",
					JOptionPane.ERROR_MESSAGE);
			System.exit(0);
		}

		ArrayList<Item> menuList = new ArrayList<Item>();
		String fullName, shortName;
		double price;

		// create items. try--catch ensures file format is correct
		try {
			while (this.input.hasNext()) {
				fullName = this.input.nextLine();
				price = Double.parseDouble(this.input.nextLine());

				int itemLength = fullName.length();
				if (itemLength < substringVal) {
					shortName = fullName;
				} else {
					shortName = fullName.substring(0, substringVal - 4) + "...";
				}

				menuList.add(new Item(shortName, fullName, price));
			}

		} catch (Exception e) {
			JOptionPane.showMessageDialog(null,
					"Improperly Formatted File. Consult README. Now Exiting.",
					"ERROR!", JOptionPane.ERROR_MESSAGE);
			System.exit(0);
		}

		// converts ArrayList into array and returns
		Item[] itemList = menuList.toArray(new Item[menuList.size()]);
		return itemList;
	}

	/**
	 * Skeleton for writing a String to the logFile
	 * 
	 * @param message
	 *            String to be appended to logFile
	 */
	public void writeToLog(String message) {
		PrintWriter output = null;

		try {
			output = new PrintWriter(new FileWriter("orderLog.log", true));
		} catch (IOException e) {

			// if file is write-protected, try secondary file
			try {
				output = new PrintWriter(new FileWriter(
						"LOG-alsoPleaseMakeorderLogDOTlogAvailabletoWrite.log",
						true));
			} catch (IOException ex) {
				e.printStackTrace();
				ex.printStackTrace();
			}
		}

		output.println(message);
		output.close();
	}

}