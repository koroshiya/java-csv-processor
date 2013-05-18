package database;

public class MySQLTable extends DBTable {
	
	private final static int defaultPort = 3306;
	private final static String defaultDriver = "com.mysql.jdbc.Driver";
	private final static String defaultIP = "localhost";
	private final static String connectionHead = "jdbc:mysql://";

	public MySQLTable(String dbName, String userName, String password) {
		super(dbName, connectionHead, defaultIP, defaultPort, defaultDriver, userName, password);
	}

	public MySQLTable(String dbName, String userName, String password, int port) {
		super(dbName, connectionHead, defaultIP, port, defaultDriver, userName, password);
	}

	public MySQLTable(String dbName, String userName, String password, String ip) {
		super(dbName, connectionHead, ip, defaultPort, defaultDriver, userName, password);
	}

	public MySQLTable(String dbName, String userName, String password, int port, String ip) {
		super(dbName, connectionHead, ip, port, defaultDriver, userName, password);
	}
	
}
