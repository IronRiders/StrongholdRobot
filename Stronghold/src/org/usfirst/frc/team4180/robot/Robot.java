package org.usfirst.frc.team4180.robot;

import java.util.Arrays;

import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class Robot extends IterativeRobot {

	//Constants
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

	private static final String LOW_BAR_STRING = "low";
	private static final String SPY_ZONE_STRING = "spy";
	
	//Class-level controls
	private final Shooter 			    shooter;
	private final DriveTrain 			driveTrain;
	private final LambdaJoystick  	    drivingJoystick;
	private final LambdaJoystick  	    shooterIntakeJoystick;
	private final ImageRecognizer 	    imageRecognizer;
	private final Solenoid 			    lift;

	public void robotInit() {
		imageRecognizer = new ImageRecognizer();
		shooter = new Shooter(SHOOTER_VIC_PORT, SHOOTER_VIC_PORT_2, SHOOTER_SOLENOID_PORT);
		driveTrain = new DriveTrain(DRIVETRAIN_VIC_PORT_LEFT,
				DRIVETRAIN_VIC_PORT_RIGHT, GEAR_SHIFTING_PORT_1,
				GEAR_SHIFTING_PORT_2);
		lift = new Solenoid(LIFTING_PORT);

		//Joystick Logic
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

	//Autoshooting state
	private int cas = 0;
	private int tick = 0;
	private boolean autoShooting = false;

	public void autonomousInit() {
		shooter.shooting = false;
		autoShooting = true;
		tick = 0;

		SmartDashboard.putString("DB/String 4", "Enter \"spy\" or \"low\"");
		final String autoMode = SmartDashboard.getString("DB/String 0", LOW_BAR_STRING);
		switch (autoMode) {
			case LOW_BAR_STRING:
				SmartDashboard.putString("DB/String 5", "Low Bar Mode");
			break;

			case SPY_ZONE_STRING:
				SmartDashboard.putString("DB/String 5", "Spy Zone Mode");
			break;
		}
	}

	public void teleopInit() {
		shooter.shooting = false;
		autoShooting = false;
		driveTrain.stop();
	}

	public void autonomousPeriodic() {
		++tick;
		if (cas == 0 && tick < 130) {
			driveTrain.updateSpeed(0, -0.75, 0);
		} else if (cas == 0 && tick > 130 && tick < 150) {
			driveTrain.updateSpeed(0.75, -0.2, 0);
		} else {
			if (autoShooting) {
				IRTick();
			}
			shooter.shooterTick();
		}
	}

	public void teleopPeriodic() {
		shooterIntakeJoystick.listen();
		if (autoShooting) {
			IRTick();
		} else {
			drivingJoystick.listen();
		}
		shooter.shooterTick();
	}

	//Uses images recognition to align to target and shoot
	public void IRTick() {
		final double[] driveInfo = imageRecognizer.alignShooting();
		final double minSpeed = 0.05;
		if (driveInfo == null) {
			driveTrain.stop();
		} else {
			boolean inShootingRange = tolerate(driveInfo[0], minSpeed) == 0
			    				   && tolerate(driveInfo[1], minSpeed) == 0
			                       && tolerate(driveInfo[2], minSpeed) == 0;
			if (inShootingRange) {
				shooter.shoot();
				autoShooting = false;
			}
			driveTrain.updateSpeed(driveInfo);
		}
	}

	private static double tolerate(double x, double tolerance) {
		return Math.abs(x) > tolerance ? x : 0;
	}
}