package com.carr.mitchell.zyxba_gui.listeners;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;

import com.carr.mitchell.zyxba_gui.prompt.HeaderPrompt;
import com.carr.mitchell.zyxba_gui.prompt.Prompt;

public class PromptListener implements ActionListener {

	private final Prompt parent;
	
	/**
	 * @param parent Prompt to manipulate when the user interacts with it
	 */
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
				parent.decline();
			}
			
		}
		
	}

}
