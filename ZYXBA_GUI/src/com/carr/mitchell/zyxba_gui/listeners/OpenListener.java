package com.carr.mitchell.zyxba_gui.listeners;

import com.carr.mitchell.zyxba_gui.GUI;
import com.japanzai.jreader.Pairing;
import com.japanzai.jreader.dialog.JxDialog;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.ArrayList;

import javax.swing.ImageIcon;

/**
 * Listener for a JxDialog used to open CSV files
 * */
public class OpenListener implements ActionListener {

	private final GUI parent;
	private ArrayList<Pairing> pairings;

	/**
	 * @param parent GUI to send the file this class is responsible for opening.
	 */
	public OpenListener(GUI parent) {
		this.parent = parent;
		ImageIcon documentIcon = new ImageIcon(getClass().getResource("/images/document.png"));
		pairings = new ArrayList<Pairing>();
		pairings.add(new Pairing(documentIcon, ".csv"));
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		
		JxDialog jx = new JxDialog(this.pairings);
		File f = jx.showDialog();

		if (f != null){
			parent.setCSV(f);
		}

	}

}
