package org.usfirst.frc.team4180.robot;

import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.VictorSP;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class DriveTrain {
	private VictorSP leftVic, rightVic;
	private DoubleSolenoid gearShifting;
	
	private boolean state = false;
	
	public DriveTrain(final int leftPort, final int rightPort, final int gearShiftPort1, final int gearShiftPort2) {
		leftVic = new VictorSP(leftPort);
		rightVic = new VictorSP(rightPort);
		gearShifting = new DoubleSolenoid(gearShiftPort1, gearShiftPort2);
	}

	public void stop() {
		updateSpeed(0, 0, 0);
	}

	//takes double array or multiple doubles and sets
	//the x speed to the first element, and the y speed to the second
	public void updateSpeed(final double... joystickInfo) {
		final double x = joystickInfo[0];
		final double y = joystickInfo[1];
		SmartDashboard.putString("DB/String 8", String.valueOf(y));
		SmartDashboard.putString("DB/String 9", String.valueOf(y));
		leftVic.set(y - x);
		rightVic.set(-y - x);
	}
	
	public void toggleGearShifting() {
		state = !state;
		if(state) {
			gearShifting.set(DoubleSolenoid.Value.kForward);
		} else {
			gearShifting.set(DoubleSolenoid.Value.kReverse);
		}
	}
}