package com.tulu.vnpetrolstation.model;

public class GPSPoint {
	double latitude;
	double longitude;

	public GPSPoint(double lat, double lng) {
		this.latitude = lat;
		this.longitude = lng;
	}

	public GPSPoint() {
		this.latitude = 0;
		this.longitude = 0;
	}

	public double getLat() {
		return latitude;
	}

	public void setLat(double lat) {
		this.latitude = lat;
	}

	public double getLng() {
		return longitude;
	}

	public void setLng(double lng) {
		this.longitude = lng;
	}

	public String toString() {
		return "Lat: " + this.latitude + " - Lng: " + this.longitude;
	}
}
