package com.japanzai.jreader.listener;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import com.japanzai.jreader.dialog.JxDialog;

public class DialogActionListener implements ActionListener {
	
	private final JxDialog dialog;
	
	/**
	 * @param dialog Dialog which the mouse events pertain to
	 */
	public DialogActionListener(JxDialog dialog){
		this.dialog = dialog;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		
		String command = e.getActionCommand();
		File curDir = dialog.getCurrentDirectory();
		
		if (command.equals(JxDialog.GO) || e.getActionCommand().equals(JxDialog.ENTER)){
			dialog.tryPath();
		}else if (command.equals(JxDialog.UP) && curDir.getParent() != null){
			curDir = curDir.getParentFile();
			dialog.parseDir(curDir.getParentFile());
			dialog.setAddress(curDir.getAbsolutePath());
		}else if (command.equals(JxDialog.ACCEPT) || command.equals(JxDialog.OPEN)){
			dialog.tryPath(dialog.getSelectedFile().getAbsolutePath());
		}else if (command.equals(JxDialog.CANCEL)){
			dialog.Close();
		}
	}
	
}
