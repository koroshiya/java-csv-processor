package database.db_table;

import java.sql.SQLException;

import database.java_table.DataCell;
import database.java_table.DataColumn;
import database.java_table.DataTable;

public class SQLServerTable extends DBTable {

	private final static int defaultPort = 1433;
	private final static String defaultDriver = "com.microsoft.sqlserver.jdbc.SQLServerDriver";
	private final static String defaultIP = "localhost";
	private final static String connectionHead = "jdbc:sqlserver://";

	/**
	 * @param dbName Name of the database these credentials are for
	 * @param userName Name of the user attempting to access a database
	 * @param password Password of the user attempting to access a database
	 */
	public SQLServerTable(String dbName, String userName, String password) {
		super(dbName, connectionHead, defaultIP, defaultPort, defaultDriver, userName, password);
	}

	/**
	 * @param dbName Name of the database these credentials are for
	 * @param userName Name of the user attempting to access a database
	 * @param password Password of the user attempting to access a database
	 * @param port Port through which the database will be accessed
	 */
	public SQLServerTable(String dbName, String userName, String password, int port) {
		super(dbName, connectionHead, defaultIP, port, defaultDriver, userName, password);
	}

	/**
	 * @param dbName Name of the database these credentials are for
	 * @param userName Name of the user attempting to access a database
	 * @param password Password of the user attempting to access a database
	 * @param ip IP address of the machine on which the database is held
	 */
	public SQLServerTable(String dbName, String userName, String password, String ip) {
		super(dbName, connectionHead, ip, defaultPort, defaultDriver, userName, password);
	}

	/**
	 * @param dbName Name of the database these credentials are for
	 * @param userName Name of the user attempting to access a database
	 * @param password Password of the user attempting to access a database
	 * @param port Port through which the database will be accessed
	 * @param ip IP address of the machine on which the database is held
	 */
	public SQLServerTable(String dbName, String userName, String password, int port, String ip) {
		super(dbName, connectionHead, ip, port, defaultDriver, userName, password);
	}

	/**
	 * @see database.db_table.DBTable#createTable(java.lang.String, java.lang.Object[][])
	 */
	@Override
	public boolean createTable(String tableName, Object[][] data) throws SQLException {
		return createTable(DataTable.convertToDataTable(data, tableName));
	}

	/**
	 * @param table DataTable which the structure of the database will reflect
	 * @return True if table was created, otherwise false.
	 * @throws SQLException if SQL couldn't be executed
	 */
	private boolean createTable(DataTable table) throws SQLException {

		StringBuffer buffer = new StringBuffer();

		buffer.append("CREATE TABLE ");
		buffer.append(table.getTableName());
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
		executeUpdate(buffer.toString());
		buffer.setLength(0);

		return true;

	}

	
	/**
	 * @see database.db_table.DBTable#getDataTypeAsString(int)
	 */
	@Override
	public String getDataTypeAsString(int type) {
		
		if (type == DataCell.TYPE_DATE) {
			return "date";
		} else if (type == DataCell.TYPE_INT) {
			return "int";
		} else if (type == DataCell.TYPE_LONG) {
			return "bigint";
		} else if (type == DataCell.TYPE_BOOLEAN) {
			return "bit(1)";
		} else if (type == DataCell.TYPE_FLOAT) {
			return "float";
		} else if (type == DataCell.TYPE_DOUBLE) {
			return "real";
		} else {
			return "text";
		}
		
	}

}
