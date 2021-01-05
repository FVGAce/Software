package org.usfirst.frc.team4592.robot;

import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

import org.usfirst.frc.team4592.robot.AutonomousModes.TestAuto;
import org.usfirst.frc.team4592.robot.Lib.*;
import org.usfirst.frc.team4592.robot.Subsystems.Shooter;
import org.usfirst.frc.team4592.robot.Subsystems.Vision;


public class SmartDashBoard extends SmartDashboard implements Loopable{

	public static SendableChooser chooser = new SendableChooser();
	public static int autoSelected;
	
	public void Shooter(){
			SmartDashboard.putNumber("Rate", Hardware.driveEncoder.get());
			SmartDashboard.putNumber("LeftRate", Hardware.leftDriveEncoder.get());
			SmartDashboard.putNumber("Left RPM", Shooter.LeftRPM);
			SmartDashboard.putNumber("Right RPM", Shooter.RightRPM);
			SmartDashboard.putNumber("Left Period", Shooter.Leftperiod);
			SmartDashboard.putNumber("Right Period", Shooter.Rightperiod);
			SmartDashboard.putNumber("angle", Shooter.goal);
	}
	
	//Test PID loop from smartdashboard
	/*public void Intake(){		
		//testing PID
			SmartDashboard.putNumber("Intake_Kp", Constants.INTAKE_Kp);
			SmartDashboard.putNumber("Intake_Ki", Constants.INTAKE_Ki);
	}*/
	
	/*public void DisplayingTestValues(){
		//Intake
			SmartDashboard.putNumber("Angle", (Hardware.intakeController.getPosition()*(360/3.14)));
			//SmartDashboard.putNumber("IntakeKp", Constants.INTAKE_Kp);
			//SmartDashboard.putNumber("IntakeKi", Constants.INTAKE_Ki);			
	}*/
	
	public void Intake(){
		SmartDashboard.putNumber("Angle", (Hardware.intakeController.getPosition()*(360/3.14)));
	}
	
	public void auto(){
		chooser.addDefault("NOTHING", 1);
	    chooser.addObject("SPYBOT", 2);
	    chooser.addObject("OBSTACLE", 3);
	    chooser.addObject("LOWBAR", 4);
	    chooser.addObject("TURN", 5);
	    chooser.addObject("OBSTACLE180", 6);
	    SmartDashboard.putData("Auto choices", chooser);
	}
	
	public SmartDashBoard(){
		//auto
			auto();
			
		//Intake
			//Intake();
	}
	
	@Override
	public void update(){
		//shooter SmartDashboard
			Shooter();
			
		//Intake			
			Intake();
			//if(!(SmartDashboard.getNumber("Intake_Kp") == Constants.INTAKE_Kp)){
				//Constants.INTAKE_Kp = SmartDashboard.getNumber("Intake_Kp");
			//}
			
			//if(!(SmartDashboard.getNumber("Intake_Ki") == Constants.INTAKE_Ki)){
				//Constants.INTAKE_Ki = SmartDashboard.getNumber("Intake_Ki");
			//}
			
		//Wacthing Testing
			//DisplayingTestValues();
			
		SmartDashboard.putNumber("Gyro",  Hardware.SpartanBoard.getAngle());
		SmartDashboard.putNumber("Output", TestAuto.angleOutput);
		SmartDashboard.putNumber("ShooterPosition", Shooter.angle);
		SmartDashboard.putNumber("IntakePosition", (Hardware.intakeController.getPosition()*(360/3.14)));
		SmartDashboard.putNumber("VisionAngle", Vision.error);
		SmartDashboard.putNumber("Vision", Vision.output);
		SmartDashboard.putNumber("VisionCameraError", Vision.cameraError);
	}
}