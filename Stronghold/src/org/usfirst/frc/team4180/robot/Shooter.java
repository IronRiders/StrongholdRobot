package org.usfirst.frc.team4180.robot;

import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.TalonSRX;
//import edu.wpi.first.wpilibj.DoubleSolenoid;

public class Shooter {
//  Notes from Hardware/Design:	
//	there will be one pneumatic piston which moves in and out (single solenoid)
//	there will be one motor (Talon SRX) which will turn in both directions
//	motor will need to be controlled by a button
	
	private TalonSRX shooterTalon;
	private Solenoid shooterSolenoid;
	
	public Shooter(int talonPort, int solenoidPort) {
		shooterTalon = new TalonSRX(talonPort);
		shooterSolenoid = new Solenoid(solenoidPort);
	}
	
	public void setShooterTalon(double shooterTalonSpeed){
		shooterTalon.set(shooterTalonSpeed);
	}
	
	//turns on the solenoid
	public void setSolenoid(boolean state) {
		shooterSolenoid.set(state);
	}
	
	public void lowerShooter() {
		
	}
	
	public void raiseShooter() {
		
	}
	
	//Maybe make this take a speed, or have it fire based on how long you hold a button
	public void fire() {
		
	}
//We may be switching to double solenoids later
}
