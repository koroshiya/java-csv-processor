package com.carr.mitchell.database.db_table;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import java.sql.DatabaseMetaData;

/**
 * Represents a generic com.carr.mitchell.database table.
 * Should be extended to match the rules of individual com.carr.mitchell.database types.
 * */
public abstract class DBTable {

	private final String DATABASE_NAME;
	private final String DATABASE_CONNECTION;
	private final String DRIVER_NAME;
	private final String USER_NAME;
	private final String PASSWORD;
	private Connection dbConnection;

	/**
	 * @param dbName Name of the com.carr.mitchell.database this table belongs to
	 * @param connectionString ConnectionString used to connect to the com.carr.mitchell.database
	 * @param driver Driver used to connect to the specific com.carr.mitchell.database type
	 * @param userName Name of the user connecting to the com.carr.mitchell.database
	 * @param password Password of the user connecting to the com.carr.mitchell.database
	 */
	public DBTable(String dbName, String connectionString, String driver,
			String userName, String password) {

		this.DATABASE_NAME = dbName;
		this.DATABASE_CONNECTION = connectionString;
		this.DRIVER_NAME = driver;
		this.USER_NAME = userName;
		this.PASSWORD = password;
		this.dbConnection = null;

	}

	/**
	 * @param dbName Name of the com.carr.mitchell.database this table belongs to
	 * @param connectionHead Beginning of the com.carr.mitchell.database ConnectionString
	 * @param ip IP address of the machine on which the com.carr.mitchell.database resides
	 * @param port Port through which the com.carr.mitchell.database will be accessed
	 * @param driver Driver used to connect to the specific com.carr.mitchell.database type
	 * @param userName Name of the user connecting to the com.carr.mitchell.database
	 * @param password Password of the user connecting to the com.carr.mitchell.database
	 */
	public DBTable(String dbName, String connectionHead, String ip, int port,
			String driver, String userName, String password) {
		this(dbName, connectionHead + ip + ":" + port + "/", driver, userName, password);
	}

	/**
	 * Attempts to connect to the com.carr.mitchell.database
	 * @return True if connection succeeded, otherwise false.
	 */
	public boolean connect() {

		try {
			Class.forName(this.getDriverName());
			dbConnection = DriverManager.getConnection(
							this.getDatabaseConnectionString() + 
							this.getDatabaseName(),
							this.getUserName(), 
							this.getPassword());
		} catch (ClassNotFoundException e) {
			System.out.println("Appropriate driver not found");
			e.printStackTrace();
		} catch (SQLException e) {
			System.out.println("Unable to connect to com.carr.mitchell.database");
			e.printStackTrace();
		}
		
		return isConnected();

	}

	/**
	 * @param sqlStatement SQL Statement to execute
	 * @return ResultSet containing the results of the executed SQL statement.
	 * 			Null if no com.carr.mitchell.database connection exists.
	 * @throws SQLException if query couldn't be executed
	 */
	public ResultSet execute(String sqlStatement) throws SQLException{

		if (isConnected()) {

			Statement statement = dbConnection.createStatement();
			return statement.executeQuery(sqlStatement);

		} else {
			System.out.println("No active com.carr.mitchell.database connection");
		}

		return null;

	}
	
	/**
	 * @param sqlStatement SQL Statement to execute
	 * @throws SQLException if query couldn't be executed
	 */
	public void executeUpdate(String sqlStatement) throws SQLException{
		
		if (isConnected()) {

			Statement statement = dbConnection.createStatement();
			statement.executeUpdate(sqlStatement);

		} else {
			System.out.println("No active com.carr.mitchell.database connection");
		}
		
	}

	/**
	 * @param tableName Name of table to write data to
	 * @param data Data to write to table
	 * @throws SQLException if connection to table couldn't be established
	 */
	public void writeToTable(String tableName, Object[][] data) throws SQLException {

		if (isConnected()){
			
			DatabaseMetaData dmb = dbConnection.getMetaData();
			ResultSet tables = dmb.getTables(null, null, tableName, null);
			if (!(tables.next())){
				if (!createTable(tableName, data)){
					write(tableName, data);
				}
				
				return;
			};
			
			write(tableName, data);
		
		}else{
			System.out.println("No active com.carr.mitchell.database connection");
		}

	}
	
	/**
	 * @param tableName Name of the table to write data to
	 * @param data Data to write to a table
	 * @throws SQLException if data couldn't be written to table
	 */
	private void write(String tableName, Object[][] data) throws SQLException{
		
		StringBuffer buffer = new StringBuffer();
		
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
	
	/**
	 * @param tableName Name of the table to create
	 * @param data Object[][] outlining the table's data types, whether fields can be null, etc.
	 * @return True if table was created successfully, otherwise false.
	 * @throws SQLException if table couldn't be created
	 */
	public abstract boolean createTable(String tableName, Object[][] data) throws SQLException;
	
	/**
	 * @param type Integer representing type of data being processed
	 * @return Database-specific name of the type of data indicated by the integer passed in
	 */
	public abstract String getDataTypeAsString(int type);

	/**
	 * @param resultSet ResultSet to read values from
	 * @param headers Column headers matching the number of rows in the ResultSet
	 * @return ArrayList of Objects pulled from ResultSet
	 */
	public static ArrayList<Object[]> readResultSet(ResultSet resultSet, String[] headers) {

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
	
	/**
	 * If the com.carr.mitchell.database is connected, this closes the connection.
	 */
	public void close() {

		if (isConnected()) {
			try {
				dbConnection.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
			dbConnection = null;
		}

	}

	/**
	 * @return Name of the com.carr.mitchell.database
	 */
	public String getDatabaseName() {
		return this.DATABASE_NAME;
	}

	/**
	 * @return ConnectionString used to connect to the com.carr.mitchell.database
	 */
	public String getDatabaseConnectionString() {
		return this.DATABASE_CONNECTION;
	}

	/**
	 * @return Driver used to connect to the specific com.carr.mitchell.database type
	 */
	public String getDriverName() {
		return this.DRIVER_NAME;
	}

	/**
	 * @return	Name of the user connecting to the com.carr.mitchell.database
	 */
	public String getUserName() {
		return this.USER_NAME;
	}

	/**
	 * @return Password of the user connecting to the com.carr.mitchell.database
	 */
	public String getPassword() {
		return this.PASSWORD;
	}
	
	/**
	 * @return If a com.carr.mitchell.database connection has been established, returns true.
	 * Otherwise, false.
	 */
	public boolean isConnected(){
		return dbConnection != null;
	}

}
