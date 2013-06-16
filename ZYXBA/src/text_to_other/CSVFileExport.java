package text_to_other;

import java.io.File;

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
	 * For a database, this would be the table name. For an XML file, it would be a tag. etc.
	 * @param columns Inner identifiers. eg. Database field names, inner XML tags, etc.
	 * @return Returns a StringBuffer containing the command necessary for an export to be applied.
	 */
	public abstract StringBuffer exportToColumns(String objectName, String[] columns);
	
	/**
	 * @see text_to_other.CSVExport#writeToColumnDefinedFile(java.lang.String, java.lang.String[])
	 */
	public void writeToColumnDefinedFile(String objectName, String[] columns) {
		
		StringBuffer buffer = exportToColumns(objectName, columns);
		writeToFile(buffer.toString());
		
	}
	
}
