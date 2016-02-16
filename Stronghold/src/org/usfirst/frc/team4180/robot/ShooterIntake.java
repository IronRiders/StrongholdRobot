package org.usfirst.frc.team4180.robot;

import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.VictorSP;
import edu.wpi.first.wpilibj.TalonSRX;
import edu.wpi.first.wpilibj.Timer;


public class ShooterIntake {
//  Notes from Hardware/Design for shooter:	
//	there will be one pneumatic piston which moves in and out (single solenoid)
//	there will be one motor (Vic SRX) which will turn in both directions
//	motor will need to be controlled by a button
//	Information we have from Design/Hardware about Intake:
//	One motor (Vic SRX) which turns in both directions for rollers
//	One motor (Or maybe pneumatics) which turns in both directions for angle

//  NOTE: Orientations of motors and pneumatics should be correct according to design and hardware
//  NOTE: We should test for which speed works best for the different tasks
	
	private VictorSP shooterVic;
	private VictorSP shooterVic2;
	private Solenoid elevationSolenoid;
	private VictorSP intakeAngle;
	private VictorSP intakeRoller;
	
	public boolean shooting = false;
	private Timer  shootTimer = new Timer();
	double shooterSpeed = 0;
	
	private static final double wait1 = 0, wait2 = 0, wait3 = 0; 
	
	public ShooterIntake(int VicPort,int VicPort2, int solenoidPort, int rollerVicPort, int angleVicPort) {
		shooterVic = new VictorSP(VicPort);
		shooterVic2 = new VictorSP(VicPort2);
		elevationSolenoid = new Solenoid(solenoidPort);
		intakeAngle = new VictorSP(angleVicPort);
		intakeRoller = new VictorSP(rollerVicPort);

		shootTimer.start();
	}

	
	public void setShooterVic(double shooterVicSpeed){
		shooterVic.set(shooterVicSpeed);
		shooterVic2.set(shooterVicSpeed);

	}
	
	public void setShooterSolenoid(boolean state) {
		elevationSolenoid.set(state);
	}
	
	public void setRollerVic(double rollerVicSpeed) {
		intakeRoller.set(rollerVicSpeed);
	}
	
	public void setAngleVic(double angleVicSpeed) {
		intakeAngle.set(angleVicSpeed);
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
		if(shootTimer.get() < 5)
			setShooterVic(-1); //pushes ball into intake by reversing shooter wheels
		if(shootTimer.get() > wait1 - 5 && shootTimer.get() < wait1 + 5)
			setShooterVic(shooterSpeed); //get shooter wheels up to speed
		if(shootTimer.get() > wait2 - 5 && shootTimer.get() < wait2 + 5)
			setRollerVic(1); //intake motors reversed (aka their normal direction???) to push ball back into shooter wheels
		if(shootTimer.get() > wait3 - 5 && shootTimer.get() < wait3 + 5) {
			stopShoot();
			stopIntake();
			shooting = false;
		}
		//test for what speeds will be best for intaking and shooting
	}
	
	public void stopShoot(){
		setShooterVic(0); 	
	}
	
	//Intake methods
	//Driver gets to control raising and lowering intake
	public void setIntakeArmSpeed(double intakeArmSpeed) {
		setAngleVic(intakeArmSpeed);
	}

	public void intakeOn(){
		setRollerVic(1);
		//test for which speed would be best for intaking  
	}
	
	public void reverseIntake(){
		setRollerVic(-1);
		//test for which speed would be best for reverseIntaking
	}	
	
	public void stopIntake(){
		setRollerVic(0);
	}
}