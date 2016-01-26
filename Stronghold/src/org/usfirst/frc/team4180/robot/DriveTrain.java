package org.usfirst.frc.team4180.robot;

//import statement needed

public class DriveTrain {
	// Placeholder Values
	int LEFT_PORT = 0;
	int RIGHT_PORT = 1;

	// declare motors ("motor" is a placeholder object)
	private motor leftMotor;
	private motor rightMotor;
	
	// declare variables (x,y)
	private double xPos;
	private double yPos;

	// initialize motors and variables
	public DriveTrain() {
		leftMotor = new motor(LEFT_PORT);
		rightMotor = new motor(RIGHT_PORT);
		xPos = 0.0;
		yPos = 0.0;
	}

	// set left speed
	public void setLeftMotorSpeed(double leftMotorSpeed) {
		leftMotor.set(-leftMotorSpeed);
		// This is assuming one of the motors (the left one) is flipped
	}

	// set right speed
	public void setRightMotorSpeed(double rightMotorSpeed) {
		rightMotor.set(rightMotorSpeed);
	}

	// set speed (left and right)
	public void setMotorSpeed(double rightMotorSpeed, double leftMotorSpeed) {
		setLeftMotorSpeed(leftMotorSpeed);
		setRightMotorSpeed(rightMotorSpeed);
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
		setMotorSpeed(Math.min((Math.max(-1, yPos - xPos)), 1),
				Math.min((Math.max(-1, yPos + xPos)), 1));
	}
	// notes from hardware
	// one motor on each side
	// Motors called Victor SP or Talon SRX
}


