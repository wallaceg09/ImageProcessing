package edu.tamut.IP.GUI;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Frame;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.ImageIcon;
import javax.swing.JCheckBoxMenuItem;
import javax.swing.JDesktopPane;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButtonMenuItem;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

import edu.tamut.IP.ImageUtils.IUtils;
import edu.tamut.IP.ImageUtils.ImageFileIO;
import edu.tamut.IP.ImageUtils.ImageInternalFrame;
import edu.tamut.IP.ImageUtils.iKernel;
import edu.tamut.IP.ImageUtils.nImage;
//Importing custom utilities class
/*
 * Author: 			Glen Wallace
 * Date: 			October 2012
 * Description:		Graphical User Interface for our Image Processing program
 * 
 * Notes:
 * 			1: Consider Affinetransform to allow zooming with a scroll wheel. http://www.javalobby.org/java/forums/t19387.html
 * 			2: BUG: Debug scrollpane will not show up if it's defaulted to not be shown, unless the GUI is resized.
 */

//TODO We need to create the images inside the frame so when the frame is closed, all references to the image are deleted
//TODO Add in the about section that icons were used from the open icon repository: http://openiconlibrary.sourceforge.net/
public class GUIMain extends JFrame implements ActionListener, WindowListener{

	
	//Delcaring various menu related variables. Not all will necessarily be used
	private JMenuBar menuBar;//Highest order menu container
	private JMenu fileMenu, debugMenu;//middle (menu) and lowest (submenu) order menu containers
	private JMenuItem open, saveAs, save;//File menu items
	
	private JMenuItem about; //about menuItem
	
	private JRadioButtonMenuItem rbMenuItem;//menu radio button
	private JCheckBoxMenuItem debugCheckBoxMenuItem;//menu checkbox [going to disable it]
	
	//Other GUI widget areas
	private JTextArea debugTextArea; //textArea containing debug outputsv[going to disable it]
	private JScrollPane debugScrollPane;//scrollpane to hold the text area [going to disable it]
	private static JDesktopPane desktop;//desktop, this allows internal frames
	
	//container holding all images
	private static List<ImageInternalFrame> internalFrameList = new ArrayList<ImageInternalFrame>();
	
	
	
	//Rectangle screen size 
	private Rectangle screenSize;
	

	
	//Menu Creation Methods
	private void createFileMenu(){//creates the file menu
		
		this.fileMenu = new JMenu("File");
		//Create the "Open" menuItem
		open = new JMenuItem("Open", new ImageIcon("Icons/document-open-8.png"));
		open.setToolTipText("Open a file");
		open.addActionListener(this);
		fileMenu.add(open);
		
		//Create the "Save As" menuItem
//		saveAs = new JMenuItem("Save As", new ImageIcon("Icons/save-1.png"));
//		saveAs.getAccessibleContext().setAccessibleDescription("Save an image as");
//		fileMenu.add(saveAs);
		
	}
	
	private void createDebugMenu(){//creates the debug menu
		debugMenu = new JMenu("Debug");
		//Create "debug" checkbox [disabled]
		
		debugCheckBoxMenuItem = new JCheckBoxMenuItem("Debug", true);
		debugCheckBoxMenuItem.setToolTipText("Toggles debug text area. Defaulted to on due to a bug");
		debugCheckBoxMenuItem.addItemListener(new ItemHandler());
		debugMenu.add(debugCheckBoxMenuItem); 
	}
	private void createMenuBar(){//creates the menu bar, and adds each menu to it
		menuBar = new JMenuBar();
		
		//Create the "File" menu
		this.createFileMenu();
		menuBar.add(fileMenu);
				
		//Create "debug" menu[disabled]
		//createDebugMenu();		
		
		//last to be added to the menubar: about
		about = new JMenuItem("About");
		about.addActionListener(this);
		menuBar.add(about);
	}
	//Dialog Creation Methods
	private JDialog createUnsavedImageDialog(){//creates the unsavedImageDialog box
		ImageUnsaveDialog unsaveWindow = new ImageUnsaveDialog(GUIMain.this);
		return unsaveWindow;
	}
	GUIMain(){
		super("Image Processing GUI");
		
		//Menu code-----------------------
		
		//Create a menubar
		this.createMenuBar();
		//end Menu code---
		
		//Frame code-----------------------------------------
		
		//adds the menuBar to the GUI Frame
		this.setJMenuBar(menuBar);
		
		//set the default to fullscreen
		this.setExtendedState(Frame.MAXIMIZED_BOTH);
		//set the default size of the frame
		Dimension dim = new Dimension(600, 500);
		this.setSize(dim);
		//Added a custom window handler to handle closing
		this.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		this.addWindowListener(this);
		
		//contentPane
		JPanel contentPanel = new JPanel(new BorderLayout());
		
		//desktop
		desktop = new JDesktopPane();
		//add the desktop to the contentPanel
		contentPanel.add(desktop, BorderLayout.CENTER);
		//add content pane to the frame
		this.add(contentPanel);
		
		//Debug window-----------------
		
		/*
		//dim = new Dimension(300,1000);
		debugTextArea = new JTextArea();
		debugTextArea.setVisible(true);
		debugTextArea.setEditable(false);
		//debugTextArea.setPreferredSize(dim);
		
		
		//Scrollpane for the debug text area[DISABLED]
		dim = new Dimension(300,300);
		debugScrollPane = new JScrollPane(debugTextArea);
		debugScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		//debugScrollPane.setVisible(false);
		debugScrollPane.setPreferredSize(dim);
		*/
		
		//end debug window--
		
		//add debugTextArea to the east side of the contentPanel [disabled]
		//contentPanel.add(debugScrollPane, BorderLayout.EAST);
		contentPanel.setVisible(true);
		
				
		//sets the MainGUI to be visible
		this.setVisible(true);
		
	}
	public static JDesktopPane getDesktop() {
		return desktop;
	}

	public static List<ImageInternalFrame> getInternalFrameList() {
		return internalFrameList;
	}

	public void actionPerformed(ActionEvent event){
		//Handles actions on buttons and menu items and such.
		Object source = event.getSource();
		//System.out.println(source);
		if(source == open){
			//If the Open menu item is pressed, then open an image and then display it (display is temporary)
			//BufferedImage img = ImageFileIO.OpenImage();
			nImage image = ImageFileIO.OpenImageIM();
			//String tempName = IUtils.NameFromFile(image.getFile());
			//imageNamesVector.add(tempName);
			//imageMap.put(image, tempName);
			//imageList.setListData(imageNamesVector);
			
			//ImageFileIO.DisplayImage(image.getImage());
			ImageInternalFrame currentFrame = ImageFileIO.ReturnDisplayImage(image);
			System.out.println(currentFrame.toString());
			desktop.add(currentFrame);
			internalFrameList.add(currentFrame);
			//System.out.println(internalFrameList.toString());
			
			//Testing some stuff. Will not remain in the program.
			
			//TestFilter filter = new TestFilter(image.getImage(), new iKernel(3,3));
			//System.out.println(img.getColorModel().toString());
			/*currentFrame = ImageFileIO.ReturnDisplayImage(new nImage(IUtils.mirrorImage(image.getImage(), new iKernel(3,3))));
			desktop.add(currentFrame);
			internalFrameList.add(currentFrame);*/
			
		}else if(source == about){//Display information about the software in a popup
			//JPanel aboutPane = new JPanel();
			String aboutString = 	"Texas A&M Texarkana Computer Science Major's Software Engineering project 2012\n"+
									"Authors: Dalton Holley, Willis Ellis, Hiroyuki Plumlee, Micheal C., Glen Wallace\n\n" +
									"Hiroyuki Plumlee: Rank EV programmer\n"+
									"Micheal C.: Median filter programmer\n" +
									"Willis Ellis: Unsharp mask filter programmer\n" +
									"Dalton Holley: Color ultilities programmer\n" +
									"Glen Wallace: Graphical User Interface programmer\n" +
									"--General Utility programmer\n" +
									"--Project Manager\n" +
									"--Mod. Rank Median filter programmer";
			JOptionPane.showMessageDialog(this, aboutString);
		}
		
	}
	//action handler for the debugMenu's checkBoxMenuItem. --Not used, can be ignored--
	public class ItemHandler implements ItemListener{	
		public void itemStateChanged(ItemEvent e){
			//What to do when it receives an event
			Object source = e.getItemSelectable();
			if(source == debugCheckBoxMenuItem)
				
			{//if the source is the debug check box then check the state
				//System.out.println(debugCheckBoxMenuItem.getState());
				if(debugCheckBoxMenuItem.getState()== true){
					//if the state is true then display the debug panel
					debugScrollPane.setVisible(true);
					
					
					
				}
				else{//otherwise don't
					//System.out.println("It's false");
					debugScrollPane.setVisible(false);
				}
				debugCheckBoxMenuItem.setVisible(true);
				//debugTextArea.append("great success\n");
				debugTextArea.append("Debug mode: " + debugCheckBoxMenuItem.getState() + "\n");
				
				
			}
			
			
		}
	}
	
	//Window Listener section
	@Override
	public void windowActivated(WindowEvent arg0) {
		
		
	}
	@Override
	public void windowClosed(WindowEvent arg0) {
	
		
	}
	@Override
	public void windowClosing(WindowEvent arg0) {
		
		
		ArrayList<ImageInternalFrame> unsavedFrames = new ArrayList<ImageInternalFrame>();//list of unsaved frames
		for(ImageInternalFrame frame : this.internalFrameList){
			if (frame.getImage().getFile()==null){
				unsavedFrames.add(frame);
			}
		}
		//System.out.println(String.format("%d unsaved images", unsavedFrames.size()));
		String formattedString = String.format("%d unsaved images", unsavedFrames.size());
		if(unsavedFrames.size()>0){
			
			int input = JOptionPane.showOptionDialog(	GUIMain.this, //parent
														"There are " + formattedString + "would you like to save them?",//message 
														formattedString, //title
														JOptionPane.YES_NO_CANCEL_OPTION,//option type 
														JOptionPane.WARNING_MESSAGE, //message type
														null, //icon
														null, //options
														null);//initial value
			switch(input){
			case JOptionPane.YES_OPTION:
				for(ImageInternalFrame frame : unsavedFrames){//iterate through each unsavedFrame
					frame.toFront();//brings the selected frame to the front of the dekstop (to tell what file you're saving)
					this.desktop.repaint();//repaints the desktop to ensure that the frame moved to the front is visualized.
					frame.close();//checks to see if the image has a file associated with it, if not then save dialog
				}
				this.dispose();
				System.exit(0);
				break;
			case JOptionPane.NO_OPTION:
				GUIMain.this.dispose();//close the entire program.
				System.exit(0);
				break;
			case JOptionPane.CANCEL_OPTION:
				break;
			
			}
			
		}
		else {this.dispose(); System.exit(0);}
		
	}
	@Override
	public void windowDeactivated(WindowEvent arg0) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void windowDeiconified(WindowEvent arg0) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void windowIconified(WindowEvent arg0) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void windowOpened(WindowEvent arg0) {
		// TODO Auto-generated method stub
		
	}
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		final JFrame GUIMain = new GUIMain();
		GUIMain.setIconImage(new ImageIcon("Icons/pictogram-din-w005-radioactive.png").getImage());
		

	}
}
