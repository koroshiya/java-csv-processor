package com.carr.mitchell.write_format;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintStream;
import java.util.ArrayList;

import com.carr.mitchell.text_to_other.CSVDecoder;
import com.carr.mitchell.text_to_other.CSVFileExport;


public class JSONFile extends CSVFileExport{

	/**
	 * @param csv CSV file to parse
	 * @param outputFile Location to write exported CSV file to
	 */
	public JSONFile(File csv, String outputFile) {
		super(csv, outputFile);
	}

	/**
	 * @see com.carr.mitchell.text_to_other.CSVFileExport#writeToFile(java.lang.String)
	 */
	@Override
	public void writeToFile(String text) {
		
		PrintStream out = null;
		try {
		    out = new PrintStream(new FileOutputStream(getOutput()));
		    out.print("{" + "\"" + getOutput().getName() + "\"" + ": {" + "\n");
		    out.print(text);
		    out.print("}" + "}");
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}finally {
		    if (out != null) {
		    	out.close();
		    }
		}
		
	}

	/**
	 * @see com.carr.mitchell.text_to_other.CSVFileExport#exportToColumns(java.lang.String, java.lang.String[])
	 */
	@Override
	public StringBuffer exportToColumns(String objectName, String[] columns) {

		StringBuffer buffer = new StringBuffer();
		ArrayList<String[]> data = CSVDecoder.CSVToArrayListArray(super.getCSV());

		for (int row = 0; row < columns.length && row < data.size(); row++) {

			String[] dataRow = data.get(row);
			if (dataRow == null) {
				break;
			}
			buffer.append("\t" + "\"" + objectName + "\"" + ": [" + "\n");
			for (int col = 0; col < columns.length && col < dataRow.length; col++) {
				buffer.append("\t" + "\t" + "{" + "\"" + columns[col] + "\"" + ": ");
				buffer.append("\"" + dataRow[col] + "\"");
				buffer.append("}" + "," + "\n");
			}
			buffer.delete(buffer.lastIndexOf(","), buffer.length());
			buffer.append("\n" + "\t" + "]," + "\n");
		}

		buffer.delete(buffer.lastIndexOf(","), buffer.length());
		buffer.append("\n");

		return buffer;

	}

}
