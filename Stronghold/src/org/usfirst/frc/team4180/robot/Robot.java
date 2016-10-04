package org.usfirst.frc.team4180.robot;

import java.util.Arrays;

import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class Robot extends IterativeRobot {

	private static final int SHOOTER_VIC_PORT = 6;
	private static final int SHOOTER_VIC_PORT_2 = 7;
	private static final int DRIVETRAIN_VIC_PORT_LEFT = 0;
	private static final int DRIVETRAIN_VIC_PORT_RIGHT = 1;

	private static final int SHOOTER_SOLENOID_PORT = 7;
	private static final int GEAR_SHIFTING_PORT_1 = 0;
	private static final int GEAR_SHIFTING_PORT_2 = 1;
	private static final int LIFTING_PORT = 6;

	private static final int DRIVING_JOYSTICK_PORT = 1;
	private static final int SHOOTERINTAKE_JOYSTICK_PORT = 0;
	
	private static final int ENCODER_PORT_1 = -1; //figure out robot ports
	private static final int ENCODER_PORT_2 = -1;

	private static final String LOW_BAR_STRING = "low";
	private static final String SPY_ZONE_STRING = "spy";
	
	private Shooter 			  shooter;
	private DriveTrain 			  driveTrain;
	private LambdaJoystick  	  drivingJoystick;
	private LambdaJoystick  	  shooterIntakeJoystick;
	private ImageRecognizer 	  imageRecognizer;
	private Solenoid 			  lift;
	private boolean 			  autoShooting;
	private UltrasonicRangeSensor rangeSensor;
	

	public void robotInit() {
		rangeSensor = new UltrasonicRangeSensor(0);
		imageRecognizer = new ImageRecognizer();
		shooter = new Shooter(SHOOTER_VIC_PORT, SHOOTER_VIC_PORT_2,
				SHOOTER_SOLENOID_PORT, ENCODER_PORT_1, ENCODER_PORT_2);
		driveTrain = new DriveTrain(DRIVETRAIN_VIC_PORT_LEFT,
				DRIVETRAIN_VIC_PORT_RIGHT, GEAR_SHIFTING_PORT_1,
				GEAR_SHIFTING_PORT_2);
		lift = new Solenoid(LIFTING_PORT);

		drivingJoystick = new LambdaJoystick(DRIVING_JOYSTICK_PORT, driveTrain::updateSpeed);
		drivingJoystick.addButton(2, driveTrain::toggleGearShifting, () -> {});
		drivingJoystick.addButton(1, lift::set);

		shooterIntakeJoystick = new LambdaJoystick(SHOOTERINTAKE_JOYSTICK_PORT, joystickInfo -> {});
		shooterIntakeJoystick.addButton(1, b -> autoShooting = b);
		shooterIntakeJoystick.addButton(2, () -> shooter.setShooterVic(0.3), shooter::stopShooterVic);
		shooterIntakeJoystick.addButton(3, () -> shooter.setShooterVic(-0.4), shooter::stopShooterVic);
		shooterIntakeJoystick.addButton(4, shooter::shoot, () -> {});
		shooterIntakeJoystick.addButton(5, () -> shooter.setShooterVic(-0.9), shooter::stopShooterVic);
		shooterIntakeJoystick.addButton(7, shooter.elevationSolenoid::set;
	}

	public void autonomousInit() {
		shooter.isShooting = false;
		autoShooting = true;
		tick = 0;

		SmartDashboard.putString("DB/String 4", "Enter \"spy\" or \"low\"");
		String autoMode = SmartDashboard.getString("DB/String 0",
				LOW_BAR_STRING);
		switch (autoMode) {
		case LOW_BAR_STRING:
			SmartDashboard.putString("DB/String 5", "Low Bar Mode");
			//cas = 0;
			break;

		case SPY_ZONE_STRING:
			SmartDashboard.putString("DB/String 5", "Spy Zone Mode");
		//	cas = 1;
			break;
		}
	}

	public void testInit() {

	}

	public void teleopInit() {
		shooter.isShooting = false;
		driveTrain.updateSpeed(new double[] { 0, 0, 0 });
		autoShooting = false;
	}

	int cas = 0;
	int tick = 0;

	public void autonomousPeriodic() {
	//	driveTrain.updateSpeed(new double[] { 0, -0.35, 0 });
		tick++;
		if (cas == 0 && tick < 130) {
			driveTrain.updateSpeed(new double[] { 0, -0.75, 0 });
			return;
		}
		if (cas == 0 && tick > 130 && tick < 150) {
			driveTrain.updateSpeed(new double[] { 0.75, -0.2, 0 });
			return;
		}
		if (autoShooting) {
			IRTick();
		}
//		shooter.shoot();
		shooter.shooterTick();
//		if (!autoShooting && !shooter.isShooting && tick > 1) {
///			tick = 1;
//			tick++;
//			cas = 150;
//		}
	//	if (tick < 50 && cas == 150) {
	//		driveTrain.updateSpeed(new double[] { 0.75, 0, 0 });
	//		return;
//		}
	///	if (tick > 50 && cas == 150) {
	//		driveTrain.updateSpeed(new double[] { 0, 0, 0 });
	//	}
	}

	public void teleopPeriodic() {
	//	SmartDashboard.putString("DB/String 6" ,""+rangeSensor.getRangeInches());
	//	SmartDashboard.putNumber("DB/Slider 0", (Math.random()*5));
	//	SmartDashboard.putNumber("DB/Slider 1", (Math.random()*5));
	//	SmartDashboard.put Number("DB/Slider 2", (Math.random()*5));
	//	SmartDashboard.putNumber("DB/Slider 3", (Math.random()*5));
		
		shooterIntakeJoystick.listen();
		if (autoShooting) {
			IRTick();
		} else {
			drivingJoystick.listen();
		}
		shooter.shooterTick();
	}

	public void testPeriodic() {

	}

	// Uses images recognition to align to target and shoot
	public void IRTick() {
		double[] driveInfo = imageRecognizer.alignShooting();
		double minSpeed = 0.05;
		if (driveInfo == null) {
			driveTrain.updateSpeed(new double[] { 0, 0, 0 });
			return;
		}
		if (Arrays.equals(new double[] { 0, 0, 0 }, new double[]{tolerate(driveInfo[0],minSpeed),tolerate(driveInfo[1],minSpeed),tolerate(driveInfo[2],minSpeed)})) {
			shooter.shoot();
			autoShooting = false;
		}
			driveTrain.updateSpeed(driveInfo);
	}
	public double tolerate(double x, double tolerance){
		return Math.abs(x)>tolerance ? x : 0;
	}
}
	