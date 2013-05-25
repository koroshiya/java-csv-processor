package database;

import java.sql.SQLException;

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

	
	@Override
	public boolean createTable(String tableName, Object[][] data) {
		return createTable(DataTable.convertToDataTable(data, tableName));
	}
	
	private boolean createTable(DataTable table){
		
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
	
	/*
	 * 
	 * CREATE TABLE "tableName"
		(
		"campusID" character(3) NOT NULL,
		"courseID" character(5) NOT NULL,
		"disciplineID" integer NOT NULL,
		"courseCoordinator" boolean NOT NULL,
		"teacherID" character varying(20) NOT NULL,
		CONSTRAINT "pk_Assessor" PRIMARY KEY ("campusID" , "courseID" , "disciplineID" , "teacherID" )
		)
	 * */

	public String getDataTypeAsString(int type){ //TODO: move to DBTable? Check CREATE statements of other types first
		
		if (type == DataCell.TYPE_DATE){
			return "date";
		}else if (type == DataCell.TYPE_INT){
			return "integer";
		}else{
			return "text";
		}
		
	}
	
}
