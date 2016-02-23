package org.usfirst.frc.team4180.robot;

import edu.wpi.first.wpilibj.networktables.NetworkTable;

public class ImageRecognizer {
	private NetworkTable table;
	double moveSpeed = 0.35;
	double turnSpeed = 0.25;
	double IdealX = 166;
	double IdealY = 123;
	double Buffer = 4;
	double ImageScaleFactor =1;
	double ImageX = 320*ImageScaleFactor;
	double ImageY = 240*ImageScaleFactor;
	
	public ImageRecognizer() {
		table = NetworkTable.getTable("GRIP/myContoursReport");
	}
	
	public Reflector[] getReflectors() {
		double[] areas = table.getNumberArray("area", new double[0]);
		double[] x = table.getNumberArray("centerX", new double[0]);
		double[] y = table.getNumberArray("centerY", new double[0]);
		double[] solid = table.getNumberArray("solidity", new double[0]);
		double[] h = table.getNumberArray("height", new double[0]);
		double[] w = table.getNumberArray("width", new double[0]);
		
		Reflector[] reflectors = new Reflector[areas.length];
		
		for(int i = 0; i < areas.length; i++) {
			reflectors[i] = new Reflector(areas[i], x[i], y[i], w[i], h[i], solid[i]);
		}
		
		return reflectors;
	}
	
	public Reflector findLargest(Reflector[] ref) { //assumes ref is not empty
		if(ref.length==0){
			return null;
		}
		int largest = 0;
		
		for(int i = 1; i < ref.length; i++) {
			if(ref[i].area > ref[largest].area)
				largest = i;
		}
		
		return ref[largest];
	}
	
	public double[] alignShooting() {
		Reflector closestRef = findLargest(getReflectors());
		if(closestRef==null)
			return new double[]{4180, 4180, 4180};
		
//		if(closestRef.x < (IdealX+Buffer)*ImageScaleFactor &&
//		   closestRef.x > (IdealX-Buffer)*ImageScaleFactor &&
//		   closestRef.y < (IdealY+Buffer)*ImageScaleFactor &&
//		   closestRef.y > (IdealY-Buffer)*ImageScaleFactor)
		
		double turnSpeed = 0;
		double moveSpeed = 0;
		
		if(closestRef.x < (IdealX-Buffer)*ImageScaleFactor) {
			//turn right
			turnSpeed = -this.turnSpeed;;
		} 
		else if(closestRef.x > (IdealX+Buffer)*ImageScaleFactor) {
			//turn left
			turnSpeed = this.turnSpeed;
		}
		
		if(closestRef.y < (IdealY-Buffer)*ImageScaleFactor) {
			//move forward
			moveSpeed = this.moveSpeed;
		}
		else if(closestRef.y > (IdealY+Buffer)*ImageScaleFactor) {
			//
			//move backward
			moveSpeed = -this.moveSpeed;
		}
		//stay still
		return new double[]{turnSpeed, moveSpeed, 0};
	}

	 public class Reflector {
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
