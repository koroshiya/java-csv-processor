package com.japanzai.jreader;

public class test {
	
	public static void main(String[] args) {
		
		JxDialog dialog = new JxDialog(new String[]{".zip", ".rar"});
		System.out.println(dialog.showDialog());
		//gets fully qualified path to file
		//Object still in memory, just hidden
		
		/**
		 * TODO: When implemented, set check for null
		 * */
	}
	
}