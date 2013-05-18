package com.japanzai.jreader;

import javax.swing.ImageIcon;
import java.io.File;
import java.util.ArrayList;

public class JxArchiveDialog {
	
	private ImageIcon dirIcon = null;
	private ImageIcon archiveIcon = null;
	private String[] filter;
	
	public JxArchiveDialog(ImageIcon dirIcon, ImageIcon archiveIcon){
		this(dirIcon, archiveIcon, new String[]{".zip", ".rar"});
	}
	
	public JxArchiveDialog(ImageIcon dirIcon, ImageIcon archiveIcon, String[] filters){
		this.dirIcon = dirIcon;
		this.archiveIcon = archiveIcon;
		this.filter = filters;
	}
	
	public ArrayList<JItem> parseDir(File path){
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
		if (file.isHidden() || !file.exists()){return null;}
		ImageIcon icon = null;
		
		if (file.isFile()){
			for (String extension : filter){
				if (file.getName().endsWith(extension)){
					icon = archiveIcon;
					break;
				}
			}
			if (icon == null){return null;}
			icon = archiveIcon;
		}else{icon = dirIcon;}
		return new JItem(file, icon);		
	}	
}