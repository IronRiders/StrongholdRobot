package org.usfirst.frc.team4180.robot;

import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.VictorSP;
import edu.wpi.first.wpilibj.DigitalInput;

public class ShooterIntake {
	private VictorSP shooterVic;
	private VictorSP shooterVic2;
	private Solenoid elevationSolenoid;
	private VictorSP intakeAngle;
	private VictorSP intakeRoller;
	
	private final double driverTrust = 0.1;
	
//	private DigitalInput tooHigh = new DigitalInput(-1);
//	private DigitalInput tooLow  = new DigitalInput(-1);

	public boolean shooting = false;

	public ShooterIntake(int VicPort,int VicPort2, int solenoidPort, int rollerVicPort, int angleVicPort) {
		shooterVic = new VictorSP(VicPort);
		shooterVic2 = new VictorSP(VicPort2);
		elevationSolenoid = new Solenoid(solenoidPort);
		intakeAngle = new VictorSP(angleVicPort);
		intakeRoller = new VictorSP(rollerVicPort);
	}
	
	public void intake(boolean stop) {
		intakeRoller.set(stop ? 0.0 : 0.5);
		setShooterVic(stop ? 0.0 : 0.07);
	}


	public void moveArm(double y) {
	//	if ((!tooHigh.get() && y < 0) || (!tooLow.get() && y > 0)) {
//	/		intakeAngle.set(driverTrust * y);
	//	}
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
	
	public void shoot() {
		shooting = true;
		tick = 0;
	}
	int tick = 0;
	public void shooterTic() {
		if(shooting){
		tick++;
		if(tick==2)
			setShooterVic(0.5);
		
		if(tick == 75)
		{		
			setShooterSolenoid(true);
		}
		if(tick > 100){
			setShooterVic(0.5 - (tick - 100) * 0.005);	
		}
		if (tick > 200) {
			tick = 0;
			shooting = false;
			setShooterSolenoid(false);
		}
		}
	}
}