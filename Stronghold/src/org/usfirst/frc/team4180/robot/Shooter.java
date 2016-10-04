package org.usfirst.frc.team4180.robot;

import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.VictorSP;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class Shooter {
	private VictorSP shooterVic;
	private VictorSP shooterVic2;
	public Solenoid elevationSolenoid;
	
	//Grayhill 1520 D-1
//	private Encoder enc;
	
	public boolean isShooting = false;
	private int tick = 0;
	
	public Shooter(int VicPort, int VicPort2, int solenoidPort, int encoderPort1, int encoderPort2) {
		shooterVic = new VictorSP(VicPort);
		shooterVic2 = new VictorSP(VicPort2);
		elevationSolenoid = new Solenoid(solenoidPort);
	//	enc = new Encoder(encoderPort1, encoderPort2, false, Encoder.EncodingType.k4X);
	//	enc.setMaxPeriod(0.1);
	//	enc.setMinRate(10);
	//	enc.setDistancePerPulse(5);
	//	enc.setReverseDirection(true);
	//	enc.setSamplesToAverage(7);
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
		isShooting = true;
		tick = 1;
		shootingSpeed = 0;
	}
	
	private static final double SHOOTING_SPEED = 0.1/*find good number for this*/;
	private double shootingSpeed = 0.0;
	
	public void shooterTick() { //find good SHOOTER_SPEED with this method then re-implement shooterTick2 as shooterTick and comment this out
	//	double rate = enc.getRate();
//		SmartDashboard.putString("DB/String 3", rate + "");
		if(isShooting) {
			tick++;
			if(tick == 2) setShooterVic(0.75);
			if(tick == 50) elevationSolenoid.set(true);
			if(tick > 100) setShooterVic(0.5 - (tick - 100) * 0.005);
			if(tick > 200) {
				tick = 0;
				isShooting = false;
				elevationSolenoid.set(false);
			}
		}
	}
	
//	public void shooterTick2() {
//		if (isShooting) {
//	//		double rate = enc.getRate();
//		double rate =0;	
//			if(Double.isNaN(rate)) {
//				return;
//			}
//			
//			if (rate < SHOOTING_SPEED) {
//				setShooterVic(shootingSpeed += 0.01);
//			}
//			else {
//				elevationSolenoid.set(true);
//				isShooting = false;
//			}
//		}
//		else if(tick > 0){
//			++tick;
//			
//			if (tick < 150 && tick > 50) {
//				setShooterVic(shootingSpeed - (tick - 50) * (shootingSpeed/100));
//			}
//			else if (tick > 150) {
//				setShooterVic(0);
//				elevationSolenoid.set(false);
//				tick = 0;
//				shootingSpeed = 0;
//			}
//		}
//	}
	
	//Is called periodically and shoots if tick >= 200 and shooting is true
	/*public void shooterTick() {
		if(isShooting) {
			tick++;
			if(tick == 2) setShooterVic(0.6);
			if(tick == 50) elevationSolenoid.set(true);
			if(tick > 100) setShooterVic(0.5 - (tick - 100) * 0.005);
			if(tick > 200) {
				tick = 0;
				isShooting = false;
				elevationSolenoid.set(false);
			}
		}
	}*/
}