package com.carr.mitchell.database.db_table;

import java.sql.SQLException;

import com.carr.mitchell.database.java_table.DataCell;
import com.carr.mitchell.database.java_table.DataColumn;
import com.carr.mitchell.database.java_table.DataTable;


public class PostgresTable extends DBTable {

	private final static int defaultPort = 5432;
	private final static String defaultDriver = "org.postgresql.Driver";
	private final static String defaultIP = "localhost";
	private final static String connectionHead = "jdbc:postgresql://";

	/**
	 * @param dbName Name of the com.carr.mitchell.database these credentials are for
	 * @param userName Name of the user attempting to access a com.carr.mitchell.database
	 * @param password Password of the user attempting to access a com.carr.mitchell.database
	 */
	public PostgresTable(String dbName, String userName, String password) {
		super(dbName, connectionHead, defaultIP, defaultPort, defaultDriver, userName, password);
	}

	/**
	 * @param dbName Name of the com.carr.mitchell.database these credentials are for
	 * @param userName Name of the user attempting to access a com.carr.mitchell.database
	 * @param password Password of the user attempting to access a com.carr.mitchell.database
	 * @param port Port through which the com.carr.mitchell.database will be accessed
	 */
	public PostgresTable(String dbName, String userName, String password, int port) {
		super(dbName, connectionHead, defaultIP, port, defaultDriver, userName, password);
	}

	/**
	 * @param dbName Name of the com.carr.mitchell.database these credentials are for
	 * @param userName Name of the user attempting to access a com.carr.mitchell.database
	 * @param password Password of the user attempting to access a com.carr.mitchell.database
	 * @param ip IP address of the machine on which the com.carr.mitchell.database is held
	 */
	public PostgresTable(String dbName, String userName, String password, String ip) {
		super(dbName, connectionHead, ip, defaultPort, defaultDriver, userName, password);
	}

	/**
	 * @param dbName Name of the com.carr.mitchell.database these credentials are for
	 * @param userName Name of the user attempting to access a com.carr.mitchell.database
	 * @param password Password of the user attempting to access a com.carr.mitchell.database
	 * @param port Port through which the com.carr.mitchell.database will be accessed
	 * @param ip IP address of the machine on which the com.carr.mitchell.database is held
	 */
	public PostgresTable(String dbName, String userName, String password, int port, String ip) {
		super(dbName, connectionHead, ip, port, defaultDriver, userName, password);
	}

	/**
	 * @see com.carr.mitchell.database.db_table.DBTable#createTable(java.lang.String, java.lang.Object[][])
	 */
	@Override
	public boolean createTable(String tableName, Object[][] data) throws SQLException {
		return createTable(DataTable.convertToDataTable(data, tableName));
	}

	/**
	 * @param table DataTable which the structure of the com.carr.mitchell.database will reflect
	 * @return True if table was created, otherwise false.
	 * @throws SQLException if SQL couldn't be executed
	 */
	private boolean createTable(DataTable table) throws SQLException {

		StringBuffer buffer = new StringBuffer();

		buffer.append("CREATE TABLE ");
		buffer.append("\"" + table.getTableName() + "\"");
		buffer.append(" (");

		for (DataColumn col : table.getColumns()) {
			buffer.append("\"" + col.getColumnName() + "\" ");
			buffer.append(getDataTypeAsString(col.getDataType()));
			buffer.append(col.isNullable() ? " NULL" : " NOT NULL");
			buffer.append(col.isPrimaryKey() ? " PRIMARY KEY" : "");
			buffer.append(", ");
		}
		buffer = buffer.delete(buffer.lastIndexOf(","), buffer.length());
		buffer.append(");");
		System.out.println(buffer.toString());
		executeUpdate(buffer.toString());
		buffer.setLength(0);

		return true;

	}

	
	/**
	 * @see com.carr.mitchell.database.db_table.DBTable#getDataTypeAsString(int)
	 */
	@Override
	public String getDataTypeAsString(int type) {
		
		if (type == DataCell.TYPE_DATE) {
			return "date";
		} else if (type == DataCell.TYPE_INT) {
			return "integer";
		} else if (type == DataCell.TYPE_LONG) {
			return "bigint";
		} else if (type == DataCell.TYPE_BOOLEAN) {
			return "boolean";
		} else if (type == DataCell.TYPE_FLOAT) {
			return "real";
		} else if (type == DataCell.TYPE_DOUBLE) {
			return "double";
		} else {
			return "text";
		}
		
	}

}
