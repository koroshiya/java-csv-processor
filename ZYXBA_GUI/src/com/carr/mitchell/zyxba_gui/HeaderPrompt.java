package com.carr.mitchell.zyxba_gui;

import java.awt.Dimension;
import java.awt.GridLayout;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JTextField;

import com.carr.mitchell.zyxba_gui.Prompt;

public class HeaderPrompt extends JDialog implements Prompt{
	
	private static final long serialVersionUID = 1L;
	
	private final int headerCount;
	private String[] headers;
	
	private final JButton btnAccept;
	private final JButton btnCancel;
	public static final String accept = "Accept";
	public static final String cancel = "Cancel";
	private final JLabel[] labels;
	private final JTextField[] entries;
	
	public HeaderPrompt(int headerCount){
		
		this.headerCount = headerCount;
		
		btnAccept = new JButton(accept);
		btnCancel = new JButton(cancel);
		
		PromptListener cbl = new PromptListener(this);
		btnAccept.addActionListener(cbl);
		btnCancel.addActionListener(cbl);

		headers = new String[headerCount];
		labels = new JLabel[headerCount];
		entries = new JTextField[headerCount];

		this.setLayout(new GridLayout(0,2));
		
		String line;
		
		for (int i = 0; i < headerCount; i++){
			
			line = "Column " + (i + 1) + ": ";
			
			labels[i] = new JLabel(line);
			entries[i] = new JTextField();
			
			this.add(labels[i]);
			this.add(entries[i]);
			
		}

		this.add(btnAccept);
		this.add(btnCancel);
		
		this.setMinimumSize(new Dimension(450, 200));
		
	}
	
	public void display(){
		
		this.setModal(true);
		this.setVisible(true);
		
	}
	
	public String[] getHeaders(){
		return this.headers;
	}
	
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
	
	@Override
	public void dispose(){
		
		headers = null;
		super.dispose();
		
	}
	
}
