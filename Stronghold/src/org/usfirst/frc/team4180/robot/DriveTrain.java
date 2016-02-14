package org.usfirst.frc.team4180.robot;

import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.VictorSP;

public class DriveTrain {

	// declare Vics
	private VictorSP leftVic;
	private VictorSP rightVic;
	private DoubleSolenoid gearShifting;
	
	//for gear shifting true is forward; false is reverse
	//we don't know which is high or low gear, but the robot should start on "reverse" gear, and the button will toggle the state
	private boolean state;

	// initialize Vics and variables
	public DriveTrain(int leftPort, int rightPort, int gearShiftPort1, int gearShiftPort2) {
		leftVic = new VictorSP(leftPort);
		rightVic = new VictorSP(rightPort);
		gearShifting = new DoubleSolenoid(gearShiftPort1, gearShiftPort2);
		state = false;
	}

	// update speed
	public void updateSpeed(double[] JstickInfo) {
		double x = JstickInfo[0];
		double y = JstickInfo[1];
		double z = JstickInfo[2];
	    leftVic.set(Math.min((Math.max(-1, y - x)), 1));
		rightVic.set(Math.min((Math.max(-1, y + x)), 1));
	}
	 
	public void toggleGearShifting() {
		state = !state;
		gearShifting.set(state ? DoubleSolenoid.Value.kForward : DoubleSolenoid.Value.kReverse);
	}
}