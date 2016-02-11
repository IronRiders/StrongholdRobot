package org.usfirst.frc.team4180.robot;

import edu.wpi.first.wpilibj.VictorSP;

public class DriveTrain {

	// declare Vics
	private VictorSP leftVic;
	private VictorSP rightVic;

	// initialize Vics and variables
	public DriveTrain(int leftPort, int rightPort) {
		leftVic = new VictorSP(leftPort);
		rightVic = new VictorSP(rightPort);
	}

	// update speed
	public void updateSpeed(double[] JstickInfo) {
		double x = JstickInfo[0];
		double y = JstickInfo[1];
		double z = JstickInfo[2];
	     leftVic.set(Math.min((Math.max(-1, y - x)), 1));
		rightVic.set(Math.min((Math.max(-1, y + x)), 1));
	}
}


