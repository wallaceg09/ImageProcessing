package edu.tamut.IP.ImageUtils;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.File;

/**
 * Encapsulates important information about our image.
 * @author Glen Wallace
 *
 */
public class nImage {
	private BufferedImage image;
	private BufferedImage luminosity;
	private File file;
	
	private Pixel mean = null;//mean of the entire image
	private Pixel variance = null;//variance of the entire image
	private Pixel standardDeviation = null;//standard deviation of the entire image

	//private int minimum;
	//private int maximum;
	private int size;
	
	/**
	 * @param image BufferedImage containing the RBG information
	 * @param file File associated with the image. Null if the image is the result of the creation of a new image.
	 */
	public nImage(BufferedImage image, File file){
		this.image = image;
		this.file = file;
		this.size = image.getHeight() * image.getWidth();
		RGBtoY();
		calculateMean();
		//System.out.println(mean.toString());
		calculateVariance();
		//System.out.println(variance.toString());
		calculateStandardDev();
		//System.out.println(standardDeviation.toString());
		
	}
	public nImage(BufferedImage image){
		this.image = image;
		this.file = null;
		this.size = image.getHeight() * image.getWidth();
		RGBtoY();
		calculateMean();
		calculateVariance();
		calculateStandardDev();
		
	}

	/**
	 * @return file: The file associated with the image.
	 */
	public File getFile() {
		return file;
	}

	/**
	 * @param file The new file associated with the image. Replaces the old file in memory
	 */
	public void setFile(File file) {
		this.file = file;
	}

	/**
	 * @return image: The BufferedImage
	 */
	public BufferedImage getImage() {
		return image;
	}

	private void calculateMean(){
		double tempRedTotal = 0;
		double tempGreenTotal = 0;
		double tempBlueTotal = 0;
		double tempLumTotal = 0;
		for(int row = 0; row < image.getHeight(); row++){
			for(int col = 0; col < image.getWidth(); col++){
				Color tempColor = new Color(image.getRGB(col, row));
				Color tempLum = new Color(luminosity.getRGB(col,row));
				tempRedTotal += tempColor.getRed();//add each channel to the tempTotal
				tempGreenTotal += tempColor.getGreen();
				tempBlueTotal += tempColor.getBlue();
				//get the luminosity's total
				tempLumTotal += tempLum.getRed();
			}
		}
		double tempRedMean = tempRedTotal/size;//find the avarage of each channel
		double tempGreenMean = tempGreenTotal/size;
		double tempBlueMean = tempBlueTotal/size;
		double tempLumMean = tempLumTotal/size;
		this.mean = new Pixel(tempRedMean, tempGreenMean, tempBlueMean, tempLumMean);//create a new pixel with the means

	}
	private void calculateVariance(){
		if(this.mean == null){
			calculateMean();
		}
		double tempRedVar = 0;//temporary variance for the red channel
		double tempGreenVar = 0;//temporary variance for the green channel
		double tempBlueVar = 0; //temporary variance for the blue channel
		
		double tempLumVar = 0;
		for(int row = 0; row < image.getHeight(); row++){
			for(int col = 0; col < image.getWidth(); col++){
				Color currentPixel = new Color(image.getRGB(col, row));
				Color currentLum = new Color(image.getRGB(col,row));
				double tempRed = (double)currentPixel.getRed()-this.mean.getRed();//image.red - mean.red cast to double
				double tempGreen = (double)currentPixel.getGreen()-this.mean.getGreen();//image.green - mean.green cast to double
				double tempBlue = (double) currentPixel.getBlue()-this.mean.getBlue();
				
				double tempLum = (double)currentLum.getRed() - this.mean.getLuminosity();
				
				tempRedVar += tempRed*tempRed;//summation of ((image.red - mean.red)^2)
				tempGreenVar += tempGreen*tempGreen;//summation of ((image.green - mean.green)^2)
				tempBlueVar += tempBlue*tempBlue;//summation of ((image.blue - mean.blue)^2)
				
				tempLumVar += tempLum * tempLum;
			}
		}/*//deleted for testing
		tempRedVar = (1/this.size)* tempRedVar;
		tempGreenVar = (1/this.size)*tempGreenVar;
		tempBlueVar = (1/this.size)*tempBlueVar;
		*/
		tempRedVar = tempRedVar/this.size;
		tempGreenVar = tempGreenVar/this.size;
		tempBlueVar = tempBlueVar/this.size;
		tempLumVar = tempLumVar/this.size;
		this.variance = new Pixel(tempRedVar, tempGreenVar, tempBlueVar, tempLumVar);
	}
	private void calculateStandardDev(){
		if (this.variance == null){
			calculateVariance();
		}
		double tempRedStDev = Math.sqrt(this.variance.getRed());
		double tempGreenStDev = Math.sqrt(this.variance.getGreen());
		double tempBlueStDev = Math.sqrt(this.variance.getBlue());
		double tempLumStDev = Math.sqrt(this.variance.getLuminosity());
		this.standardDeviation = new Pixel(tempRedStDev, tempGreenStDev, tempBlueStDev, tempLumStDev);
	}
	public Pixel getMean() {
		return mean;
	}
	public Pixel getVariance() {
		return variance;
	}
	public Pixel getStandardDeviation() {
		return standardDeviation;
	}
	public BufferedImage getLuminosity() {
		return luminosity;
	}
	private void RGBtoY(){
		//Action: Y=[0.299, 0.587, 0.114] [R, G, B]’

		luminosity = new BufferedImage(this.image.getWidth(), this.image.getHeight(), this.image.getType());
		int[] tempPix = new int[3];
		for(int row = 0; row < image.getHeight(); row++){
			for(int col = 0; col < image.getWidth(); col++){
				Color tempColor = new Color(this.image.getRGB(col, row));
				double tempLuminosity = 0;
				tempLuminosity += IUtils.Y[0] * tempColor.getRed();
				tempLuminosity += IUtils.Y[1] * tempColor.getGreen();
				tempLuminosity += IUtils.Y[2] * tempColor.getBlue();
				int lum = (int)Math.round(tempLuminosity);
				if(lum < 0){
					lum = 0;
				}else if (lum>255){
					lum = 255;
				}
				
				tempColor = new Color(lum, lum, lum);
				luminosity.setRGB(col, row, tempColor.getRGB());
			}
		}
	}


}
