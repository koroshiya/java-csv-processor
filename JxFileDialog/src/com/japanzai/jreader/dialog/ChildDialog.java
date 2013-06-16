package com.japanzai.jreader.dialog;

import java.io.File;
import java.util.ArrayList;

import javax.swing.ImageIcon;

import com.japanzai.jreader.JItem;

public abstract class ChildDialog {
	
	protected final ImageIcon dirIcon;
	
	/**
	 * @param dirIcon Icon shown for directories to be displayed
	 */
	public ChildDialog(ImageIcon dirIcon){
		this.dirIcon = dirIcon;
	}
	
	/**
	 * @param path Directory to parse JItems from within
	 * @return List of JItem objects within the directory passed in
	 */
	protected abstract ArrayList<JItem> parseDir(File path);
	
	/**
	 * @param file File to turn into a JItem
	 * @return JItem encompassing the file passed in
	 */
	protected abstract JItem parseJItem(File file);
	
}
