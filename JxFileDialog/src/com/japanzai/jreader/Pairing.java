package com.japanzai.jreader;

import java.util.ArrayList;

import javax.swing.ImageIcon;

public class Pairing {

	private ArrayList<String> extensions;
	private final ImageIcon icon;
	
	public Pairing(ImageIcon icon, String extension){
		
		this.extensions = new ArrayList<String>();
		this.extensions.add(extension);
		this.icon = icon;
		
	}
	
	public Pairing(ImageIcon icon, String[] extensions){

		this.extensions = new ArrayList<String>();
		
		for (String s : extensions){
			this.extensions.add(s);
		}
		
		this.icon = icon;
		
	}
	
	public boolean isSupported(String fileName){
		
		for (String s : this.extensions){
			if (fileName.endsWith(s)){
				return true;
			}
		}
		
		return false;
		
	}
	
	public ArrayList<String> getSupportedFileTypes(){
		
		return this.extensions;
		
	}
	
	public ImageIcon getIcon(){
		return this.icon;
	}

}
