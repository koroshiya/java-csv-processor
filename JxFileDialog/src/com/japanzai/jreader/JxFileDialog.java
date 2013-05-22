package com.japanzai.jreader;

import javax.swing.ImageIcon;
import java.io.File;
import java.util.ArrayList;

public class JxFileDialog {
	
	private final ImageIcon dirIcon;
	private final ArrayList<Pairing> pairings;
	
	public JxFileDialog(ImageIcon dirIcon, ArrayList<Pairing> pairings){
		this.dirIcon = dirIcon;
		this.pairings = pairings;
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
			for (Pairing p : pairings){
				for (String s : p.getSupportedFileTypes()){
					if (file.getName().endsWith(s)){
						icon = p.getIcon();
						return new JItem(file, icon);
					}
				}
				
			}
			return null;
		}else{
			icon = dirIcon;
		}
		return new JItem(file, icon);
	}
	
}