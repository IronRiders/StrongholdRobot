package org.usfirst.frc.team4180.robot;

import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.TalonSRX;

public class Shooter {
//  Notes from Hardware/Design:	
//	there will be one pneumatic piston which moves in and out
//	there will be one motor (Talon SRX) which will turn in both directions
//	motor will need to be controlled by a button
	public static final int SHOOTER_TALON_PORT = 0; //place holder value
	public static final int SHOOTER_SOLENOID_1 = 0;  //place holder value
	public static final int SHOOTER_SOLENOID_2 = 0;  //place holder value
	
	private TalonSRX shooterTalon;
	private DoubleSolenoid shooterSolenoid;
	
	public Shooter() {
		shooterTalon = new TalonSRX(SHOOTER_TALON_PORT);
		shooterSolenoid = new DoubleSolenoid(SHOOTER_SOLENOID_1, SHOOTER_SOLENOID_2);
	}
	
	public void setShooterTalon(double shooterTalonSpeed){
		shooterTalon.set(shooterTalonSpeed);
	}
	
	//Set Pneumatics???

}
