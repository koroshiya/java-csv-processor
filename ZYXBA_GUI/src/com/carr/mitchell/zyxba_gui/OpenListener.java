package com.carr.mitchell.zyxba_gui;

import com.japanzai.jreader.JxDialog;
import com.japanzai.jreader.Pairing;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.ArrayList;

import javax.swing.ImageIcon;

public class OpenListener implements ActionListener {

	private final GUI parent;
	private ArrayList<Pairing> pairings;

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

		parent.setCSV(f);

	}

}
