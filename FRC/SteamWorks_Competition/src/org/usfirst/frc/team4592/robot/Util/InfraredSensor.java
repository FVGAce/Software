package org.usfirst.frc.team4592.robot.Util;

import edu.wpi.first.wpilibj.AnalogInput;

public class InfraredSensor{
	private AnalogInput infraredSensor;
	
	public InfraredSensor(int infraredSensor){
		this.infraredSensor = new AnalogInput(infraredSensor);
	}
	
	public double getVoltage(){
		return infraredSensor.getVoltage();
	}
	
	public boolean checkPresenceOfObject(){
		if(getVoltage() < 1.5){
			return true;
		}
		
		return false;
	}
}
