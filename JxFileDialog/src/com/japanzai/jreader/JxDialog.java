package com.japanzai.jreader;

import com.japanzai.jreader.JItem;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.FlowLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.WindowEvent;
import java.io.File;
import java.util.ArrayList;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JMenuBar;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class JxDialog extends JDialog implements ActionListener, MouseListener, KeyListener{
	
	private static final long serialVersionUID = 1L;
	private static ArrayList<JItem> items = new ArrayList<JItem>();
	private static ImageIcon dirIcon = null;
	private static ImageIcon upIcon = null;
	private static ImageIcon archiveIcon = null;
	private static ImageIcon goIcon = null;
	
	private static JFrame frame;
	private static JPanel panel;
	private static JMenuBar menu;
	private static JMenuBar statusBar;
	private static JTextField address;
	
	private static JButton parentFolder;
	private static JButton goButton;
	private static JButton accept;
	private static JButton cancel;
	
	private static JxFolderDialog folderDialog;
	//private static JxArchiveDialog archiveDialog;
	private static JxFileDialog fileDialog;
	
	private static boolean allowArchives;
	
	private static File curDir = new File(System.getProperty("user.dir"));
	private static File returnFile = null;
	private static File selectedFile = null;
	
	private static String[] filter;
	private WindowEvent arv = new WindowEvent(this, WindowEvent.WINDOW_CLOSING);
	
	/**
	 *  Implement JScrollPane and/or JPanel //JPanel currently used
	 * TODO: Back and forward arrows. Accept button?
	 * TODO: String arraylists for back/forward buttons
	 * --One arraylist; compare current position to list size
	 * TODO: Add key listener for arrows to select other files
	 
	 *for later implementation, don't make visible, save setup, return public method
	 * */
	
	public JxDialog(String[] filteredFiles){
		super(frame, true);
		frame = new JFrame("JxDialog");
		
		allowArchives = filteredFiles != null;
		if (allowArchives){filter = filteredFiles;}
		
		
		panel = new JPanel();
		menu = new JMenuBar();
		statusBar = new JMenuBar();
		address = new JTextField(25);
		parentFolder = new JButton();
		goButton = new JButton();
		accept = new JButton("Open");
		cancel = new JButton("Cancel");
		
		//panel.addKeyListener(this);
		panel.addMouseListener(this);
		
		try{			

			dirIcon = new ImageIcon(getClass().getResource("/images/folder.png"));
			
			upIcon = new ImageIcon(getClass().getResource("/images/up.png"));
			
			archiveIcon = new ImageIcon(getClass().getResource("/images/archive.png"));
			
			goIcon = new ImageIcon(getClass().getResource("/images/go.png"));			
			
			//Back
			//Forward
		}catch(Exception e){
			System.out.println(e + "\nIcon loader");
		}
		//archiveDialog = new JxArchiveDialog(dirIcon, archiveIcon);
		folderDialog = new JxFolderDialog(dirIcon);
		fileDialog = new JxFileDialog(dirIcon, archiveIcon, filteredFiles);
		
		panel.setBackground(Color.WHITE);
		
		parentFolder.setIcon(upIcon);
		parentFolder.setActionCommand("Up");
		parentFolder.addActionListener(this);
		parentFolder.setToolTipText("Go to parent folder");
		
		goButton.setIcon(goIcon);
		goButton.setActionCommand("Go");
		goButton.addActionListener(this);
		goButton.setToolTipText("Go to path entered");
		
		accept.setActionCommand("Accept");
		accept.addActionListener(this);
		accept.setToolTipText("Choose file currently selected");
		
		cancel.setActionCommand("Cancel");
		cancel.addActionListener(this);
		cancel.setToolTipText("Exit without selecting a file");
		
		address.setActionCommand("Enter");
		address.addActionListener(this);
		
		//frame.setLayout(new BorderLayout());
		this.setLayout(new BorderLayout());
		panel.setLayout(new FlowLayout(FlowLayout.LEFT));
		menu.setLayout(new FlowLayout(FlowLayout.LEFT));
		statusBar.setLayout(new FlowLayout(FlowLayout.RIGHT));
		
		//frame.setJMenuBar(menu);
		//frame.add(panel);
		//frame.setResizable(false);
		this.setJMenuBar(menu);
		this.add(panel);
		this.add(statusBar, BorderLayout.AFTER_LAST_LINE);
		
		this.setResizable(false);
		/**
		* TODO: Make resizable? Only for external projects; not internal file browser
		*/
		menu.add(address);
		menu.add(goButton);
		menu.add(parentFolder);
		
		statusBar.add(accept);
		statusBar.add(cancel);
		
		this.setSize(600, 400);
		//this.setMinimumSize(menu.getSize());
		/**
		* TODO: Min size; correspond with menubar
		*/
		this.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		
		parseDir(curDir);
		address.setText(curDir.getAbsolutePath());

		panel.setDoubleBuffered(true);

	}
	
	private void tryPath(){
		String newDir = address.getText();
		if (!newDir.equals(curDir)){
			File newFile = new File(newDir);
			if (!newFile.exists()){
				address.setText(curDir.getAbsolutePath());
			}else if (newFile.isDirectory()){
				curDir = newFile.getAbsoluteFile();
				parseDir(curDir);
			}else{
				for (String extension : filter){
					if (newFile.getName().endsWith(extension)){ 
						returnFile = newFile;
						Close();
						return;
					}
				}
				System.out.println("Unsupported file type");
				curDir = new File(newFile.getParent());
				address.setText(curDir.getAbsolutePath());
				parseDir(curDir);
			}
		}
	}
	
	private void tryPath(String newPath){ //MouseClick
		if (!newPath.equals(curDir)){
			File newFile = new File(newPath);
			if (!newFile.exists()){
				address.setText(curDir.getAbsolutePath());
			}else if (newFile.isDirectory()){
				curDir = newFile.getAbsoluteFile();
				parseDir(curDir);
				address.setText(newPath);
			}else{
				returnFile = newFile;
				Close();
			}
		}
	}
	
	private void parseDir(File path){
		purgeItems();
		items = allowArchives ? fileDialog.parseDir(path) : folderDialog.parseDir(path);
		for (JItem item : items){
			panel.add(item.getJLabel());
			item.setToolTipText(item.getPath());
			item.setFocusable(true);
			item.addMouseListener(this);
			item.addKeyListener(this);
		}
		accept.setEnabled(false);
		selectedFile = null;
		panel.updateUI();
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		//System.out.println(e.getActionCommand());
		String command = e.getActionCommand();
		if (command.equals("Go") || e.getActionCommand().equals("Enter")){tryPath();}
		else if (command.equals("Up") && curDir.getParent() != null){
			curDir = curDir.getParentFile();
			parseDir(curDir);
			address.setText(curDir.getAbsolutePath());
		}else if (command.equals("Accept")){tryPath(selectedFile.getAbsolutePath());}
		else if (command.equals("Cancel")){Close();}
	}
	
	private void purgeItems(){
		items.clear();
		panel.removeAll();
	}
	
	
	public File getReturnFile(){return returnFile;}
	
	public void mousePressed(MouseEvent me) {
		//System.out.println(me.getSource());
		if (me.getSource().toString().startsWith("com.japanzai.jreader.JItem")){
			JItem j = (JItem) me.getSource();
			if (me.getClickCount() == 2){tryPath(j.getPath());}
			else if(me.getClickCount() == 1){setFocus(j);}
		}else{
			setFocus(null);
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
	
	private void setFocus(JItem j){
		for (JItem item : items){item.setNotSelected();}
		if (j != null){
			j.setSelected();
			j.requestFocusInWindow();
			
			accept.setEnabled(true);
			accept.setText(j.getFile().isDirectory() ? "Open" : "Accept");
			selectedFile = j.getFile();
		}else{
			selectedFile = null;
		}
		panel.repaint();
		panel.updateUI();
	}
	
	
	public File showDialog() {
		panel.setVisible(true);
		menu.setVisible(true);
		address.setVisible(true);
		
		returnFile = null;
		setVisible(true);
		return getReturnFile();
	}
	
	
	private void Close(){Toolkit.getDefaultToolkit().getSystemEventQueue().postEvent(arv);}

	@Override
	public void keyPressed(KeyEvent arg0) {}

	@Override
	public void keyReleased(KeyEvent e) {
		//System.out.println(e.getKeyCode());
		if (e.getKeyCode() != 37 && e.getKeyCode() != 39){return;}
		for (int i = 0; i < items.size(); i++){
			if (items.get(i).getFile().getAbsolutePath().equals(
					selectedFile.getAbsolutePath())){
				rotateFile(e.getKeyCode(), i);
				return;
			}
		}
	}

	@Override
	public void keyTyped(KeyEvent arg0) {}
	
	private void rotateFile(int direction, int index){
		if (index == 0 && direction == 37){setFocus(items.get(items.size() - 1));}
		else if (index == items.size() - 1 && direction == 39){setFocus(items.get(0));}
		else{setFocus(items.get(direction == 37 ? --index : ++index));}
	}
}