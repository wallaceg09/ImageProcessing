package edu.tamut.IP.ImageUtils;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;


import javax.swing.JComponent;
import javax.swing.JFrame;

public class ImageCanvas extends JComponent{
	public Image img;
	ImageCanvas(Image img)
	{
		this.img=img;
	}
	public void paint(Graphics g)
	{
		g.drawImage(img, 0, 0, null);
	}

}
