import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.NumberFormat;

import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

/**
 * Controller for GUI.  This handles action responses as well as populating the reserved menu space.
 * @author Chae Jubb
 * @version 1.0
 *
 */
public class GUIController implements ActionListener {

	private final GUI gui;
	private Order order;
	private IOHandler io;

	/**
	 * Constructor for GUI Controller
	 * 
	 * @param gui
	 *            GUI which this controller controls
	 */
	public GUIController(GUI gui) {
		this.gui = gui;
		this.order = new Order();
		this.io = new IOHandler();
	}

	/**
	 * Handles responses to button clicks (clear, submit, menu item) and directs
	 * them to appropriate sub-methods
	 * 
	 * @param arg0
	 *            Action Event triggering this method
	 */
	@Override
	public void actionPerformed(ActionEvent arg0) {
		JTextArea textArea = this.gui.getTextArea();

		if (arg0.getSource().equals(this.gui.getClearButton())) {
			this.clear(textArea);
		} else if (arg0.getSource().equals(this.gui.getSubmitButton())) {
			this.submit(textArea);
		} else {
			// find menu item corresponding to button text
			JButton btnSrc = (JButton) arg0.getSource();
			Item itmSrc = Menu.findItem(new Item(btnSrc.getText()));

			// keeps GUI and order object up to date
			textArea.append(itmSrc.toString() + "\n");

			this.order.addItem(itmSrc);
			this.updateRunningTotal();
		}
	}

	/**
	 * Resets GUI and sub-objects and prepares for another order to be placed
	 * 
	 * @param textArea
	 *            Text Area in the GUI to be cleared
	 */
	private void clear(JTextArea textArea) {
		textArea.setText("");
		this.order.clearItems();
		this.updateRunningTotal();
	}

	/**
	 * Submits order by recording to log file and resetting for another order to
	 * be placed.
	 * 
	 * @param textArea
	 */
	private void submit(JTextArea textArea) {
		this.io.writeToLog(this.order.logWrite());

		JOptionPane.showMessageDialog(null, "Transaction recorded in logfile!",
				"Success!", JOptionPane.INFORMATION_MESSAGE);

		this.clear(textArea);
	}

	/**
	 * Updates running value of total cost. To be called after each button
	 * action.
	 */
	private void updateRunningTotal() {
		double totalPrice = this.order.getTotalPrice();
		NumberFormat formatter = NumberFormat.getCurrencyInstance();

		this.gui.getRunningTotalArea().setText(
				"TOTAL: " + formatter.format(totalPrice));
	}

	/**
	 * Creates menu formatting and fills menu skeleton with menu item buttons
	 * 
	 * @param fileLocation
	 *            Location of menu source file
	 * @param menuArea
	 *            JPanel area which is to be filled
	 * @return Menu-filled JPanel
	 */
	public JPanel loadMenu(String fileLocation, JPanel menuArea) {
		Menu menuList = Menu.getMenu(fileLocation);
		int itemCount = menuList.itemList.length;

		menuArea = new JPanel();

		// creates different arrangements based on number of menu items
		if (itemCount == 1) {
			menuArea.setLayout(new GridLayout(1, 1));
		} else if (itemCount == 2) {
			menuArea.setLayout(new GridLayout(2, 1));
		} else if (itemCount == 3) {
			menuArea.setLayout(new GridLayout(3, 1));
		} else if (itemCount == 4) {
			menuArea.setLayout(new GridLayout(2, 2));
		} else if (itemCount == 5 || itemCount == 6) {
			menuArea.setLayout(new GridLayout(3, 2));
		} else if (itemCount == 7 || itemCount == 8 || itemCount == 9) {
			menuArea.setLayout(new GridLayout(3, 3));
		} else if ((itemCount >= 10) && (itemCount <= 12)) {
			menuArea.setLayout(new GridLayout(4, 3));
		} else if ((itemCount >= 13) && (itemCount <= 14)) {
			menuArea.setLayout(new GridLayout(5, 3));
		} else if ((itemCount >= 15) && (itemCount <= 18)) {
			menuArea.setLayout(new GridLayout(6, 3));
		} else if ((itemCount >= 19) && (itemCount <= 21)) {
			menuArea.setLayout(new GridLayout(7, 3));
		} else {
			// If >21 menuItems, set up scroll bar
			int menuHeight = (int) itemCount / 3;
			if ((itemCount % 3) != 0) {
				menuHeight++;
			}

			JPanel innerMenu = new JPanel(new GridLayout(menuHeight, 3));
			this.populateMenu(innerMenu, itemCount);

			JScrollPane scroll = new JScrollPane(innerMenu,
					JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
					JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
			menuArea.setLayout(new BorderLayout());
			menuArea.add(scroll, BorderLayout.CENTER);
		}

		// already done otherwise
		if (itemCount <= 21) {
			this.populateMenu(menuArea, itemCount);
		}

		return menuArea;
	}

	/**
	 * Fills menu skeleton with buttons for each menu item
	 * 
	 * @param panel
	 *            JPanel to be populated
	 * @param itemCount
	 *            Number of buttons to create in panel
	 */
	private void populateMenu(JPanel panel, int itemCount) {
		String itemName;
		JButton btn;

		for (int i = 0; i < itemCount; i++) {
			// short names go on buttons to help avoid overflow
			itemName = Menu.getMenu(null).itemList[i].getShortName();

			btn = new JButton(itemName);
			btn.setPreferredSize(new Dimension(0, 100));

			btn.addActionListener(this);

			panel.add(btn);
		}

	}
}
