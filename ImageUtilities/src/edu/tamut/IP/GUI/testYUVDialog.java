package edu.tamut.IP.GUI;

import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import edu.tamut.IP.ImageUtils.ImageInternalFrame;
import edu.tamut.IP.ImageUtils.nImage;

public class testYUVDialog {
	private JTextField inputRow;
	private JTextField inputCol;
	private JPanel inputPanel, outputPanel;
	private String[] fields = {"Red", "Green", "Blue", "Y", "U", "V"};
	
	public testYUVDialog(ImageInternalFrame parent, nImage image){
		init();
		JOptionPane.showMessageDialog(parent, inputPanel);
	}
	private void init(){
		//initialize all the components
		inputPanel = new JPanel();
		outputPanel = new JPanel();
		inputRow = new JTextField();
		inputRow.setColumns(2);
		inputCol = new JTextField();
		inputCol = new JTextField();
		inputCol.setColumns(2);
		//input panel
		inputPanel.add(new JLabel("Row: "));
		inputPanel.add(inputRow);
		inputPanel.add(new JLabel("Col: "));
		inputPanel.add(inputCol);
		//output panel
		
		
	}
	private void execute(){
		
	}
}
