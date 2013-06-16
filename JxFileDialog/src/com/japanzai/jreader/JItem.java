package com.japanzai.jreader;

import java.awt.Color;
import java.io.File;
import javax.swing.ImageIcon;
import javax.swing.JLabel;

/**
 * Represents a file on disk as displayed in a file browser.
 * */
public class JItem extends JLabel{

	private static final long serialVersionUID = 1L;
	
	private File file;
	private Color selectedColor = new Color(20, 0, 20, 20);  
	private Color nonSelectedColor = Color.WHITE;
	
	/**
	 * @param file File which this object will represent
	 * @param icon Icon associated with this file
	 */
	public JItem(File file, ImageIcon icon){
		super(icon);
		this.file = file;
		this.setText(file.getName());
		this.setBackground(nonSelectedColor);  
	    this.setBorder(null); 
	    this.setOpaque(true);
	}
	
	/**
	 * @param path Path to the file which this object will represent
	 * @param icon Icon associated with this file
	 */
	public JItem(String path, ImageIcon icon){
		this(new File(path), icon);
	}
		
	/**
	 * @return Path to the file this object represents
	 */
	public String getPath(){
		return this.file != null ? this.file.getAbsolutePath() : "/";
	}
	
	/**
	 * @return Name of the file being held
	 */
	public String getName(){
		return this.file != null ? file.getName() : "/";
	}
	
	/**
	 * @return String representing directory the specified file is contained within.
	 * 			If the file is null, "/" is returned.
	 */
	public String getDir(){
		
		String path;
		
		if (this.file != null){
			String absPath = file.getAbsolutePath();
			path = absPath.substring(0, absPath.lastIndexOf('/'));
		}else{
			path = "/";
		}
		
		return path;
		
	}
	
	/**
	 * @return File held by this object
	 */
	public File getFile(){
		return this.file;
	}
		
	/**
	 * @return ImageIcon associated with the file this class holds
	 */
	public ImageIcon getImageIcon(){
		return (ImageIcon)this.getIcon();
	}
		
	/**
	 * Sets this item to selected. Changes the background color to match.
	 */
	public void setSelected(){
		this.setBackgroundColor(selectedColor);
	}
	
	/**
	 * Unselects this item. Changes the background color to match.
	 */
	public void setNotSelected(){
		this.setBackgroundColor(nonSelectedColor);
	}
	
	/**
	 * @param color Color to paint this item's background
	 */
	private void setBackgroundColor(Color color){
		this.setBackground(color);
		this.refresh();
	}
	
	/**
	 * Repaints this item. Called after the background color changes.
	 */
	private void refresh(){
		this.updateUI();
		this.repaint();
		this.validate();
	}
	
}