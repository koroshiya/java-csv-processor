package com.japanzai.jreader.listener;

import java.awt.Component;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import com.japanzai.jreader.JItem;
import com.japanzai.jreader.dialog.JxDialog;

public class DialogMouseListener implements MouseListener{
	
	private final JxDialog dialog;
	
	/**
	 * @param dialog Dialog which the mouse events pertain to
	 */
	public DialogMouseListener(JxDialog dialog){
		this.dialog = dialog;
	}
	
    @Override
	public void mousePressed(MouseEvent me) {
		
		if (me.getSource() instanceof JItem){
			JItem j = (JItem) me.getSource();
			int clickCount = me.getClickCount();
			if (clickCount == 2){dialog.tryPath(j.getPath());}
			else if(clickCount == 1){dialog.setFocus(j);}
		}else{
			dialog.setFocus(null);
			Component comp = (Component) me.getSource();
			comp.requestFocusInWindow();
		}
		
	}
	
	@Override
	public void mouseClicked(MouseEvent arg0) {}
	
	@Override
	public void mouseEntered(MouseEvent arg0) {}
	
	@Override
	public void mouseExited(MouseEvent arg0) {}
	
	@Override
	public void mouseReleased(MouseEvent arg0) {}

}
