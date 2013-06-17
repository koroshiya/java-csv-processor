package com.carr.mitchell.text_to_other;

import java.io.File;

/**
 * Abstract class that any export classes should extend.
 * eg. A CSV to Database exporter should extend this class. 
 *
 */
public abstract class CSVExport {
	
	private final File csv;
	
	/**
	 * @param csv file to process and later export
	 */
	public CSVExport(File csv){
		this.csv = csv;
	}
	
	/**
	 * @return CSV file to be processed
	 */
	public File getCSV(){
		return this.csv;
	}
	
	/**
	 * @param objectName Name of the object we're setting the columns for.
	 * eg. In a DB, it would be the table name. In an XML file, it would be a field. etc.
	 * @param columnNames String array of names for the columns to be exported.
	 * */
	public abstract void writeToColumnDefinedFile(String objectName, String[] columnNames);

}
