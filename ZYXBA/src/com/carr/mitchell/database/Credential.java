package com.carr.mitchell.database;

/**
 * Object easing the application of holding/using credentials when attempting to access a com.carr.mitchell.database.
 * */
public class Credential {

	private final String dbName;
	private final String userName;
	private final String password;
	private final int port;
	private final String ip;
		
	/**
	 * @param dbName Name of the com.carr.mitchell.database these credentials are for
	 * @param userName Name of the user attempting to access a com.carr.mitchell.database
	 * @param password Password of the user attempting to access a com.carr.mitchell.database
	 * @param port Port through which the com.carr.mitchell.database will be accessed
	 * @param ip IP address of the machine on which the com.carr.mitchell.database is held
	 */
	public Credential(String dbName, String userName, String password,
						int port, String ip){
		
		this.dbName = dbName;
		this.userName = userName;
		this.password = password;
		this.port = port;
		this.ip = ip;
		
	}
	
	/**
	 * @return Name of the com.carr.mitchell.database these credentials are for
	 */
	public String getDatabaseName(){
		return this.dbName;
	}
	
	/**
	 * @return Name of the user attempting to access the com.carr.mitchell.database
	 */
	public String getUserName(){
		return this.userName;
	}
	
	/**
	 * @return Password of the user attempting to access the com.carr.mitchell.database
	 */
	public String getPassword(){
		return this.password;
	}
	
	/**
	 * @return Port through which the com.carr.mitchell.database will be accessed
	 */
	public int getPort(){
		return this.port;
	}
	
	/**
	 * @return IP address of the machine on which the com.carr.mitchell.database is held
	 */
	public String getIP(){
		return this.ip;
	}
	
}
