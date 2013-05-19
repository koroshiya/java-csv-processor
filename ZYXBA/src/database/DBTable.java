package database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public abstract class DBTable {
	
	private final String DATABASE_NAME;
	private final String DATABASE_CONNECTION;
	private final String DRIVER_NAME;
	private final String USER_NAME;
	private final String PASSWORD;
	private Connection dbConnection;
	
	public DBTable(String DBname, String connectionString, String driverName, String userName, String password){
		
		this.DATABASE_NAME = DBname;
		this.DATABASE_CONNECTION = connectionString;
		this.DRIVER_NAME = driverName;
		this.USER_NAME = userName;
		this.PASSWORD = password;
		this.dbConnection = null;
		
	}

	public DBTable(String dbName, String connectionHead, String ip, int port, String driver, String userName, String password) {
		this(dbName, connectionHead + ip + ":" + port + "/", driver, userName, password);
	}
	
	public void connect(){

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
	    }catch (SQLException e){
                System.out.println("Unable to connect to database");
                e.printStackTrace();
            }

	}
	
	public ResultSet execute(String sqlStatement){
		
		if (dbConnection != null){
		
			try{
                            Statement statement = dbConnection.createStatement();  
                            return statement.executeQuery(sqlStatement);
			} catch (SQLException e) {
				e.printStackTrace();
			}
		
		}else{
			System.out.println("No active database connection");
		}
		
		return null;
		
	}
        
        public void writeToTable(String tableName, Object[][] data){
            
           StringBuffer buffer = new StringBuffer();
            
            for (Object[] obj : data){
                buffer.append("insert into ");
                buffer.append(tableName);
                buffer.append(" values(");
                for (Object o : obj){
                    buffer.append(o);
                    buffer.append(", ");
                }
                buffer = buffer.delete(buffer.lastIndexOf(","), buffer.length());
                buffer.append(");");
                execute(buffer.toString());
                buffer.setLength(0);
            } 
            
        }
        
        public void writeToTable(String tableName, ResultSet rs){
            
            StringBuffer buffer = new StringBuffer();
            try{
                if (rs.next()){

                        buffer.append("insert into ");
                        buffer.append(tableName);
                        buffer.append(" values(");
                        int i = 0;
                        do {
                            buffer.append(rs.getObject(i++));
                            buffer.append(", ");
                        }while (rs.next());
                        buffer = buffer.delete(buffer.lastIndexOf(","), buffer.length());
                        buffer.append(");");
                        execute(buffer.toString());
                        buffer.setLength(0);

                }
            }catch (SQLException ex){
                System.out.println("Couldn't write to table");
                ex.printStackTrace();
            }
            
        }
	
	public static ArrayList<Object[]> readResultSet(ResultSet resultSet, String[] headers){
		
            ArrayList<Object[]> objects = new ArrayList<Object[]>();
            Object[] o;
		
            try {
		while (resultSet.next()) {
                	o = new Object[headers.length];
			for (int i = 0; i < headers.length; i++){
				o[i] = resultSet.getObject(headers[i]);
			}
			objects.add(o);
		}
            } catch (Exception e) {
            	e.printStackTrace();
            }
        
            return objects;
        
	}
	
	public void close(){
		
		if (dbConnection != null){
			try {
				dbConnection.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
			dbConnection = null;
		}
		
	}
	
	public String getDatabaseName(){
		return this.DATABASE_NAME;
	}
	
	public String getDatabaseConnectionString(){
		return this.DATABASE_CONNECTION;
	}
	
	public String getDriverName(){
		return this.DRIVER_NAME;
	}
	
	public String getUserName(){
		return this.USER_NAME;
	}
	
	public String getPassword(){
		return this.PASSWORD;
	}
	
	
}
