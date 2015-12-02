package com.example.lord_tyler.meettoeat.YelpClasses;

/**
 * Parsed yelp raw data
 * Created by stephencheung on 10/7/15.
 */
public class Region {
	private Span span;
	private Center center;

	public Center getCenter() {
		return center;
	}

	public void setCenter(Center center) {
		this.center = center;
	}

	public Span getSpan() {
		return span;
	}

	public void setSpan(Span span) {
		this.span = span;
	}
	
}
