package edu.tamut.IP.GUI;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.JTextField;

import edu.tamut.IP.Filters.Unsharp.sharp;
import edu.tamut.IP.ImageUtils.Channel;
import edu.tamut.IP.ImageUtils.IUtils;
import edu.tamut.IP.ImageUtils.ImageInternalFrame;
import edu.tamut.IP.ImageUtils.Window;
import edu.tamut.IP.ImageUtils.iKernel;
import edu.tamut.IP.ImageUtils.nImage;

public class UnsharpMaskDialog extends JFrame implements ActionListener{

	private JTabbedPane tabbedPanelUnsharp;
	private JPanel weightPanel, content;
	private JTextField weightText;
	private JButton filterButton;
	
	private ImageInternalFrame parent;
	private nImage image;
	
	BufferedImage filteredImage;
	BufferedImage mirroredImage;
	
	
	public UnsharpMaskDialog(ImageInternalFrame parent, nImage image){
		this.parent = parent;
		this.image=image;
		
		init();
	}
	
	public void init(){
		this.getContentPane().setLayout(new BoxLayout(this.getContentPane(), BoxLayout.PAGE_AXIS));//vertical layout
		this.setSize(200, 100);
		this.setResizable(false);
		
		
		weightPanel = new JPanel();
		weightPanel.setLayout(new BoxLayout(weightPanel, BoxLayout.LINE_AXIS));//horizontal layout
		
		weightText = new JTextField("1.5");
		weightText.setMaximumSize(new Dimension(20,18));
		
		weightPanel.add(new JLabel("Weight: "));
		weightPanel.add(weightText);
		
		filterButton = new JButton("Sharpen");
		filterButton.addActionListener(this);
		
		this.add(weightPanel);
		this.add(filterButton);
		
		this.setVisible(true);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		Object source = e.getSource();
		if(source == filterButton){
			
			prepareImage();
			System.out.println(mirroredImage.getWidth() + " " + mirroredImage.getHeight());
			filterRGB();
		}
		
	}
	private void prepareImage(){
		filteredImage = IUtils.deepCopy(this.image.getImage());
		mirroredImage = IUtils.mirrorImage(this.image.getImage(), new iKernel(3,3));
	}
	private void filterRGB(){
		
		for(int row = 0; row < filteredImage.getHeight(); row++){
			for(int col = 0; col < filteredImage.getWidth(); col++){
				Window currentWindow = new Window(row, col, mirroredImage, new iKernel(3,3));
				double weight = Double.parseDouble(weightText.getText());
				int redChannel = sharp.sharp(weight, currentWindow.getChannel(Channel.RED));
				int greenChannel = sharp.sharp(weight, currentWindow.getChannel(Channel.GREEN));
				int blueChannel = sharp.sharp(weight, currentWindow.getChannel(Channel.BLUE));
				
//				filteredImage.setRGB(col, row, new Color(redChannel, blueChannel, greenChannel).getRGB());
				filteredImage.setRGB(col, row, new Color(redChannel, greenChannel, blueChannel).getRGB());
			}
		}
		parent.getDesktopPane().add(new ImageInternalFrame(new nImage(filteredImage), "SharpRGBFilter"));
		JOptionPane.showMessageDialog(parent.getParent(), "Filter complete");
	}

}
