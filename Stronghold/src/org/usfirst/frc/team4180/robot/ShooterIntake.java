package org.usfirst.frc.team4180.robot;

import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.TalonSRX;

public class ShooterIntake {
//  Notes from Hardware/Design for shooter:	
//	there will be one pneumatic piston which moves in and out (single solenoid)
//	there will be one motor (Talon SRX) which will turn in both directions
//	motor will need to be controlled by a button
//	Information we have from Design/Hardware about Intake:
//	One motor (Talon SRX) which turns in both directions for rollers
//	One motor (Or maybe pneumatics) which turns in both directions for angle
		
	
	private TalonSRX shooterTalon;
	private Solenoid elevationSolenoid;
	private TalonSRX intakeAngle;
	private TalonSRX intakeRoller;
	
	public ShooterIntake(int talonPort, int solenoidPort, int rollerTalonPort, int angleTalonPort) {
		shooterTalon = new TalonSRX(talonPort);
		elevationSolenoid = new Solenoid(solenoidPort);
		intakeAngle = new TalonSRX(angleTalonPort);
		intakeRoller = new TalonSRX(rollerTalonPort);
	}
	
	public void setShooterTalon(double shooterTalonSpeed){
		shooterTalon.set(shooterTalonSpeed);
	}
	
	public void setShooterSolenoid(boolean state) {
		elevationSolenoid.set(state);
	}
	
	public void setRollerTalon(double rollerTalonSpeed) {
		intakeRoller.set(rollerTalonSpeed);
	}
	
	public void setAngleTalon(double angleTalonSpeed) {
		intakeAngle.set(angleTalonSpeed);
	}

//	We may be switching the shooter solenoids to double solenoids later
}
