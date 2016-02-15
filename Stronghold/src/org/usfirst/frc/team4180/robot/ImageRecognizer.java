package org.usfirst.frc.team4180.robot;

import edu.wpi.first.wpilibj.networktables.NetworkTable;

public class ImageRecognizer {
	NetworkTable table;
	Reflector[] reflectors;
	
	public ImageRecognizer(){
	table = NetworkTable.getTable("GRIP/contours");
	}
	
	public void tick(){
		double[] areas = table.getNumberArray("area", new double[0]);
		double[] x = table.getNumberArray("centerX", new double[0]);
		double[] y = table.getNumberArray("centerY", new double[0]);
		double[] solid = table.getNumberArray("solidity", new double[0]);
		double[] h = table.getNumberArray("height", new double[0]);
		double[] w = table.getNumberArray("width", new double[0]);
		
		reflectors = new Reflector[areas.length];
		
		for(int i = 0; i < areas.length; i++){
			reflectors[i] = new Reflector(areas[i], x[i], y[i], w[i], h[i], solid[i]);
		}
	}
	
	public Reflector findLargest(Reflector[] ref) {
		double largestArea = 0;
		int largestAreaPos = 0;
		
		for(int i = 0; i < ref.length; i++) {
			double newArea = ref[i].getArea();
			
			if(newArea > largestArea) {
				newArea = largestArea;
				largestAreaPos = i;
			}
		}
		return ref[largestAreaPos];
	}
	
	public double[] alignShooting() {
		Reflector closestRef = findLargest(reflectors);
		
		if(closestRef.getXPos() < 155) {
			//turn right
			return new double[]{1, 0, 0};
		} 
		else if(closestRef.getXPos() > 165) {
			//turn left
			return new double[]{-1, 0, 0};
		}
		//stay still
		return new double[]{0, 0, 0};
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
		 
		 public double getArea() {
			 return area;
		 }
		 
		 public double getXPos() {
			 return x;
		 }
	 }	
}