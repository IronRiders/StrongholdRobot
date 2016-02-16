package org.usfirst.frc.team4180.robot;

import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.Timer;

/**
 * The VM is configured to automatically run this class, and to call the
 * functions corresponding to each mode, as described in the IterativeRobot
 * documentation. If you change the name of this class or the package after
 * creating this project, you must also update the manifest file in the resource
 * directory.
 */
public class Robot extends IterativeRobot {

	//place holder values
	private static final int SHOOTER_VIC_PORT = 6;
	private static final int SHOOTER_VIC_PORT_2 = 7; 
	private static final int INTAKE_VIC_PORT_1 = 8; 
	private static final int INTAKE_VIC_PORT_2 = 9; 
	private static final int DRIVETRAIN_VIC_PORT_LEFT = 0; 
	private static final int DRIVETRAIN_VIC_PORT_RIGHT = 1; 
	
	private static final int SHOOTER_SOLENOID_PORT = 7;
	private static final int GEAR_SHIFTING_PORT_1 = 0;
	private static final int GEAR_SHIFTING_PORT_2 = 1;
	
	private static final int USRS_PORT = 0;

	private static final int DRIVING_JOYSTICK_PORT = 1;
	private static final int SHOOTERINTAKE_JOYSTICK_PORT = 0; 
	
	public static final Timer TIMER = new Timer();
	
	private ShooterIntake shooterIntake;
	private DriveTrain driveTrain;
	private LambdaJoystick drivingJoystick;
	private LambdaJoystick shooterIntakeJoystick;
	private ImageRecognizer IR;
	
    /**
     * This function is run when the robot is first started up and should be
     * used for any initialization code.
     */
	public void robotInit() {
		TIMER.start();
		IR = new ImageRecognizer();
	    //how to use the new joystick:
    	//intitalization:	
    	//j = new Joystick(PORT, (joystickInfo) -> driveTrain.updateSpeed(joystickInfo));
		//Adding a Button
    	//j.addButton(BUTTON_NUMBER, ()-> driveTrain.setJagspeed(0.1, 0.1), ()->driveTrain.setJagspeed(0, 0)); 
		
		shooterIntake = new ShooterIntake(SHOOTER_VIC_PORT , SHOOTER_VIC_PORT_2, SHOOTER_SOLENOID_PORT,INTAKE_VIC_PORT_1, INTAKE_VIC_PORT_2);
    	driveTrain = new DriveTrain(DRIVETRAIN_VIC_PORT_LEFT, DRIVETRAIN_VIC_PORT_RIGHT, GEAR_SHIFTING_PORT_1, GEAR_SHIFTING_PORT_2);
    	 	
    	drivingJoystick = new LambdaJoystick(DRIVING_JOYSTICK_PORT, (joystickInfo) -> driveTrain.updateSpeed(joystickInfo));
    	shooterIntakeJoystick = new LambdaJoystick(SHOOTERINTAKE_JOYSTICK_PORT, (joystickInfo) -> {
    		shooterIntake.setIntakeArmSpeed(joystickInfo[1]);
    		shooterIntake.shooterSpeed = joystickInfo[2];
    	});
    	
    	shooterIntakeJoystick.addButton(1, () -> shooterIntake.setShooterVic(1), () -> {});
    	shooterIntakeJoystick.addButton(2, () -> shooterIntake.setShooterVic(0.75), () -> {});
    	shooterIntakeJoystick.addButton(3, () -> shooterIntake.setShooterVic(0.5), () -> {});
    	shooterIntakeJoystick.addButton(4, () -> shooterIntake.setShooterVic(0.25), () -> {});
    	shooterIntakeJoystick.addButton(5, () -> shooterIntake.setShooterVic(0), () -> {});
    //	shooterIntakeJoystick.addButton(2, () -> shooterIntake.intakeOn(), () -> shooterIntake.stopIntake());
    //	shooterIntakeJoystick.addButton(3, () -> shooterIntake.reverseIntake(), () -> shooterIntake.stopIntake());
   // 	shooterIntakeJoystick.addButton(4, () -> shooterIntake.raiseShooter(), () -> {});
    //	shooterIntakeJoystick.addButton(5, () -> shooterIntake.lowerShooter(), () -> {});
    //	drivingJoystick.addButton(1, () -> driveTrain.toggleGearShifting(), () -> {});
    }

	/**
     * This function is called initially during autonomous
     */    
    public void autonomousInit() {
    	//this auto code will have the robot drive beneath the low bar:
    	shooterIntake.lowerShooter();
    	driveTrain.updateSpeed(new double[] {0, 1, 0}); //set speed to 1
    	TIMER.delay(10); //number of seconds to drive forward for to be tested
    	driveTrain.updateSpeed(new double[] {0, 0, 0}); //set speed to 0
    	shooterIntake.raiseShooter();
    }
    
	/**
     * This function is called periodically during autonomous
     */
    public void autonomousPeriodic() {
    	IR.tick();
    	double[] driveInfo = IR.alignShooting();
    	if(driveInfo.equals(new double[]{0, 0, 0})) {
    		shooterIntake.shoot();
    	}
    	driveTrain.updateSpeed(driveInfo);
    }

    /**
     * This function is called periodically during operator control
     */
    public void teleopPeriodic() {
    	drivingJoystick.listen();
    	shooterIntakeJoystick.listen();
    	if(shooterIntake.shooting) shooterIntake.shooterTic();
    }
    
    /**
     * This function is called periodically during test mode
     */
    public void testPeriodic() {
    	
    }
}

