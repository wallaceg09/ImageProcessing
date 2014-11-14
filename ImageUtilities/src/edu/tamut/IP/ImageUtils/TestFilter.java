package edu.tamut.IP.ImageUtils;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
/*
 * Test Filter for playing around with data manipulation. This is not to be included
 */
public class TestFilter {
	private int width, height;
	private BufferedImage img;
	private iKernel kernel;
	
	public TestFilter(BufferedImage img, iKernel kernel){
		this.img = img;
		this.width = img.getWidth();
		this.height = img.getHeight();
		this.kernel = kernel;
		
		filter();
	}
	
	public void filter(){
		for(int row = 0; row < 5; row++){
			for(int col = 0; col < 5; col++){
				Window testWindow = new Window(row, col, this.img, new iKernel(3,3));
				ArrayList<Integer> redChannel = testWindow.getChannel(Channel.RED);
				redChannel.remove(0);
				System.out.println(redChannel);
				System.out.println(testMedian(redChannel));
			}
			System.out.println();
		}
	}
	private int testMedian(ArrayList<Integer> AL){
		int output = 0;
		System.out.println(AL.size());
		if(AL.size()%2 != 0){
			output = AL.get(AL.size()/2);
		}else{
			output =  (AL.get(AL.size()/2) + AL.get((AL.size()/2)-1))/2;
		}
		return output;
	}
		
		
		
		
}
	
