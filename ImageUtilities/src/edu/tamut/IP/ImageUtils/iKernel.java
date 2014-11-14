package edu.tamut.IP.ImageUtils;

/**
 *  Contains the width, height, row indexing modifier, and column indexing modifier of a Kernel for spatial filtering with a minimum kernel size of 3x3 	
 * 	Only odd Kernels are allowed, if an even kernel is presented then the kernel size is reduced by 1
 * @author Glen Wallace
 */
public class iKernel {

	/**
	 * Represents the width of the kernel
	 */
	private int width;
	/**
	 * Represents the height of the kernel
	 */
	private int height;
	
	/**
	 * Holds the index modifier for the width of the kernel. Calculated as the integer half of the width
	 */
	private int colMod;
	/**
	 * Holds the index modifier for the height of the kernel. Calculated as the integer half of the height.
	 */
	private int rowMod;
	/**
	 * Holds the area of the kernel, (width * height)
	 */
	private int size;
	/**
	 * 
	 * @param width Width of the nxm kernel. 3 is the minimum width, and if the inputed width is even then it is subtracted by 1.
	 * @param height Height of the nxm kernel 3 is the minimum height, and if the inputed height is even then it is subtracted by 1.
	 */
	public iKernel(int width, int height){
		if(width < 3){
			this.width = 3;
		}
		else if(width%2 == 0){
			this.width=width-1;
		}
		else{
			this.width = width;
		}
		if(height < 3){
			this.height = 3;
		}
		else if(height%2 == 0){
			this.height = height-1;
		}
		else{
			this.height = height;
		}
		/*colMod and rowMod are calculated based on the integer half of the width and height.
		 * For a kernel with a width of 3, the colMod is 1. For width 5 the colMod is 2, 7 = 3.
		 * The widthMod is used to grab the width x height window around a pixel.
		 * Example code:
		 * for(int windowRow = imageRow-rowMod; windowRow <= imageRow+rowMod; windowRow++){
		 * 		for(int windowCol = imageCol-colMod; windowCol <= imageCol+colMod; windowCol++){
		 * 			//do something
		 * 		}
		 * }
		 * 
		 */
		
		colMod = this.width/2;
		rowMod = this.height/2;
		size = this.width * this.height;
		//System.out.printf("Width: %d, Height: %d, colMod: %d, rowMod: %d\n", this.width, this.height, this.colMod, this.rowMod);
		//System.out.println(this.width/2);
	}
	public iKernel(int val){
		if(val < 3){
			this.width = val;
			this.height = val;
		}
		else if(val%2 == 0){
			this.width=width-1;
			this.height=val-1;
		}
		else{
			this.width = val;
			this.height = val;
		}
		
		/*colMod and rowMod are calculated based on the integer half of the width and height.
		 * For a kernel with a width of 3, the colMod is 1. For width 5 the colMod is 2, 7 = 3.
		 * The widthMod is used to grab the width x height window around a pixel.
		 * Example code:
		 * for(int windowRow = imageRow-rowMod; windowRow <= imageRow+rowMod; windowRow++){
		 * 		for(int windowCol = imageCol-colMod; windowCol <= imageCol+colMod; windowCol++){
		 * 			//do something
		 * 		}
		 * }
		 * 
		 */
		
		colMod = this.width/2;
		rowMod = this.height/2;
		size = this.width * this.height;
	}
	/**
	 * @return Width of the nxm kernel
	 */
	public int getWidth(){
		return this.width;
	}
	/**
	 * @return Height of the nxm kernel
	 */
	public int getHeight(){
		return this.height;
	}
	/**
	 * @return colMod: a modifier for indexing the columns of an image with a mirrored border based on this kernel
	 */
	public int ColMod(){
		return colMod;
	}
	/**
	 * @return rowMod: a modifier for indexing the rows of an image with a mirrored border based on this kernel
	 */
	public int RowMod(){
		return rowMod;
	}
	/**
	 * @return size: width*height, it's the total number of pixels in the window based on this kernel
	 */
	public int getSize(){
		return size;
	}
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	public String toString(){
		String returnString = String.format("edu.tamut.iKernel [width: %d, height: %d, colMod (width): %d, rowMod(height): %d, size: %d]", this.width, this.height, this.colMod, this.rowMod, this.size);
		return returnString;
	}
	

}
