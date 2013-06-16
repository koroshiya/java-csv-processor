package database;

/**
 * Object easing the application of holding/using credentials when attempting to access a database.
 * */
public class Credential {

	private final String dbName;
	private final String userName;
	private final String password;
	private final int port;
	private final String ip;
		
	/**
	 * @param dbName Name of the database these credentials are for
	 * @param userName Name of the user attempting to access a database
	 * @param password Password of the user attempting to access a database
	 * @param port Port through which the database will be accessed
	 * @param ip IP address of the machine on which the database is held
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
	 * @return Name of the database these credentials are for
	 */
	public String getDatabaseName(){
		return this.dbName;
	}
	
	/**
	 * @return Name of the user attempting to access the database
	 */
	public String getUserName(){
		return this.userName;
	}
	
	/**
	 * @return Password of the user attempting to access the database
	 */
	public String getPassword(){
		return this.password;
	}
	
	/**
	 * @return Port through which the database will be accessed
	 */
	public int getPort(){
		return this.port;
	}
	
	/**
	 * @return IP address of the machine on which the database is held
	 */
	public String getIP(){
		return this.ip;
	}
	
}
