package com.carr.mitchell.zyxba_gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.swing.JButton;

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
				
			}else {
				//TODO: assertion
			}
			
		}
		
	}
	
	

}
