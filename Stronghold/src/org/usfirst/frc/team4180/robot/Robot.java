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
	
	public static final Timer TIMER = new Timer();
	
	private Shooter   shooter;
	private DriveTrain      driveTrain;
	private LambdaJoystick  drivingJoystick;
	private LambdaJoystick  shooterIntakeJoystick;
	private ImageRecognizer imageRecognizer;
	private Solenoid        lift;
	
	public void robotInit() {
		TIMER.start();
		
		imageRecognizer = new ImageRecognizer();  
		shooter = new Shooter(SHOOTER_VIC_PORT , SHOOTER_VIC_PORT_2, SHOOTER_SOLENOID_PORT);
    	driveTrain = new DriveTrain(DRIVETRAIN_VIC_PORT_LEFT, DRIVETRAIN_VIC_PORT_RIGHT, GEAR_SHIFTING_PORT_1, GEAR_SHIFTING_PORT_2);
    	lift = new Solenoid(LIFTING_PORT);
    	
    	drivingJoystick = new LambdaJoystick(DRIVING_JOYSTICK_PORT, joystickInfo -> driveTrain.updateSpeed(joystickInfo));
    	drivingJoystick.addButton(2, () -> driveTrain.toggleGearShifting(), () -> {});
    	//drivingJoystick.addButton(1, () -> lift.set(!lift.get()), () -> {});
    	
    	shooterIntakeJoystick = new LambdaJoystick(SHOOTERINTAKE_JOYSTICK_PORT, (joystickInfo) -> {});
    	shooterIntakeJoystick.addButton(1, () -> shooter.shoot(), () -> {});
    	shooterIntakeJoystick.addButton(2, () -> shooter.setShooterVic(0.2), () -> shooter.setShooterVic(0));
    	shooterIntakeJoystick.addButton(3, () -> shooter.setShooterVic(-0.3), () -> shooter.setShooterVic(0));
    }
	boolean shot;
    public void autonomousInit() {
    	shooter.shooting = false;
    	shot = false;
    }
    
    public void testInit() {
    	
    }
     
    public void autonomousPeriodic() {
    	IRTick();
    }

    public void teleopPeriodic() {
    	drivingJoystick.listen();
        shooterIntakeJoystick.listen();
        shooter.shooterTic();   	
    }
    
    public void testPeriodic() {
    	
    }
    public void IRTick(){
    	double[] driveInfo = imageRecognizer.alignShooting();
    	if(Arrays.equals(new double[]{0, 0, 0}, driveInfo) && !shot) {
    		shooter.shoot();
    		shot = true;
    	}
    	if(!Arrays.equals(new double[]{4180, 4180, 4180}, driveInfo) && !shot) 
    		driveTrain.updateSpeed(driveInfo);
    	else driveTrain.updateSpeed(new double[]{0, 0, 0});
    	shooter.shooterTic();
    }
}