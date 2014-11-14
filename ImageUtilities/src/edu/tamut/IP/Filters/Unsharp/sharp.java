package edu.tamut.IP.Filters.Unsharp;

import java.util.ArrayList;
import java.util.Arrays;
import java.awt.Color;

import com.sun.xml.internal.bind.v2.runtime.unmarshaller.XsiNilLoader.Array;
import com.sun.xml.internal.bind.v2.schemagen.xmlschema.List;




public class sharp {
	
	public sharp(){}
	private static double kweight;

	static public int sharp(double inweight, ArrayList<Integer> image)
	{		//*Comment from Glen. KWeight must be in the range of 0-3 double
		if(inweight < 0){
			kweight = 0;
		}else if(inweight > 3){
			kweight = 3;
		}else {kweight = inweight;}
		//create point of interest g which stores value
		//Color g;
		int g;
		//local values which fits within the boundary: index 0 for red, 1 for green, 2 for blue
		//int[] included = new int[3];
		int included=0;
		//counts how many elements found within the boundary: index 0 for red, 1 for green, 2 for blue
		//int[] counter = new int[3];
		int counter = 0;
		//int[] currentColor = new int[3];
		int currentColor = 0;
		//store color rgb value: index 0 for red, 1 for green, 2 for blue
		//int[] newColor = new int[3];		
		int newColor = 0;
		
		//this for loop repeats until all local elements are processed.
		//stores base intensity value, which is the element in the middle
		g = image.get(image.size()/2);
		
		//there begins checks with local windows
		//add the pixels that fit within the iEV range
			for (int n=0;n<image.size();n++)
			{
				//Process Red

					/*included[0] = included[0] + image.get(n).getRed();
					currentColor[0]=g.getRed();

					included[1] = included[1] + image.get(n).getGreen();
					currentColor[0]=g.getGreen();

					included[2] = included[2] + image.get(n).getBlue();
					currentColor[0]=g.getBlue();
					*/
				included = included+image.get(n);
				
			} //end for
			
			
			
		//calculate new g
			/*
		for(int m = 0;m<3;m++)
		{
			included[m] = included[m] / 9;
			double tempColor = (double) currentColor[m];
			double tempIncluded = (double) included[m];
			newColor[m] = (int)(tempColor + kweight * (tempColor - tempIncluded));
			//newColor[m] = ((double)currentColor[m]) + kweight * ((double)currentColor[m] - (double)included[m]);
		}*/
		included = included/9;
		//g(x,y)= f(x,y) + (k*(f(x,y)-windowAverage))
		newColor = (int)((double)g + kweight*((double)g - (double)included));
		//g  = new Color(newColor[0],newColor[1],newColor[2]);
		if(newColor < 0){
			newColor = 0;
		}else if(newColor > 255){
			newColor = 255;
		}
		return newColor;
		
		
		//return calculated value of the center color of the image
		//return g;
	}//end class
	/*
	public static void main(String[] args){//debuging
		int[] test = {110, 132, 104, 133, 147, 199, 182, 210, 200};
		ArrayList<Integer> ttest = new ArrayList<Integer>();
		for(int element : test){
			ttest.add(element);
		}
		System.out.println(sharp(1.5, ttest));
	}
	*/
}
