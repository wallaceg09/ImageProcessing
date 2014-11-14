package edu.tamut.IP.ImageUtils;
import java.awt.Image;
import java.awt.image.BufferedImage;

import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JDesktopPane;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JInternalFrame;
import javax.swing.JWindow;

public class ImageFileIO {
	
	/**
	 * Creates an OpenFile dialog box, then returns a BufferedImage from the image in the file.
	 * @return BufferedImage img- contains only the RGB information, returns null and prints any exceptions if the file is unable to be opened.
	 */
	public static BufferedImage OpenImage(){
		/*
		 * Static method. Creates an openFile dialog box, then attempts to open that file as an image. 
		 * If the file is successfully opened it returns the image, otherwise it returns null
		 * and raises an IOException
		 * 
		 */
		JFileChooser chooser = new JFileChooser();//An interface for searching for files
		File imageOpenFile;//File used in conjunction with JFileChooser to open image fles
		BufferedImage img = null;//A buffered image holds rasterized image data
		
		chooser.addChoosableFileFilter(new SuportedImageFilter());
		chooser.setFileFilter(new SuportedImageFilter());
		chooser.showOpenDialog(null);//choser.showOpenDialog(parent)
		imageOpenFile = chooser.getSelectedFile();//points to the file selected by the user
		//debug output
		/*
		System.out.println(IUtils.NameFromFile(imageOpenFile));
		*/
		try{
			img = ImageIO.read(imageOpenFile);
			//System.out.println(img);
		}catch (IOException e){
			System.err.println("Cannot open the file: " + e);
		}
		catch (IllegalArgumentException e2)
		{
			//System.err.println("File type not suported");
		}catch(Exception e3){
			System.err.println(e3);
		}
		//consider returning an arra with [img, imageOpenFile]
		return img;
		
	
	}
	/**
	 * Creates an OpenFile dialog box, then returns an nImage Object containing the selected file and the BufferedImage from the file.
	 * @return nImage img- An nImage object which encapsulates a BufferedImage containing the RGB information from the file, and the file itself, returns null and prints any exceptions if the file is unable to be opened. 
	 */
	public static nImage OpenImageIM(){
		/*
		 * Static method. Creates an openFile dialog box, then attempts to open that file as a buffered image. 
		 * If the file is successfully opened it encapsulates the image into a custom image class, otherwise it returns null
		 * and raises an IOException
		 * 
		 */
		JFileChooser chooser = new JFileChooser();//An interface for searching for files
		File imageOpenFile;//File used in conjunction with JFileChooser to open image fles
		BufferedImage img = null;//A buffered image holds rasterized image data
		
		chooser.addChoosableFileFilter(new SuportedImageFilter());
		chooser.showOpenDialog(null);//choser.showOpenDialog(parent)
		imageOpenFile = chooser.getSelectedFile();//points to the file selected by the user
		
		nImage image = null;
		
		System.out.println(IUtils.NameFromFile(imageOpenFile));
		try{
			img = ImageIO.read(imageOpenFile);
			//System.out.println(img);
		}catch (IOException e){
			System.err.println("Cannot open the file: " + e);
		}
		catch (IllegalArgumentException e2)
		{
			//System.err.println("File type not suported");
		}catch(Exception e3){
			System.out.println(e3);
		}
		//consider returning an arra with [img, imageOpenFile]
		 image = new nImage(img, imageOpenFile);
		return image;
		
	
	}
	
	/**
	 * Draws the inputted image to a JFrame component. This is best used without a GUI for debugging purposes as it cannot be parented.
	 * @param img BufferedImage to be displayed.
	 */
	public static void DisplayImage(BufferedImage img){
		JFrame window = new JFrame();
		//*window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		window.setBounds(10, 50, img.getWidth(), img.getHeight());
		window.getContentPane().add(new ImageCanvas(img));
		window.setVisible(true);
	}
	public static void DisplayImage(BufferedImage img, JFrame parent){
		JWindow window = new JWindow(parent);
		//*window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		window.setBounds(10, 50, img.getWidth(), img.getHeight());
		window.getContentPane().add(new ImageCanvas(img));
		window.setVisible(true);
	}
	/**
	 * Not ready yet, do not use.
	 * @param img BufferedImage to be displayed.
	 * @return Hidden (work in progress)
	 */
	public static ImageInternalFrame ReturnDisplayImage(nImage img){
		//I have plans to fully impliment this later. At the moment however it should not be used.
		//This method returns a Frame with the image attached. This is to hopefully constrain the images to the main GUI
		String name = "Untitled";
		if(img.getFile()!=null){
			name = IUtils.NameFromFile(img.getFile());
		}
		ImageInternalFrame window = new ImageInternalFrame(img, name);
		return window;
	}
	public static ImageInternalFrame ReturnDisplayImage(nImage img, String name){
		ImageInternalFrame window = new ImageInternalFrame(img, name);
		return window;
	}
	
	

	/**
	 * Creates a SaveAs dialog box and attempts to save the image to the selected file location. 
	 * @param inputImage BufferedImage to be saved to a file, returns null and prints any exceptions if the image cannot be saved.
	 */
	public static File SaveImageAs(BufferedImage inputImage)
	{
		JFileChooser chooser = new JFileChooser();//an interface for searching files
		File imageSaveFile = null;//File used in conjunction with JFileChooser to save the image to file
		BufferedImage img = inputImage;//the input image is the image to be saved
		
		chooser.showSaveDialog(null);//chooser.showSaveDialog(parent)
		try{
			imageSaveFile = chooser.getSelectedFile();//points to the file created/selected by the user.
		
			String extension =  IUtils.Extension(imageSaveFile);
			
			try{
				ImageIO.write(img,extension, imageSaveFile);
			}catch(IOException e){
				System.err.println("Could not save file " + e);
			}//catch(NullPointerException e){
				//System.err.println("No file selected " + e);
			/*}*/catch(Exception e){
				System.err.println(e);
			}
		}catch (NullPointerException nullP){
			
		}
		return imageSaveFile;
	}

}
