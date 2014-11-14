//Dalton Holley 
//Software Engineering
//11/14/12

package edu.tamut.IP.color_utilities;
import java.awt.Color;
import edu.tamut.IP.color_utilities.known_values;



public class YUV 
{
	//Constructers
	public YUV( int red, int green, int blue )
	{
		RGB2YUV( red, green, blue );
	}

	public YUV( Color rgb )
	{
		RGB2YUV( rgb ); 
	}

	public YUV( int rgb)
	{
		RGB2YUV( rgb );
	}
	
	
	//Variables	
	private int Y;
	private double U;
	private double V;
	
	private int R;
	private int G;
	private int B;
	
	
	public void SetY(int y)
	{
		this.Y = y;
	}
	
	public int GetY()
	{
		return this.Y;
	}
	
	private void SetU(double u)
	{
		this.U = u;
	}
	
	public double GetU()
	{
		return this.U;
	}
	
	private void SetV(double v)
	{
		this.V = v;
	}
	
	public double GetV()
	{
		return this.V;
	}

	private void SetR(int r)
	{
		this.R = r;
	}
	
	public int GetR()
	{
		return this.R;
	}
	
	private void SetG(int g)
	{
		this.G = g;
	}
	
	public int GetG()
	{
		return this.G;
	}
	
	private void SetB(int b)
	{
		this.B = b;
	}
	
	public int GetB()
	{
		return this.B;
	}
	
		private void RGB2YUV( int red, int green, int blue)
		{
			SetY((int)Math.round(((known_values.toY[0][0] * red)+
								(known_values.toY[0][1] * green)+
								(known_values.toY[0][2] * blue))));
		
			SetU((double)((known_values.toU[0][0] * red)+
								(known_values.toU[0][1] * green)+
								(known_values.toU[0][2] * blue)));
			
			
			SetV((double)((known_values.toV[0][0] * red)+
								(known_values.toV[0][1] * green)+
								(known_values.toV[0][2] * blue)));
			
			int a = 1;
			
		}
	
		private void RGB2YUV( Color rgb )
		{
			SetY((int)Math.round(((known_values.toY[0][0] * rgb.getRed())+
								(known_values.toY[0][1] * rgb.getGreen())+
								(known_values.toY[0][2] * rgb.getBlue()))));
		
			SetU((double)((known_values.toU[0][0] * rgb.getRed())+
								(known_values.toU[0][1] * rgb.getGreen())+
								(known_values.toU[0][2] * rgb.getBlue())));
		
			SetV((double)((known_values.toV[0][0] * rgb.getRed())+
								(known_values.toV[0][1] * rgb.getGreen())+
								(known_values.toV[0][2] * rgb.getBlue())));
		}
	
		private void RGB2YUV( int rgb )
		{
			Color tempRGB = new Color(rgb);
			SetY((int)Math.round(((known_values.toY[0][0] * tempRGB.getRed())+
								(known_values.toY[0][1] * tempRGB.getGreen())+
								(known_values.toY[0][2] * tempRGB.getBlue()))));
		
			SetU((double)((known_values.toU[0][0] * tempRGB.getRed())+
								(known_values.toU[0][1] * tempRGB.getGreen())+
								(known_values.toU[0][2] * tempRGB.getBlue())));
		
			SetV((double)((known_values.toV[0][0] * tempRGB.getRed())+
								(known_values.toV[0][1] * tempRGB.getGreen())+
								(known_values.toV[0][2] * tempRGB.getBlue())));
		}
	
		
		public void YUV2RGB()
		{
			int y = GetY();
			double u = GetU();
			double v = GetV();
			
			SetR((int)Math.round(((known_values.toR[0][0] * y)+
								(known_values.toR[0][1] * u)+
								(known_values.toR[0][2] * v))));
						
			SetG((int)Math.round(((known_values.toG[0][0] * y)+
								(known_values.toG[0][1] * u)+
								(known_values.toG[0][2] * v))));
			
			SetB((int)Math.round(((known_values.toB[0][0] * y)+
								(known_values.toB[0][1] * u)+
								(known_values.toB[0][2] * v))));
		}
		
		public Color toColor()
		{
			YUV2RGB();
			return new Color(GetR(), GetG(), GetB());
		}
		
}
