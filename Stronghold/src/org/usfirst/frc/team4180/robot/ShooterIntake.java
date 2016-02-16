package org.usfirst.frc.team4180.robot;

import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.VictorSP;
import edu.wpi.first.wpilibj.TalonSRX;
import edu.wpi.first.wpilibj.Timer;


public class ShooterIntake {

	private VictorSP shooterVic;
	private VictorSP shooterVic2;
	private Solenoid elevationSolenoid;
	private VictorSP intakeAngle;
	private VictorSP intakeRoller;
	
	public boolean shooting = false;
	private Timer  shootTimer = new Timer();
	
	private static final double wait1 = 0, wait2 = 0; 
	
	public ShooterIntake(int VicPort,int VicPort2, int solenoidPort, int rollerVicPort, int angleVicPort) {
		shooterVic = new VictorSP(VicPort);
		shooterVic2 = new VictorSP(VicPort2);
		elevationSolenoid = new Solenoid(solenoidPort);
		intakeAngle = new VictorSP(angleVicPort);
		intakeRoller = new VictorSP(rollerVicPort);

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
	
	public void shoot() {
		shooting = true;
		tick=0;
	}
	int tick =0;
	public void shooterTic() {
		if(shooting){
		tick++;
		if(tick==2)
			setShooterVic(0.5);
		
		if(tick == 100)
		{		
			setShooterSolenoid(true);
		}
		if(tick>200){
			setShooterVic(0);	
			setShooterSolenoid(false);
			tick=0;
			shooting = false;
		}
		}
	}
}