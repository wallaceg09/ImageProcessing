package edu.tamut.IP.ImageUtils;
/*
 * http://docs.oracle.com/javase/tutorial/uiswing/components/internalframe.html#decorate
 */
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JDialog;
import javax.swing.JInternalFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.border.BevelBorder;
import javax.swing.event.InternalFrameEvent;
import javax.swing.event.InternalFrameListener;

import edu.tamut.IP.GUI.GUIMain;
import edu.tamut.IP.GUI.MedianFilterDialog;
import edu.tamut.IP.GUI.ModifiedRankOrderFilterDialog;
import edu.tamut.IP.GUI.RankEVDialog;
import edu.tamut.IP.GUI.TestFilterDialog;
import edu.tamut.IP.GUI.UnsharpMaskDialog;
import edu.tamut.IP.color_utilities.YUV;



//TODO: move to edu.tamut.IP.GUI

public class ImageInternalFrame extends JInternalFrame implements InternalFrameListener,
																	ActionListener{
	nImage image;
	JPanel statusBar = new JPanel();//status bar
	JLabel statusLabel; //status label (prints a string to the GUI)
	//menu Items
	JMenuBar menuBar;
	JMenuItem saveMenuItem, statistics,  statisticsMenuItem, luminosityMenuItem;
	JMenuItem testFilter,  testYUV, rankEVFilter, unsharpMaskFilter, simpleMedianFilter, modifiedRankMedian;//filter menu items
	JMenu filterMenu, fileMenu, statisticsMenu;
	
	//menu stuff
	public void createFilterMenu(){
		filterMenu=new JMenu("Filters");//create the filterMenu
		
		testFilter=new JMenuItem("Test Filter");
		testFilter.addActionListener(this);
		
		testYUV = new JMenuItem("Test YUV conversion");//tests the YUV converter
		testYUV.addActionListener(this);
		
		rankEVFilter = new JMenuItem("Rank EV filter");
		rankEVFilter.addActionListener(this);
		
		simpleMedianFilter = new JMenuItem("Mask Median filter");
		simpleMedianFilter.addActionListener(this);
		
		unsharpMaskFilter = new JMenuItem("Unsharp Mask");
		unsharpMaskFilter.addActionListener(this);
		
		modifiedRankMedian = new JMenuItem("Mod. Rank Median Filter");
		modifiedRankMedian.addActionListener(this);
		
		//filterMenu.add(testFilter);
		//filterMenu.add(testYUV);
		filterMenu.add(rankEVFilter);
		filterMenu.add(unsharpMaskFilter);
		filterMenu.add(simpleMedianFilter);
		filterMenu.add(modifiedRankMedian);
		
		
	}
	public void createFileMenu(){
		this.fileMenu = new JMenu("File");
		
		this.saveMenuItem = new JMenuItem("Save", new ImageIcon("Icons/save-1.png"));
		try{ 
			ImageIcon imIc = new ImageIcon("save.png");
			System.out.println(imIc.toString());
		}catch (Exception e){
			System.err.println(e);
		}
		//this.saveMenuItem.setIcon();
		saveMenuItem.addActionListener(this);
		this.fileMenu.add(saveMenuItem);
		//TODO: Possibly make a luminosity sub-menu with display, new from image (creates a new image based on the luminosity), and a save (save the luminosity as it's own image)
		this.luminosityMenuItem = new JMenuItem("Luminosity");
		this.luminosityMenuItem.addActionListener(this);
		this.fileMenu.add(luminosityMenuItem);
	}
	public void createStatisticsMenu(){
		this.statisticsMenu = new JMenu("Statistics");
		this.statisticsMenuItem = new JMenuItem("Statistical Analysis");
		this.statisticsMenuItem.addActionListener(this);
		this.statisticsMenu.add(statisticsMenuItem);
		
	}
	
	public ImageInternalFrame(nImage image, String name){
		super(name , 	//header string
				false, 	//resizeable
				true, 	//closable
				false, 	//maximizable
				true);	//iconifiable (minimizeable)
		this.setLayout(new BorderLayout());//set the layout to border
		this.image = image;
		this.setSize(this.image.getImage().getWidth(), this.image.getImage().getHeight()+72);//set the size of the internal frame (add 72 for the status bar and menubar)
		this.getContentPane().add(new ImageCanvas(this.image.getImage()));//add an ImageCanvas to the contentPane
		this.setVisible(true); //visualize the frame
		this.addInternalFrameListener(this); //add InternalFrameListener to the frame to handle actions
		this.setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);//File closing is handled by internalFrameClosing()
		
		//Menubar
		this.menuBar = new JMenuBar();
		//this.menuBar.setPreferredSize(new Dimension(this.getWidth(), 32));
		//SaveMenuButton
		this.createFileMenu();
		this.menuBar.add(this.fileMenu);
		//filter menu
		this.createFilterMenu();
		this.menuBar.add(this.filterMenu);
		//statistics menu
		this.createStatisticsMenu();
		this.menuBar.add(this.statisticsMenu);
		
		this.setJMenuBar(menuBar);
		//Status Bar
		this.statusBar.setBorder(new BevelBorder(BevelBorder.LOWERED));//creates a bevel on the status bar
		this.add(statusBar, BorderLayout.SOUTH);//adds the statusBar to the bottom of the image frame
		this.statusBar.setPreferredSize(new Dimension(this.getWidth(), 16));//sets the preferred size to be the width of the internalFrame and height of 16 pixels
		statusBar.setLayout(new BoxLayout(statusBar, BoxLayout.X_AXIS));
		
		String status = String.format("Width: %d Height: %d", this.image.getImage().getWidth(), this.image.getImage().getHeight());
		statusLabel = new JLabel(status);
		
		statusBar.add(statusLabel);
		
	}
	public void internalFrameClosing(InternalFrameEvent e){//called if the frame is being called
		this.close();
	}
	public void internalFrameClosed(InternalFrameEvent e){
		
		
		
		
		//this.image.delete();
	}
	public void internalFrameOpened(InternalFrameEvent e){
		
	}
	public void internalFrameIconified(InternalFrameEvent e){
		
	}
	public void internalFrameDeiconified(InternalFrameEvent e){
		
	}
	public void internalFrameActivated(InternalFrameEvent e){
		System.out.println("Frame activated");
	}
	public void internalFrameDeactivated(InternalFrameEvent e){
		System.out.println("Frame deactivated");
	}
	public void actionPerformed(ActionEvent e){//handle actions
		Object source = e.getSource();
		//System.out.println(source.toString());
		if(source == saveMenuItem){
			File tempFile = this.image.getFile();
			File imageFile = ImageFileIO.SaveImageAs(this.image.getImage());
			if(imageFile == null){
				imageFile = tempFile;
			}
			this.image.setFile(imageFile);
			this.setTitle(IUtils.NameFromFile(imageFile));
		}
		else if(source == this.statisticsMenuItem){
			new statisticalAnalysisDialog(this.image);
		}
		else if(source == luminosityMenuItem){
			ImageInternalFrame luminosity = ImageFileIO.ReturnDisplayImage(new nImage(this.image.getLuminosity()), IUtils.NameFromFile(this.image.getFile()) + "-luminosity");
			GUIMain.getDesktop().add(luminosity);
			GUIMain.getInternalFrameList().add(luminosity);
		}
		else if(source == testFilter){
			//FIXME This isn't activating when I press the button.
			System.out.println("testFilter activated");
			new TestFilterDialog(this, this.image);
		}
		else if(source == testYUV){
			System.out.println("testYUV activated");
			Color testColor = new Color(this.image.getImage().getRGB(10, 10));
			YUV testYUV = new YUV(testColor);
			System.out.printf("R: %d, G: %d, B: %d\nY: %d, U: %f, V: %f\n", testColor.getRed(), testColor.getGreen(), testColor.getBlue(), testYUV.GetY(), testYUV.GetU(), testYUV.GetV() );
			testYUV.YUV2RGB();
			System.out.printf("R2: %d, G2: %d, B2: %d", testYUV.GetR(), testYUV.GetG(), testYUV.GetB());
		}
		else if(source == rankEVFilter){
			RankEVDialog rEV = new RankEVDialog(this, this.image);

			//rEV.setVisible(true);
		}else if(source == unsharpMaskFilter){
			new UnsharpMaskDialog(this, this.image);
		}
		else if(source == simpleMedianFilter){
			new MedianFilterDialog(this, this.image);
		}else if(source == modifiedRankMedian){
			new ModifiedRankOrderFilterDialog(this, this.image);
		}
	}
	
	//miscellaneous methods
	public nImage getImage(){
		return this.image;
	}
	public void close(){
		if(this.image.getFile()==null){	//If there is no file associated with the image then deploy an saveFileDialog
			//System.out.println("Closed a null file...");//debugging
			int choice = JOptionPane.showConfirmDialog(null, "The file is not saved to a disk, save the file?" , "Save File?", JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.WARNING_MESSAGE);
			switch (choice){
			case JOptionPane.YES_OPTION:
				ImageFileIO.SaveImageAs(this.image.getImage());
				break;
			case JOptionPane.NO_OPTION:
				this.dispose();
				GUIMain.getInternalFrameList().remove(this);
				break;
			case JOptionPane.CANCEL_OPTION:
				//this.setVisible(true);
				break;
			}
		}
		else { 
			this.dispose(); 
			GUIMain.getInternalFrameList().remove(this);
			}
	}
	
	private static class statisticalAnalysisDialog extends JDialog implements ActionListener{
		private JPanel contentPanel;
		private JLabel text;
		private JTabbedPane pane = new JTabbedPane(JTabbedPane.TOP);
		
		
		statisticalAnalysisDialog(nImage image){
			super();
			//TODO: Tidy this up
			this.setTitle("Statistical Analysis: " + IUtils.NameFromFile(image.getFile()));
			
			this.setPreferredSize(new Dimension(300,300));
			this.setResizable(false);
			this.setSize(new Dimension(400, 300));
			this.setResizable(false);
			
			//Content-----
			
			//add a tab for the red channel
			contentPanel  = new JPanel();
			contentPanel.setLayout(new BoxLayout(contentPanel,BoxLayout.PAGE_AXIS));
			//Add the mean to the contentPanel
			String currentString = String.format("Mean: %f", image.getMean().getRed());//formatted string 
			text = newText(currentString);//new textField with the mean
			contentPanel.add(text);
			//add the Variance to the contentPanel
			currentString = String.format("Variance: %f", image.getVariance().getRed());
			text = newText(currentString);
			contentPanel.add(text);
			//add standard Deviation to the content panel
			currentString = String.format("Standard Deviation: %f", image.getStandardDeviation().getRed());
			text = newText(currentString);
			contentPanel.add(text);
			//add min-x
			//TODO: find min/max x and min/max y
					
			pane.addTab("Red", contentPanel);
			//reset the contentPanel
			contentPanel = new JPanel();
			contentPanel.setLayout(new BoxLayout(contentPanel,BoxLayout.PAGE_AXIS));
			contentPanel.setLayout(new FlowLayout());
			//add tab for the green channel
			//Add the mean to the contentPanel
			currentString = String.format("Mean: %f", image.getMean().getGreen());//formatted string 
			text = newText(currentString);//new textField with the mean
			contentPanel.add(text);
			//add the Variance to the contentPanel
			currentString = String.format("Variance: %f", image.getVariance().getGreen());
			text = newText(currentString);
			contentPanel.add(text);
			//add standard Deviation to the content panel
			currentString = String.format("Standard Deviation: %f", image.getStandardDeviation().getGreen());
			text = newText(currentString);
			contentPanel.add(text);
			
			
			pane.addTab("Green", contentPanel);
			//reset the contentPanel
			contentPanel = new JPanel();
			contentPanel.setLayout(new BoxLayout(contentPanel,BoxLayout.PAGE_AXIS));
			contentPanel.setLayout(new FlowLayout());
			//add tab for the blue channel
			//Add the mean to the contentPanel
			currentString = String.format("Mean: %f", image.getMean().getBlue());//formatted string 
			text = newText(currentString);//new textField with the mean
			contentPanel.add(text);
			//add the Variance to the contentPanel
			currentString = String.format("Variance: %f", image.getVariance().getBlue());
			text = newText(currentString);
			contentPanel.add(text);
			//add standard Deviation to the content panel
			currentString = String.format("Standard Deviation: %f", image.getStandardDeviation().getBlue());
			text = newText(currentString);
			contentPanel.add(text);
			pane.addTab("Blue", contentPanel);
			
			this.add(pane);
			this.setVisible(true);
			
			
		}
		public JLabel newText(String input){//creates a new JTextField, disables editability, then returns it
			JLabel output = new JLabel(input);
			return output;
		}
		@Override
		public void actionPerformed(ActionEvent arg0) {
			// TODO Auto-generated method stub
			
		}
		
	}
	
}


