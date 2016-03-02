package org.usfirst.frc.team4180.robot;

import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.VictorSP;
import edu.wpi.first.wpilibj.DigitalInput;

public class Shooter {
	private VictorSP shooterVic;
	private VictorSP shooterVic2;
	private Solenoid elevationSolenoid;
		
	public boolean shooting = false;
	private int tick = 0;
	
	public Shooter(int VicPort, int VicPort2, int solenoidPort) {
		shooterVic = new VictorSP(VicPort);
		shooterVic2 = new VictorSP(VicPort2);
		elevationSolenoid = new Solenoid(solenoidPort);
	}
	
	public void setShooterVic(double shooterVicSpeed) {
		shooterVic.set(shooterVicSpeed);
		shooterVic2.set(shooterVicSpeed);
	}

	//starts the shooting sequence (will only work if shooterTick is called periodically)
	public void shoot() {
		shooting = true;
		tick = 0;
	}
	
	//Is called periodically and shoots if tick >= 200 and shooting is true
	public void shooterTick() {
		if(shooting) {
			tick++;
			if(tick == 2) setShooterVic(0.6);
			if(tick == 50) elevationSolenoid.set(true);
			if(tick > 100) setShooterVic(0.5 - (tick - 100) * 0.005);
			if(tick > 200) {
				tick = 0;
				shooting = false;
				elevationSolenoid.set(false);
			}
		}
	}
}