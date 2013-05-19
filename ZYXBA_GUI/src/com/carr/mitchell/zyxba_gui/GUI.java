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
import text_to_other.CSVDecoder;

public class GUI {
	
	private JFrame frame;
	private JButton btnOpen;
	private JButton btnExportToXML;
	private JButton btnExportToJSON;
	private JButton btnExportToMSQL;
	private JButton btnExportToPostgres;
        
        private JMenuBar menuBar;
        private JMenu menu;
        private JMenuItem menuItemOpen;
        private JMenuItem menuItemExportXML;
        private JMenuItem menuItemExportJSON;
        private JMenuItem menuItemExportMSQL;
        private JMenuItem menuItemExportPostgres;
        
        private JTable table;
        private ActionListener actListener;

	public static final String strMenuFile = "File";
	public static final String openCsv = "Open CSV File";
	public static final String exportXML = "Export to XML File";
	public static final String exportJSON = "Export to JSon File";
	public static final String exportMSQL = "Export to MySQL Database";
	public static final String exportPostgres = "Export to Postgres Database";
	
	private File csv;
	
	public GUI(){
		instantiateGUI();
	}
	
	public File getCSV(){
		return this.csv;
	}
	
	public void setCSV(File csv){
		this.csv = csv;
	}
        
        public void readCSV(){
            
            if (this.csv != null){
                Object[][] data = CSVDecoder.CSVToTwoDimensionalArray(this.csv);

                table.setModel(new DefaultTableModel(data.length, data[0].length));
                
                for (int row = 0; row < data.length; row++){
                    for (int col = 0; col < data[row].length; col++){
                        table.getModel().setValueAt(data[row][col], row, col);
                    }
                }
            }
            
        }
	
	private void instantiateGUI(){
		
		frame = new JFrame();
                frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		btnOpen = new JButton(openCsv);
		btnExportToXML = new JButton(exportXML);
		btnExportToJSON = new JButton(exportJSON);
		btnExportToMSQL = new JButton(exportMSQL);
		btnExportToPostgres = new JButton(exportPostgres);
		
		OpenListener ol = new OpenListener(this);
		ExportListener el = new ExportListener(this);
		
		btnOpen.addActionListener(ol);
		btnExportToXML.addActionListener(el);
		btnExportToJSON.addActionListener(el);
		btnExportToMSQL.addActionListener(el);
		btnExportToPostgres.addActionListener(el);
                
                frame.setMinimumSize(new Dimension(600, 300));
                
                menuBar = new JMenuBar();
                menu = new JMenu(strMenuFile);
                actListener = new ButtonListener(this);
                menu.add(createMenuItem(menuItemOpen, openCsv));
                menu.add(createMenuItem(menuItemExportXML, exportXML));
                menu.add(createMenuItem(menuItemExportJSON, exportJSON));
                menu.add(createMenuItem(menuItemExportMSQL, exportMSQL));
                menu.add(createMenuItem(menuItemExportPostgres, exportPostgres));
                menuBar.add(menu);
                
                frame.setJMenuBar(menuBar);
                
                table = new JTable(testData(), testHeaders());
                frame.add(new JScrollPane(table));
		
	}
        
        private Object[][] testData(){

            Object[][] data = {
                {"Kathy", "Smith",
                 "Snowboarding", 5, false},
                {"John", "Doe",
                 "Rowing", 3, true},
                {"Sue", "Black",
                 "Knitting", 2, false},
                {"Jane", "White",
                 "Speed reading", 20, true},
                {"Joe", "Brown",
                 "Pool", 10, false}
            };
            
            return data;
            
        }
        
        private String[] testHeaders(){

            String[] columnNames = {"First Name",
                        "Last Name",
                        "Sport",
                        "# of Years",
                        "Vegetarian"};
            
            return columnNames;
            
        }
        
        private JMenuItem createMenuItem(JMenuItem item, String command){
            
            item = new JMenuItem();
            item.setText(command);
            item.addActionListener(actListener);
            
            return item;
            
        }
	
	public void display(){
		frame.setVisible(true);
	}
        
        public JFrame getFrame(){
            return this.frame;
        }
        
        public Object[][] getData(){
            if (this.csv != null){
                return CSVDecoder.CSVToTwoDimensionalArray(this.csv);
            }else{
                return null;
            }
        }
	
}
