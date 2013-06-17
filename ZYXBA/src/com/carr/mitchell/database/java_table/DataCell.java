package com.carr.mitchell.database.java_table;

import java.sql.Date;
import java.util.Calendar;

/**
 * Represents a single cell in a table.
 * May be thought of as a cell in a JTable row, the value of a single field in a com.carr.mitchell.database table, etc.
 * */
public class DataCell {
	
	private final String dateCreated; //PK
	private final Object data;

	public static final int TYPE_INT = -1000;
	public static final int TYPE_DATE = -1001;
	public static final int TYPE_STRING = -1002;
	public static final int TYPE_LONG = -1003;
	public static final int TYPE_BOOLEAN = -1004;
	public static final int TYPE_FLOAT = -1005;
	public static final int TYPE_DOUBLE = -1006;
	
	/**
	 * @param data Object for this DataCell to represent
	 */
	public DataCell(Object data){
		
		dateCreated = Long.toString(Calendar.getInstance().getTimeInMillis());
		this.data = data;
		
	}
	
	/**
	 * @return Date this cell was created as a String
	 */
	public String getDateCreated(){
		return this.dateCreated;
	}
	
	/**
	 * @return Object this DataCell represents
	 */
	public Object getData(){
		return this.data;
	}
	
	/**
	 * @param value Object to test the data type of
	 * @return Integer representing a specific java data type
	 */
	public static int determineType(Object value){
		
		if (value instanceof Date){
			return TYPE_DATE;
		}else if (value instanceof Boolean){
			return TYPE_BOOLEAN;
		}else if (value instanceof Float){
			return TYPE_FLOAT;
		}else if (value instanceof Double){
			return TYPE_DOUBLE;
		}else if (value instanceof Long){
			return TYPE_LONG;
		}else if (value instanceof Integer){
			return TYPE_INT;
		}else{
			return TYPE_STRING;
		}		
		
	}
	
	/**
	 * @param column Object[] for which to test the data type of its contents
	 * @return Integer representing a specific java data type
	 */
	public static int determineType(Object[] column){
		
		int types[] = {TYPE_DATE, TYPE_BOOLEAN, TYPE_FLOAT, TYPE_DOUBLE, TYPE_LONG, TYPE_INT};
		boolean exitLoop;
		
		for (int type : types){
			exitLoop = true;
			for (Object value : column){
				if (type != DataCell.determineType(value)){
					exitLoop = false;
					break;
				}
			}
			if (exitLoop){
				return type;
			}
		}
		
		return TYPE_STRING;
		
	}
	
	/**
	 * @param column Object[] to test if nullable
	 * @return If parameter passed in is nullable, true. Otherwise, false.
	 */
	public static boolean isNullable(Object[] column){
		
		for (Object value : column){
			if (value.toString().equals("")){
				return true;
			}
		}
		
		return false;
		
	}
	
}
