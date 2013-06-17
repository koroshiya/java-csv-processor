package com.carr.mitchell.zyxba_gui.listeners;

import database.Credential;
import database.db_table.DBTable;
import database.db_table.MySQLTable;
import database.db_table.PostgresTable;
import database.db_table.SQLServerTable;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.sql.SQLException;

import javax.swing.JButton;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;

import com.carr.mitchell.zyxba_gui.GUI;
import com.carr.mitchell.zyxba_gui.prompt.CredentialEntry;
import com.carr.mitchell.zyxba_gui.prompt.HeaderPrompt;

import text_to_other.CSVFileExport;
import write_format.JSONFile;
import write_format.XMLFile;

public class ExportListener implements ActionListener {

	private final GUI parent;

	/**
	 * @param parent GUI from which to retrieve data to export
	 */
	public ExportListener(GUI parent) {
		this.parent = parent;
	}

	@Override
	public void actionPerformed(ActionEvent arg0) {

		File csv = this.parent.getCSV();

		if (csv != null) {

			String command;
			Object source = arg0.getSource();

			if (source instanceof JButton) {

				JButton btnSource = (JButton) source;
				command = btnSource.getText();

			} else if (source instanceof JMenuItem) {

				JMenuItem btnSource = (JMenuItem) source;
				command = btnSource.getText();

			} else {
				return;
			}

			if (command.equals(GUI.exportXML) || command.equals(GUI.exportJSON)) {
				exportFile(csv, command);
			} else {
				exportDatabase(command);
			}

		}else {
			JOptionPane.showMessageDialog(null, "You have not opened a CSV file", "No data to export", JOptionPane.WARNING_MESSAGE);
		}

	}
	
	/**
	 * @param csv File to export the contents of
	 * @param command Command illustrating what kind of file to export the data to
	 */
	private void exportFile(File csv, String command){
		
		CSVFileExport form;
		
		if (command.equals(GUI.exportXML)){
			form = new XMLFile(csv, "/home/koro/test.xml");
		}else{
			form = new JSONFile(csv, "/home/koro/test.json");
		}
		
		HeaderPrompt prompt = new HeaderPrompt(parent.getData()[0].length);
		prompt.display();
		String[] headers = prompt.getHeaders();
		if (headers == null){
			return;
		}
		
		form.writeToColumnDefinedFile(csv.getName(), headers);
		JOptionPane.showMessageDialog(null, "Export successful", "Export succeeded", JOptionPane.PLAIN_MESSAGE);
		
	}
	
	/**
	 * @param command Command illustrating what kind of database to export the data to
	 */
	private void exportDatabase(String command){
		
		String type = "";
		DBTable table;
		
		switch (command) {
			case GUI.exportMSQL:
				type = "MySQL";
				break;
			case GUI.exportSQLServer:
				type = "SQLServer";
				break;
			case GUI.exportPostgres:
				type = "Postgres";
				break;
			default:
				return;
		}
		
		CredentialEntry ce = new CredentialEntry();
		ce.display();
		Credential c = ce.getCredential();
		
		if (c == null){
			return;
		}
		
		String db = c.getDatabaseName();
		String name = c.getUserName();
		String password = c.getPassword();
		int port = c.getPort();
		String ip = c.getIP();
		
		switch (type) {
			case "Postgres":
				if (port == -1){
					if (ip.equals("")){
						table = new PostgresTable(db, name, password);
					}else{
						table = new PostgresTable(db, name, password, ip);
					}
				}else if (ip.equals("")){
					table = new PostgresTable(db, name, password, port);
				}else{
					table = new PostgresTable(db, name, password, port, ip);
				}
				break;
			case "MySQL":
				if (port == -1){
					if (ip.equals("")){
						table = new MySQLTable(db, name, password);
					}else{
						table = new MySQLTable(db, name, password, ip);
					}
				}else if (ip.equals("")){
					table = new MySQLTable(db, name, password, port);
				}else{
					table = new MySQLTable(db, name, password, port, ip);
				}
				break;
			case "SQLServer":
				if (port == -1){
					if (ip.equals("")){
						table = new SQLServerTable(db, name, password);
					}else{
						table = new SQLServerTable(db, name, password, ip);
					}
				}else if (ip.equals("")){
					table = new SQLServerTable(db, name, password, port);
				}else{
					table = new SQLServerTable(db, name, password, port, ip);
				}
				break;
			default:
				return; // TODO: assertion
		}
		
		if (table.connect()){
			String result = JOptionPane.showInputDialog(
					parent.getFrame(),
					"Please enter the name of the Table you wish to export to",
					"Export to " + type, JOptionPane.PLAIN_MESSAGE);
			if (result == null || result.equals("")) {
				return;
			}
			try {
				table.writeToTable(result, parent.getData());
				JOptionPane.showMessageDialog(null, "Export successful", "Export succeeded", JOptionPane.PLAIN_MESSAGE);
			} catch (SQLException e) {
				e.printStackTrace();
				JOptionPane.showMessageDialog(null, "Couldn't write to table", "Export failed", JOptionPane.WARNING_MESSAGE);
			}
			table.close();
		}else {
			JOptionPane.showMessageDialog(null, "Unable to connect to database", "Connection failed", JOptionPane.WARNING_MESSAGE);
		}
		
	}

}
