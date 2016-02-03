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
		
	}
}
