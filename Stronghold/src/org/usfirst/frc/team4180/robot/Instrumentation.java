package org.usfirst.frc.team4180.robot;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.time.LocalDateTime;
import java.util.LinkedList;
import java.util.Queue;

public class Instrumentation {
	private Queue joystickData;
	
	public Instrumentation() {
		joystickData = new LinkedList();
	}
	
	public void addData(Object data) {
		joystickData.add(data);
	}
	
	public void dumpData() {
		File dataStorage;
		FileOutputStream stream; 
        try {
            dataStorage = new File("..\\" +  LocalDateTime.now() + "_GhostDriver.robot");
            dataStorage.createNewFile();
            stream = new FileOutputStream(dataStorage);
        } catch(IOException e) {
            e.printStackTrace();
            return;
        }
        
        ObjectOutputStream writer;
        try {
            writer = new ObjectOutputStream(stream);
            writer.writeObject(joystickData);
            writer.close();
            stream.close();
        } catch(IOException e) {
            e.printStackTrace();
            return;
        }
	}
	
	static class ButtonPress {
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