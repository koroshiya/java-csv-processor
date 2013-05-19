package com.carr.mitchell.zyxba_gui;

import com.japanzai.jreader.JxDialog;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import javax.swing.JMenuItem;

public class ButtonListener implements ActionListener {

	private final GUI parent;

	public ButtonListener(GUI gui) {
		this.parent = gui;
	}

	@Override
	public void actionPerformed(ActionEvent ae) {

		Object obj = ae.getSource();

		if (obj instanceof JMenuItem) {

			JMenuItem jbt = (JMenuItem) obj;
			String command = jbt.getText();

			if (command.equals(GUI.openCsv)) {
				JxDialog jdf = new JxDialog(new String[] { ".csv" });
				File f = jdf.showDialog();
				if (f != null) {
					parent.setCSV(f);
					parent.readCSV();
				}
			} else {
				ExportListener el = new ExportListener(parent);
				el.actionPerformed(ae);
			}

		}

	}

}
