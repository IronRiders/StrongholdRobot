package org.usfirst.frc.team4180.robot;

import edu.wpi.first.wpilibj.networktables.NetworkTable;

private class ImageRecognizer {
	private final NetworkTable table;
	//Where did this numbers come from?
	private double moveSpeed = 1; //TODO: Figure out if these are being shadowed
	private double turnSpeed = 1.1;
	private static final double IDEAL_X = 176;
	private static final double IDEAL_Y = 34;
	private static final double BUFFER = 4;
	private static final double IMAGE_SCALE_FACTOR = 1;
	private static final double IMAGE_X = 320 * IMAGE_SCALE_FACTOR;
	private static final double IMAGE_Y = 240 * IMAGE_SCALE_FACTOR;

	public ImageRecognizer() {
		table = NetworkTable.getTable("GRIP/myContoursReport");
	}

	//returns all reflectors found in the grip contours report
	private Reflector[] getReflectors() {
		final double[] areas = table.getNumberArray("area", new double[0]);
		final double[] xs = table.getNumberArray("centerX", new double[0]);
		final double[] ys = table.getNumberArray("centerY", new double[0]);
		
		final Reflector[] reflectors = new Reflector[areas.length];
		
		for(int i = 0; i < areas.length; i++) {
			reflectors[i] = new Reflector(areas[i], xs[i], ys[i]);
		}
		return reflectors;
	}
	
	//returns Reflector with largest area
	private static Reflector findLargest(Reflector[] refs) { 
		if(refs.length == 0) {
			return null;
		}

		Reflector largest = refs[1];
		for(int i = 2; i < refs.length; i++) { //Why skip refs[0]?
			if(refs[i].area > largest.area) {
				largest = i;
			}
		}
		
		return largest;
	}
	
	//reSturns double array for drive train that contains [turnSpeed, moveSpeed, 0]
	public double[] alignShooting() {
		final Reflector largestRef = findLargest(getReflectors());
		if(largestRef == null) return null; 
		
		double turnSpeed = 0; //ω
		double moveSpeed = 0; //v
		
		if(largestRef.x < (IDEAL_X - BUFFER) * IMAGE_SCALE_FACTOR) {
			turnSpeed =  Math.min((this.turnSpeed * ((largestRef.x - IDEAL_X)/Math.max(IDEAL_X, ImageX - IDEAL_X)) - 0.07), -0.25);
		    //change to biggest negative x where robot still turns
		}
		
		else if(largestRef.x > (IDEAL_X + BUFFER) * IMAGE_SCALE_FACTOR) {
			turnSpeed =  Math.max((this.turnSpeed * ((largestRef.x - IDEAL_X)/Math.max(IDEAL_X, ImageX - IDEAL_X)) + 0.07), 0.25);
	     	//change to smallest positive x where robot still turns
		}
		
		if(largestRef.y < (IDEAL_Y - BUFFER) * IMAGE_SCALE_FACTOR) {
			moveSpeed = Math.max((-this.moveSpeed * ((largestRef.y - IDEAL_Y)/Math.max(IDEAL_X, ImageX - IDEAL_X)) + 0.1), 0.07);
		    //change to smallest possible y where robot still moves
		}
		
		else if(largestRef.y > (IDEAL_Y + BUFFER) * IMAGE_SCALE_FACTOR) {
			moveSpeed = Math.min((-this.moveSpeed * ((largestRef.y - IDEAL_Y)/Math.max(IDEAL_X, ImageX - IDEAL_X)) - 0.1), -0.07);
		    //change to biggest negative y where robot still moves
		}

		return new double[] {turnSpeed, moveSpeed, 0};
	}

	 private static class Reflector {
		 private final double area, x, y;
	
		 private Reflector(final double area, final double x, final double y){
			 this.area = area;
			 this.x = x;
			 this.y = y;
		 }
	 }
}
