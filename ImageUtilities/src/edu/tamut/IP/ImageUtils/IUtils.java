package edu.tamut.IP.ImageUtils;
/*
 * This utility class is based on the utility class from the FileChooserDemo2.java example code 
 * from http://docs.oracle.com/javase/tutorial/uiswing/components/filechooser.html
 * 
 */

/*
 * Copyright (c) 1995, 2008, Oracle and/or its affiliates. All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions
 * are met:
 *
 *   - Redistributions of source code must retain the above copyright
 *     notice, this list of conditions and the following disclaimer.
 *
 *   - Redistributions in binary form must reproduce the above copyright
 *     notice, this list of conditions and the following disclaimer in the
 *     documentation and/or other materials provided with the distribution.
 *
 *   - Neither the name of Oracle or the names of its
 *     contributors may be used to endorse or promote products derived
 *     from this software without specific prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS
 * IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO,
 * THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR
 * PURPOSE ARE DISCLAIMED.  IN NO EVENT SHALL THE COPYRIGHT OWNER OR
 * CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL,
 * EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO,
 * PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR
 * PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF
 * LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING
 * NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS
 * SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */ 

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.awt.image.ColorModel;
import java.awt.image.WritableRaster;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * Class does not have to be instantiated to access the methods or the fields encapsulated by it, as they are static.
 * @author Glen Wallace
 *
 */
public class IUtils {
	//A group of definitions for image file extensions.
	/**
	 * A definition of the "jpeg" file extension
	 */
	public final static String jpeg = "jpeg";
	/**
	 * A definition of the "jpg" file extension
	 */
	public final static String jpg = "jpg";
	/**
	 * A definition of the "png" file extension
	 */
	public final static String png = "png";
	/**
	 * A definition of the "bmp" file extension
	 */
	public final static String bmp = "bmp";
	
	//Additional image file formats, however they are not expected to be supported.
	//If any become supported later, then uncomment.
	
	/*
	 * A definition of the "tif" file extension
	 */
	/*public final static String tif = "tif";*/
	/*
	 * A definition of the "tiff" file extension
	 */
	/*public final static String tiff = "tiff";*/
	/*
	 * A definition of the "gif" file extension
	 */
	/*public final static String gif = "gif";*/
	
	public final static double[] Y = {0.299, 0.587, 0.114};
	/**
	 * Returns a String containing the file's extension
	 * @param f File created from opening a file
	 * @return String containing the file's extension, with the delimiter removed
	 */
	public static String Extension(File f){
		String extension = null;
		String s = f.getName();
		int i = s.lastIndexOf('.');
		
		if (i > 0 && i < s.length() - 1){
			extension=s.substring(i+1).toLowerCase();
		}
		return extension;
	}
	/**
	 * Returns a String containing the name of the file without the extension
	 * @param f file created from opening a file
	 * @return String containing the name with the extension and delimiter removed
	 */
	public static String NameFromFile(File f){
		String name = null;
		try{
			String string = f.getName();
			int i = string.lastIndexOf('.');
			
			if(i > 0 && i < string.length()-1){
				name = string.substring(0, i);
			}
		}catch(NullPointerException nullP){
			name="untitled";
		}
		return name;
	}
	/**
	 * Returns a BufferedImage with the edges mirrored
	 * @param img input image (BufferedImage)
	 * @param kernel This is a custom class that contains information about a nxm kernel for a sliding window for image filtration
	 * @return BufferedImage with the edges mirrored according to the nxm kernel
	 */
	public static BufferedImage mirrorImage(BufferedImage img, iKernel kernel){
		
		//Returns a BufferedImage that is (2 * kernel.colMod()) by (2 * kernel.rowMod())larger than img
		//For example. For a 3x3 kernel the image is (2*1) by (2*1) larger than the original image
		
		BufferedImage mirror = null;
		//Create the new size based on the kernel
		int mirrorRowSize = (img.getHeight()+(2*kernel.RowMod()));
		int mirrorColSize = (img.getWidth()+(2*kernel.ColMod()));
		//debugging output
		System.out.printf("Row: %d Col: %d\n", mirrorRowSize, mirrorColSize);
		//create new mirrored Image
		mirror = new BufferedImage(mirrorColSize, mirrorRowSize, BufferedImage.TYPE_INT_RGB);
		//debugging output
		System.out.printf("Rows: %d Cols: %d \n", img.getHeight(), img.getWidth());
		System.out.println(kernel.toString()+"\n");
		//apply the image to the mirror, shifting according to the kernel.
		for (int row = 0; row < img.getHeight(); row++){
			for (int col = 0; col < img.getWidth(); col++){
				//*System.out.printf("Current row: %d Current Col: %d\n", row, col);
				mirror.setRGB(col+kernel.ColMod(),row+kernel.RowMod(), img.getRGB(col, row));
				//*System.out.printf("Current row: %d Current Col: %d Mirror row: %d Mirror col: %d\n", row, col, row+kernel.RowMod(), col+kernel.ColMod());
			}
		}
		/*
		for(int row = 0; row < 5; row++){
			System.out.println(new Color(mirror.getRGB(row, row)).toString());
		}*/
		//Mirror the top and bottom rows
		
		int rowGrab = 2*kernel.RowMod();//row index to grab the new pixel from. This is decremented as the row is incremented
		int endRow = mirror.getHeight()-1;//indicates the bottom row to help grab the bottom RGB values
		for (int row = 0; row < kernel.RowMod(); row++){
			for (int col = 0; col < mirror.getWidth(); col++){
				mirror.setRGB(col, row, mirror.getRGB(col, rowGrab));
				mirror.setRGB(col, endRow-row, mirror.getRGB(col, endRow-rowGrab));
			}
			rowGrab--;
		}
		//debugging output
//		for (int i = 0; i < kernel.getHeight(); i++){
//			System.out.printf("[%d, 5]: %s\n", endRow-i, new Color(mirror.getRGB(5,endRow-i)).toString());
//		}
//		System.out.println();
		//Mirror the left and right columns
		int colGrab = 2*kernel.ColMod();
		int endCol = mirror.getWidth()-1;
		
		for (int col = 0; col < kernel.ColMod(); col++){
			for( int row = 0; row < mirror.getHeight(); row++){
				//Debugging output
				//System.out.printf("[%d, %d]: %d\n", row, col, colGrab);
				mirror.setRGB(col, row, mirror.getRGB(colGrab, row));
				mirror.setRGB(endCol - col, row, mirror.getRGB(endCol-colGrab, row));
			}
			colGrab--;
		}
//		System.out.println();
		//debugging output
		/*for (int i = 0; i < kernel.getWidth(); i ++){
			System.out.printf("[5,%d]: %s\n", endCol-i, new Color(mirror.getRGB(endCol-i,5)).toString());
		}*/
		
		return mirror;
	}
	public static BufferedImage deepCopy(BufferedImage bi) {//create a deep copy of an image
		 ColorModel cm = bi.getColorModel();
		 boolean isAlphaPremultiplied = cm.isAlphaPremultiplied();
		 WritableRaster raster = bi.copyData(null);
		 return new BufferedImage(cm, raster, isAlphaPremultiplied, null);
	}
	public static boolean[][] mirrorNoiseMap(boolean[][] noiseMap, iKernel kernel){
		//FIXME: Bigassed problem somewhere... When using it with the modifiedRankOrder filter it infintely loops at the end... 
		//Returns a BufferedImage that is (2 * kernel.colMod()) by (2 * kernel.rowMod())larger than img
		//For example. For a 3x3 kernel the image is (2*1) by (2*1) larger than the original image
		
		boolean[][] mirror = null;
		//Create the new size based on the kernel
		int mirrorRowSize = (noiseMap.length+(2*kernel.RowMod()));
		int mirrorColSize = (noiseMap[0].length+(2*kernel.ColMod()));
		//debugging output
		System.out.printf("Row: %d Col: %d\n", mirrorRowSize, mirrorColSize);
		//create new mirrored Image
		mirror = new boolean[mirrorRowSize][mirrorColSize];
		//debugging output
		
//				System.out.printf("Rows: %d Cols: %d \n", img.getHeight(), img.getWidth());
//				System.out.println(kernel.toString()+"\n");
		
		//apply the image to the mirror, shifting according to the kernel.
		for (int row = 0; row < noiseMap.length; row++){
			for (int col = 0; col < noiseMap[0].length; col++){
				
//						System.out.printf("Current row: %d Current Col: %d\n", row, col);
				mirror[row+kernel.RowMod()][col+kernel.ColMod()] = noiseMap[row][col];
				//*System.out.printf("Current row: %d Current Col: %d Mirror row: %d Mirror col: %d\n", row, col, row+kernel.RowMod(), col+kernel.ColMod());
			}
		}
		/*
		for(int row = 0; row < 5; row++){
			System.out.println(new Color(mirror.getRGB(row, row)).toString());
		}*/
		
		//Mirror the top and bottom rows
		
		int rowGrab = 2*kernel.RowMod();//row index to grab the new pixel from. This is decremented as the row is incremented
		int endRow = mirror.length-1;//indicates the bottom row to help grab the bottom RGB values
		for (int row = 0; row < kernel.RowMod(); row++){
			for (int col = 0; col < mirror.length; col++){
				mirror[row][col] = mirror[rowGrab] [col];
//						mirror[endRow-rowGrab][col] = mirror [endRow-row][col]  ;
				mirror[endRow-row][col] = mirror[endRow-rowGrab] [col]  ;
			}
			rowGrab--;
		}
		//debugging output
//				for (int i = 0; i < kernel.getHeight(); i++){
//					System.out.printf("[%d, 5]: %s\n", endRow-i, new Color(mirror.getRGB(5,endRow-i)).toString());
//				}
//				System.out.println();
		//Mirror the left and right columns
		int colGrab = 2*kernel.ColMod();
		int endCol = (mirror[0].length)-1;
		
		for (int col = 0; col < kernel.ColMod(); col++){
			for( int row = 0; row < mirror.length; row++){
				//Debugging output
				//System.out.printf("[%d, %d]: %d\n", row, col, colGrab);
//						mirror[row][col] = mirror[row][colGrab];//seems incorrect
//						mirror[row][endCol - col] = mirror [row][endCol-colGrab];//seems incorrect
				
				mirror[row][col] = mirror[row][colGrab];
				mirror[row][endCol - col] = mirror [row][endCol-colGrab];
			}
			colGrab--;
		}
//				System.out.println();
		//debugging output
		/*for (int i = 0; i < kernel.getWidth(); i ++){
			System.out.printf("[5,%d]: %s\n", endCol-i, new Color(mirror.getRGB(endCol-i,5)).toString());
		}*/
		
		return mirror;
	}
//	public static void main(String[] args){
////		for( int i = 0; i < 5; i++){
////			System.out.println(i);
////		}
//		boolean[][] map = {	{false, true, false, true, false}, 
//							{true, true, false, true, true},
//							{true, false, true, false, true},
//							{false, false, false, false, false},
//							{true, true, true, true, true}
//																};
//		for(boolean[] row : map){
//			for(boolean element : row){
//				System.out.print(element + "\t");
//			}
//			System.out.println();
//		}
//		boolean[][] mirrorMap= IUtils.mirrorNoiseMap(map, new iKernel(3));
//		
//		for(boolean[] row : mirrorMap){
//			for(boolean element : row){
//				System.out.print(element + "\t");
//			}
//			System.out.println();
//		}
//	}
	

}
