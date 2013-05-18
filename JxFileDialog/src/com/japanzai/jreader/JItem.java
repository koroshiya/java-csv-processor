package com.japanzai.jreader;

import java.awt.Color;
import java.io.File;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JLabel;

public class JItem extends JLabel{

	private static final long serialVersionUID = 1L;
	private File file;
	private Color selectedColor = new Color(20, 0, 20, 20);  
	private Color nonSelectedColor = Color.WHITE;
	
	public JItem(File file, ImageIcon icon){
		super(icon);
		this.file = file;
		this.setText(file.getName());
		newLabel();
	}
	
	public JItem(String path, ImageIcon icon){
		super(icon);
		this.file = new File(path);
		this.setText(this.file.getName());
		newLabel();
	}
	
	private void newLabel(){
	    this.setBackground(nonSelectedColor);  
	    this.setBorder(null); 
	    this.setOpaque(true);
	}
	
	public String getPath(){
		if (this.file != null){return this.file.getAbsolutePath();}
		return "/";
	}
	
	public String getName(){
		if (this.file != null){return file.getName();}
		return "/";
	}
	
	public String getDir(){
		if (this.file != null){
			int instance = file.getAbsolutePath().lastIndexOf('/');
			String path = file.getAbsolutePath().substring(0, instance);
			return path;
		}
		return "/";
	}
	
	public File getFile(){return this.file.getAbsoluteFile();}
	
	public JLabel getJLabel(){return this;}
	
	public ImageIcon getImageIcon(){return (ImageIcon)this.getIcon();}
	
	public Icon getBaseIcon(){return this.getIcon();}
	
	public void setSelected(){
		this.setBackground(selectedColor);
		this.refresh();
	}
	
	public void setNotSelected(){
		this.setBackground(nonSelectedColor);
		this.refresh();
	}
	
	private void refresh(){
		this.updateUI();
		this.repaint();
		this.validate();
	}
}