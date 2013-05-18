package text_to_other;

import java.io.File;

/**
 * Extends CSVExport to provide functionality for text-based file exports.
 * eg. XML and JSON as opposed to databases
 * */
public abstract class CSVFileExport extends CSVExport{

	private final File output;
	
	public CSVFileExport(File csv, String outputFile) {
		super(csv);
		output = new File(outputFile);
	}
	
	public File getOutput(){
		return this.output;
	}
	
	public abstract void writeToFile(String text);	

}
