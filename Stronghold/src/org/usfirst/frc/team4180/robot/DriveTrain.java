package org.usfirst.frc.team4180.robot;

import java.util.ArrayList;
import java.util.List;

import edu.wpi.first.wpilibj.VictorSP;

public class DriveTrain {

	private VictorSP leftVic;
	private VictorSP rightVic;
	boolean drift = true;
	private List<Double> pastX = new ArrayList<Double>();
	private List<Double> pastY = new ArrayList<Double>();
	
	public DriveTrain(int leftPort, int rightPort) {
		leftVic = new VictorSP(leftPort);
		rightVic = new VictorSP(rightPort);
		pastX = populateList(50, 0);
		pastY = populateList(50, 0);
	}

	public void setLeftVicSpeed(double leftVicSpeed) {
		leftVic.set(-leftVicSpeed);
		// This is assuming one of the Vics (the left one) is flipped
	}

	public void setRightVicSpeed(double rightVicSpeed) {
		rightVic.set(rightVicSpeed);
	}
		
	


    public void updateSpeed(double[] d){
    	double x = d[0];
    	double y = d[1];
    	double leftSpeed = Math.min((Math.max(-1, y - x)), 1);
    	double rightSpeed = Math.min((Math.max(-1, y + x)), 1);
    	if(drift){
    		pastX.add(0, leftSpeed);
    		pastY.add(0, rightSpeed);
    		pastX.remove(pastX.size()-1);
    		pastY.remove(pastY.size()-1);
    		leftSpeed = drift(pastX);
    		rightSpeed = drift(pastY);
    		
    	}
    	setLeftVicSpeed(leftSpeed);
		setRightVicSpeed(rightSpeed);
    }
    
   private List<Double> populateList(int size, double item){
	   List<Double> l = new ArrayList<Double>(); 
	   for (int i = 0; i < size; i++) {
		l.add(item);
	}
	   return l;
   }
   
   private double drift(List<Double> l) {
	   double div = 0; 
	   double tot = 0;
	   double mult = 1;
	   for (int i = 0; i < l.size(); i++) {
		   div += mult;
		   tot += l.get(i)*mult;
		   mult -= 0.02;
	   }
	   return tot/div;
   }
}


