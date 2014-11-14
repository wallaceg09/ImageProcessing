package edu.tamut.IP.ImageUtils;
import java.io.File;
import javax.swing.*;
import javax.swing.filechooser.*;
//
public class SuportedImageFilter extends FileFilter{
	/*
	 * (non-Javadoc)
	 * @see javax.swing.filechooser.FileFilter#accept(java.io.File)
	 * 
	 * Purpose: Filters selections in the OpenImage method's OpenFileDialog to only display 
	 * supported imageextensions
	 */
	@Override
	public boolean accept(File file)
	{
		//
		if (file.isDirectory()){
			return true;
		}
		String extension = IUtils.Extension(file);
		if(extension != null){
			//If it has no extension then return false
			if(extension.equals(IUtils.bmp) || //if the extension is supported, then accept returns true
				extension.equals(IUtils.jpeg)||
				extension.equals(IUtils.jpg)||
				extension.equals(IUtils.png)){
				return true;
			}else {
				return false;
			}
		}
		return false;
	}
	
	@Override
	public String getDescription() {
		// TODO Description that apears in the drop-down menu of the filter selection
		return "Suported Images: *.jpg, *.bmp, *.png";
	}
}
