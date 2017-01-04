package org.usfirst.frc.team4180.robot;

import java.util.function.Consumer;
import edu.wpi.first.wpilibj.Joystick;

public class LambdaJoystick extends Joystick {
	public Button[] buttons = new Button[11];
	private Consumer<double[]> joystickListener;
	
	public LambdaJoystick(int port, Consumer<double[]> joystickListener) {
		super(port);
		this.joystickListener = joystickListener;
	}
	
	public void addButton(int buttonNum, Consumer<Boolean> toggler) {
		addButton(buttonNum, () -> toggler.accept(true), () -> toggler.accept(false));
	}

	public void addButton(int buttonNum, Runnable onKeyDown, Runnable onKeyUp) {
		buttons[buttonNum - 1] = new Button(onKeyDown, onKeyUp);
	}
	
	public void listen() {
		//Iterate through the array of buttons and do whatever they're asking
		for (int i = 0; i < buttons.length; i++) {
			if (buttons[i] != null) {
				buttons[i].listen(this.getRawButton(i + 1));
			}
		}

		final int x = this.getX();
		final int y = this.getY();
		final int z = this.getZ();

		joystickListener.accept(new double[] {x, y, z});
	}
	
	private static class Button {
		private boolean currentState = false;
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
