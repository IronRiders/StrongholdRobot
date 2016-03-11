package org.usfirst.frc.team4180.robot;

import java.util.Arrays;
import java.util.concurrent.Callable;

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
	
	private Shooter         shooter;
	private DriveTrain      driveTrain;
	private LambdaJoystick  drivingJoystick;
	private LambdaJoystick  shooterIntakeJoystick;
	private ImageRecognizer imageRecognizer;
	private Solenoid        lift;
	private boolean			autoShooting;
	
	private Command autonomousCommand;
	private SendableChooser chooser;
	
	public void robotInit() {
		imageRecognizer = new ImageRecognizer();  
		shooter = new Shooter(SHOOTER_VIC_PORT , SHOOTER_VIC_PORT_2, SHOOTER_SOLENOID_PORT);
    	driveTrain = new DriveTrain(DRIVETRAIN_VIC_PORT_LEFT, DRIVETRAIN_VIC_PORT_RIGHT, GEAR_SHIFTING_PORT_1, GEAR_SHIFTING_PORT_2);
    	lift = new Solenoid(LIFTING_PORT);
    	
    	drivingJoystick = new LambdaJoystick(DRIVING_JOYSTICK_PORT, joystickInfo -> driveTrain.updateSpeed(joystickInfo));
    	drivingJoystick.addButton(2, () -> driveTrain.toggleGearShifting(), () -> {});
    	drivingJoystick.addButton(1, () -> lift.set(false), () -> lift.set(true));
    	
    	shooterIntakeJoystick = new LambdaJoystick(SHOOTERINTAKE_JOYSTICK_PORT, (joystickInfo) -> {});
    	shooterIntakeJoystick.addButton(1, () -> autoShooting = true, () -> autoShooting = false);
    	shooterIntakeJoystick.addButton(2, () -> shooter.setShooterVic(0.2), () -> shooter.setShooterVic(0));
    	shooterIntakeJoystick.addButton(3, () -> shooter.setShooterVic(-0.3), () -> shooter.setShooterVic(0));
    	
    	Runnable test = ()->{};
    	
    	chooser = new SendableChooser();
    	chooser.addDefault("Default", test);
    	chooser.addObject("Option 1", test);
    	SmartDashboard.putData("Name", chooser);
    }

    public void autonomousInit() {
    	shooter.isShooting = false;
    	autoShooting = true;
    	tick =0;
    }
    
    public void testInit() {
    	
    }
    
    public void teleopInit() {
    	autoShooting = false;
    }
    
    int cas = 0;
    int tick = 0;
    public void autonomousPeriodic() {
    	tick++;
    if(cas == 0 && tick < 100){
    	driveTrain.updateSpeed(new double[]{0,-0.75,0});
    	return;
    }
    if(cas == 0 && tick > 100 && tick < 120){
    	driveTrain.updateSpeed(new double[]{0.75,-0.2,0});
    	return;
    }
    	if(autoShooting) {
    		IRTick();
    	}
    	shooter.shooterTick();
    	if(!autoShooting&&!shooter.isShooting&&tick>1){
    		tick=1;
    		tick++;
    		cas = 150;
    	}
   // 	if(tick<50&&cas==150){
  //  		driveTrain.updateSpeed(new double[]{0.75,0,0});
 //       	return;
//    	}
    }

    public void teleopPeriodic() {
    
        shooterIntakeJoystick.listen();   	
        if(autoShooting) {
        	IRTick();
        }
        else{
        	drivingJoystick.listen();
        }
        shooter.shooterTick();
    }
    
    public void testPeriodic() {
    	
    }
    
    //Uses images recognition to align to target and shoot
    public void IRTick(){
    	double[] driveInfo = imageRecognizer.alignShooting();
    	if(Arrays.equals(new double[]{0, 0, 0}, driveInfo)) {
    		shooter.shoot();
    		autoShooting = false;
    		
    	}
    	if(driveInfo != null) {
    		driveTrain.updateSpeed(driveInfo);
    	}
    	else {
    		driveTrain.updateSpeed(new double[]{0, 0, 0});
    	}
    }
}