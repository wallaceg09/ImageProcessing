//Dalton Holley 
//Software Engineering
//11/07/12

/*Values for getting YUV from RGB
					Y from RGB
					RGB from YUV
*/

package edu.tamut.IP.color_utilities;

public class known_values 
{
	static double[][] YUV = new double[3][1];
	static double[][] RGB = new double[3][1];
	static double[][] Y = new double[3][1];
	
	static final double[][] toY = new double[][] {{.299, .587, .114}};
	static final double[][] toU = new double[][]{{-.14713, -.28886, .436}};
	static final double[][] toV = new double[][]{{.615, -.514990, -.10001}};
	
	
	static final double[][] toR = new double[][] {{1, 0, 1.13983}};
	static final double[][] toG = new double[][]{{1, -.39465, -.5806}};
	static final double[][] toB = new double[][]{{1, 2.03211, 0}};

	
}
