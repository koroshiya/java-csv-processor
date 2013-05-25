package database;

import java.sql.SQLException;

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

	@Override
	public boolean createTable(String tableName, Object[][] data) {
		return createTable(DataTable.convertToDataTable(data, tableName));
	}
	
	private boolean createTable(DataTable table){ //TODO: check that this works/is syntactically correct
		
		StringBuffer buffer = new StringBuffer();
		try {

				buffer.append("CREATE TABLE ");
				buffer.append(table.getTableName());
				buffer.append(" (");
				
				for (DataColumn col : table.getColumns()){
					buffer.append("\"" + col.getColumnName() + "\" ");
					buffer.append(getDataTypeAsString(col.getDataType()));
					buffer.append(col.isNullable() ? " NULL" : " NOT NULL");
					buffer.append(col.isPrimaryKey() ? " PRIMARY KEY" : "");
					buffer.append(", ");
				}
				buffer = buffer
						.delete(buffer.lastIndexOf(","), buffer.length());
				buffer.append(");");
				execute(buffer.toString());
				buffer.setLength(0);
				
				return true;
				
		} catch (SQLException ex) {
			System.out.println("Couldn't create table");
			ex.printStackTrace();
		}

		return false;
		
	}
	
	public String getDataTypeAsString(int type){ //TODO: move to DBTable? Check CREATE statements of other types first
		
		if (type == DataCell.TYPE_DATE){
			return "date";
		}else if (type == DataCell.TYPE_INT){
			return "int";
		}else{
			return "text";
		}
		
	}
	
}
