package com.carr.mitchell.zyxba_gui.prompt;

import java.awt.Dimension;
import java.awt.GridLayout;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JTextField;

import com.carr.mitchell.zyxba_gui.listeners.PromptListener;
import com.carr.mitchell.zyxba_gui.prompt.Prompt;

/**
 * Prompts a user for column headers
 * */
public class HeaderPrompt extends Prompt{
	
	private static final long serialVersionUID = 1L;
	
	private final int headerCount;
	private String[] headers;
	
	public static final String accept = "Accept";
	public static final String cancel = "Cancel";
	private final JTextField[] entries;
	
	/**
	 * @param headerCount Number of column headers to instantiate
	 */
	public HeaderPrompt(int headerCount){
		
		this.headerCount = headerCount;
		
		JButton btnAccept = new JButton(accept);
		JButton btnCancel = new JButton(cancel);
		
		PromptListener cbl = new PromptListener(this);
		btnAccept.addActionListener(cbl);
		btnCancel.addActionListener(cbl);

		headers = new String[headerCount];
		entries = new JTextField[headerCount];

		this.setLayout(new GridLayout(0,2));
		
		String line;
		
		for (int i = 0; i < headerCount; i++){
			
			line = "Column " + (i + 1) + ": ";
			
			entries[i] = new JTextField();
			
			this.add(new JLabel(line));
			this.add(entries[i]);
			
		}

		this.add(btnAccept);
		this.add(btnCancel);
		
		this.setMinimumSize(new Dimension(450, 200));
		
	}
	
	/**
	 * @return String[] of column headers
	 */
	public String[] getHeaders(){
		return this.headers;
	}
	
	/**
	 * @see com.carr.mitchell.zyxba_gui.prompt.Prompt#accept()
	 */
	@Override
	public void accept(){
		
		String line;
		
		for (int i = 0; i < headerCount; i++){
			
			line = entries[i].getText();
			
			if (line.length() < 1){
				//TODO: JOptionPane warning - Cannot be blank
				headers = null;
				return;
			}else{
				System.out.println("line: " + line);
				headers[i] = line; 
			}
			
		}
		
		this.setVisible(false);
		
	}
	
	/**
	 * @see com.carr.mitchell.zyxba_gui.prompt.Prompt#decline()
	 */
	@Override
	public void decline(){
		
		headers = null;
		super.dispose();
		
	}
	
}