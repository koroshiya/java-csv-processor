package database;

import java.util.ArrayList;

public class DataTable {
	
	private ArrayList<DataColumn> columns;
	private final String tableName;
	
	public DataTable(String tableName){
		
		this.columns = new ArrayList<DataColumn>();
		this.tableName = tableName;
		
	}
	
	public ArrayList<DataColumn> getColumns(){
		return this.columns;
	}
	
	public String getTableName(){
		return tableName;
	}
	
	public int getColumnDataType(int i){
		
		if (i >= 0 && i < columns.size()){
			return columns.get(i).getDataType();
		}
		
		return -1;
	}
	
	public void addColumn(DataColumn column){
		this.columns.add(column);
	}
	
	public static DataTable convertToDataTable(Object[][] data, String tableName){
		
		DataTable table = new DataTable(tableName);
		
		for (int count = 0; count < data.length; count++){
			ArrayList<Object> column = new ArrayList<Object>();
			for (Object col : data[count]){
				column.add(col);
			}
			table.addColumn(new DataColumn(column.toArray(), "field" + count));
		}
		
		return table;
		
	}
	
}
