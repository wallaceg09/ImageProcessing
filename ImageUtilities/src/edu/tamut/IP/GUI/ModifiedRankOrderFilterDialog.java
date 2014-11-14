package edu.tamut.IP.GUI;

import java.awt.Color;
import java.awt.List;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Collections;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JInternalFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import edu.tamut.IP.Filters.Median.medianFilter;
import edu.tamut.IP.ImageUtils.*;

public class ModifiedRankOrderFilterDialog extends JFrame implements ActionListener{
	//GUI component members
	private JPanel optionPanel;
	private JButton filterButton;
	private JTextField thresholdText, kernelText;
	//end GUI component members
	
	//Misc members
	JInternalFrame parent;
	nImage image;
	
	public ModifiedRankOrderFilterDialog(JInternalFrame parent, nImage image){
		this.parent = parent;
		this.image = image;
		
		init();
		this.setVisible(true);
	}
	
	private void init(){
		this.getContentPane().setLayout(new BoxLayout(this.getContentPane(), BoxLayout.PAGE_AXIS));//vertical layout
		this.setSize(150,  150);
		this.setTitle("Rank Order Median Filter");
		
		initOptionPanel();
		this.add(optionPanel);
		
		filterButton = new JButton("Filter");
		filterButton.addActionListener(this);
		this.add(filterButton);
		
	}
	
	private void initOptionPanel(){
		optionPanel=new JPanel();
		optionPanel.setLayout(new BoxLayout(optionPanel, BoxLayout.PAGE_AXIS));//vertical layout
		
		JPanel thresholdPanel = new JPanel();//holds the threshold text field and label to prevent their resizing being manipulated by the BoxLayout of optionPanel
		
		thresholdText = new JTextField("20");
		thresholdText.setColumns(3);
		
		thresholdPanel.add(new JLabel("Threshold: "));
		thresholdPanel.add(thresholdText);
		
		JPanel kernelPanel = new JPanel();
		
		kernelText = new JTextField("3");
		kernelText.setColumns(2);
		
		kernelPanel.add(new JLabel("Kernel: "));
		kernelPanel.add(kernelText);
		
		optionPanel.add(thresholdPanel);
		optionPanel.add(kernelPanel);
		
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		Object source = e.getSource();
		
		if(source == filterButton){
			System.out.println("Filtering...");
			try {
				int kernelInput = Integer.parseInt(kernelText.getText());
				int thresholdInput = Integer.parseInt(thresholdText.getText());
				
				iKernel kernel = new iKernel(kernelInput);
				BufferedImage filteredResult = filter(kernel, thresholdInput);
				this.parent.getDesktopPane().add(new ImageInternalFrame(new nImage (filteredResult), IUtils.NameFromFile(this.image.getFile())+"RankOrderModLum"));
				JOptionPane.showMessageDialog(parent.getParent(), "Filter complete");
			} catch (NumberFormatException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
				JOptionPane.showConfirmDialog(this, "Please enter a valid integer");
				return;
			} 
			
		}
		
	}
	
	private BufferedImage filter(iKernel kernel, int threshold){
		int noiseCount = 0;//debugging how many noisy pixels 
		int minWindowSize = kernel.getSize()/2;//minimum size of the window before incrementing the size of the mirror.
		iKernel alternateKernel = new iKernel(kernel.getWidth()+2, kernel.getHeight()+2);//the alternate kernel size in the case of clumping
		
		
		BufferedImage filteredResult = IUtils.deepCopy(this.image.getLuminosity());//create a deep copy of the luminosity chanel
		BufferedImage filterInput_base = IUtils.mirrorImage(this.image.getLuminosity(), kernel);
		BufferedImage filterInput_increment = IUtils.mirrorImage(this.image.getLuminosity(), alternateKernel);
		boolean[][] noiseMap = new boolean[this.image.getImage().getHeight()][this.image.getImage().getWidth()];
		
		
		
		//First loop to determine if the pixel is considered noise
		//FIXME: Too many are being falsely attributed as noise!
		for( int row =0; row < filteredResult.getHeight(); row++){
			for( int col =0; col < filteredResult.getWidth(); col++){
				Window currentWindowColor = new Window(row, col, filterInput_base, kernel);
				ArrayList<Integer> currentWindowLum = currentWindowColor.getChannel(Channel.RED);//only works on the luminosity channel for now, so it assumes the red channel is the same as the rest of the channels
//				int currentTarget = currentWindowLum.get(currentWindowLum.size()/2);
				int currentTarget = new Color(filteredResult.getRGB(col, row)).getRed();
				Collections.sort(currentWindowLum);
				int end = currentWindowLum.size()-1;
				
				//check to see if the currentTarget (the center pixel of the unsorted series) is at the beginning of the window. 
				if(currentWindowLum.get(0).equals(currentTarget)){
					//If it is then determine if the current target is within a threshold of it's three closest neighbors
					if(	(currentWindowLum.get(1)-currentTarget) >= threshold ||//FIXME: checking to see if it's greater than, if not change back to less than!
						(currentWindowLum.get(2)-currentTarget) >= threshold ||
						(currentWindowLum.get(3)-currentTarget) >= threshold){	//if it is, then mark the current position as noisy on the noisemap
						
						noiseMap[row][col] = true;
						noiseCount++;
					}//end if
				}//end if
				else if(currentWindowLum.get(end).equals(currentTarget)){//otherwise check to see if the currentTarget is at the end of the window
					if(	(currentTarget - currentWindowLum.get(end-1) >= threshold) ||//FIXME: checking to see if it's greater than, if not change back to less than!
						(currentTarget - currentWindowLum.get(end-2) >= threshold) ||
						(currentTarget - currentWindowLum.get(end-3) >= threshold)){//if it is, then mark the current position as noisy on the noisemap
						
						noiseMap[row][col] = true;	
						noiseCount++;
					}//end if
							
				}//end elseif
			}//end for col
		}//end for row
		
		//second loop to actually filter the image
		boolean[][] mirroredMap_base = IUtils.mirrorNoiseMap(noiseMap, kernel);//base mirroring of the noisemap
		boolean[][] mirroredMap_increment = IUtils.mirrorNoiseMap(noiseMap, alternateKernel);//incremented mirroring of the noiseMap
		
		for(int row = 0; row < filteredResult.getHeight(); row++){
			for(int col = 0; col < filteredResult.getWidth(); col++){
				Window currentWindowColor = new Window(row, col, filterInput_base, kernel, mirroredMap_base);
				if(currentWindowColor.getChannel(Channel.RED).size() < minWindowSize){
					currentWindowColor = new Window(row, col, filterInput_increment, alternateKernel, mirroredMap_increment);
				}
				
				ArrayList<Integer> currentWindowLum = currentWindowColor.getChannel(Channel.RED);
				
				
				
				int result = 0;
				
				
				try {
					result = medianFilter.medianFilter(currentWindowLum, null);
					filteredResult.setRGB(col, row, new Color(result, result, result).getRGB());
				} catch (IndexOutOfBoundsException e2) {
					System.err.println(String.format("Row: %d, Col: %d",row, col));
					e2.printStackTrace();
					
				}
		
				
				
			}//end for row
		}//end for col
		System.out.println(noiseCount);
		return filteredResult;
	}//end filter

}
