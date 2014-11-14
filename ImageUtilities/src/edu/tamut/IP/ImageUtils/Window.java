package edu.tamut.IP.ImageUtils;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Glen Wallace
 * This object encapsulates an image's window in an ArrayList, as well as the kernel used to create the window.
 *
 */
public class Window {
	private ArrayList <Color> colors = new ArrayList<Color>();
	private iKernel kernel;
	
	/**
	 * 
	 * @param row -row The row-index of the desired center of the series
	 * @param col -col The row-index of the desired center of the series
	 * @param image -image The image from which the pixels are being grabbed. IMPORTANT: must be a bordered image sharing the same kernel as the inputted kernel
	 * @param kernel -kernel used to create the window.
	 */
	public Window(int row, int col, BufferedImage image, iKernel kernel){
		this.kernel = kernel;
		calculateWindow(row, col, image);
		
	}
	public Window(int row, int col, BufferedImage image, iKernel kernel, boolean[][] noiseMap){
		this.kernel = kernel;
		calculateWindow(row, col, image, noiseMap);
	}
	/**
	 * @param row -The row-index of the desired center of the series
	 * @param col -The col-index of the desired center of the series
	 * @param image -The image from which the pixels are being grabbed. IMPORTANT: must be a bordered image sharing the same kernel as the inputted kernel
	 */
	public void calculateWindow(int row, int col, BufferedImage image){
		int rowModifier = 2*this.kernel.RowMod();//The grabber for the row is 2 * the rowMod
		int colModifier = 2*this.kernel.ColMod();//the grabber for the col is 2 * the colMod
		for (int i_row = row; i_row <= rowModifier+row; i_row ++ ){//since
			for(int i_col = col; i_col <= colModifier+col; i_col++){
				this.colors.add(new Color(image.getRGB(i_col, i_row)));
			}
			
		}
		colors.trimToSize();//remove any trailing nulls
		//System.out.println(colors.toString());

	}
	public void calculateWindow(int row, int col, BufferedImage image, boolean[][] noiseMap){
		int rowModifier = 2*this.kernel.RowMod();//The grabber for the row is 2 * the rowMod
		int colModifier = 2*this.kernel.ColMod();//the grabber for the col is 2 * the colMod
		
		for (int i_row = row; i_row <= rowModifier+row; i_row ++ ){//since
			for(int i_col = col; i_col <= colModifier+col; i_col++){
				if(!noiseMap[i_row][i_col]){//if the pixel isn't noisy then add it to the ArrayList
					this.colors.add(new Color(image.getRGB(i_col, i_row)));					
				}
			}
			
		}
		colors.trimToSize();//remove any trailing nulls
		
	}
	/**
	 * @param channel -A Channel enumeration to decide which channel to pull. Either RED, GREEN, or BLUE
	 * @return -An ArrayList containing the pixels in the window of the pertaining color channel. Returns "-99" for an invalid channel
	 */
	public ArrayList<Integer> getChannel(Channel channel){
		ArrayList<Integer> outputChannel = new ArrayList<Integer>();
		for(Color color : this.colors){
			switch (channel){
			case RED:
				outputChannel.add(color.getRed());
				break;
			case GREEN:
				outputChannel.add(color.getGreen());
				break;
			case BLUE:
				outputChannel.add(color.getBlue());
				break;
			default:
				outputChannel.add(-99);
			}
			
		}
		outputChannel.trimToSize();//remove any trailing nulls
		return outputChannel;
	}

	public ArrayList<Color> getColors() {
		return colors;
	}

	public iKernel getKernel() {
		return kernel;
	}

	@Override
	public String toString() {
		return "Window [colors=" + colors + ", colors.size =" + colors.size() + ", kernel=" + kernel + "]";
	}
	

}
