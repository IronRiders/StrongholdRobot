package org.usfirst.frc.team4180.robot;

import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.VictorSP;

public class DriveTrain {

	private VictorSP leftVic;
	private VictorSP rightVic;
	private DoubleSolenoid gearShifting;
	
	private boolean state = false;
	
	public DriveTrain(int leftPort, int rightPort, int gearShiftPort1, int gearShiftPort2) {
		leftVic = new VictorSP(leftPort);
		rightVic = new VictorSP(rightPort);
		gearShifting = new DoubleSolenoid(gearShiftPort1, gearShiftPort2);
	}

	//takes double array [x,y] and sets the speeds to that
	public void updateSpeed(double[] JstickInfo) {
		double x = JstickInfo[0];
		double y = JstickInfo[1];
		leftVic.set(y-x);
		rightVic.set(-y-x);
	}
	
	public void toggleGearShifting() {
			state = !state;
			gearShifting.set(state ? DoubleSolenoid.Value.kForward : DoubleSolenoid.Value.kReverse);
	}
}