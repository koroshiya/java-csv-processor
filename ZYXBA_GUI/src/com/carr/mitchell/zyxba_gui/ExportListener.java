package com.carr.mitchell.zyxba_gui;

import database.DBTable;
import database.MySQLTable;
import database.PostgresTable;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.swing.JButton;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;

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

			} else {
				String type = "";
				switch (command) {
				case GUI.exportJSON:
					break;
				case GUI.exportMSQL:
					DBTable table;
					type = "MySQL";
				case GUI.exportPostgres:
					if (type.equals("")) {
						type = "Postgres";
					}
					String db = JOptionPane
							.showInputDialog(
									parent.getFrame(),
									"Please enter the name of the Database you wish to export to",
									"Export to " + type, JOptionPane.YES_OPTION);
					String result = JOptionPane
							.showInputDialog(
									parent.getFrame(),
									"Please enter the name of the Table you wish to export to",
									"Export to " + type, JOptionPane.YES_OPTION);
					if (db == null || db.equals("") || result == null
							|| result.equals("")) {
						return;
					}
					switch (type) {
					case "Postgres":
						table = new PostgresTable(db, "postgres", "postgres");
						break;
					case "MySQL":
						table = new MySQLTable(db, "root", "passwd");
						break;
					default:
						return; // TODO: assertion
					}
					table.connect();
					table.writeToTable(result, parent.getData());
					table.close();
					break;

				}

			}

		}

	}

}
