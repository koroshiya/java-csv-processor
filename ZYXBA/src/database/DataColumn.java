package database;

import java.util.ArrayList;

public class DataColumn {

	private final int dataType;
	private final boolean nullable;
	private final String columnName;
	private final boolean primaryKey;
	private ArrayList<DataCell> cells;
	
	public DataColumn(Object[] column, String columnName){
		this(DataCell.determineType(column), columnName, DataCell.isNullable(column), column, false);
	}
	
	public DataColumn(Object[] column, String columnName, boolean nullable){
		this(DataCell.determineType(column), columnName, nullable, column, false);
	}
	
	public DataColumn(int dataType, String columnName, boolean nullable){
		this(dataType, columnName, nullable, new Object[]{}, false);
	}
	
	public DataColumn(int dataType, String columnName, boolean nullable, Object[] col, boolean primaryKey){
		cells = new ArrayList<DataCell>();
		for (Object obj : col){
			cells.add(new DataCell(obj));
		}
		this.dataType = dataType;
		this.columnName = columnName;
		this.nullable = nullable;
		this.primaryKey = primaryKey;
	}
	
	public int getDataType(){
		return this.dataType;
	}
	
	public String getColumnName(){
		return this.columnName;
	}
	
	public boolean isNullable(){
		return this.nullable;
	}
	
	public boolean isPrimaryKey(){
		return this.primaryKey;
	}
	
	public void addCell(DataCell cell){
		this.cells.add(cell);
	}
	
	public void addCells(ArrayList<DataCell> cell){
		this.cells.addAll(cell);
	}
	
}