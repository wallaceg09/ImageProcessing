package edu.tamut.IP.GUI;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;

import edu.tamut.IP.Filters.Median.medianFilter;
import edu.tamut.IP.ImageUtils.Channel;
import edu.tamut.IP.ImageUtils.IUtils;
import edu.tamut.IP.ImageUtils.ImageInternalFrame;
import edu.tamut.IP.ImageUtils.Window;
import edu.tamut.IP.ImageUtils.iKernel;
import edu.tamut.IP.ImageUtils.nImage;

public class MedianFilterDialog extends JFrame implements ActionListener {
	
	JCheckBox[] mask, maskLuminosityThree_Three, maskLuminosityFive_Five;
	JCheckBox isGrayScale, redChannel, greenChannel, BlueChannel;
	JPanel maskPanel, maskPanelThree_Three, maskPanelFive_Five;
	JTabbedPane channelTabbedPane;
	JTabbedPane kernelTabbedPaneLuminosity, kernelTabbedPaneRGB;
	
	JButton filterLumButtonThree_Three, filterLumButtonFive_Five;
	
	nImage image;
	ImageInternalFrame parent;
	
	public MedianFilterDialog(ImageInternalFrame parent, nImage image){
		this.parent = parent;
		this.image = image;
		init();		
		
	}
	
	private void init(){
		this.setSize(125, 250);
		this.setResizable(false);
		
		channelTabbedPane = new JTabbedPane();
		
		/*mask = new JCheckBox[9];
		
		channelTabbedPane = new JTabbedPane();
		
		maskPanel = new JPanel();
		maskPanel.setLayout(new GridLayout(3,3));
		for(int i = 0; i < 9; i++){
			mask[i]=new JCheckBox();
			maskPanel.add(mask[i]);
		}
		this.add(maskPanel);
		*/
		initKernelTabbedPaneLuminosity();
		channelTabbedPane.addTab("Luminosity", kernelTabbedPaneLuminosity);
		this.add(channelTabbedPane);
		
		this.setVisible(true);
		
		
	}
	
	private void initLuminosityTab(){
		
	}
	private void initKernelTabbedPaneLuminosity(){
		kernelTabbedPaneLuminosity = new JTabbedPane();
		
		JPanel content = new JPanel();
		content.setLayout(new BoxLayout(content, BoxLayout.PAGE_AXIS));
		
		JPanel dummy = new JPanel();//this is to prevent the button panel from resizing. Adding the button panel to this dummy panel then adding the dummy to the content panel
		
		//start with 3x3
		maskLuminosityThree_Three = new JCheckBox[9];
		
		maskPanelThree_Three = new JPanel();
		maskPanelThree_Three.setLayout(new GridLayout(3,3));
		
		for(int i = 0; i < 9; i++){
			maskLuminosityThree_Three[i] = new JCheckBox();
			maskLuminosityThree_Three[i].setSelected(true);
			maskPanelThree_Three.add(maskLuminosityThree_Three[i]);
		}
		
		isGrayScale = new JCheckBox("Grayscale");
		
		filterLumButtonThree_Three = new JButton("Filter");
		filterLumButtonThree_Three.addActionListener(this);
		
		dummy.add(maskPanelThree_Three);
		
		content.add(dummy);
		content.add(isGrayScale);
		content.add(filterLumButtonThree_Three);
		
		kernelTabbedPaneLuminosity.addTab("3x3", content);
		
		//resetting the content panel
		content = new JPanel();
		content.setLayout(new BoxLayout(content, BoxLayout.PAGE_AXIS));
		
		dummy = new JPanel();//resetting the dummy
		//5x5
		maskLuminosityFive_Five = new JCheckBox[25];
		
		maskPanelFive_Five = new JPanel();
		maskPanelFive_Five.setLayout(new GridLayout(5,5));
		
		for(int i = 0; i < 25; i++){
			maskLuminosityFive_Five[i] = new JCheckBox();
			maskLuminosityFive_Five[i].setSelected(true);
			maskPanelFive_Five.add(maskLuminosityFive_Five[i]);
		}
		
		isGrayScale = new JCheckBox("Grayscale");
		
		filterLumButtonFive_Five = new JButton("Filter");
		filterLumButtonFive_Five.addActionListener(this);
		
		dummy.add(maskPanelFive_Five);
		
		content.add(dummy);
		content.add(isGrayScale);
		content.add(filterLumButtonFive_Five);
		
		kernelTabbedPaneLuminosity.addTab("5x5", content);

	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		Object source = e.getSource();
		if(source == filterLumButtonThree_Three){
			medianFilterThree_Three();
		}else if(source == filterLumButtonFive_Five){
			medianFilterFive_Five();
		}
		
	}

	

	private void medianFilterThree_Three() {
		// TODO Auto-generated method stub
		log("Median Filter 3x3");
		iKernel kernel = new iKernel(3);
		BufferedImage filteredResult = IUtils.deepCopy(this.image.getImage());
		BufferedImage filterInput = IUtils.mirrorImage(this.image.getImage(), kernel);
		ArrayList<Integer> maskedInput = new ArrayList<>();
		
		Color currentColor;
		log(isGrayScale.isSelected());
		if(isGrayScale.isSelected()){//if grayscale then don't convert to YUV
			log("Grayscale");
			for(int row = 0; row < filterInput.getHeight(); row++){
				for(int col = 0; col < filterInput.getWidth(); col++){
					Window currentWindow = new Window(row, col, filterInput, kernel);
					for(int i = 0; i < 9; i++){
						if(maskLuminosityThree_Three[i].isSelected()){
							maskedInput.add(currentWindow.getChannel(Channel.RED).get(i));
							log(String.format("Index: %d", i));
						}
					}
				}
			}
			
		}
		
	}
	private void medianFilterFive_Five() {
		// TODO Auto-generated method stub
		
	}
	private void log(Object arg){
		System.out.println(arg);
	}

	

}
