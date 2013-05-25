package com.carr.mitchell.zyxba_gui;

import database.Credential;
import database.DBTable;
import database.MySQLTable;
import database.PostgresTable;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.sql.SQLException;

import javax.swing.JButton;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;

import com.carr.mitchell.zyxba_gui.credential.CredentialEntry;

public class ExportListener implements ActionListener {

	private GUI parent;

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

			if (command.equals(GUI.exportXML)) {

			} else if (command.equals(GUI.exportJSON)){
				
			} else {
				String type = "";
				switch (command) {
				case GUI.exportMSQL:
					DBTable table;
					type = "MySQL";
				case GUI.exportPostgres:
					if (type.equals("")) {
						type = "Postgres";
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
						} catch (SQLException e) {
							e.printStackTrace();
							JOptionPane.showMessageDialog(null, "Couldn't write to table", "Export failed", JOptionPane.WARNING_MESSAGE);
						}
						table.close();
					}else {
						JOptionPane.showMessageDialog(null, "Unable to connect to database", "Connection failed", JOptionPane.WARNING_MESSAGE);
					}
					break;
				default:
					return;
				}

			}

		}else {
			JOptionPane.showMessageDialog(null, "You have not opened a CSV file", "No data to export", JOptionPane.WARNING_MESSAGE);
		}

	}

}
