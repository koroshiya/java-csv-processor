package com.japanzai.jreader.dialog;

import javax.swing.ImageIcon;

import com.japanzai.jreader.JItem;

import java.io.File;
import java.util.ArrayList;

/**
 * FileChooser that allows for the navigation and selection of directories
 * */
public class JxFolderDialog extends ChildDialog{
	
	/**
	 * @param dirIcon Icon mapping to directories listed
	 */
	protected JxFolderDialog(ImageIcon dirIcon){
		super(dirIcon);
	}
	
	@Override
	protected ArrayList<JItem> parseDir(File path){
		
		if (!path.exists() || path.isFile()){
			return null;
		}
	
		ArrayList<JItem> items = new ArrayList<JItem>();
		File[] files = path.listFiles();
		
		for (File file : files){
			JItem jItem = parseJItem(file);
			if (jItem != null){items.add(jItem);}
		}
		
		return items;
		
	}
	
	@Override
	protected JItem parseJItem(File file){
		return (file.isDirectory() && !file.isHidden()) ? new JItem(file, dirIcon) : null;
	}
	
}