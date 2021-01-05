package org.usfirst.frc.team4592.robot.Util;

import org.usfirst.frc.team4592.robot.Util.Pixy.Pixy;
import org.usfirst.frc.team4592.robot.Util.Pixy.PixyException;
import org.usfirst.frc.team4592.robot.Util.Pixy.PixyPacket;

import edu.wpi.first.wpilibj.AnalogInput;
import edu.wpi.first.wpilibj.Solenoid;

@SuppressWarnings("unused")
public class PixyCam{
	private static final double CAMERA_FOCAL = 416.938111;
	private static final double CAMERA_SCALAR = 640/3.3;
	private AnalogInput analogPixy;
	private Pixy SPIPixy;
	private Solenoid LedRing1, LedRing2, LedRing3;
	private PID initial_PI;
	private PID greater_PI;
	private PID greatest_P;
	private double camera_goal;
	private double camera_signal;
	private double camera_pixel;
	private double angle_radians;
	private double camera_error;
	private double angle_goal;
	private double error;
	private double errorSum;
	private double output = 0;
	private PixyPacket newestPacket;
	
	//Analog Pixy
	public PixyCam(int port, double camera_goal,
			Solenoid LedRing1, Solenoid LedRing2, Solenoid LedRing3){
		this.analogPixy = new AnalogInput(port);
		this.camera_goal = camera_goal;
		this.LedRing1 = LedRing1;
		this.LedRing2 = LedRing2;
		this.LedRing3 = LedRing3;
	}
	
	public PixyCam(int port, double camera_goal, int LedRing1){
		this.analogPixy = new AnalogInput(port);
		this.camera_goal = camera_goal;
		this.LedRing1 = new Solenoid(LedRing1);
	}
	
	//SPI Pixy
	public PixyCam(Pixy SPIPixy, double camera_goal,
			Solenoid LedRing1, Solenoid LedRing2, Solenoid LedRing3){
		this.SPIPixy = SPIPixy;
		this.camera_goal = camera_goal;
		this.LedRing1 = LedRing1;
		this.LedRing2 = LedRing2;
		this.LedRing3 = LedRing3;
	}
	
	public PixyCam(Pixy SPIPixy, double camera_goal, Solenoid LedRing1){
		this.SPIPixy = SPIPixy;
		this.camera_goal = camera_goal;
		this.LedRing1 = LedRing1;
	}
	
	//Only call when Pixy is using analog system
		//get output method
		public double getAnalogOutput(double currentAngle){
			camera_signal = analogPixy.getVoltage();
			camera_pixel = camera_signal * CAMERA_SCALAR;
			angle_radians = Math.atan((camera_pixel - camera_goal) / CAMERA_FOCAL);
			camera_error = Math.toDegrees(angle_radians);
			angle_goal = currentAngle + camera_error;
			error = currentAngle - angle_goal;
			//errorSum += error;
			
			if(Math.abs(error) < 2){
				output = initial_PI.getOutputPI(error);
			}else if(Math.abs(error) >= 2 && Math.abs(error) < 10){
				output = greater_PI.getOutputPI(error);
			}else if(Math.abs(error) >= 10){
				output = greatest_P.getOutputP(error);
			}
			
			return output;
		}
		
		//Analog Testing
		public double getVoltage(){
			return analogPixy.getVoltage();
		}
		
	//Only call when Pixy is using SPI system
		//SPI testing methods
		public void reset(){
			SPIPixy.pixyReset();
		}
		
		public int getX(int signature){
			try{
				newestPacket = SPIPixy.readPacket(signature);
			}catch(PixyException e){
				e.printStackTrace();
				return -1;
			}
			
			if(newestPacket == null){
				return -1;
			}
			
			return newestPacket.X;
		}
		
		public int getY(int signature){
			try{
				newestPacket = SPIPixy.readPacket(signature);
			}catch(PixyException e){
				e.printStackTrace();
				return -1;
			}
			
			if(newestPacket == null){
				return -1;
			}
			
			return newestPacket.Y;
		}
		
		public int getWidth(int signature){
			try{
				newestPacket = SPIPixy.readPacket(signature);
			}catch(PixyException e){
				e.printStackTrace();
				return -1;
			}
			
			if(newestPacket == null){
				return -1;
			}
			
			return newestPacket.Width;
		}
		
		public int getHeight(int signature){		
			try{
				newestPacket = SPIPixy.readPacket(signature);
			}catch(PixyException e){
				e.printStackTrace();
				return -1;
			}
			
			if(newestPacket == null){
				return -1;
			}
			
			return newestPacket.Height;
		}
}
