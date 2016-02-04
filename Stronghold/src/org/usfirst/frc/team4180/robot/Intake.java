package org.usfirst.frc.team4180.robot;

import edu.wpi.first.wpilibj.TalonSRX;

public class Intake {
//Information we have from Design and Hardware about Mechanism:
	//One motor (Talon SRX) which turns in both directions for rollers
	//One motor (Or maybe pneumatics) which turns in both directions for angle
	
	private TalonSRX rollerTalon;
	private TalonSRX angleTalon;
	
	public Intake(int rollerTalonPort, int angleTalonPort) {
		rollerTalon = new TalonSRX(rollerTalonPort);
		angleTalon = new TalonSRX(angleTalonPort);
	}
	
	//sets speed for front roller motors
	public void setRollerTalon(double rollerTalonSpeed) {
		rollerTalon.set(rollerTalonSpeed);
	}
	
	//sets speed for arm angle motors
	public void setAngleTalon(double angleTalonSpeed) {
		angleTalon.set(angleTalonSpeed);
	}
	
	public void intakeBall() {
		// turn on/off intake motor with toggle 
		// only one direction
		setRollerTalon(1);
	}
	
	public void raiseArm() {
		//set motors speed to 1
		//wait until arm is raised
		//set motor speed to 0
		//this will be able to stop at any point with stopArm method
		
		setAngleTalon(1);
		//wait until arm raised
		setAngleTalon(0);
	}

	public void lowerArm() {
		//set motors speed to -1
		//wait until arm is lowered
		//set motor speed to 0
		//this will be able to stop at any point with stopArm method
		
		setAngleTalon(-1);
		//wait until arm lowered
		setAngleTalon(0);
	}

	public void stopArm() {
		//set speed to 0
		//probably won't need this
		setAngleTalon(0);
	}

	public void rollOut() {
		//raise the intake arm
		//set the intake motor to reverse
		//set 
		raiseArm();
		setRollerTalon(-1);
	}
}
