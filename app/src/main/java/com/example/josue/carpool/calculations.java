package com.example.josue.carpool;

public class Calculations
{
	private static double R = 3959; //Radius of Earth in kilometers
	
	public static int calculateMiles(double lat1, double long1, double lat2, double long2)
	{
		double dlat = Math.toRadians(lat2-lat1);
		double dlon = Math.toRadians(long2-long1);
		double lat1r = Math.toRadians(lat1);
		double lat2r = Math.toRadians(lat2);
		double a = Math.sin(dlat/2) * Math.sin(dlat/2) + Math.sin(dlon/2) * Math.sin(dlon/2) * Math.cos(lat1r) * Math.cos(lat2r);  
		double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1-a)); 
		return (int) (R * c);
	}

	public static int calculatePoints(int miles){
		double points = miles * 15;
		return (int) Math.ceil(points);
	}
}