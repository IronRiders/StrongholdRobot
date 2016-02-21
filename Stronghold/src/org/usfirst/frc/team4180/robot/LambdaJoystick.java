package org.usfirst.frc.team4180.robot;

import java.util.function.Consumer;

public class LambdaJoystick extends edu.wpi.first.wpilibj.Joystick {
	public Button[] buttons = new Button[11];
	private Consumer<double[]> joystickListener;
	
	public Recording tracking;
	
	public LambdaJoystick(int port, Consumer<double[]> joystickListener, String fileIn, String fileOut) {
		super(port);
		this.joystickListener = joystickListener;
		tracking = new Recording(fileIn, fileOut);
	}
	
	public static double buffer(double d) {
		return (d > 0.05 || d < -0.05) ? d : 0;
	}
	
	public void addButton(int buttonNum, Runnable onKeyDown, Runnable onKeyUp) {
		buttons[buttonNum-1] = new Button(onKeyDown, onKeyUp);
	}
	
	public void listen() {
		//Iterate through the array of buttons and do whatever they're asking
		for (int i = 0; i < buttons.length; i++) {
			if (buttons[i] != null) {
				buttons[i].listen(this.getRawButton(i + 1));
				if(!buttons[i].currentState) tracking.addData(new Recording.ButtonPress(i));
				else tracking.addRelease();
			}
		}
		
		double[] joystickData = {buffer(this.getX()), buffer(this.getY()), buffer(this.getZ())};
		//Do whatever we've told the joystick it should
		joystickListener.accept(joystickData);
		tracking.addData(joystickData);
	}
	
	private class Button {
		public boolean currentState = false;
		public Runnable onKeyDown;
		public Runnable onKeyUp;
		
		public Button(Runnable onKeyDown, Runnable onKeyUp) {
			this.onKeyDown = onKeyDown;
			this.onKeyUp = onKeyUp;
		}

		public void listen(boolean newState) {
			//If the button has been toggled and it's down, run onKeyDown
			//If it's now up, run onKeyUp
			if(currentState != newState) {
				currentState = newState;
				if(newState) onKeyDown.run();
				else onKeyUp.run();
			}
		}
	}
}
