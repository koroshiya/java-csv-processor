package com.carr.mitchell.zyxba_gui.prompt;

import javax.swing.JDialog;

/**
 * Represents a prompt with two options.
 * A positive response maps to accept(),
 * a negative response maps to decline().
 * */
public abstract class Prompt extends JDialog {
	
	private static final long serialVersionUID = 1L;

	/**
	 * Action to be invoked if user responds with a positive response
	 */
	public abstract void accept();
	
	/**
	 * Action to be invoked if user responds with a negative response
	 */
	public abstract void decline();

	/**
	 * Sets a JDialog Modal and makes it visible
	 */
	public void display(){
		
		this.setModal(true);
		this.setVisible(true);
		
	}
	
}
