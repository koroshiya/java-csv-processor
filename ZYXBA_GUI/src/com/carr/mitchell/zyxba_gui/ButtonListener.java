package com.carr.mitchell.zyxba_gui;

import com.japanzai.jreader.JxDialog;
import com.japanzai.jreader.Pairing;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.ArrayList;

import javax.swing.ImageIcon;
import javax.swing.JMenuItem;

public class ButtonListener implements ActionListener {

	private final GUI parent;
	private ArrayList<Pairing> pairings;

	public ButtonListener(GUI gui) {
		this.parent = gui;
		ImageIcon documentIcon = new ImageIcon(getClass().getResource("/images/document.png"));
		pairings = new ArrayList<Pairing>();
		pairings.add(new Pairing(documentIcon, ".csv"));
	}

	@Override
	public void actionPerformed(ActionEvent ae) {

		Object obj = ae.getSource();

		if (obj instanceof JMenuItem) {

			JMenuItem jbt = (JMenuItem) obj;
			String command = jbt.getText();

			if (command.equals(GUI.openCsv)) {
				JxDialog jdf = new JxDialog(pairings);
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
