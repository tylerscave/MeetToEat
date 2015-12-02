package com.example.lord_tyler.meettoeat.YelpClasses;

import java.util.List;

/**
 * Parsed yelp raw data
 * Created by stephencheung on 10/7/15.
 */
public class YelpSearchResult {
	private Region region;
	private int total;
	private List<Business> businesses;
	public Region getRegion() {
		return region;
	}
	public void setRegion(Region region) {
		this.region = region;
	}
	public int getTotal() {
		return total;
	}
	public void setTotal(int total) {
		this.total = total;
	}
	public List<Business> getBusinesses() {
		return businesses;
	}
	public void setBusinesses(List<Business> businesses) {
		this.businesses = businesses;
	}
	
}
