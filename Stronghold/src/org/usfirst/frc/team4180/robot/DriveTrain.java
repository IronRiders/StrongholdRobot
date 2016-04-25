package org.usfirst.frc.team4180.robot;

import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.VictorSP;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class DriveTrain {
	private VictorSP leftVic, rightVic;
	private DoubleSolenoid gearShifting;
	
	private boolean state = false;
	
	public DriveTrain(int leftPort, int rightPort, int gearShiftPort1, int gearShiftPort2) {
		leftVic = new VictorSP(leftPort);
		rightVic = new VictorSP(rightPort);
		gearShifting = new DoubleSolenoid(gearShiftPort1, gearShiftPort2);
	}

	//takes double array [x, y] and sets the speeds to that
	public void updateSpeed(double[] JoystickInfo) {
		double x = JoystickInfo[0];
		double y = JoystickInfo[1];
		SmartDashboard.putString("DB/String 8", x + "");
		SmartDashboard.putString("DB/String 9", y + "");
		leftVic.set(y - x);
		rightVic.set(-y -x);
	}
	
	public void toggleGearShifting() {
		state = !state;
		gearShifting.set(state ? DoubleSolenoid.Value.kForward : DoubleSolenoid.Value.kReverse);
	}
}