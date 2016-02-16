package org.usfirst.frc.team4180.robot;

import edu.wpi.first.wpilibj.networktables.NetworkTable;

public class ImageRecognizer {
	NetworkTable table;
	public ImageRecognizer(){
	table = NetworkTable.getTable("GRIP/contours");
	}
	
	public void tick(){
		double[] areas = table.getNumberArray("area", new double[0]);
		double[] x = table.getNumberArray("centerX", new double[0]);
		double[] y = table.getNumberArray("centerY", new double[0]);
		double[] sol = table.getNumberArray("solidity", new double[0]);
		double[] h = table.getNumberArray("height", new double[0]);
		double[] w = table.getNumberArray("width", new double[0]);
		
		Reflector[] reflectors = new Reflector[areas.length];
		
		for(int i = 0; i < areas.length; i++){
			reflectors[i] = new Reflector(areas[i], x[i], y[i], w[i], h[i], sol[i]);
		}
	}
	
	 public class Reflector{
		 private double area, x , y , w , h , solid;
	
		 public Reflector(double area, double x, double y, double w, double h, double solid){
			 this.area = area;
			 this.x = x;
			 this.y = y;
			 this.w = w;
			 this.h = h;
			 this.solid = solid;
		 }
	 }	
}