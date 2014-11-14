package edu.tamut.IP.ImageUtils;

public class Pixel {
	private double red;
	private double green;
	private double blue;
	private double luminosity = 0.0;
	public Pixel(int red, int green, int blue){
		this.red = (double)red;
		this.green = (double)green;
		this.blue = (double) blue;
	}
	public Pixel(double red, double green, double blue){
		this.red = red;
		this.blue = blue;
		this.green = green;
	}
	public Pixel(int red, int green, int blue, int luminosity){
		this.red = (double) red;
		this.blue = (double) blue;
		this.green = (double) green;
		this.luminosity = (double) luminosity;
	}
	public Pixel (double red, double green, double blue, double luminosity){
		this.red = red;
		this.blue = blue;
		this.green = green;
		this.luminosity = luminosity;
	}
	public double getRed() {
		return red;
	}
	public double getGreen() {
		return green;
	}
	public double getBlue() {
		return blue;
	}
	public double getLuminosity(){
		return luminosity;
	}
	public String toString()
	{
		String returnString = String.format("edu.tamut.IP.ImageUtils.Pixel [r=%f,g=%f,b=%f,l%f]", this.red, this.green, this.blue, this.luminosity);
		return returnString;
	}

}
