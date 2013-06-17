package com.carr.mitchell.database.java_table;

import java.util.ArrayList;

/**
 * Represents the contents of a JTable or a similar construct.
 * Hold DataColumn objects, a name for the table, etc.
 * This class aims to ease the process of exporting an Object[][] to a com.carr.mitchell.database or other field-based file.
 * */
public class DataTable {
	
	private ArrayList<DataColumn> columns;
	private final String tableName;
	
	/**
	 * @param tableName Name of the table this class represents
	 */
	public DataTable(String tableName){
		
		this.columns = new ArrayList<DataColumn>();
		this.tableName = tableName;
		
	}
	
	/**
	 * @return Returns all of the columns this table currently holds
	 */
	public ArrayList<DataColumn> getColumns(){
		return this.columns;
	}
	
	/**
	 * @return Name of the table this class represents
	 */
	public String getTableName(){
		return tableName;
	}
	
	/**
	 * @param i Index of the column to retrieve the data type for
	 * @return Integer representing the type of data held by the column at the index passed in.
	 * -1 if the column does not exist.
	 */
	public int getColumnDataType(int i){
		
		if (i >= 0 && i < columns.size()){
			return columns.get(i).getDataType();
		}
		
		return -1;
	}
	
	/**
	 * @param column Column to add to this table
	 */
	public void addColumn(DataColumn column){
		this.columns.add(column);
	}
	
	/**
	 * @param data Object[][] to turn into a DataTable
	 * @param tableName Name of the table to create
	 * @return Returns a DataTable containing the data passed in
	 */
	public static DataTable convertToDataTable(Object[][] data, String tableName){
		
		return convertToDataTable(data, new String[]{}, tableName);
		
	}
	
	/**
	 * @param data Object[][] to turn into a DataTable
	 * @param fieldNames String[] of names for the columns of the table being created
	 * @param tableName Name of the table to create
	 * @return Returns a DataTable containing the data passed in
	 */
	public static DataTable convertToDataTable(Object[][] data, String[] fieldNames, String tableName){
		
		DataTable table = new DataTable(tableName);
		String fieldName;
		
		for (int count = 0; count < data.length; count++){
			ArrayList<Object> column = new ArrayList<Object>();
			for (Object col : data[count]){
				column.add(col);
			}
			fieldName = count < fieldNames.length ? fieldNames[count] : "field" + count;
			table.addColumn(new DataColumn(column.toArray(), fieldName));
		}
		
		return table;
		
	}
	
}
