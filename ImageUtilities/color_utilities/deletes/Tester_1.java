package edu.tamut.IP.color_utilities;
import edu.tamut.IP.color_utilities.YUV;


public class Tester_1 
{

	
	public static void main(String[] args)
	{
		int redPix = 89;
		int greenPix = 102;
		int bluePix = 120;
		
		YUV ugly = new YUV(redPix, greenPix, bluePix);
		System.out.println(ugly.GetY());
		System.out.println(ugly.GetU());
		System.out.println(ugly.GetV());
		System.out.println(ugly.toColor().toString());
		System.out.println(ugly.GetR());
		System.out.println(ugly.GetG());
		System.out.println(ugly.GetB());
			
	}

}
