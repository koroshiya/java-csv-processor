package com.japanzai.jreader;

import java.io.File;
import java.util.ArrayList;

import javax.swing.ImageIcon;

import com.japanzai.jreader.dialog.JxDialog;

public class test {
	
	public static void main(String[] args) {
		
		ArrayList<Pairing> pairings = new ArrayList<Pairing>();
		ImageIcon archiveIcon = new ImageIcon(pairings.getClass().getResource("/images/archive.png"));
		pairings.add(new Pairing(archiveIcon, new String[]{".zip", ".rar"}));
		JxDialog dialog = new JxDialog(pairings);
		File tempFile = dialog.showDialog();
		
		if (tempFile != null){
			System.out.println(tempFile.getAbsolutePath());
		}else{
			System.out.println("No file chosen");
		}
		
	}
	
}