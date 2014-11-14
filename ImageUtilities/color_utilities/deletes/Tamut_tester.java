package edu.tamut.IP.color_utilities;
import edu.tamut.IP.color_utilities.Color_scale;
import edu.tamut.IP.color_utilities.known_values;

public class Tamut_tester {

	/**
	 * @param args
	 */
	public static void main(String[] args) 
	{
		Color_scale.RGB2YUV(56,120,89);
		for(int i = 0; i < 3; i++)
		{
			System.out.println(known_values.YUV[i][0]);
		}
		
		Color_scale.YUV2RGB(56,120,89);
		for(int i = 0; i < 3; i++)
		{
			System.out.println(known_values.RGB[i][0]);
		}

		/*
		Color_scale.RGB2Y(56,120,89);
		for(int i = 0; i < 3; i++)
		{
			System.out.println(known_values.Y[i][0]);
		}
		*/
	}

}
