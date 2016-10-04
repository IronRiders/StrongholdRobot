package org.usfirst.frc.team4180.robot;

import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.VictorSP;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class Shooter {
	private final VictorSP shooterVic;
	private final VictorSP shooterVic2;
	public  final Solenoid elevationSolenoid;
	
	public boolean shooting = false;
	private int tick = 0;

	private static final double SHOOTING_SPEED = 0.1;
	private double shootingSpeed = 0.0;
	
	public Shooter(int vicPort, int vicPort2, int solenoidPort) {
		shooterVic = new VictorSP(vicPort);
		shooterVic2 = new VictorSP(vicPort2);
		elevationSolenoid = new Solenoid(solenoidPort);
	}
	
	public void stopShooterVic() {
		setShooterVic(0);
	}

	public void setShooterVic(double shooterVicSpeed) {
		shooterVic.set(shooterVicSpeed);
		shooterVic2.set(shooterVicSpeed);
	}

	//starts the shooting sequence (will only work if shooterTick is called periodically)
	public void shoot() {
		shooting = true;
		tick = 1;
		shootingSpeed = 0;
	}
	
	public void shooterTick() {
		if(shooting) {
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
}