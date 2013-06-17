package com.carr.mitchell.text_to_other;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintStream;
import java.util.ArrayList;

/**
 * Extends CSVExport to provide functionality for text-based file exports. 
 * eg. XML and JSON as opposed to databases
 * */
public abstract class CSVFileExport extends CSVExport {

	protected final File output;

	/**
	 * @param csv CSV file to parse
	 * @param outputFile Location to write exported CSV file to
	 */
	public CSVFileExport(File csv, String outputFile) {
		super(csv);
		output = new File(outputFile);
	}

	/**
	 * @return File returned after the input CSV file has been processed
	 */
	public File getOutput() {
		return this.output;
	}

	/**
	 * @param text String to write to output file
	 */
	public abstract void writeToFile(String text);

	/**
	 * @param objectName Identifier for output file contents.
	 * For a com.carr.mitchell.database, this would be the table name. For an XML file, it would be a tag. etc.
	 * @param columns Inner identifiers. eg. Database field names, inner XML tags, etc.
	 * @return Returns a StringBuffer containing the command necessary for an export to be applied.
	 */
	public abstract StringBuffer exportToColumns(String objectName, String[] columns);
	
	/**
	 * @see com.carr.mitchell.text_to_other.CSVExport#writeToColumnDefinedFile(java.lang.String, java.lang.String[])
	 */
	public void writeToColumnDefinedFile(String objectName, String[] columns) {
		
		StringBuffer buffer = exportToColumns(objectName, columns);
		writeToFile(buffer.toString());
		
	}
	
	/**
	 * Writes a custom formatted column to file in the format:
	 * pre1 + tag + pre2 + text + post
	 * eg. in JSON, using the examples below, it would be: '{"tag": {text}}'
	 * 
	 * @param text Body of text to write to the document. 
	 * Should be the output of writeToCustomColumn(
	 * 		String, String[], String, String, 
			String, String, String, String, String)
	 * @param pre1 Text preceding body, before first tag. eg. in JSON, it would be '{"'
	 * @param pre2 Text preceding body, after first tag. eg. in JSON, it would be '": {'
	 * @param post Text proceeding body. eg. in JSON, it would be '}}'
	 */
	public void writeToCustomFile(String text, String pre1, String pre2, String post){
		
		PrintStream out = null;
		try {
		    out = new PrintStream(new FileOutputStream(getOutput()));
		    out.print(pre1 + getOutput().getName() + pre2);
		    out.print(text);
		    out.print(post);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}finally {
		    if (out != null) {
		    	out.close();
		    }
		}
		
	}
	
	/**
	 * Outputs a custom formatted column in the format:
	 * rowPreOuter1 + objectName + rowPreOuter2 +
	 * rowPreInner1 + columns[col] + rowPreInner2 +
	 * dataRow[col] + rowPostInner +
	 * rowPostOuter + end
	 * 
	 * eg. in JSON, using the examples below, it would be: 
	 * '"objectName": [{"columns[i]": "dataRow[i]"},],'
	 * 
	 * Extra formatting may be required for some formats. 
	 * eg. in the above example, you would want to remove the final comma before writing to file.
	 * 
	 * @param objectName Name of the object being written.
	 * 			This serves as a tag for individual objects being written.
	 * @param columns String[] of column names. These become inner tags.
	 * @param rowPreOuter1 Text preceding object name in each segment
	 * 			eg. in JSON, it would be '"'
	 * @param rowPreOuter2 Text proceeding object name in each segment
	 * 			eg. in JSON, it would be '": ['
	 * @param rowPreInner1 Text preceding column name in each segment
	 * 			eg. in JSON, it would be '{"'
	 * @param rowPreInner2 Text proceeding column name in each segment
	 * 			eg. in JSON, it would be '": "'
	 * @param rowPostInner Text proceeding line of data
	 * 			eg. in JSON, it would be '"},'
	 * @param rowPostOuter Text proceeding row entry
	 * 			eg. in JSON, it would be '],'
	 * @param end Text to end column. Usually a break, such as "\n"
	 * @return Returns a StringBuffer containing the command necessary for an export to be applied.
	 */
	public StringBuffer writeToCustomColumn(String objectName, String[] columns, 
			String rowPreOuter1, String rowPreOuter2, 
			String rowPreInner1, String rowPreInner2, 
			String rowPostInner, 
			String rowPostOuter, String end){

		StringBuffer buffer = new StringBuffer();
		ArrayList<String[]> data = CSVDecoder.CSVToArrayListArray(super.getCSV());

		for (int row = 0; row < columns.length && row < data.size(); row++) {

			String[] dataRow = data.get(row);
			if (dataRow == null) {
				break;
			}
			buffer.append(rowPreOuter1 + objectName + rowPreOuter2);
			for (int col = 0; col < columns.length && col < dataRow.length; col++) {
				buffer.append(rowPreInner1 + columns[col] + rowPreInner2);
				buffer.append(dataRow[col] + rowPostInner);
			}
			buffer.append(rowPostOuter);
		}
		buffer.append(end);

		return buffer;

	}
	
}
