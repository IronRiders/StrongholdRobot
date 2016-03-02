package org.usfirst.frc.team4180.robot;

import edu.wpi.first.wpilibj.networktables.NetworkTable;

public class ImageRecognizer {
	private NetworkTable table;
	double moveSpeed = 1;
	double turnSpeed = 0.6;
	double IdealX = 166;
	double IdealY = 40;
	double Buffer = 7;
	double ImageScaleFactor =1;
	double ImageX = 320*ImageScaleFactor;
	double ImageY = 240*ImageScaleFactor;
	
	public ImageRecognizer() {
		table = NetworkTable.getTable("GRIP/myContoursReport");
	}
	
	//returns all reflectors found in the grip contours report
	public Reflector[] getReflectors() {
		double[] areas = table.getNumberArray("area", new double[0]);
		double[] x = table.getNumberArray("centerX", new double[0]);
		double[] y = table.getNumberArray("centerY", new double[0]);
		
		Reflector[] reflectors = new Reflector[areas.length];
		
		for(int i = 0; i < areas.length; i++) {
			reflectors[i] = new Reflector(areas[i], x[i], y[i]);
		}
		return reflectors;
	}
	
	//returns Reflector with largest area
	public Reflector findLargest(Reflector[] ref) { 
		if(ref == null || ref.length == 0)
			return null;
		
		int largest = 0;
		
		for(int i = 1; i < ref.length; i++) 
			if(ref[i].area > ref[largest].area)
				largest = i;
		
		return ref[largest];
	}
	
	//returns double array for drive train that contains [turnSpeed, moveSpeed, 0]
	public double[] alignShooting() {
		Reflector largestRef = findLargest(getReflectors());
		if(largestRef==null)
			return null;
		
		double turnSpeed = 0;
		double moveSpeed = 0;
		
		if(largestRef.x < (IdealX-Buffer)*ImageScaleFactor) 
			turnSpeed =  this.turnSpeed*((largestRef.x-IdealX)/Math.max(IdealX,ImageX-IdealX))-0.03;
		
		else if(largestRef.x > (IdealX+Buffer)*ImageScaleFactor)
			turnSpeed =  this.turnSpeed*((largestRef.x-IdealX)/Math.max(IdealX,ImageX-IdealX))+0.03;
		
		
		if(largestRef.y < (IdealY-Buffer)*ImageScaleFactor) 
			moveSpeed = -this.moveSpeed*((largestRef.y-IdealY)/Math.max(IdealX,ImageX-IdealX))+0.1;
		
		else if(largestRef.y > (IdealY+Buffer)*ImageScaleFactor)
			moveSpeed = -this.moveSpeed*((largestRef.y-IdealY)/Math.max(IdealX,ImageX-IdealX))-0.1;
		
		return new double[]{turnSpeed, moveSpeed, 0};
	}

	 public class Reflector {
		 private double area, x , y ;
	
		 public Reflector(double area, double x, double y){
			 this.area = area;
			 this.x = x;
			 this.y = y;
		 }
	 }	
}