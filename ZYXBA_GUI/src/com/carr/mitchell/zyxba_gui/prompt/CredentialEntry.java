package com.carr.mitchell.zyxba_gui.prompt;

import java.awt.Dimension;
import java.awt.GridLayout;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import com.carr.mitchell.zyxba_gui.listeners.PromptListener;


import database.Credential;

public class CredentialEntry extends Prompt {
	
	private static final long serialVersionUID = 1L;
	
	private JTextField database;
	private JTextField userName;
	private JPasswordField password;
	private JTextField ip;
	private JTextField port;
	
	public static final String accept = "Accept";
	public static final String cancel = "Cancel";
	
	private Credential credential;
	
	/**
	 * Instantiates GUI through which a user will enter their credentials
	 */
	public CredentialEntry(){
		
		credential = null;
		
		database = new JTextField();
		userName = new JTextField();
		password = new JPasswordField();
		ip = new JTextField();
		port = new JTextField();
		
		JLabel lblDatabase = new JLabel("Database name");
		JLabel lblUserName = new JLabel("Database user's name");
		JLabel lblPassword = new JLabel("Database user password");
		JLabel lblIp = new JLabel("IP address (blank for localhost)");
		JLabel lblPort = new JLabel("Port (blank for default port)");
		
		JButton btnAccept = new JButton(accept);
		JButton btnCancel = new JButton(cancel);
		
		PromptListener cbl = new PromptListener(this);
		btnAccept.addActionListener(cbl);
		btnCancel.addActionListener(cbl);
		
		this.setLayout(new GridLayout(0,2));

		this.add(lblDatabase);
		this.add(database);
		this.add(lblUserName);
		this.add(userName);
		this.add(lblPassword);
		this.add(password);
		this.add(lblIp);
		this.add(ip);
		this.add(lblPort);
		this.add(port);
		this.add(btnAccept);
		this.add(btnCancel);
		
		this.setMinimumSize(new Dimension(450, 200));
		
	}
		
	/**
	 * @return Credential held by this class
	 */
	public Credential getCredential(){
		
		return this.credential;
		
	}
	
	/**
	 * @see com.carr.mitchell.zyxba_gui.prompt.Prompt#accept()
	 */
	@Override
	public void accept(){

		String strUsername = userName.getText();
		String strDatabase = database.getText();
		
		if (strUsername.equals("") || strDatabase.equals("")){ //password can currently be blank
			this.setVisible(false);
			return;
		}
		
		String strPassword = password.getPassword().toString();
		String strPort = port.getText();
		String strIp = ip.getText();
		
		int intPort = -1;
		
		try{
			if (!strPort.equals("")){
				intPort = Integer.parseInt(strPort);
			}
		}catch (Exception ex){
			intPort = -1;
		}
		
		this.credential = new Credential(strDatabase, 
										strUsername,
										strPassword,
										intPort,
										strIp);
		
		this.setVisible(false);
		
	}
	
	/**
	 * @see com.carr.mitchell.zyxba_gui.prompt.Prompt#decline()
	 */
	@Override
	public void decline(){
		super.dispose();
	}
	
}
