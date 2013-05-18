package com.japanzai.jreader;

import javax.swing.ImageIcon;
import java.io.File;
import java.util.ArrayList;

public class JxFileDialog {
	
	private final ImageIcon dirIcon;
	private final ImageIcon fileIcon;
	private final String[] filter;
	
	public JxFileDialog(ImageIcon dirIcon, ImageIcon fileIcon, String[] filters){
		this.dirIcon = dirIcon;
		this.fileIcon = fileIcon;
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
					icon = fileIcon;
					break;
				}
			}
			if (icon == null){return null;}
			icon = fileIcon;
		}else{icon = dirIcon;}
		return new JItem(file, icon);		
	}
	
}