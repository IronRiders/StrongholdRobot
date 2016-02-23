package org.usfirst.frc.team4180.robot;

import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.VictorSP;
//import edu.wpi.first.wpilibj.DigitalInput;

public class ShooterIntake {
	private VictorSP shooterVic;
	private VictorSP shooterVic2;
	private Solenoid elevationSolenoid;
	private VictorSP intakeAngle;
	private VictorSP intakeRoller;
	private VictorSP intakeArm;
	
	private final double driverTrust = 0.01;
	public boolean shooting = false;
	
	private int tick = 0;
	
//	private DigitalInput tooHigh = new DigitalInput(-1);
//	private DigitalInput tooLow  = new DigitalInput(-1);

	public ShooterIntake(int VicPort, int VicPort2, int solenoidPort, int rollerVicPort, int angleVicPort, int intakeArmPort) {
		shooterVic = new VictorSP(VicPort);
		shooterVic2 = new VictorSP(VicPort2);
		elevationSolenoid = new Solenoid(solenoidPort);
		intakeAngle = new VictorSP(angleVicPort);
		intakeRoller = new VictorSP(rollerVicPort);
		intakeArm = new VictorSP(intakeArmPort);
	}
	
	public void intake(boolean stop) {
		intakeRoller.set(stop ? 0.0 : 0.5);
		setShooterVic(stop ? 0.0 : 0.23);
	}
	//needs to stop raising
	//not yet used anywhere
	//motor values needs to be checked
	public void setIntakeArm(boolean raise) {
		intakeArm.set(raise ? 0.5 : -0.5);
	}
	
	public void setShooterVic(double shooterVicSpeed) {
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
	
	public void shooterTic() {
		if(shooting) {
			tick++;
			if(tick == 2) setShooterVic(0.6);
			if(tick == 50) setShooterSolenoid(true);
			if(tick > 100) setShooterVic(0.5 - (tick - 100) * 0.005);
			if(tick > 200) {
				tick = 0;
				shooting = false;
				setShooterSolenoid(false);
			}
		}
	}
}
