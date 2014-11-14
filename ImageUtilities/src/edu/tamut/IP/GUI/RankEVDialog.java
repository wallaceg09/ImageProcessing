package edu.tamut.IP.GUI;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.awt.image.ColorModel;
import java.awt.image.WritableRaster;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.swing.BoxLayout;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JInternalFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTabbedPane;
import javax.swing.JTextField;


import edu.tamut.IP.Filters.RankEV.RankEV;
import edu.tamut.IP.ImageUtils.Channel;
import edu.tamut.IP.ImageUtils.IUtils;
import edu.tamut.IP.ImageUtils.ImageInternalFrame;
import edu.tamut.IP.ImageUtils.Window;
import edu.tamut.IP.ImageUtils.iKernel;
import edu.tamut.IP.ImageUtils.nImage;
import edu.tamut.IP.color_utilities.YUV;
import edu.tamut.IP.*;

public class RankEVDialog extends JFrame implements ActionListener {
	private JPanel optionsLuminosity, optionsRGB, mode, inputsLuminosity, inputsRGB, kernelPanelLuminosity, kernelPanelRGB, evPanelLuminosity, evPanelRed, evPanelGreen, evPanelBlue;
	private JTabbedPane tabbedPanelEV;
	private JTextField kernelInputLuminosity,kernelInputRGB, evInputLuminosity, evInputRed, evInputGreen, evInputBlue;
	private ButtonGroup radioButtons;
	private JRadioButton isRGB, isLuminosity;
	private JButton filterButtonLuminosity, filterButtonRGB;
	
	private nImage image;
	private JInternalFrame parent;
	
	
	public RankEVDialog(JInternalFrame parent, nImage image){
		this.image = image;
		this.parent = parent;
		init();
	}
	private void init(){//initialize all components
		System.out.println("Initializing RankEV");
		//set up the frame
		this.getContentPane().setLayout(new BoxLayout(this.getContentPane(), BoxLayout.PAGE_AXIS));
		this.setSize(250, 200);//width, height
		this.setTitle("Rank EV");
		this.setResizable(false);
		
		tabbedPanelEV = new JTabbedPane();

		
		//setup luminosity panels and their layouts--------------
		optionsLuminosity = new JPanel();//separates the options from the filter button
		optionsLuminosity.setLayout(new BoxLayout(optionsLuminosity, BoxLayout.PAGE_AXIS));//vertical layout
		
		inputsLuminosity = new JPanel();//separates the inputs from the filter modes. contained in options
		inputsLuminosity.setLayout(new BoxLayout(inputsLuminosity, BoxLayout.PAGE_AXIS));//vertical layout
		
		kernelPanelLuminosity = new JPanel();//separates the kernel input from the ev input. Contained in inputs.
		kernelPanelLuminosity.setLayout(new BoxLayout(kernelPanelLuminosity, BoxLayout.LINE_AXIS));//horizontal layout
		kernelPanelLuminosity.setSize(new Dimension(5,20));
		
		kernelInputLuminosity = new JTextField("3");//creates the kernel input text field
		kernelInputLuminosity.setMaximumSize(new Dimension(18,20));
		
		//kernel input
		kernelPanelLuminosity.add(new JLabel("Kernel: "));
		kernelPanelLuminosity.add(kernelInputLuminosity);

		//ev input
		evPanelLuminosity = new JPanel();//horizontal layout
		evPanelLuminosity.setLayout(new BoxLayout(evPanelLuminosity, BoxLayout.LINE_AXIS));
		
		evInputLuminosity = new JTextField(Double.toString(this.image.getStandardDeviation().getLuminosity()));
		evInputLuminosity.setMaximumSize(new Dimension(80, 20));
		
		evPanelLuminosity.add(new JLabel("EV: "));
		evPanelLuminosity.add(evInputLuminosity);
		
		//filter Button
		filterButtonLuminosity = new JButton("Filter");
		filterButtonLuminosity.addActionListener(this);
		
		optionsLuminosity.add(kernelPanelLuminosity);
		optionsLuminosity.add(evPanelLuminosity);
		optionsLuminosity.add(filterButtonLuminosity);
		
		
		tabbedPanelEV.addTab("Luminosity", optionsLuminosity);
		
		//End Luminosity panels-----------------
		
		//setup RGB panels ----------------------
		optionsRGB = new JPanel();//separates the options from the filter button
		optionsRGB.setLayout(new BoxLayout(optionsRGB, BoxLayout.PAGE_AXIS));//vertical layout
		
		kernelPanelRGB = new JPanel();//separates the kernel input from the ev input. Contained in inputs.
		kernelPanelRGB.setLayout(new BoxLayout(kernelPanelRGB, BoxLayout.LINE_AXIS));//horizontal layout
		kernelPanelRGB.setSize(new Dimension(5,20));
		
		kernelInputRGB = new JTextField("3");//creates the kernel input text field
		kernelInputRGB.setMaximumSize(new Dimension(18,20));
		
		kernelPanelRGB.add(new JLabel("Kernel: "));
		kernelPanelRGB.add(kernelInputRGB);
		
		evPanelRed = new JPanel();
		evPanelRed.setLayout(new BoxLayout(evPanelRed, BoxLayout.LINE_AXIS));//horizontal layout
		evPanelRed.setSize(new Dimension(5,20));
		
		evInputRed = new JTextField(Double.toString(this.image.getStandardDeviation().getRed()));
		evInputRed.setMaximumSize(new Dimension(80, 20));
		
		evPanelRed.add(new JLabel("EV Red: "));
		evPanelRed.add(evInputRed);
		
		evPanelGreen = new JPanel();
		evPanelGreen.setLayout(new BoxLayout(evPanelGreen, BoxLayout.LINE_AXIS));//hroizontal layout
		evPanelGreen.setSize(new Dimension(5,20));
		
		evInputGreen = new JTextField(Double.toString(this.image.getStandardDeviation().getGreen()));
		evInputGreen.setMaximumSize(new Dimension(80, 20));
		
		evPanelGreen.add(new JLabel("EV Green: "));
		evPanelGreen.add(evInputGreen);
		
		evPanelBlue = new JPanel();
		evPanelBlue.setLayout(new BoxLayout(evPanelBlue, BoxLayout.LINE_AXIS));//horizontal layout
		
		evInputBlue = new JTextField(Double.toString(this.image.getStandardDeviation().getBlue()));
		evInputBlue.setMaximumSize(new Dimension(80, 20));
		
		evPanelBlue.add(new JLabel("EV Blue: "));
		evPanelBlue.add(evInputBlue);
		
		filterButtonRGB = new JButton("Filter");
		filterButtonRGB.addActionListener(this);
		
		optionsRGB.add(kernelPanelRGB);
		optionsRGB.add(evPanelRed);
		optionsRGB.add(evPanelGreen);
		optionsRGB.add(evPanelBlue);
		optionsRGB.add(filterButtonRGB);
		
		tabbedPanelEV.addTab("RGB", optionsRGB);
		
		//end RGB panels ------------------------
		
		this.add(tabbedPanelEV);
		this.setVisible(true);
		
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		Object source = e.getSource();
		
		if (source == filterButtonLuminosity){
			filterLuminosity();
		}
		else if(source == filterButtonRGB){
			System.out.println("RankEV RGB filter initiating");
			filterRGB();
		}
		
	}
	
	private void filterLuminosity(){//filters the luminosity channel and returns a grayscale image of only the luminosity channel

		BufferedImage filteredResult = IUtils.deepCopy(this.image.getLuminosity());//create a deep copy of the luminosity channel.
		int kernelInput = Integer.parseInt(kernelInputLuminosity.getText());
		iKernel kernel = new iKernel(kernelInput, kernelInput);
		BufferedImage filterInput = IUtils.mirrorImage(this.image.getLuminosity(), kernel);
		Color currentColor;
		
		for(int row = 0; row < filteredResult.getHeight(); row++){
			for( int col = 0; col < filteredResult.getWidth(); col++){
				Window currentWindow = new Window(row, col, filterInput, kernel);
				int result = (int) RankEV.EVFilter(Double.parseDouble(evInputLuminosity.getText()), currentWindow.getChannel(Channel.RED));
				currentColor = new Color(result, result, result);
				filteredResult.setRGB(col, row, currentColor.getRGB());
			}
		}
		//TODO: add this to the vector of unsaved images
		this.parent.getDesktopPane().add(new ImageInternalFrame(new nImage(filteredResult), IUtils.NameFromFile(this.image.getFile())+"-RankEVLum"));
		JOptionPane.showMessageDialog(this.parent.getParent(), "Filter Successful!");
	}
	private void filterColorLuminosity(){//this will filter the Y channel then merge it back with the UV channels to get a new RGB image
		BufferedImage filteredResult = IUtils.deepCopy(this.image.getImage());//create a deep copy of the image
		int kernelInput = Integer.parseInt(kernelInputLuminosity.getText());//grab the information from the textBox
		iKernel kernel = new iKernel(kernelInput, kernelInput);
		BufferedImage filterInput = IUtils.mirrorImage(this.image.getImage(), kernel);
		Color currentColor;
		
		for(int row = 0; row < filteredResult.getHeight(); row++){
			for(int col = 0; col < filteredResult.getWidth(); col++){
				YUV currentYUV = new YUV(new Color(filterInput.getRGB(col+kernel.ColMod(), row+kernel.RowMod())));//grab the YUV for the current pixel (the modifiers must be added)
				ArrayList luminosity = new ArrayList();
				Window currentWindowRGB = new Window(row, col, filterInput, kernel);
				for(Color color : currentWindowRGB.getColors()){
					luminosity.add(new YUV(color));
					
				}//end luminosity conversion
				int result = (int)RankEV.EVFilter(Double.parseDouble(evInputLuminosity.getText()), luminosity);
				//TODO: finish
			}//end col iteration
		}//end row iteration
	}//end method
	private void filterRGB(){
		System.out.println("Entering EV RGB Filter");
		BufferedImage filteredResult = IUtils.deepCopy(this.image.getImage());
		int kernelInput = Integer.parseInt(kernelInputRGB.getText());
		iKernel kernel = new iKernel(kernelInput, kernelInput);
		BufferedImage filterInput = IUtils.mirrorImage(this.image.getImage(), kernel);
		Color currentColor;
		
		for(int row = 0; row < filteredResult.getHeight(); row++){
			for(int col = 0; col < filteredResult.getWidth(); col++){
				Window currentWindow = new Window(row, col, filterInput, kernel);
				int redChannel = (int) RankEV.EVFilter(Double.parseDouble(evInputRed.getText()), currentWindow.getChannel(Channel.RED));
				int greenChannel = (int) RankEV.EVFilter(Double.parseDouble(evInputGreen.getText()), currentWindow.getChannel(Channel.GREEN));
				int blueChannel = (int)	RankEV.EVFilter(Double.parseDouble(evInputBlue.getText()), currentWindow.getChannel(Channel.BLUE));
				
				currentColor = new Color(redChannel, greenChannel, blueChannel);
				filteredResult.setRGB(col, row, currentColor.getRGB());
			}
		}
		this.parent.getDesktopPane().add(new ImageInternalFrame(new nImage(filteredResult), IUtils.NameFromFile(this.image.getFile())+"rankEVfilterRGB"));
		JOptionPane.showMessageDialog(this.parent.getParent(), "Filter successful!");
	}
	

	

}
