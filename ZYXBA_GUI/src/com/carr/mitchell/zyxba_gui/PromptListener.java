package com.carr.mitchell.zyxba_gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;

public class PromptListener implements ActionListener {

	private final Prompt parent;
	
	public PromptListener(Prompt parent){
		
		this.parent = parent;
		
	}
	
	@Override
	public void actionPerformed(ActionEvent arg0) {
		
		Object source = arg0.getSource();
		
		if (source instanceof JButton){
			
			JButton btn = (JButton) source;
			String command = btn.getText();
			
			if (command.equals(HeaderPrompt.accept)){
				parent.accept();
			}else if (command.equals(HeaderPrompt.cancel)){
				parent.dispose();
			}
			
		}
		
	}

}
