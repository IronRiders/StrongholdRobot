package org.usfirst.frc.team4180.robot;

import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.VictorSP;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class Shooter {
	private final VictorSP vic1;
	private final VictorSP vic2;
	public  final Solenoid elevationSolenoid;
	
	public boolean shooting = false;
	private int tick = 0;

	private static final double SHOOTING_SPEED = 0.1;

	public Shooter(int vicPort1, int vicPort2, int solenoidPort) {
		vic = new VictorSP(vicPort1);
		vic2 = new VictorSP(vicPort2);
		elevationSolenoid = new Solenoid(solenoidPort);
	}
	
	public void stopShooting() {
		shooting = false;
		setShooterVic(0);
	}

	public void setShooterVic(double speed) {
		vic1.set(speed);
		vic2.set(speed);
	}

	//starts the shooting sequence (will only work if shooterTick is called periodically)
	public void shoot() {
		shooting = true;
		tick = 1;
	}
	
	public void shooterTick() {
		if(!shooting) return;
		tick++;
		if(tick == 2) setShooterVic(0.75);
		if(tick == 50) elevationSolenoid.set(true);
		if(tick > 100) setShooterVic(0.5 - (tick - 100) * 0.005);
		if(tick > 200) {
			tick = 0;
			shooting = false;
			elevationSolenoid.set(false);
		}
	}
}