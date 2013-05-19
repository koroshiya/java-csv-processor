package database;

public class PostgresTable extends DBTable {

	private final static int defaultPort = 5432;
	private final static String defaultDriver = "org.postgresql.Driver";
	private final static String defaultIP = "localhost";
	private final static String connectionHead = "jdbc:postgresql://";

	public PostgresTable(String dbName, String userName, String password) {
		super(dbName, connectionHead, defaultIP, defaultPort, defaultDriver,
				userName, password);
	}

	public PostgresTable(String dbName, String userName, String password,
			int port) {
		super(dbName, connectionHead, defaultIP, port, defaultDriver, userName,
				password);
	}

	public PostgresTable(String dbName, String userName, String password,
			String ip) {
		super(dbName, connectionHead, ip, defaultPort, defaultDriver, userName,
				password);
	}

	public PostgresTable(String dbName, String userName, String password,
			int port, String ip) {
		super(dbName, connectionHead, ip, port, defaultDriver, userName,
				password);
	}

}
