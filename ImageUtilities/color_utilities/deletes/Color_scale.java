
//NOT USED, FOR BACKUP PURPOSES ONLY

/*

package edu.tamut.IP.color_utilities;
import java.awt.Color;

import edu.tamut.IP.color_utilities.known_values;
//import java.util.Arrays;

public class Color_scale 
{
	public static void RGB2YUV( int red, int green, int blue)
	{
		
		int[][] RGB = new int[][] {{red},{green},{blue}};
		
		
		for( int i = 0; i < 3; i++)
		{
			known_values.YUV[i][0] = (known_values.toYUV[i][0] * RGB[i][0]
									+ known_values.toYUV[i][1] * RGB[i][0]
									+ known_values.toYUV[i][2] * RGB[i][0]);
			known_values.YUV[i][0] = (int)known_values.YUV[i][0];
						
		}
	}
	public static void RGB2YUV( Color rgb)
	{
		
		int[][] RGB = new int[][] {{rgb.getRed()},{rgb.getGreen()},{rgb.getBlue()}};
		
		
		for( int i = 0; i < 3; i++)
		{
			known_values.YUV[i][0] = (known_values.toYUV[i][0] * RGB[i][0]
									+ known_values.toYUV[i][1] * RGB[i][0]
									+ known_values.toYUV[i][2] * RGB[i][0]);
			known_values.YUV[i][0] = (int)known_values.YUV[i][0];
						
		}
	}
	public static void RGB2YUV( int rgb)
	{
		Color tempRGB = new Color(rgb);
		int[][] RGB = new int[][] {{tempRGB.getRed()},{tempRGB.getGreen()},{tempRGB.getBlue()}};
		
		
		for( int i = 0; i < 3; i++)
		{
			known_values.YUV[i][0] = (known_values.toYUV[i][0] * RGB[i][0]
									+ known_values.toYUV[i][1] * RGB[i][0]
									+ known_values.toYUV[i][2] * RGB[i][0]);
			known_values.YUV[i][0] = (int)known_values.YUV[i][0];
						
		}
	}
	
	
	public static void YUV2RGB( int y, int u, int v)
	{
		
		int[][] YUV = new int[][] {{y},{u},{v}};
		
		
		for( int i = 0; i < 3; i++)
		{
			known_values.RGB[i][0] = (known_values.toRGB[i][0] * YUV[i][0]
									+ known_values.toRGB[i][1] * YUV[i][0]
									+ known_values.toRGB[i][2] * YUV[i][0]);
			known_values.RGB[i][0] = (int)known_values.RGB[i][0];
						
		}
	}
	
	
	/*		
	Uneeded
	
	public static void RGB2Y( int red, int green, int blue)
	{
		
		int[][] RGB = new int[][] {{red},{green},{blue}};
		
		
		for( int i = 0; i < 3; i++)
		{
			known_values.Y[i][0] = (known_values.toY[i][0] * RGB[i][0]);
			known_values.Y[i][0] = (int)known_values.Y[i][0];
						
		}
	}
	
}
*/