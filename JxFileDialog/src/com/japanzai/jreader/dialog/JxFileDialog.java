package com.japanzai.jreader.dialog;

import javax.swing.ImageIcon;

import com.japanzai.jreader.JItem;
import com.japanzai.jreader.Pairing;

import java.io.File;
import java.util.ArrayList;

/**
 * FileChooser that allows for the navigation of directories and selection of files
 * */
public class JxFileDialog extends ChildDialog{
	
	private final ArrayList<Pairing> pairings;
	
	/**
	 * @param dirIcon Icon mapping to directories listed
	 * @param pairings List of Icon/Extension pairings
	 */
	protected JxFileDialog(ImageIcon dirIcon, ArrayList<Pairing> pairings){
		super(dirIcon);
		this.pairings = pairings;
	}
	
	@Override
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
	
	@Override
	protected JItem parseJItem(File file){
		
		if (file.isHidden() || !file.exists()){
			return null;
		}
		
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