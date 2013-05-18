package com.carr.mitchell.zyxba_gui;

import com.japanzai.jreader.JxDialog;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

public class OpenListener implements ActionListener{
	
	private GUI parent;
	
	public OpenListener(GUI parent){
		this.parent = parent;
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		
		JxDialog jx = new JxDialog(new String[]{".csv"});
		File f = jx.showDialog();
		
		parent.setCSV(f);
		
	}

}
