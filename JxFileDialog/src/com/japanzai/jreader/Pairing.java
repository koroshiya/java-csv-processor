package com.japanzai.jreader;

import java.util.ArrayList;

import javax.swing.ImageIcon;

/**
 * This class represents an extension to icon pairing.
 * eg. ZIP files might have a certain icon, while RAR files might have a different one.
 * Also supports multiple mappings for the same icon.
 * eg. ZIP and RAR might use the same icon
 * */
public class Pairing {

	private ArrayList<String> extensions;
	private final ImageIcon icon;
	
	/**
	 * @param icon Icon to pair to an extension
	 * @param extension Extension to associate with a particular icon
	 */
	public Pairing(ImageIcon icon, String extension){
		
		this(icon, new String[]{extension});
		
	}
	
	/**
	 * @param icon Icon to pair to an extension
	 * @param extensions Extensions to associate with a particular icon
	 */
	public Pairing(ImageIcon icon, String[] extensions){

		this.extensions = new ArrayList<String>();
		
		for (String s : extensions){
			this.extensions.add(s);
		}
		
		this.icon = icon;
		
	}
	
	/**
	 * @param fileName Name of the file to check if supported or not
	 * @return True if the file is supported, otherwise false.
	 */
	public boolean isSupported(String fileName){
		
		for (String s : this.extensions){
			if (fileName.endsWith(s)){
				return true;
			}
		}
		
		return false;
		
	}
	
	/**
	 * @return ArrayList of file types this Pairing maps to
	 */
	public ArrayList<String> getSupportedFileTypes(){
		
		return this.extensions;
		
	}
	
	/**
	 * @return Icon for this pairing
	 */
	public ImageIcon getIcon(){
		return this.icon;
	}

}
