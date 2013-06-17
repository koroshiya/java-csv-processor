package com.carr.mitchell.zyxba_gui;

import java.awt.Dimension;
import java.awt.event.ActionListener;
import java.io.File;

import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import com.carr.mitchell.text_to_other.CSVDecoder;
import com.carr.mitchell.zyxba_gui.listeners.ButtonListener;


public class GUI extends JFrame{
	
	private static final long serialVersionUID = 1L;

	private JTable table;

	public static final String strMenuFile = "File";
	public static final String openCsv = "Open CSV File";
	public static final String exportXML = "Export to XML File";
	public static final String exportJSON = "Export to JSon File";
	public static final String exportMSQL = "Export to MySQL Database";
	public static final String exportPostgres = "Export to Postgres Database";
	public static final String exportSQLServer = "Export to SQLServer Database";

	private File csv;
	
	public GUI() {
		super("Java CSV Decoder");
		instantiateGUI();
	}

	/**
	 * @return Returns the CSV file held by this class
	 */
	public File getCSV() {
		return this.csv;
	}

	/**
	 * @param csv CSV file from which data will be extracted
	 */
	public void setCSV(File csv) {
		this.csv = csv;
	}

	/**
	 * Reads the held CSV file into this control's data table
	 */
	public void readCSV() {

		if (this.csv != null) {
			Object[][] data = CSVDecoder.CSVToTwoDimensionalArray(this.csv);
			if (data.length == 0 || data[0].length == 0){
				System.out.println("No valid data in file");
				return;
			}

			table.setModel(new DefaultTableModel(data.length, data[0].length));

			for (int row = 0; row < data.length; row++) {
				for (int col = 0; col < data[row].length && col < data[0].length; col++) {
					table.getModel().setValueAt(data[row][col], row, col);
				}
			}
		}

	}

	/**
	 * Instantiates GUI
	 */
	private void instantiateGUI() {

		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		String[] cmdArray = {openCsv, exportXML, exportJSON, exportMSQL, exportPostgres, exportSQLServer};
		JMenuItem[] menuItemArray = new JMenuItem[6];

		this.setMinimumSize(new Dimension(600, 300));

		JMenuBar menuBar = new JMenuBar();
		JMenu menu = new JMenu(strMenuFile);
		ActionListener actListener = new ButtonListener(this);
		for (int i = 0; i < menuItemArray.length; i++){
			menu.add(createMenuItem(menuItemArray[i], cmdArray[i], actListener));
		}
		menuBar.add(menu);

		this.setJMenuBar(menuBar);

		table = new JTable(testData(), testHeaders());
		this.add(new JScrollPane(table));

	}

	/**
	 * @return Object[][] of test data to populate the JTable with
	 */
	private Object[][] testData() {

		Object[][] data = { { "Kathy", "Smith", "Snowboarding", 5, false },
				{ "John", "Doe", "Rowing", 3, true },
				{ "Sue", "Black", "Knitting", 2, false },
				{ "Jane", "White", "Speed reading", 20, true },
				{ "Joe", "Brown", "Pool", 10, false } };

		return data;

	}

	/**
	 * @return String[] of test column headers
	 */
	private String[] testHeaders() {

		String[] columnNames = { "First Name", "Last Name", "Sport",
				"# of Years", "Vegetarian" };

		return columnNames;

	}

	/**
	 * @param item JMenuItem to set
	 * @param command String to set as the JMenuItem's text
	 * @param actListener ActionListener for JMenuItem click events
	 * @return Resulting JMenuItem
	 */
	private JMenuItem createMenuItem(JMenuItem item, String command, ActionListener actListener) {

		item = new JMenuItem();
		item.setText(command);
		item.addActionListener(actListener);

		return item;

	}

	/**
	 * Makes component visible
	 */
	public void display() {
		this.setVisible(true);
	}

	/**
	 * @return Data from CSV file as Object[][]
	 * If no CSV file has been set, returns null.
	 */
	public Object[][] getData() {
		return this.csv != null ? CSVDecoder.CSVToTwoDimensionalArray(this.csv) : null;
	}
	
}
