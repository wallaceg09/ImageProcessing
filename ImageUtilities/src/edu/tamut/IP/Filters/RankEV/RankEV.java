package edu.tamut.IP.Filters.RankEV;

import java.util.ArrayList;
import java.util.Arrays;
import java.awt.Color;

/* Work done by: Hiroyuki Plumlee
 * This method applies EVSet Filter to passed kernel.
 * 
 */

public class RankEV {
	
	//-----------------------------------------------------------------
	//----------------------Constructor---------------------------------
	//------------------------------------------------------------------
	//Constructor.
	public RankEV(){}
	/*--------------------------------------------
	 * -----------EVFilter------------------------
	 *-------------------------------------------
	 */
	static public double EVFilter(double iEV, ArrayList<Integer> image)
	{		
		//create point of interest g which stores value
		int g;

		
		//the temporary average of those pixels that meet the Rank EV criterion
		double tempAverage = 0;
		//the pixel in question

		g = image.get(image.size()/2);

		
		//number of elements in the ev set
		int count = 0;
		
		//there begins checks with local windows
		//add the pixels that fit within the iEV range
			for (int element : image)
			{//for each element in image:
				//Process the window. Any element between g-ev and g+ev is averaged
				if(element <= g + iEV && element >= g - iEV) 
				{
					tempAverage += element;
					count++;
					
				} //end if
				
			} //end for
			//average the tempAverage
			tempAverage /= count;
			
		//return calculated value of the center color of the image
		return tempAverage;
	}//end class
	

}//end class

