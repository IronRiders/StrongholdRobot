package src.org.usfirst.frc.team4180.robot;

import edu.wpi.first.wpilibj.VictorSP;

//import statement needed

public class DriveTrain {
	// Placeholder Values
	int LEFT_PORT = 0;
	int RIGHT_PORT = 1;

	// declare Vics
	private VictorSP leftVic;
	private VictorSP rightVic;
	
	// declare variables (x,y)
	private double xPos;
	private double yPos;

	// initialize Vics and variables
	public DriveTrain() {
		leftVic = new VictorSP(LEFT_PORT);
		rightVic = new VictorSP(RIGHT_PORT);
		xPos = 0.0;
		yPos = 0.0;
	}

	// set left speed
	public void setLeftVicSpeed(double leftVicSpeed) {
		leftVic.set(-leftVicSpeed);
		// This is assuming one of the Vics (the left one) is flipped
	}

	// set right speed
	public void setRightVicSpeed(double rightVicSpeed) {
		rightVic.set(rightVicSpeed);
	}

	// set speed (left and right)
	public void setVicSpeed(double rightVicSpeed, double leftVicSpeed) {
		setLeftVicSpeed(leftVicSpeed);
		setRightVicSpeed(rightVicSpeed);
	}

	// set x and y variables
	public void setXPos(double newXPos) {
		xPos = newXPos;
	}

	public void setYPos(double newYPos) {
		yPos = newYPos;
	}

	// update speed
	public void updateSpeed() {
		setVicSpeed(Math.min((Math.max(-1, yPos - xPos)), 1),
				Math.min((Math.max(-1, yPos + xPos)), 1));
	}
	// notes from hardware
	// one Vic on each side
	// Vics called VictorSP or Talon SRX
}


