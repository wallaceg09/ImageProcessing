


import java.awt.Color;
import java.awt.image.BufferedImage;

import javax.imageio.ImageIO;

import edu.tamut.IP.ImageUtils.ImageFileIO;

public class Test {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		/*
		 //some testing to see what extensions are supported
		String readerNames[] = ImageIO.getReaderFormatNames();
		String writerNames[] = ImageIO.getWriterFormatNames();
		for(String name : readerNames)
		{
			System.out.println("Read: " + name);
		}
		for(String name : writerNames){
			System.out.println("Write: " + name);
		}
		*/
		BufferedImage img = null;
		try{
			img = ImageFileIO.OpenImage();
		}catch (IllegalArgumentException e)
		{
			//System.err.println("File type not supported");
			System.exit(-1);
		}
		
		try{
			ImageFileIO.DisplayImage(img);
		}catch (NullPointerException e){
			System.err.println("No image selected... " + e);
		}catch (Exception e){
			System.err.println(e);
		}
		int row = 5;
		int col = 10;
		int pixel = img.getRGB(row, col);//returns a single int RGB value, must be converted to a color.
		Color color = new Color(pixel);
		
		System.out.println("Red "+ color.getRed());
		System.out.println("Green " + color.getGreen());
		System.out.println("Blue " + color.getBlue());
		System.out.println("Alpha " + color.getAlpha());
		
		ImageFileIO.SaveImageAs(img);
		
	}

}
