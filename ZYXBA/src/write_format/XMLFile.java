package write_format;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintStream;
import java.util.ArrayList;

import text_to_other.CSVDecoder;
import text_to_other.CSVFileExport;

public class XMLFile extends CSVFileExport{
	
	private File output;
	
	public XMLFile(File csv, String outputFile){
		super(csv, outputFile);
	}
	
	@Override
	public void writeToColumnDefinedFile(String objectName, String[] columns){
		
		StringBuffer buffer = exportToColumns(objectName, columns);
		writeToFile(buffer.toString());
		
	}
	
	private StringBuffer exportToColumns(String objectName, String[] columns){
		
		StringBuffer buffer = new StringBuffer();
		ArrayList<String[]> data = CSVDecoder.CSVToArrayListArray(super.getCSV());
		
		buffer.append("<" + objectName + ">");
		
		for (int row = 0; row < columns.length; row++){
			
			String[] dataRow = data.get(row);
			if (dataRow == null){
				break;
			}
			
			for (int col = 0; col < columns.length; col++){
				buffer.append("<" + columns[col] + ">");
				buffer.append(dataRow[col]);
				buffer.append("</" + columns[col] + ">");
			}
			
		}

		buffer.append("</" + objectName + ">");
		
		return buffer;
		
	}
	
	@Override
	public void writeToFile(String text){
		
		PrintStream out = null;
		try {
		    out = new PrintStream(new FileOutputStream(output));
		    out.print("<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"no\" ?> ");
		    out.print("<" + this.output.getName() + ">");
		    out.print(text);
		    out.print("</" + this.output.getName() + ">");
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}finally {
		    if (out != null) out.close();
		}
		
	}
	
}
