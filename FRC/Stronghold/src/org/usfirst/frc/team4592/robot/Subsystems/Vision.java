package org.usfirst.frc.team4592.robot.Subsystems;

import edu.wpi.first.wpilibj.AnalogInput;
import edu.wpi.first.wpilibj.Solenoid;
import org.usfirst.frc.team4592.robot.*;
import org.usfirst.frc.team4592.robot.Lib.Loopable;


public class Vision implements Loopable{
	public double cameraScalar = 640/3.3;
	public int counter = 0;
	public Solenoid Led1;
	public Solenoid Led2;
	public Solenoid Led3;
	public AnalogInput PixyCam;
	public double CameraGoal;
	public static double cameraError;
	public double cameraSignal;
	public double cameraPixel;
	public double CameraFocal;
	public double angleRadians;
	public double angle;
	public static double angleGoal;
	public static double error;
	public double errorSum;
	public static double output;
	
	public Vision(Solenoid Led1, Solenoid Led2, Solenoid Led3, AnalogInput PixyCam, double CameraGoal, double CameraFocal){
		this.Led1 = Led1;
		this.Led2 = Led2;
		this.Led3 = Led3;
		this.PixyCam = PixyCam;
		this.CameraGoal = CameraGoal;
		this.CameraFocal = CameraFocal;
	}
	
	public void update(){
		if(Hardware.drivePad.getRawButton(Constants.VISION_BUTTON)){
			Led1.set(true);
			Led2.set(true);
			Led3.set(true);
			cameraSignal = PixyCam.getVoltage();
			cameraPixel = cameraSignal * cameraScalar;
			angleRadians = Math.atan((cameraPixel - CameraGoal)/CameraFocal);
			cameraError = Math.toDegrees(angleRadians);
			angle = Hardware.SpartanBoard.getAngle();
			angleGoal = angle + cameraError;
			error = angle - angleGoal;
			errorSum += error;
			
			if(cameraSignal < 0.12){
				angleGoal = angle - 10;
				error = angle - angleGoal;
				counter++;
			}else if(cameraSignal >= 0.09 || counter > 30){
				counter = 0;
			}
			
			if(Math.abs(error) < 0.5){
				errorSum = 0;
			}else if(Math.abs(error) < 2){
				output = ((Constants.VISION_INITIAL_KP * error) + (Constants.VISION_kI * errorSum));
			}else if(Math.abs(error) >= 2 && Math.abs(error) < 10){
				output = ((Constants.VISION_GREATER_KP * error) + (Constants.VISION_kI * errorSum));
			}else if(Math.abs(error) >= 10 && Math.abs(error) < 11){
				output = (Constants.VISION_GREATEST_KP * error);
			}else if (Math.abs(error)>=11){
				output = (Constants.VISION_GREATEST_KP * error) + ((Constants.VISION_kI * error)*2.5);
			}
			if(output > .9){
				output = .9;
			}else if(output < -.9){
				output = -.9;
			}
			
			Robot.myRobot.arcadeDrive(0, -output);
		}
		
		else if (!Hardware.drivePad.getRawButton(Constants.VISION_BUTTON)){	
		Led1.set(false);
		Led2.set(false);
		Led3.set(false);
		counter = 0;
		}
	}
}
