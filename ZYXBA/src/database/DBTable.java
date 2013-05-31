package database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import java.sql.DatabaseMetaData;

public abstract class DBTable {

	private final String DATABASE_NAME;
	private final String DATABASE_CONNECTION;
	private final String DRIVER_NAME;
	private final String USER_NAME;
	private final String PASSWORD;
	private Connection dbConnection;

	public DBTable(String DBname, String connectionString, String driverName,
			String userName, String password) {

		this.DATABASE_NAME = DBname;
		this.DATABASE_CONNECTION = connectionString;
		this.DRIVER_NAME = driverName;
		this.USER_NAME = userName;
		this.PASSWORD = password;
		this.dbConnection = null;

	}

	public DBTable(String dbName, String connectionHead, String ip, int port,
			String driver, String userName, String password) {
		this(dbName, connectionHead + ip + ":" + port + "/", driver, userName,
				password);
	}

	public boolean connect() {

		try {
			Class.forName(this.getDriverName());
			dbConnection = DriverManager
					.getConnection(
							this.getDatabaseConnectionString()
									+ this.getDatabaseName(),
							this.getUserName(), this.getPassword());
		} catch (ClassNotFoundException e) {
			System.out.println("Appropriate driver not found");
			e.printStackTrace();
		} catch (SQLException e) {
			System.out.println("Unable to connect to database");
			e.printStackTrace();
		}
		
		return dbConnection != null;

	}

	public ResultSet execute(String sqlStatement) throws SQLException{

		if (dbConnection != null) {

			Statement statement = dbConnection.createStatement();
			return statement.executeQuery(sqlStatement);

		} else {
			System.out.println("No active database connection");
		}

		return null;

	}
	
	public void executeUpdate(String sqlStatement) throws SQLException{
		
		if (dbConnection != null) {

			Statement statement = dbConnection.createStatement();
			statement.executeUpdate(sqlStatement);

		} else {
			System.out.println("No active database connection");
		}
		
	}

	public void writeToTable(String tableName, Object[][] data) throws SQLException {

		StringBuffer buffer = new StringBuffer();
		
		DatabaseMetaData dmb = dbConnection.getMetaData();
		ResultSet tables = dmb.getTables(null, null, tableName, null);
		if (!(tables.next())){
			//TODO: table does not exist. Create table now?
			//TODO: prompt for data types
			if (!createTable(tableName, data)){
				write(buffer, tableName, data);
			}
			
			return;
		};
		
		write(buffer, tableName, data);

	}
	
	private void write(StringBuffer buffer, String tableName, Object[][] data) throws SQLException{
		
		for (Object[] obj : data) {
			buffer.append("insert into ");
			buffer.append(tableName);
			buffer.append(" values(");
			for (Object o : obj) {
				buffer.append(o);
				buffer.append(", ");
			}
			buffer = buffer.delete(buffer.lastIndexOf(","), buffer.length());
			buffer.append(");");
			System.out.println(buffer.toString());
			execute(buffer.toString());
			buffer.setLength(0);
		}
		
	}
	
	public abstract boolean createTable(String tableName, Object[][] data) throws SQLException;
	
	public abstract String getDataTypeAsString(int type);

	public static ArrayList<Object[]> readResultSet(ResultSet resultSet,
			String[] headers) {

		ArrayList<Object[]> objects = new ArrayList<Object[]>();
		Object[] o;

		try {
			while (resultSet.next()) {
				o = new Object[headers.length];
				for (int i = 0; i < headers.length; i++) {
					o[i] = resultSet.getObject(headers[i]);
				}
				objects.add(o);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return objects;

	}
	
	public void close() {

		if (dbConnection != null) {
			try {
				dbConnection.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
			dbConnection = null;
		}

	}

	public String getDatabaseName() {
		return this.DATABASE_NAME;
	}

	public String getDatabaseConnectionString() {
		return this.DATABASE_CONNECTION;
	}

	public String getDriverName() {
		return this.DRIVER_NAME;
	}

	public String getUserName() {
		return this.USER_NAME;
	}

	public String getPassword() {
		return this.PASSWORD;
	}

}
