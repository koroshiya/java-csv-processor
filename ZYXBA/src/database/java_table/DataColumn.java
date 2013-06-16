package database.java_table;

import java.util.ArrayList;

/**
 * Represents the contents of a JTable column or a similar construct.
 * Hold DataCell objects, a name for the column, the column's data type, etc.
 * */
public class DataColumn {

	private final int dataType;
	private final boolean nullable;
	private final String columnName;
	private final boolean primaryKey;
	private ArrayList<DataCell> cells;
	
	/**
	 * @param column Object[] to convert into a DataColumn
	 * @param columnName Name of the DataColumn to create
	 */
	public DataColumn(Object[] column, String columnName){
		this(DataCell.determineType(column), columnName, DataCell.isNullable(column), column, false);
	}
	
	/**
	 * @param column Object[] to convert into a DataColumn
	 * @param columnName Name of the DataColumn to create
	 * @param nullable Boolean indicating whether the field can be null or not
	 */
	public DataColumn(Object[] column, String columnName, boolean nullable){
		this(DataCell.determineType(column), columnName, nullable, column, false);
	}
	
	/**
	 * Creates an <b>empty</b> DataColumn
	 * @param dataType Integer representing the type of data held by this column.
	 * 		Data type maps to a field in DataCell.
	 * 		If you don't know what to put, use DataCell.determineType(Object)
	 * @see database.java_table.DataCell#determineType(Object)
	 * @param columnName Name of the DataColumn to create
	 * @param nullable Boolean indicating whether the field can be null or not
	 */
	public DataColumn(int dataType, String columnName, boolean nullable){
		this(dataType, columnName, nullable, new Object[]{}, false);
	}
	
	/**
	 * @param dataType Integer representing the type of data held by this column.
	 * 		Data type maps to a field in DataCell.
	 * 		If you don't know what to put, use DataCell.determineType(Object)
	 * @see database.java_table.DataCell#determineType(Object)
	 * @param columnName Name of the DataColumn to create
	 * @param nullable Boolean indicating whether the field can be null or not
	 * @param col Object[] to convert into a DataColumn
	 * @param primaryKey Boolean representing whether this Column represents the primary key in a database table
	 */
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
	
	/**
	 * @return Integer representing the data type of this column
	 * @see database.java_table.DataCell#determineType(Object)
	 */
	public int getDataType(){
		return this.dataType;
	}
	
	/**
	 * @return Name of this column
	 */
	public String getColumnName(){
		return this.columnName;
	}
	
	/**
	 * @return If this field can be null, true. Otherwise, false.
	 */
	public boolean isNullable(){
		return this.nullable;
	}
	
	/**
	 * @return If this field represents the primary key in a database table, true. Otherwise, false.
	 */
	public boolean isPrimaryKey(){
		return this.primaryKey;
	}
	
	/**
	 * @param cell DataCell to add to this column
	 */
	public void addCell(DataCell cell){
		this.cells.add(cell);
	}
	
	/**
	 * @param cell ArrayList of DataCell objects to add to this column
	 */
	public void addCells(ArrayList<DataCell> cell){
		this.cells.addAll(cell);
	}
	
}