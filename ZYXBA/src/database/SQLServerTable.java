package database;

public class SQLServerTable extends DBTable {

	private final static int defaultPort = -1;
	private final static String defaultDriver = ""; //TODO: get defaults for SQL Server
	private final static String defaultIP = "localhost";
	private final static String connectionHead = "";

	public SQLServerTable(String dbName, String userName, String password) {
		super(dbName, connectionHead, defaultIP, defaultPort, defaultDriver,
				userName, password);
	}

	public SQLServerTable(String dbName, String userName, String password, int port) {
		super(dbName, connectionHead, defaultIP, port, defaultDriver, userName,
				password);
	}

	public SQLServerTable(String dbName, String userName, String password, String ip) {
		super(dbName, connectionHead, ip, defaultPort, defaultDriver, userName,
				password);
	}

	public SQLServerTable(String dbName, String userName, String password,
			int port, String ip) {
		super(dbName, connectionHead, ip, port, defaultDriver, userName,
				password);
	}

}
