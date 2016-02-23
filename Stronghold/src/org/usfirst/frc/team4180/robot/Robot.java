package org.usfirst.frc.team4180.robot;

import java.util.Arrays;

import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.Timer;

import java.util.Arrays;

public class Robot extends IterativeRobot {
	private static final boolean GHOST_DRIVER = false;
	
	private static final int SHOOTER_VIC_PORT = 6;
	private static final int SHOOTER_VIC_PORT_2 = 7; 
	private static final int INTAKE_VIC_PORT_1 = 8; 
	private static final int INTAKE_VIC_PORT_2 = 9; 
	private static final int DRIVETRAIN_VIC_PORT_LEFT = 0; 
	private static final int DRIVETRAIN_VIC_PORT_RIGHT = 1; 
	
	private static final int SHOOTER_SOLENOID_PORT = 7;
	private static final int GEAR_SHIFTING_PORT_1 = 0;
	private static final int GEAR_SHIFTING_PORT_2 = 1;

	private static final int DRIVING_JOYSTICK_PORT = 1;
	private static final int SHOOTERINTAKE_JOYSTICK_PORT = 0; 
	
	public static final Timer TIMER = new Timer();
	
	private ShooterIntake   shooterIntake;
	private DriveTrain      driveTrain;
	private LambdaJoystick  drivingJoystick;
	private LambdaJoystick  shooterIntakeJoystick;
	private ImageRecognizer imageRecognizer;
	
    private boolean writeInstrumentationFile = false;
    boolean shot;
    
	public void robotInit() {
		TIMER.start();
		
		imageRecognizer = new ImageRecognizer();
	 
		shooterIntake = new ShooterIntake(SHOOTER_VIC_PORT , SHOOTER_VIC_PORT_2, SHOOTER_SOLENOID_PORT,INTAKE_VIC_PORT_1, INTAKE_VIC_PORT_2);
    	driveTrain = new DriveTrain(DRIVETRAIN_VIC_PORT_LEFT, DRIVETRAIN_VIC_PORT_RIGHT, GEAR_SHIFTING_PORT_1, GEAR_SHIFTING_PORT_2);
    	 	
    	drivingJoystick = new LambdaJoystick(DRIVING_JOYSTICK_PORT, joystickInfo -> driveTrain.updateSpeed(joystickInfo), "", "driving");
    	drivingJoystick.addButton(1, () -> driveTrain.toggleGearShifting(), () -> {});
    	
    	shooterIntakeJoystick = new LambdaJoystick(SHOOTERINTAKE_JOYSTICK_PORT, (joystickInfo) -> shooterIntake.moveArm(joystickInfo[1]), "", "shooting");
    	shooterIntakeJoystick.addButton(1, () -> shooterIntake.shoot(), () -> {});
    	shooterIntakeJoystick.addButton(2, () -> shooterIntake.intake(false), () -> shooterIntake.intake(true));
    	//shooterIntakeJoystick.addButton(3, () -> shooterIntake.setRollerVic(-1), () -> shooterIntake.setRollerVic(0));
    }
	
    public void autonomousInit() {
    	shooterIntake.shooting = false;
    	shot = false;
    }
    
    public void disabledInit() {
    	writeInstrumentationFile = true;
    }
    
    public void autonomousPeriodic() {
		if(!GHOST_DRIVER) {
			double[] driveInfo = imageRecognizer.alignShooting();
	    	if(Arrays.equals(new double[]{0, 0, 0}, driveInfo)&&!shot) {
	    		shooterIntake.shoot();
	    		shot = true;
	    	}
	    	if(!Arrays.equals(new double[]{4180, 4180, 4180}, driveInfo)&&!shot) {
	    		driveTrain.updateSpeed(driveInfo);
	    	}
	    	else driveTrain.updateSpeed(new double[]{0, 0, 0});
	    	shooterIntake.shooterTic();
		}
		else {
			LambdaJoystick joystick = drivingJoystick.tracking.getTime() >  shooterIntakeJoystick.tracking.getTime() ? 
					drivingJoystick : shooterIntakeJoystick;
			Recording.Action action = joystick.tracking.tic();
			if(action.isButton) {
				if(action.buttonPressed) joystick.buttons[action.getButtonNumber()].onKeyDown.run();
				else joystick.buttons[action.getButtonNumber()].onKeyUp.run();
			}
			else {
				joystick.joystickListener.accept(action.getJoystickInfo());
			}
		}
	}

    public void teleopPeriodic() {
    	drivingJoystick.listen();
    	shooterIntakeJoystick.listen();
    	shooterIntake.shooterTic();
    	writeInstrumentationFile = true;
    }

    public void testPeriodic() {

    }

   public void disabledPeriodic() {
    	if (writeInstrumentationFile) {
    		drivingJoystick.tracking.dumpData();
    		shooterIntakeJoystick.tracking.dumpData();
    		writeInstrumentationFile = false;
    	}
    }
}