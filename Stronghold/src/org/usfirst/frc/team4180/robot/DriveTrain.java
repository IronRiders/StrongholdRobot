package org.usfirst.frc.team4180.robot;

import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.VictorSP;

public class DriveTrain {

	private VictorSP leftVic;
	private VictorSP rightVic;
	private DoubleSolenoid gearShifting;
	
	public DriveTrain(int leftPort, int rightPort, int gearShiftPort1, int gearShiftPort2) {
		leftVic = new VictorSP(leftPort);
		rightVic = new VictorSP(rightPort);
		gearShifting = new DoubleSolenoid(gearShiftPort1, gearShiftPort2);
		state = false;
	}

	public void updateSpeed(double[] JstickInfo) {
		double x = buffer(JstickInfo[0]);
		double y = buffer(JstickInfo[1]);
		double z = buffer(JstickInfo[2]);
		
	    leftVic.set(Math.min((Math.max(-1, y - x)), 1));
		rightVic.set(-Math.min((Math.max(-1, y + x)), 1));
	}
	public static double buffer(double d){
		if(d>0.05||d<-0.05) return d;
		return 0;
	}
	
	private boolean state; 
	public void toggleGearShifting() {
		state = !state;
		gearShifting.set(state ? DoubleSolenoid.Value.kForward : DoubleSolenoid.Value.kReverse);
	}
}