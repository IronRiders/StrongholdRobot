package org.usfirst.frc.team4180.robot;

import java.awt.*;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.time.LocalDateTime;
import java.util.LinkedList;
import java.util.Queue;

public class Recording
{
	private Queue joystickDataOut;
	private Queue joystickDataIn;

	private String JOYSTICK_IN_PATH = "";
	private String JOYSTICK_OUT_PATH = "";

	public Recording(String in, String out) {
		this.JOYSTICK_IN_PATH = in;
		this.JOYSTICK_OUT_PATH = out;

		joystickDataOut = new LinkedList();
		joystickDataIn  = load(JOYSTICK_IN_PATH);
	}
	
	public void addData(Object data) {
		joystickDataOut.add(data);
	}
	public void addRelease() {
		((ButtonPress)joystickDataOut.peek()).addRelease();
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
            return;
        }
	}
	
	public Queue load(String fileLocation) {
		Queue toReturn;

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
            toReturn = (Queue)reader.readObject();
            reader.close();
            stream.close();
        } catch(IOException | ClassNotFoundException e) {
            e.printStackTrace();
            return null;
        }
		return toReturn;
	}
	
	public static class ButtonPress {
		private int pressedButton;
		private double timePressed;
		private double timeReleased;
		
		public ButtonPress(int buttonNumber) {
			pressedButton = buttonNumber;
			timePressed = Robot.TIMER.get();
		}
		
		public void addRelease() {
			timeReleased = Robot.TIMER.get();
		}
		
		public int getButton() {
			return pressedButton;
		}
		
		public double getTimePressed() {
			return timePressed;
		}
		
		public double getTimeReleased() {
			return timeReleased;
		}
	}
}