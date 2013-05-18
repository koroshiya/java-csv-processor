package write_format;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintStream;

import text_to_other.CSVFileExport;

public class JSONFile extends CSVFileExport{

	public JSONFile(File csv, String outputFile) {
		super(csv, outputFile);
	}

	@Override
	public void writeToFile(String text) {
		
		PrintStream out = null;
		try {
		    out = new PrintStream(new FileOutputStream(getOutput()));
		    out.print("{\"" + getOutput().getName() + "\": {");
		    out.print(text);
		    out.print("}}");
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}finally {
		    if (out != null) out.close();
		}
		
	}

	@Override
	public void writeToColumnDefinedFile(String objectName, String[] columns) {
		
		
		
		/*
		{"menu": {
			  "id": "file",
			  "value": "File",
			  "popup": {
			    "menuitem": [
			      {"value": "New", "onclick": "CreateNewDoc()"},
			      {"value": "Open", "onclick": "OpenDoc()"},
			      {"value": "Close", "onclick": "CloseDoc()"}
			    ]
			  }
			}}
		*/
	}

}
