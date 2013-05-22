package com.japanzai.jreader;

import javax.swing.ImageIcon;
import java.io.File;
import java.util.ArrayList;

public class JxFolderDialog {
	
	private ImageIcon dirIcon = null;
	
	protected JxFolderDialog(ImageIcon dirIcon){this.dirIcon = dirIcon;}
	
	protected ArrayList<JItem> parseDir(File path){
		if (!path.exists() || path.isFile()){return null;}
	
		ArrayList<JItem> items = new ArrayList<JItem>();
		File[] files = path.listFiles();
		
		for (File file : files){
			JItem jItem = parseJItem(file);
			if (jItem != null){items.add(jItem);}
		}
		return items;
	}
	
	private JItem parseJItem(File file){
		return file.isDirectory() && !file.isHidden() ? new JItem(file, dirIcon) : null;
	}
}