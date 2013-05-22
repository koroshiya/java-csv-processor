package database;

public class Credential {

	private final String dbName;
	private final String userName;
	private final String password;
	private final int port;
	private final String ip;
	
	
	public Credential(String dbName, 
						String userName, 
						String password,
						int port, 
						String ip){
		
		this.dbName = dbName;
		this.userName = userName;
		this.password = password;
		this.port = port;
		this.ip = ip;
		
	}
	
	public String getDatabaseName(){
		return this.dbName;
	}
	
	public String getUserName(){
		return this.userName;
	}
	
	public String getPassword(){
		return this.password;
	}
	
	public int getPort(){
		return this.port;
	}
	
	public String getIP(){
		return this.ip;
	}
	
}
