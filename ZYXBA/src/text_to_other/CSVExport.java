package text_to_other;

import java.io.File;

public abstract class CSVExport {
	
	private final File csv;
	
	public CSVExport(File csv){
		this.csv = csv;
	}
	
	public File getCSV(){
		return this.csv;
	}
	
	/**
	 * @param objectName Name of the object we're setting the columns for.
	 * eg. In a DB, it would be the table name. In an XML file, it would be a field. etc.
	 * */
	public abstract void writeToColumnDefinedFile(String objectName, String[] columns);

}
