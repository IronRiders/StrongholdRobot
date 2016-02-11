package org.usfirst.frc.team4180.robot;


import java.util.function.Consumer;

public class Joystick extends edu.wpi.first.wpilibj.Joystick {

	public Button[] buttons = new Button[11];
	//This checks if the joystick is being used for movement or something else
	Consumer<double[]> joystickListener;
	
	public Joystick(int port, Consumer<double[]> joystickListener) {
		super(port); 	
		this.joystickListener = joystickListener;
	}
	
	public void addButton(int buttonNum, Runnable onKeyDown, Runnable onKeyUp){
		buttons[buttonNum-1] = new Button(onKeyDown, onKeyUp);
	}
	
	public void listen() {
		//Iterate through the array of buttons and do whatever they're asking
		for (int i = 0; i < buttons.length; i++) {
			if (buttons[i] != null) {
				buttons[i].listen(this.getRawButton(i + 1));
			}
		}
		
		//Do whatever we've told the joystick it should
		joystickListener.accept(new double[]{this.getX(), this.getY(), this.getZ()});
	}
	
	public class Button {
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
				if(newState) {
					onKeyDown.run();
				}
				else {
					onKeyUp.run();
				}
			}
		}
	}
}
