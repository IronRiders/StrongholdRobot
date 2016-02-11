package org.usfirst.frc.team4180.robot;

import java.util.function.Consumer;

import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.Joystick;

/**
 * The VM is configured to automatically run this class, and to call the
 * functions corresponding to each mode, as described in the IterativeRobot
 * documentation. If you change the name of this class or the package after
 * creating this project, you must also update the manifest file in the resource
 * directory.
 */
public class Robot extends IterativeRobot {

	//place holder values
	private static final int SHOOTER_TALON_PORT = -1;
	private static final int SHOOTER_SOLENOID_PORT = -1;
	private static final int INTAKE_TALON_PORT_1 = -1;
	private static final int INTAKE_TALON_PORT_2 = -1;
	private static final int DRIVETRAIN_VIC_PORT_LEFT = -1; 
	private static final int DRIVETRAIN_VIC_PORT_RIGHT = -1; 
	private static final int DRIVING_JOYSTICK_PORT = -1; 
	private static final int SHOOTERINTAKE_JOYSTICK_PORT = -1; 
	
	private ShooterIntake shooterIntake;
	private DriveTrain driveTrain;
	private Joystick drivingJoystick;
	private Joystick shooterIntakeJoystick;
	
    /**
     * This function is run when the robot is first started up and should be
     * used for any initialization code.
     */
	public void robotInit() {
	    //how to use the new joystick:
    	//intitalization:	
    	//j = new Joystick(PORT, (joystickInfo) -> driveTrain.updateSpeed(joystickInfo));
		//Adding a Button
    	//j.addButton(BUTTON_NUMBER, ()-> driveTrain.setJagspeed(0.1, 0.1), ()->driveTrain.setJagspeed(0, 0)); 
		
		shooterIntake = new ShooterIntake(SHOOTER_TALON_PORT, SHOOTER_SOLENOID_PORT,
										   INTAKE_TALON_PORT_1, INTAKE_TALON_PORT_2);
    	driveTrain = new DriveTrain(DRIVETRAIN_VIC_PORT_LEFT, DRIVETRAIN_VIC_PORT_RIGHT);
    	
    	drivingJoystick = new Joystick(DRIVING_JOYSTICK_PORT, (joystickInfo) -> driveTrain.updateSpeed(joystickInfo));
    	shooterIntakeJoystick = new Joystick(SHOOTERINTAKE_JOYSTICK_PORT, (joystickInfo) -> {
    		shooterIntake.setIntakeArmSpeed(joystickInfo[1]);
    		shooterIntake.shoot(joystickInfo[2]);
    	});
    	
    	shooterIntakeJoystick.addButton(1, () -> shooterIntake.shoot(), () -> shooterIntake.stopShoot());
    	shooterIntakeJoystick.addButton(2, () -> shooterIntake.intakeOn(), () -> shooterIntake.stopIntake());
    	shooterIntakeJoystick.addButton(3, () -> shooterIntake.reverseIntake(), () -> shooterIntake.stopIntake());
    	shooterIntakeJoystick.addButton(4, () -> shooterIntake.raiseShooter(), () -> {});
    	shooterIntakeJoystick.addButton(5, () -> shooterIntake.lowerShooter(), () -> {});
    }

    /**
     * This function is called periodically during autonomous
     */
    public void autonomousPeriodic() {

    }

    /**
     * This function is called periodically during operator control
     */
    public void teleopPeriodic() {
        
    }
    
    /**
     * This function is called periodically during test mode
     */
    public void testPeriodic() {
    
    }
}
