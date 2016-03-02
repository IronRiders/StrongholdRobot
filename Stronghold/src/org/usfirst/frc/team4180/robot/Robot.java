package org.usfirst.frc.team4180.robot;

import java.util.Arrays;
import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.Timer;

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
	
	private Shooter   shooter;
	private DriveTrain      driveTrain;
	private LambdaJoystick  drivingJoystick;
	private LambdaJoystick  shooterIntakeJoystick;
	private ImageRecognizer imageRecognizer;
	private Solenoid        lift;
	private boolean			autoShooting;
	
	public void robotInit() {
		imageRecognizer = new ImageRecognizer();  
		shooter = new Shooter(SHOOTER_VIC_PORT , SHOOTER_VIC_PORT_2, SHOOTER_SOLENOID_PORT);
    	driveTrain = new DriveTrain(DRIVETRAIN_VIC_PORT_LEFT, DRIVETRAIN_VIC_PORT_RIGHT, GEAR_SHIFTING_PORT_1, GEAR_SHIFTING_PORT_2);
    	lift = new Solenoid(LIFTING_PORT);
    	
    	drivingJoystick = new LambdaJoystick(DRIVING_JOYSTICK_PORT, joystickInfo -> driveTrain.updateSpeed(joystickInfo));
    	drivingJoystick.addButton(2, () -> driveTrain.toggleGearShifting(), () -> {});
    	//drivingJoystick.addButton(1, () -> lift.set(!lift.get()), () -> {});
    	
    	shooterIntakeJoystick = new LambdaJoystick(SHOOTERINTAKE_JOYSTICK_PORT, (joystickInfo) -> {});
    	shooterIntakeJoystick.addButton(1, () -> autoShooting = true, () -> autoShooting = false);
    	shooterIntakeJoystick.addButton(2, () -> shooter.setShooterVic(0.2), () -> shooter.setShooterVic(0));
    	shooterIntakeJoystick.addButton(3, () -> shooter.setShooterVic(-0.3), () -> shooter.setShooterVic(0));
    }

    public void autonomousInit() {
    	shooter.shooting = false;
    	autoShooting = true;
    }
    
    public void testInit() {
    	
    }
    
    public void teleopInit() {
    	autoShooting = false;
    }
     
    public void autonomousPeriodic() {
    	if(autoShooting)
    		IRTick();
    	
    	shooter.shooterTic();
    }

    public void teleopPeriodic() {
    	drivingJoystick.listen();
        shooterIntakeJoystick.listen();   	
        if (autoShooting) {
        	IRTick();
        }
        shooter.shooterTic();
    }
    
    public void testPeriodic() {
    	
    }
    
    public void IRTick(){
    	double[] driveInfo = imageRecognizer.alignShooting();
    	if(Arrays.equals(new double[]{0, 0, 0}, driveInfo)) {
    		shooter.shoot();
    		autoShooting = false;
    	}
    	if(driveInfo != null) 
    		driveTrain.updateSpeed(driveInfo);
    	else driveTrain.updateSpeed(new double[]{0, 0, 0});
    }
}