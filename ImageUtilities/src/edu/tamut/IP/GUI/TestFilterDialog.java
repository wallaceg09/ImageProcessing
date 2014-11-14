package edu.tamut.IP.GUI;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;

import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import edu.tamut.IP.ImageUtils.*;

public class TestFilterDialog{
	private nImage image;
	private iKernel kernel;
	
	BufferedImage borderedImage;
	
	private JTextField inputKernel;
	//private JTextField rubbish;
	
	//TODO Consider implimenting my own showMessageDialog... Just consider...
	public TestFilterDialog(ImageInternalFrame imageInternalFrame, nImage image){
		this.inputKernel = new JTextField();
		this.inputKernel.setColumns(10);
		this.image = image;
		int kernelSize = 0;
		//provides a list of options.
		final JComponent[] options = new JComponent[]{new JLabel("Kernel: "), inputKernel};
		JOptionPane.showMessageDialog(imageInternalFrame, options);
		if(!inputKernel.getText().equalsIgnoreCase("") ){
			try{
				kernelSize=Integer.parseInt(inputKernel.getText());		
			}catch (NumberFormatException e){
				JOptionPane.showMessageDialog(imageInternalFrame, "Input not an integer\n" + e);
				JOptionPane.showMessageDialog(imageInternalFrame, options);
			}
			catch(Exception e){
				System.err.println(e);
				
			}
			iKernel kernel = new iKernel(kernelSize, kernelSize);
			TestFilter filter = new TestFilter(IUtils.mirrorImage(image.getImage(),kernel), kernel);
		}

		//System.out.println(inputKernel.getText());
	}
}
