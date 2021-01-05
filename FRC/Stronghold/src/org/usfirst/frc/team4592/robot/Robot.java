package org.usfirst.frc.team4592.robot;

import edu.wpi.first.wpilibj.CANTalon.FeedbackDevice;
import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.RobotDrive;
import org.usfirst.frc.team4592.robot.AutonomousModes.TestAuto;
import org.usfirst.frc.team4592.robot.Lib.*;
import org.usfirst.frc.team4592.robot.Subsystems.*;

//@SuppressWarnings("unused")
public class Robot extends IterativeRobot{
    //Control Loops
		MultiLooper SSLooper = new MultiLooper("SSLooper", 1/200.0, false); //Looper for everything but the Drive
		MultiLooper AutoLooper = new MultiLooper("AutoLooper", 1/200.0, false); // Autonomous Loop
	
	//Subsystems
		public static Intake Accumulator = new Intake(Hardware.intakeController, Hardware.intakePosition, Hardware.intakeMotor, Hardware.intakeMotor2, Hardware.intakeSwitch);//Declaring the Intake
		public static Shooter Unnamed = new Shooter(Hardware.LeftShooterWheels, Hardware.RightSho	
													Hardware.shooterPosition,  
													Hardware.shooterShoot2, Hardware.shooterShoot, Hardware.Shooter_zero,
													Hardware.shooterSwitch, Hardware.hallEffectShooterLeft, Hardware.hallEffectShooterRight);
		public static Vision PixyCam = new Vision(Hardware.Led1, Hardware.Led2, Hardware.Led3, Hardware.PixyCam, Constants.CAMERA_GOAL, Constants.CAMERA_FOCAL);
		public SmartDashBoard Smartdashboard = new SmartDashBoard();
		public static RobotDrive myRobot = new RobotDrive(Hardware.rightMotor, Hardware.leftMotor);	
		public static TestAuto Auto = new TestAuto();
		public static double angleOutput = 0;
		public static int angleGoal = 0;
		public static double angleError = 0;
		
	/**
     * This function is run when the robot is first started up and should be
     * used for any initialization code.
     */
	public void robotInit(){
		//hi aj
		//Intilaze Loops For Subclass
			SSLooper.addLoopable(Unnamed);
			SSLooper.addLoopable(Accumulator);
			SSLooper.addLoopable(PixyCam);
			SSLooper.addLoopable(Smartdashboard);
			AutoLooper.addLoopable(Auto);
			SSLooper.start();
			
		//Reset sensor values
			Hardware.driveEncoder.reset();
			Hardware.hallEffectShooterLeft.reset();
		    Hardware.hallEffectShooterRight.reset();
		  
	    	Hardware.hallEffectShooterLeft.setMaxPeriod(1);
	    	Hardware.hallEffectShooterRight.setMaxPeriod(1);
		//Setup sensors
			Hardware.hallEffectShooterLeft.setUpSourceEdge(false, true);
			Hardware.hallEffectShooterRight.setUpSourceEdge(false, true);
			Hardware.intakeController.setFeedbackDevice(FeedbackDevice.CtreMagEncoder_Absolute);
			Hardware.shooterPosition.setFeedbackDevice(FeedbackDevice.CtreMagEncoder_Absolute);
			Hardware.SpartanBoard.calibrate();
			
			Hardware.LeftShooterWheels.setInverted(true);
			Hardware.RightShooterWheels.setInverted(false);
			
			Shooter.state = Shooter.ShooterStates.POSITION1;
			
	}
	
    
	/**
	 * This function is run when the robot is set into autonomous mode
	 * this used for any initialization code of autonomous. 
	 */
    public void autonomousInit(){
    	//Start Control Loops
	    	SSLooper.start();
	    	SSLooper.update();
	    	AutoLooper.start();
	    	AutoLooper.update();
	    	
	    	
	    //auto selected
			SmartDashBoard.autoSelected = (int) SmartDashBoard.chooser.getSelected();
    }
    
    /**
     * This function is called periodically during autonomous
     */
    public void autonomousPeriodic(){
    }

    /**
     * This function is called periodically during operator control
     */
    public void teleopInit(){
    	//Start Control Loops
	    	SSLooper.start();
	    	SSLooper.update();
	    	AutoLooper.stop();
    }
    public void teleopPeriodic(){    	
    	/*if(!Hardware.intakeSwitch.get()){
    		Hardware.
.setPosition(0);
    	}*/
    	//Drive
    	myRobot.arcadeDrive(Hardware.drivePad.getY(), (Hardware.drivePad.getRawAxis(4))*-1, false);
    	
    	if (Hardware.SpartanBoard.getAngle() >= 360){
	    	Hardware.SpartanBoard.reset();
	    } else if (Hardware.SpartanBoard.getAngle() <= -360){
	    	Hardware.SpartanBoard.reset();
	    }
    }
    
    public void disabledInit(){
    	//Stop all loops
	    	SSLooper.stop();
	    	AutoLooper.stop();
    }
    
    /**
     * This function is called periodically during test mode
     */
    public void testPeriodic(){
    }
    
   
}
