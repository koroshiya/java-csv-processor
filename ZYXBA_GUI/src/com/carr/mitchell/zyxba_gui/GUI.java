package com.carr.mitchell.zyxba_gui;

import java.io.File;

import javax.swing.JButton;
import javax.swing.JFrame;

public class GUI {
	
	private JFrame frame;
	private JButton btnOpen;
	private JButton btnExportToXML;
	private JButton btnExportToJSON;
	private JButton btnExportToMSQL;

	public static String openCsv = "Open CSV File";
	public static String exportXML = "Export to XML File";
	public static String exportJSON = "Export to JSon File";
	public static String exportMSQL = "Export to MySQL Database";
	
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
	
	private void instantiateGUI(){
		
		frame = new JFrame();
		
		btnOpen = new JButton(openCsv);
		btnExportToXML = new JButton(exportXML);
		btnExportToJSON = new JButton(exportJSON);
		btnExportToMSQL = new JButton(exportMSQL);
		
		OpenListener ol = new OpenListener(this);
		ExportListener el = new ExportListener(this);
		
		btnOpen.addActionListener(ol);
		btnExportToXML.addActionListener(el);
		btnExportToJSON.addActionListener(el);
		btnExportToMSQL.addActionListener(el);
		
	}
	
	public void display(){
		frame.setVisible(true);
	}
	
}
