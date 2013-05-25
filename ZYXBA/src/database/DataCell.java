package database;

import java.sql.Date;
import java.util.Calendar;

public class DataCell {
	
	private final String dateCreated; //PK
	private final Object data;

	public static int TYPE_INT;
	public static int TYPE_DATE;
	public static int TYPE_STRING;
	
	public DataCell(Object data){
		
		dateCreated = Long.toString(Calendar.getInstance().getTimeInMillis());
		this.data = data;
		
	}
	
	public String getDateCreated(){
		return this.dateCreated;
	}
	
	public Object getData(){
		return this.data;
	}
	
	public static int determineType(Object value){
		
		if (value instanceof Date){
			return TYPE_DATE;
		}else if (value instanceof Integer){
			return TYPE_INT;
		}else{
			return TYPE_STRING;
		}		
		
	}
	
	public static int determineType(Object[] column){
		
		int types[] = {TYPE_DATE, TYPE_INT, TYPE_STRING};
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
	
	public static boolean isNullable(Object[] column){
		
		for (Object value : column){
			if (value.toString().equals("")){
				return true;
			}
		}
		
		return false;
		
	}
	
}
