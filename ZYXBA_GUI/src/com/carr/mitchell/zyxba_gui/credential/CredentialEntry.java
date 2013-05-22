package com.carr.mitchell.zyxba_gui.credential;

import java.awt.Dimension;
import java.awt.GridLayout;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JTextField;

import database.Credential;

public class CredentialEntry extends JDialog {
	
	private static final long serialVersionUID = 1L;
	
	private JTextField database;
	private JTextField userName;
	private JTextField password;
	private JTextField ip;
	private JTextField port;
	
	private final JLabel lblDatabase;
	private final JLabel lblUserName;
	private final JLabel lblPassword;
	private final JLabel lblIp;
	private final JLabel lblPort;
	
	private final JButton btnAccept;
	private final JButton btnCancel;
	public static final String accept = "Accept";
	public static final String cancel = "Cancel";
	
	private Credential credential;
	
	public CredentialEntry(){
		
		credential = null;
		
		database = new JTextField();
		userName = new JTextField();
		password = new JTextField();
		ip = new JTextField();
		port = new JTextField();
		
		lblDatabase = new JLabel("Database name");
		lblUserName = new JLabel("Database user's name");
		lblPassword = new JLabel("Database user password");
		lblIp = new JLabel("IP address (blank for localhost)");
		lblPort = new JLabel("Port (blank for default port)");
		
		btnAccept = new JButton(accept);
		btnCancel = new JButton(cancel);
		
		CredentialButtonListener cbl = new CredentialButtonListener(this);
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
	
	public void display(){
		this.setVisible(true);
	}
	
	public Credential getCredential(){
		
		return this.credential;
		
	}
	
	public void accept(){

		String strUsername = userName.getText();
		String strDatabase = database.getText();
		
		if (strUsername.equals("") || strDatabase.equals("")){ //password can currently be blank
			this.setVisible(false);
			return;
		}
		
		String strPassword = password.getText();
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
	
}
