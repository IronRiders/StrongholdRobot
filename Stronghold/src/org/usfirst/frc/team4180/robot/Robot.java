package org.usfirst.frc.team4180.robot;

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

	private static final int SHOOTER_TALON_PORT = 1; //place holder value
	private static final int SHOOTER_SOLENOID_PORT = 2;  //place holder value
	private static final int INTAKE_TALON_PORT_1 = 3; //place holder value
	private static final int INTAKE_TALON_PORT_2 = 4; //place holder value
	private static final int DRIVETRAIN_VIC_PORT_LEFT = 5; 
	private static final int DRIVETRAIN_VIC_PORT_RIGHT = 6; 
	
	private Shooter shooter;
	private Intake intake;
	private DriveTrain driveTrain;
	private Joystick joystick; //placeholder for a joystick
	
    /**
     * This function is run when the robot is first started up and should be
     * used for any initialization code.
     */
	public void robotInit() {
	    //how to use the new joystick:
    	//intitalization:	
    	//j = new Joystick(0,(joystickInfo) -> driveTrain.updateSpeed(joystickInfo));
		//Adding a Button
    	//j.addButton(1, ()-> driveTrain.setJagspeed(0.1, 0.1), ()->driveTrain.setJagspeed(0, 0));
		
    	shooter = new Shooter(SHOOTER_TALON_PORT, SHOOTER_SOLENOID_PORT);
    	intake = new Intake(INTAKE_TALON_PORT_1, INTAKE_TALON_PORT_2);
    	driveTrain = new DriveTrain(DRIVETRAIN_VIC_PORT_LEFT, DRIVETRAIN_VIC_PORT_RIGHT);
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
