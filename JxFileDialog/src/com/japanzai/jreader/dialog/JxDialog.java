package com.japanzai.jreader.dialog;

import com.japanzai.jreader.JItem;
import com.japanzai.jreader.Pairing;
import com.japanzai.jreader.listener.DialogActionListener;
import com.japanzai.jreader.listener.DialogKeyListener;
import com.japanzai.jreader.listener.DialogMouseListener;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Toolkit;
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

/**
 * Custom FileChooser dialog where developers can choose the icons and file types applicable.
 * */
public class JxDialog extends JDialog {
	
	private static final long serialVersionUID = 1L;
	
	private ArrayList<JItem> items = new ArrayList<JItem>();
	private ImageIcon dirIcon = null;
	private ImageIcon upIcon = null;
	private ImageIcon goIcon = null;
	
	private static final JFrame frame = new JFrame("JxDialog");
	private JPanel panel;
	private JMenuBar menu;
	private JMenuBar statusBar;
	private JTextField address;
	
	private JButton parentFolder;
	private JButton goButton;
	private JButton accept;
	private JButton cancel;
	
	private JxFolderDialog folderDialog;
	private JxFileDialog fileDialog;
	
	private boolean allowFiles;

	public static final String GO = "Go";
	public static final String ENTER = "Enter";
	public static final String UP = "Up";
	public static final String ACCEPT = "Accept";
	public static final String OPEN = "Open";
	public static final String CANCEL = "Cancel";

	private DialogMouseListener dialogMouseListener = new DialogMouseListener(this);
	private DialogKeyListener dialogKeyListener = new DialogKeyListener(this);
	
	private File curDir = new File(System.getProperty("user.home"));
	private File returnFile = null;
	private File selectedFile = null;
	
	private static ArrayList<String> filter;
	private WindowEvent arv = new WindowEvent(this, WindowEvent.WINDOW_CLOSING);
	
	/*
	 * Implement JScrollPane and/or JPanel //JPanel currently used
	 * TODO: Back and forward arrows.
	 * TODO: String arraylists for back/forward entries
	 * ^--One arraylist; compare current position to list size
	 * TODO: Add key listener for arrows to select other files
	 * */
	
	/**
	 * @param pairings List of Icon/Extension pairings.
	 * If null, only directories will be displayed.
	 */
	public JxDialog(ArrayList<Pairing> pairings){
		super(frame, true);
		
		allowFiles = pairings != null;
		
		if (allowFiles){
			filter = new ArrayList<String>();
			for (Pairing p : pairings){
				filter.addAll(p.getSupportedFileTypes());
			}
		}
		
		instantiateGUI(pairings);
		
	}
	
	/**
	 * @param pairings List of Icon/Extension pairings.
	 * If null, only directories will be displayed.
	 */
	private void instantiateGUI(ArrayList<Pairing> pairings){
		
		panel = new JPanel();
		menu = new JMenuBar();
		statusBar = new JMenuBar();
		address = new JTextField(25);
		parentFolder = new JButton();
		goButton = new JButton();
		accept = new JButton(OPEN);
		cancel = new JButton(CANCEL);
		
		panel.addMouseListener(dialogMouseListener);
		
		try{

			dirIcon = new ImageIcon(getClass().getResource("/images/folder.png"));
			
			upIcon = new ImageIcon(getClass().getResource("/images/up.png"));
			
			goIcon = new ImageIcon(getClass().getResource("/images/go.png"));
			
		}catch(Exception e){
			System.out.println(e + "\n" + "Icon loader");
		}
		
		folderDialog = new JxFolderDialog(dirIcon);
		fileDialog = new JxFileDialog(dirIcon, pairings);
		
		panel.setBackground(Color.WHITE);
		
		DialogActionListener dal = new DialogActionListener(this);
		
		parentFolder.setIcon(upIcon);
		parentFolder.setActionCommand(UP);
		parentFolder.addActionListener(dal);
		parentFolder.setToolTipText("Go to parent folder");
		
		goButton.setIcon(goIcon);
		goButton.setActionCommand(GO);
		goButton.addActionListener(dal);
		goButton.setToolTipText("Go to path entered");
		
		accept.setActionCommand(ACCEPT);
		accept.addActionListener(dal);
		accept.setToolTipText("Choose file currently selected");
		
		cancel.setActionCommand(CANCEL);
		cancel.addActionListener(dal);
		cancel.setToolTipText("Exit without selecting a file");
		
		address.setActionCommand(ENTER);
		address.addActionListener(dal);
		
		this.setLayout(new BorderLayout());
		panel.setLayout(new FlowLayout(FlowLayout.LEFT));
		menu.setLayout(new FlowLayout(FlowLayout.LEFT));
		statusBar.setLayout(new FlowLayout(FlowLayout.RIGHT));
		
		this.setJMenuBar(menu);
		this.add(panel);
		this.add(statusBar, BorderLayout.AFTER_LAST_LINE);
		
		this.setResizable(false);
		
		menu.add(address);
		menu.add(goButton);
		menu.add(parentFolder);
		
		statusBar.add(accept);
		statusBar.add(cancel);
		
		this.setSize(600, 400);
		this.setMinimumSize(new Dimension(600, 400));
		this.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		
		parseDir(curDir);
		address.setText(curDir.getAbsolutePath());

		panel.setDoubleBuffered(true);
		
	}
	
	/**
	 * Attempts to parse the path listed in the address bar.
	 * Doesn't chain to tryPath(String); this method can be fed nonsensical 
	 * text via the address bar and thus requires different validation.
	 * */
	public void tryPath(){
		
		File newDir = new File(address.getText());
		
		if (!newDir.equals(curDir)){
			
			if (!newDir.exists()){
				address.setText(curDir.getAbsolutePath());
			}else if (newDir.isDirectory()){
				curDir = newDir.getAbsoluteFile();
				parseDir(curDir);
			}else{
				for (String extension : filter){
					if (newDir.getName().endsWith(extension)){ 
						returnFile = newDir;
						Close();
						return;
					}
				}
				System.out.println("Unsupported file type");
				curDir = new File(newDir.getParent());
				address.setText(curDir.getAbsolutePath());
				parseDir(curDir);
			}
			
		}
		
	}
	
	/**
	 * Attempts to parse the path passed in.
	 * */
	public void tryPath(String newPath){
		
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
	
	/**
	 * @param path Path to the directory to parse for JItem objects
	 */
	public void parseDir(File path){
		
		this.curDir = path;
		purgeItems();
		items = allowFiles ? fileDialog.parseDir(path) : folderDialog.parseDir(path);
		
		for (JItem item : items){
			panel.add(item);
			item.setToolTipText(item.getPath());
			item.setFocusable(true);
			item.addMouseListener(dialogMouseListener);
			item.addKeyListener(dialogKeyListener);
		}
		
		accept.setEnabled(false);
		selectedFile = null;
		panel.updateUI();
		
	}

	/**
	 * Removes all JItem entries stored and clears those displayed
	 */
	private void purgeItems(){
		items.clear();
		panel.removeAll();
	}
		
	/**
	 * @return File to return to calling component.
	 */
	public File getReturnFile(){
		return returnFile;
	}
	
	/**
	 * @return File currently selected
	 */
	public File getSelectedFile(){
		return selectedFile;
	}
	
	/**
	 * @return Directory currently displaying the results for
	 */
	public File getCurrentDirectory(){
		return this.curDir;
	}
	
	/**
	 * @return List of JItem objects currently displayed
	 */
	public ArrayList<JItem> getItems(){
		return this.items;
	}
	
	/**
	 * @param text String to set in address bar
	 */
	public void setAddress(String text){
		this.address.setText(text);
	}
	
	/**
	 * @param j JItem to give focus to
	 */
	public void setFocus(JItem j){
		
		for (JItem item : items){
			item.setNotSelected();
		}
		
		if (j != null){
			j.setSelected();
			j.requestFocusInWindow();
			
			accept.setEnabled(true);
			accept.setText(j.getFile().isDirectory() ? OPEN : ACCEPT);
			selectedFile = j.getFile();
		}else{
			selectedFile = null;
		}
		
		panel.repaint();
		panel.updateUI();
		
	}
	
	/**
	 * @return File selected via dialog shown
	 */
	public File showDialog() {
		
		panel.setVisible(true);
		menu.setVisible(true);
		address.setVisible(true);
		
		returnFile = null;
		setVisible(true);
		
		return getReturnFile();
		
	}
	
	/**
	 * Calls the WindowClosing event expected of a dialog
	 */
	public void Close(){
		Toolkit.getDefaultToolkit().getSystemEventQueue().postEvent(arv);
	}
	
}