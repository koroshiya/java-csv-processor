package com.japanzai.jreader.listener;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;

import com.japanzai.jreader.JItem;
import com.japanzai.jreader.dialog.JxDialog;

public class DialogKeyListener implements KeyListener {
	
	private final JxDialog dialog;

	private final static int BACKWARD = 37;
	private final static int FORWARD = 39;
	
	/**
	 * @param dialog Dialog which the mouse events pertain to
	 */
	public DialogKeyListener(JxDialog dialog){
		this.dialog = dialog;
	}
	
	/**
	 * Changes which file is selected according to the currently selected
	 * file's index and the directional key pressed.
	 * @param direction Integer representing the directional key pressed
	 * @param index Index of the currently selected item
	 */
	private void rotateFile(int direction, int index){
		
		ArrayList<JItem> items = dialog.getItems();
		int resultingIndex;
		
		if (index == 0 && direction == BACKWARD){
			resultingIndex = items.size() - 1;
		}else if (index == items.size() - 1 && direction == FORWARD){
			resultingIndex = 0;
		}else{
			resultingIndex = direction == BACKWARD ? --index : ++index;
		}
		
		dialog.setFocus(items.get(resultingIndex));
		
	}
	
	@Override
	public void keyPressed(KeyEvent arg0) {}
	
	@Override
	public void keyReleased(KeyEvent e) {
		
		if (e.getKeyCode() != BACKWARD && e.getKeyCode() != FORWARD){
			return;
		}
		
		ArrayList<JItem> items = dialog.getItems();
		
		for (int i = 0; i < items.size(); i++){
			if (items.get(i).getFile().getAbsolutePath().equals(
					dialog.getSelectedFile().getAbsolutePath())){
				rotateFile(e.getKeyCode(), i);
				return;
			}
		}
		
	}
	
	@Override
	public void keyTyped(KeyEvent arg0) {}

}
