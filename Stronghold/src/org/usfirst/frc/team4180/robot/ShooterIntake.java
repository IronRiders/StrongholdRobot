package org.usfirst.frc.team4180.robot;

import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.TalonSRX;
import edu.wpi.first.wpilibj.Timer;

public class ShooterIntake {
//  Notes from Hardware/Design for shooter:	
//	there will be one pneumatic piston which moves in and out (single solenoid)
//	there will be one motor (Talon SRX) which will turn in both directions
//	motor will need to be controlled by a button
//	Information we have from Design/Hardware about Intake:
//	One motor (Talon SRX) which turns in both directions for rollers
//	One motor (Or maybe pneumatics) which turns in both directions for angle

//  NOTE: Orientations of motors and pneumatics should be correct according to design and hardware
//  NOTE: We should test for which speed works best for the different tasks
	
	private TalonSRX shooterTalon;
	private Solenoid elevationSolenoid;
	private TalonSRX intakeAngle;
	private TalonSRX intakeRoller;
	
	public boolean shooting = false;
	private Timer  shootTimer = new Timer();
	double shooterSpeed = 0;
	
	private static final double wait1 = 0, wait2 = 0, wait3 = 0; 
	
	public ShooterIntake(int talonPort, int solenoidPort, int rollerTalonPort, int angleTalonPort) {
		shooterTalon = new TalonSRX(talonPort);
		elevationSolenoid = new Solenoid(solenoidPort);
		intakeAngle = new TalonSRX(angleTalonPort);
		intakeRoller = new TalonSRX(rollerTalonPort);
		shootTimer.start();
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
		setShooterSolenoid(false);
	}
	
	public void raiseShooter() {
		setShooterSolenoid(true);
	}
	
	//SET UP: ball is held against shooting wheels and shooter is up (and intake is raised up by drive)
	public void shoot() {
		shooting = true;
		shootTimer.reset();
	}
	
	public void shooterTic() {
		setShooterTalon(-1); //pushes ball into intake by reversing shooter wheels
		if(shootTimer.get() > wait1 - 5 && shootTimer.get() < wait1 + 5)
			setShooterTalon(shooterSpeed); //get shooter wheels up to speed
		if(shootTimer.get() > wait2 - 5 && shootTimer.get() < wait2 + 5)
			setRollerTalon(1); //intake motors reversed (aka their normal direction???) to push ball back into shooter wheels
		if(shootTimer.get() > wait3 - 5 && shootTimer.get() < wait3 + 5) {
			stopShoot();
			stopIntake();
			shooting = false;
		}
		//test for what speeds will be best for intaking and shooting
	}
	
	public void stopShoot() {
		setShooterTalon(0); 
	}
	
	//Intake methods
	//Driver gets to control raising and lowering intake
	public void setIntakeArmSpeed(double intakeArmSpeed) {
		setAngleTalon(intakeArmSpeed);
	}

	public void intakeOn(){
		setRollerTalon(1);
		//test for which speed would be best for intaking  
	}
	
	public void reverseIntake(){
		setRollerTalon(-1);
		//test for which speed would be best for reverseIntaking
	}	
	
	public void stopIntake(){
		setRollerTalon(0);
	}
}
