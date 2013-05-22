package com.japanzai.jreader;

import java.util.ArrayList;

import javax.swing.ImageIcon;

public class test {
	
	public static void main(String[] args) {
		
		ArrayList<Pairing> pairings = new ArrayList<Pairing>();
		ImageIcon archiveIcon = new ImageIcon(pairings.getClass().getResource("/images/archive.png"));
		pairings.add(new Pairing(archiveIcon, new String[]{".zip", ".rar"}));
		JxDialog dialog = new JxDialog(pairings);
		System.out.println(dialog.showDialog());
		//gets fully qualified path to file
		//Object still in memory, just hidden
		
		/**
		 * TODO: When implemented, set check for null
		 * */
	}
	
}