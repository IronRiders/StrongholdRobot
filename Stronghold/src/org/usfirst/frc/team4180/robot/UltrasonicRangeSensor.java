package org.usfirst.frc.team4180.robot;

import java.math.BigDecimal;

import edu.wpi.first.wpilibj.AnalogInput;

public class UltrasonicRangeSensor {
	private AnalogInput analogInput;
	public static final BigDecimal VOLTAGE_PER_INCH = new BigDecimal(5.0/512.0);

	public UltrasonicRangeSensor(int port) {
		analogInput = new AnalogInput(port);
	}

	public double getRangeInches() {
		double voltage = analogInput.getAverageVoltage();
		//variable for ease of editing
		//TODO: Remove editVoltage once the voltage is calculated correctly
		double editVoltage = voltage/VOLTAGE_PER_INCH.doubleValue();
		return editVoltage;
	}
}