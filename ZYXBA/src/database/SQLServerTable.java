package database;

public class SQLServerTable extends DBTable {

	private final static int defaultPort = 1433;
	private final static String defaultDriver = "com.microsoft.sqlserver.jdbc.SQLServerDriver";
	private final static String defaultIP = "localhost";
	private final static String connectionHead = "jdbc:sqlserver://";

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
