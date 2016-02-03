package org.usfirst.frc.team4180.robot;

import edu.wpi.first.wpilibj.IterativeRobot;

/**
 * The VM is configured to automatically run this class, and to call the
 * functions corresponding to each mode, as described in the IterativeRobot
 * documentation. If you change the name of this class or the package after
 * creating this project, you must also update the manifest file in the resource
 * directory.
 */
public class Robot extends IterativeRobot {
    /**
     * This function is run when the robot is first started up and should be
     * used for any initialization code.
     */
    public void robotInit() {
    	Shooter shooter = new Shooter(SHOOTER_TALON_PORT, SHOOTER_SOLENOID_PORT);
    	Intake intake = new Intake(INTAKE_TALON_PORT_1, INTAKE_TALON_PORT_2);
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
    
	private static final int SHOOTER_TALON_PORT = 0; //place holder value
	private static final int SHOOTER_SOLENOID_PORT = 0;  //place holder value
	private static final int INTAKE_TALON_PORT_1 = 0; //place holder value
	private static final int INTAKE_TALON_PORT_2 = 0; //place holder value
}
