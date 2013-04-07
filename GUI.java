import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;

/**
 * Graphical User Interface for Point of Sale emulator. This class handles only
 * initialization and creation of the GUI structure
 * 
 * @author Chae Jubb
 * @version 1.0
 * 
 */
public class GUI {

	private GUIController controller;
	private JFrame mainGUI;
	private JPanel mainPane, posLeft;
	private JTextArea orderContents;
	private JButton clearOrder, submitOrder;
	private JTextField runningTotal;

	/**
	 * Empty Constructor.
	 */
	public GUI() {
	}

	/**
	 * Prompts user for menu source file
	 */
	public void initGUI() {
		String header = "Chae's Fancy POS GUI!";
		String message = "Continuing will allow selection of menu source file.";
		int n = JOptionPane.showConfirmDialog(null, message, header,
				JOptionPane.OK_CANCEL_OPTION);

		// continue to allow file to be chosen if "ok"
		if (n == JOptionPane.OK_OPTION) {
			JFileChooser fileChooser = new JFileChooser();
			FileNameExtensionFilter filter = new FileNameExtensionFilter(
					"Text Files", "txt");
			fileChooser.setFileFilter(filter);
			if (JFileChooser.APPROVE_OPTION == fileChooser.showOpenDialog(null)) {
				this.createGUI(fileChooser.getSelectedFile().getAbsolutePath());
			}

			// otherwise quit
		} else if (n == JOptionPane.CANCEL_OPTION) {
			System.exit(0);
		}
	}

	/**
	 * Creates GUI structure, including panels, buttons, and text areas
	 * 
	 * @param menuLocation
	 *            String location of the menu source file
	 */
	private void createGUI(String menuLocation) {
		this.controller = new GUIController(this);
		this.mainGUI = new JFrame("Chae's Fancy POS GUI!");
		this.mainPane = new JPanel(new GridLayout(1, 2));

		// create clear order and submit order buttons
		JPanel menuModifiers = new JPanel(new GridLayout(1, 2));

		this.clearOrder = new JButton("Clear Order");
		this.submitOrder = new JButton("Finished Order");
		this.clearOrder.setPreferredSize(new Dimension(0, 200));
		this.submitOrder.setPreferredSize(new Dimension(0, 200));
		this.clearOrder.addActionListener(this.controller);
		this.submitOrder.addActionListener(this.controller);
		menuModifiers.add(this.submitOrder);
		menuModifiers.add(this.clearOrder);

		// running list of order
		this.orderContents = new JTextArea();
		this.orderContents.setEditable(false);

		// running total price
		this.runningTotal = new JTextField();
		this.runningTotal.setEditable(false);
		this.runningTotal.setText("TOTAL: $0.00");

		// increase Font Size in running Total
		Font tempFont = this.runningTotal.getFont();
		tempFont = new Font(tempFont.getName(), tempFont.getStyle(), 30);
		this.runningTotal.setFont(tempFont);

		// increase Font Size in orderContents
		tempFont = this.orderContents.getFont();
		tempFont = new Font(tempFont.getName(), tempFont.getStyle(), 18);
		this.orderContents.setFont(tempFont);

		// make running order scrollable
		JScrollPane orderScroll = new JScrollPane(this.orderContents,
				JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
				JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);

		// set-up right-side GUI: clear/submit, order, price
		JPanel posRight = new JPanel(new BorderLayout());
		posRight.add(menuModifiers, BorderLayout.NORTH);
		posRight.add(orderScroll, BorderLayout.CENTER);
		posRight.add(this.runningTotal, BorderLayout.SOUTH);

		// load menu from file
		this.posLeft = this.controller.loadMenu(menuLocation, this.posLeft);

		// assemble GUI and frame
		this.mainPane.add(this.posLeft);
		this.mainPane.add(posRight);

		this.mainGUI.setContentPane(mainPane);
		this.mainGUI.setSize(1500, 1000);
		this.mainGUI.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.mainGUI.setVisible(true);
	}

	/**
	 * Getter method for textArea object
	 * 
	 * @return textArea object
	 */
	public JTextArea getTextArea() {
		return this.orderContents;
	}

	/**
	 * Getter method for runningTotal object
	 * 
	 * @return runningTotal object
	 */
	public JTextField getRunningTotalArea() {
		return this.runningTotal;
	}

	/**
	 * Getter method for clear button
	 * 
	 * @return clearButton
	 */
	public JButton getClearButton() {
		return this.clearOrder;
	}

	/**
	 * Getter method for submit button
	 * 
	 * @return submitButton
	 */
	public JButton getSubmitButton() {
		return this.submitOrder;
	}
}