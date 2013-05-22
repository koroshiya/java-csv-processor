package com.carr.mitchell.zyxba_gui.credential;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;

public class CredentialButtonListener implements ActionListener{
	
	private final CredentialEntry parent;
	
	public CredentialButtonListener(CredentialEntry parent){
		
		this.parent = parent;
		
	}

	@Override
	public void actionPerformed(ActionEvent arg0) {
		
		Object source = arg0.getSource();
		
		if (source instanceof JButton){
			
			JButton btn = (JButton) source;
			String command = btn.getText();
			
			if (command.equals(CredentialEntry.accept)){
				parent.accept();
			}else if (command.equals(CredentialEntry.cancel)){
				//TODO: cancel
			}
			
		}
		
	}
	
}
