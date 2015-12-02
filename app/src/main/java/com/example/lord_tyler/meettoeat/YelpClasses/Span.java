package com.example.lord_tyler.meettoeat.YelpClasses;

/**
 * Parsed yelp raw data
 * Created by stephencheung on 10/7/15.
 */
public class Span {
	private double latitudeDelta;
	private double longitudeDelta;
	public double getLatitudeDelta() {
		return latitudeDelta;
	}
	public void setLatitudeDelta(double latitudeDelta) {
		this.latitudeDelta = latitudeDelta;
	}
	public double getLongitudeDelta() {
		return longitudeDelta;
	}
	public void setLongitudeDelta(double longitudeDelta) {
		this.longitudeDelta = longitudeDelta;
	}
}
