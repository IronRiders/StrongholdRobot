package org.usfirst.frc.team4180.robot;

import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.TalonSRX;
//import edu.wpi.first.wpilibj.DoubleSolenoid;

public class Shooter {
//  Notes from Hardware/Design:	
//	there will be one pneumatic piston which moves in and out (single solenoid)
//	there will be one motor (Talon SRX) which will turn in both directions
//	motor will need to be controlled by a button
	public static final int SHOOTER_TALON_PORT = 0; //place holder value
	public static final int SHOOTER_SOLENOID = 0;  //place holder value
	
	private TalonSRX shooterTalon;
	private Solenoid shooterSolenoid;
	
	public Shooter() {
		shooterTalon = new TalonSRX(SHOOTER_TALON_PORT);
		shooterSolenoid = new Solenoid(SHOOTER_SOLENOID);
	}
	
	public void setShooterTalon(double shooterTalonSpeed){
		shooterTalon.set(shooterTalonSpeed);
	}
	
	//turns on the solenoid
	public void solenoidOn() {
		shooterSolenoid.set(true);
	}
	
	//turns off the solenoid
	public void solenoidOff() {
		shooterSolenoid.set(false);
	}
	
// Here just in case we need to change back to double solenoids
//	//Turns the solenoid on forwards
//	public void pistonSolenoidForward() {
//		shooterSolenoid.set(DoubleSolenoid.Value.kForward);
//	}
//	
//	//Turns both solenoids off
//	//maybe unnecessary
//	public void pistonSolenoidOff() {
//		shooterSolenoid.set(DoubleSolenoid.Value.kOff);
//	}
//	
//	//Turns the solenoid on backwards
//	public void pistonSolenoidReverse() {
//		shooterSolenoid.set(DoubleSolenoid.Value.kReverse);
//	}
}
