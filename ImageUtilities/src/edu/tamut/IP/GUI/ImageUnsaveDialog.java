package edu.tamut.IP.GUI;

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class ImageUnsaveDialog extends JDialog implements ActionListener {//Might be used later
	
	public ImageUnsaveDialog(JFrame parent){
		super(parent,
				true);//bool modal (determines whether or not parent can be accessed while dialog is alive. True means parent cannot be accessed)
				this.setSize(new Dimension(300,100));
				this.setResizable(false);

				JPanel panel = new JPanel();
				panel.setLayout(new FlowLayout());
	}

	@Override
	public void actionPerformed(ActionEvent arg0) {
		// TODO Auto-generated method stub

	}

}
