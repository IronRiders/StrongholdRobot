package org.usfirst.frc.team4180.robot;

import java.math.BigDecimal;

import edu.wpi.first.wpilibj.AnalogInput;

public class UltrasonicRangeSensor {
	private AnalogInput analogInput;
	public static final Double VOLTAGE_PER_INCH = 0.0095;

	public UltrasonicRangeSensor(int port) {
		analogInput = new AnalogInput(port);
	}

	public double getRangeInches() {		
		return analogInput.getAverageVoltage() / VOLTAGE_PER_INCH;		 
	}
}