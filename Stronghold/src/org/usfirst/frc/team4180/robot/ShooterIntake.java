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
	
	
	//Shooter methods
	public void lowerShooter() {
		setShooterSolenoid(false); //check which is which
	}
	
	public void raiseShooter() {
		setShooterSolenoid(true); //check which is which
	}
	
	public void shoot() {
		setShooterTalon(1); 
		//check whether negative or positive is shooting and test for which speed
		//this should probably call some other methods. need to get procedure from design/hardware
	}
	
	public void stopShoot(){
		setShooterTalon(0); 
	}
	
	//Intake methods
	
	//are we going to base how long to raise/lower for off of a timer? Or a limit switch?
	public void raiseIntake() {
		setAngleTalon(1); 
		//check whether negative or positive is raising and test for which speed
	}
	
	public void lowerIntake() {
		setAngleTalon(-1); 
		//check whether negative or positive is lowering and test for which speed
	}
	
	public void stopIntakeArm(){
		setAngleTalon(0); 
	}
	
	public void intakeOn(){
		setRollerTalon(1);
		//check whether negative or positive is intake and test for which speed
	}
	
	public void reverseIntake(){
		setRollerTalon(-1);
		//check whether negative or positive is reverseIntake and test for which speed
	}	
	
	public void stopIntake(){
		setRollerTalon(0);
	}
	
	
	//	We may be switching the shooter solenoids to double solenoids later
}
