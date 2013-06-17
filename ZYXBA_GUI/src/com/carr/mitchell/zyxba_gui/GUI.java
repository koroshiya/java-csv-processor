package com.carr.mitchell.zyxba_gui;

import java.awt.Dimension;
import java.awt.event.ActionListener;
import java.io.File;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import com.carr.mitchell.zyxba_gui.listeners.ButtonListener;
import com.carr.mitchell.zyxba_gui.listeners.ExportListener;
import com.carr.mitchell.zyxba_gui.listeners.OpenListener;

import text_to_other.CSVDecoder;

public class GUI {

	private JFrame frame;
	private JButton btnOpen;
	private JButton btnExportToXML;
	private JButton btnExportToJSON;
	private JButton btnExportToMSQL;
	private JButton btnExportToPostgres;
	private JButton btnExportToSQLServer;

	private JMenuBar menuBar;
	private JMenu menu;
	private JMenuItem menuItemOpen;
	private JMenuItem menuItemExportXML;
	private JMenuItem menuItemExportJSON;
	private JMenuItem menuItemExportMSQL;
	private JMenuItem menuItemExportPostgres;
	private JMenuItem menuItemExportSQLServer;

	private JTable table;
	private ActionListener actListener;

	public static final String strMenuFile = "File";
	public static final String openCsv = "Open CSV File";
	public static final String exportXML = "Export to XML File";
	public static final String exportJSON = "Export to JSon File";
	public static final String exportMSQL = "Export to MySQL Database";
	public static final String exportPostgres = "Export to Postgres Database";
	public static final String exportSQLServer = "Export to SQLServer Database";

	private File csv;

	public GUI() {
		instantiateGUI();
	}

	public File getCSV() {
		return this.csv;
	}

	public void setCSV(File csv) {
		this.csv = csv;
	}

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

	private void instantiateGUI() {

		frame = new JFrame();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		JButton[] btnArray = {btnOpen, btnExportToXML, btnExportToJSON, 
								btnExportToMSQL, btnExportToPostgres, btnExportToSQLServer};
		String[] cmdArray = {openCsv, exportXML, exportJSON, exportMSQL, exportPostgres, exportSQLServer};
		JMenuItem[] menuItemArray = {menuItemOpen, menuItemExportXML, menuItemExportJSON, 
									menuItemExportMSQL, menuItemExportPostgres, menuItemExportSQLServer};
		
		for (int i = 0; i < btnArray.length; i++){
			btnArray[i] = new JButton(cmdArray[i]);
		}

		OpenListener ol = new OpenListener(this);
		ExportListener el = new ExportListener(this);

		btnOpen.addActionListener(ol);
		
		for (int i = 1; i < btnArray.length; i++){
			btnArray[i].addActionListener(el);
		}

		frame.setMinimumSize(new Dimension(600, 300));

		menuBar = new JMenuBar();
		menu = new JMenu(strMenuFile);
		actListener = new ButtonListener(this);
		for (int i = 0; i < menuItemArray.length; i++){
			menu.add(createMenuItem(menuItemArray[i], cmdArray[i]));
		}
		menuBar.add(menu);

		frame.setJMenuBar(menuBar);

		table = new JTable(testData(), testHeaders());
		frame.add(new JScrollPane(table));

	}

	private Object[][] testData() {

		Object[][] data = { { "Kathy", "Smith", "Snowboarding", 5, false },
				{ "John", "Doe", "Rowing", 3, true },
				{ "Sue", "Black", "Knitting", 2, false },
				{ "Jane", "White", "Speed reading", 20, true },
				{ "Joe", "Brown", "Pool", 10, false } };

		return data;

	}

	private String[] testHeaders() {

		String[] columnNames = { "First Name", "Last Name", "Sport",
				"# of Years", "Vegetarian" };

		return columnNames;

	}

	private JMenuItem createMenuItem(JMenuItem item, String command) {

		item = new JMenuItem();
		item.setText(command);
		item.addActionListener(actListener);

		return item;

	}

	public void display() {
		frame.setVisible(true);
	}

	public JFrame getFrame() {
		return this.frame;
	}

	public Object[][] getData() {
		if (this.csv != null) {
			return CSVDecoder.CSVToTwoDimensionalArray(this.csv);
		} else {
			return null;
		}
	}

	public void storeCredentials(){
		
	}
	
}
