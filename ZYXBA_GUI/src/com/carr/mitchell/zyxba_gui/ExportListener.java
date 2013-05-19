package com.carr.mitchell.zyxba_gui;

import database.PostgresTable;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.swing.JButton;
import javax.swing.JOptionPane;

public class ExportListener implements ActionListener{
	
	private GUI parent;
	
	public ExportListener(GUI parent){
		this.parent = parent;
	}

	@Override
	public void actionPerformed(ActionEvent arg0) {
		
		File csv = this.parent.getCSV();
		
		if (csv != null){
			
			JButton btnSource = (JButton)arg0.getSource();
			String command = btnSource.getText();
			
			if (command.equals(GUI.exportXML)){
				
			}else if (command.equals(GUI.exportJSON)){
				
			}else if (command.equals(GUI.exportMSQL)){
				
			}else if (command.equals(GUI.exportPostgres)){
                                String db = JOptionPane.showInputDialog(
                                        parent.getFrame(), 
                                        "Please enter the name of the Database you wish to export to", 
                                        "Export to Postgres", 
                                        JOptionPane.YES_OPTION);
                                String result = JOptionPane.showInputDialog(
                                        parent.getFrame(), 
                                        "Please enter the name of the Table you wish to export to", 
                                        "Export to Postgres", 
                                        JOptionPane.YES_OPTION);
                                if (db != null && !db.equals("") && 
                                        result != null && !result.equals("")){
                                    PostgresTable table = new PostgresTable(db, "postgres", "postgres");
                                    table.writeToTable(result, parent.getData());
                                }
			}else {
				//TODO: assertion
			}
			
		}
		
	}
	
	

}
