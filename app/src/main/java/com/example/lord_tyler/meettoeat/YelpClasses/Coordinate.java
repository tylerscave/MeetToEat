package com.example.lord_tyler.meettoeat.YelpClasses;

/**
 * Parsed yelp raw data
 * Created by stephencheung on 10/7/15.
 */
public class Coordinate {
	private double latitude;
	private double longitude;
	public double getLatitude() {
		return latitude;
	}
	public void setLatitude(double latitude) {
		this.latitude = latitude;
	}
	public double getLongitude() {
		return longitude;
	}
	public void setLongitude(double longitude) {
		this.longitude = longitude;
	}

	/**
	 * Checks coordinates
	 * @param c coordinates
	 * @return whether or not coordinates are the same
	 * Method Programmed By Shubaan Taheri
	 */
	@Override
	public boolean equals(Object c)
	{
		Coordinate coordinate = (Coordinate)c;
		if (this.latitude == coordinate.getLatitude() && this.longitude == coordinate.getLongitude())
		{
			return true;
		}
		else
		{
			return false;
		}
	}
	
}
