package org.usfirst.frc.team4180.robot;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.time.LocalDateTime;
import java.util.LinkedList;
import java.util.Queue;

public class Recording {
	private Queue<Action> joystickDataOut;
	private Queue<Action> joystickDataIn;

	private String JOYSTICK_IN_PATH = "";
	private String JOYSTICK_OUT_PATH = "";

	public Recording(String in, String out) {
		this.JOYSTICK_IN_PATH = in;
		this.JOYSTICK_OUT_PATH = out;

		joystickDataOut = new LinkedList();
		joystickDataIn  = load(JOYSTICK_IN_PATH);
	}

	public double getTime() {
		return joystickDataIn.peek().getTime();
	}

	public Action tic() {
		return joystickDataIn.remove();
	}
	
	public void addData(Action data) {
		joystickDataOut.add(data);
	}
	
	public void dumpData() {
		File dataStorage;
		FileOutputStream stream; 
        try {
            dataStorage = new File(!"".equals(JOYSTICK_OUT_PATH) ? "/tmp/" + JOYSTICK_OUT_PATH + ".robot" : ("/tmp/" + LocalDateTime.now() + "_GhostDriver.robot"));
            dataStorage.createNewFile();
            stream = new FileOutputStream(dataStorage);
        } catch(IOException e) {
            e.printStackTrace();
            return;
        }
        
        ObjectOutputStream writer;
        try {
            writer = new ObjectOutputStream(stream);
            writer.writeObject(joystickDataOut);
            writer.close();
            stream.close();
        } catch(IOException e) {
            e.printStackTrace();
        }
	}
	
	public Queue<Action> load(String fileLocation) {
		Queue<Action> toReturn;

		File dataStorage;
		FileInputStream stream;
        try {
            dataStorage = new File(fileLocation);
            stream = new FileInputStream(dataStorage);
        } catch(IOException e) {
            e.printStackTrace();
            return null;
        }
        
        ObjectInputStream reader;
        try {
            reader = new ObjectInputStream(stream);
            toReturn = (Queue<Action>)reader.readObject();
            reader.close();
            stream.close();
        } catch(IOException | ClassNotFoundException e) {
            e.printStackTrace();
            return null;
        }
		return toReturn;
	}
	
	public static class Action {
		private int pressedButton;
		private double actionTime;
		boolean buttonPressed;

		private double[] joystickInfo;

		boolean isButton;
		
		public Action(int buttonNumber, boolean pressed) {
			pressedButton = buttonNumber;
			actionTime = Robot.TIMER.get();
			buttonPressed = pressed;

			isButton = true;
		}

		public Action(double[] joystick) {
			joystickInfo = joystick;
			actionTime = Robot.TIMER.get();

			isButton = false;
		}

		public double[] getJoystickInfo() {
			return joystickInfo;
		}

		public double getTime() {
			return actionTime;
		}

		public int getButtonNumber() {
			return pressedButton;
		}
	}
}