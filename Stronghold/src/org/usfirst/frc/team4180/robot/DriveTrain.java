package org.usfirst.frc.team4180.robot;

import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.VictorSP;

public class DriveTrain {

	private VictorSP leftVic;
	private VictorSP rightVic;
	private DoubleSolenoid gearShifting;
	private boolean shift = false;
	private boolean state = false; 
	
	public DriveTrain(int leftPort, int rightPort, int gearShiftPort1, int gearShiftPort2) {
		leftVic = new VictorSP(leftPort);
		rightVic = new VictorSP(rightPort);
		gearShifting = new DoubleSolenoid(gearShiftPort1, gearShiftPort2);
	}

	public void updateSpeed(double[] JstickInfo) {
		double left = JstickInfo[1] - JstickInfo[0];
		double right = -JstickInfo[1] - JstickInfo[0];
		shift = Math.abs(left) < 0.5 && Math.abs(right) < 0.5;	
		leftVic.set(left);
		rightVic.set(right);
	}

	public void toggleGearShifting() {
		if(shift) {
			state = !state;
			gearShifting.set(state ? DoubleSolenoid.Value.kForward : DoubleSolenoid.Value.kReverse);
		}
	}
}