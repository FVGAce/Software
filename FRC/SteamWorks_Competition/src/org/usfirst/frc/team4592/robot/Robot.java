package org.usfirst.frc.team4592.robot;

import org.usfirst.frc.team4592.robot.AutonomousModes.AngleLeftGear;
import org.usfirst.frc.team4592.robot.AutonomousModes.AngleRightGear;
import org.usfirst.frc.team4592.robot.AutonomousModes.AutoCenterGear;
import org.usfirst.frc.team4592.robot.AutonomousModes.AutoStraight;
import org.usfirst.frc.team4592.robot.AutonomousModes.SideGearAuto;
import org.usfirst.frc.team4592.robot.Lib.MultiLooper;
import org.usfirst.frc.team4592.robot.Subsystems.Climber;
import org.usfirst.frc.team4592.robot.Subsystems.Drivetrain;
//import org.usfirst.frc.team4592.robot.Subsystems.FuelIntake;
//import org.usfirst.frc.team4592.robot.Subsystems.GearDelivery;
import org.usfirst.frc.team4592.robot.Subsystems.GearMech.FlipperPosition;
import org.usfirst.frc.team4592.robot.Subsystems.GearMech.GearIntake;
import org.usfirst.frc.team4592.robot.Subsystems.GearMech.GearLock;

import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

@SuppressWarnings("unused")
public class Robot extends IterativeRobot{
	//Loopers
		private MultiLooper DriveLooper = new MultiLooper("DriveLooper", 1/200.0, false); //Drivetrain looper
		private MultiLooper SSLooper = new MultiLooper("SSLooper", 1/200.0, false); //Subsystems looper
		private MultiLooper AutoLooper = new MultiLooper("AutoLooper", 1/200.0, false); //Auto looper
	
	//Subsystems
		private Drivetrain myDrive = new Drivetrain(Hardware.rightMasterMotor, Hardware.rightSlaveMotor, Hardware.leftMasterMotor, Hardware.leftSlaveMotor, Hardware.shifter, Hardware.SpartanBoard, Constants.Average_Ticks_Per_Meter, Constants.Drive_ANGLE_Kp, Constants.Drive_ANGLE_Ki, Constants.Drive_Kf, Constants.Drive_Kp, Constants.Drive_Ki, Constants.Drive_Kd);
		private Climber climb = new Climber(Hardware.rightClimberMotor, Hardware.leftClimberMotor);
		private FlipperPosition flipperPosition = new FlipperPosition(Hardware.flipperPosition);
		private GearLock gearLock = new GearLock(Hardware.gearLock);
		private GearIntake gearIntake = new GearIntake(Hardware.gearIntakeMotor);
		
	//Autos
		private AutoStraight autoStraight = new AutoStraight(myDrive);
		//private UCenterGear UCenterGear = new UCenterGear(myDrive, flipperPosition, gearLock);
		private AutoCenterGear centerGear = new AutoCenterGear(myDrive, flipperPosition, gearLock); 
		private AngleLeftGear angleLeftGear  = new AngleLeftGear(myDrive, flipperPosition, gearLock);
		private AngleRightGear angleRightGear = new AngleRightGear(myDrive,flipperPosition, gearLock);
		private SideGearAuto sideGearAuto = new SideGearAuto(myDrive, flipperPosition, gearLock);
		
	//Selectables
		private SendableChooser<String> autoChooser = new SendableChooser<String>();
		private SendableChooser<String> sideSelect = new SendableChooser<String>();
		
	/**
     * This function is run when the robot is first started up and should be
     * used for any initialization code.
     */
    public void robotInit(){
    	//Need To Reset All Encoders And Set It Up In Intended Way
	    	myDrive.setupSensors();
	    	
    	//Subsystems To Loopers
	    	DriveLooper.addLoopable(myDrive);
	    	SSLooper.addLoopable(climb);
	    	SSLooper.addLoopable(flipperPosition);
	    	SSLooper.addLoopable(gearLock);
	    	SSLooper.addLoopable(gearIntake);
	    	
    	/*//Setup Auto Select
	    	autoChooser.addDefault("Nothing", "1");
	    	autoChooser.addObject("Drive Straight", "2");
	    	autoChooser.addObject("Center Gear", "3");
	    	autoChooser.addObject("Left Gear", "4");
	    	autoChooser.addObject("Right Gear", "5");
	    	
    	//Setup Side Select
	    	sideSelect.addDefault("Red", "1");
	    	sideSelect.addObject("Blue", "2");
	    	*/
    	//Put Selectable into SmartDashboard
    		//SmartDashboard.putData("Auto Select", autoChooser);
    		//sSmartDashboard.putData("Side Select", sideSelect);
    		
    		System.out.println("We definitely have new code");
    		SmartDashboard.putString("HI", "HI");
    }
    
    /**
	 * This function is run when the robot is set into autonomous mode
	 * this used for any initialization code of autonomous. 
	 */
    public void autonomousInit(){
    	/*//Do Nothing Auto
	    	if(autoChooser.getSelected() == "1"){
	    		//do nothing
	    	}
    	//Drive Straight Auto
	    	else if(autoChooser.getSelected() == "2"){    		
	    		AutoLooper.addLoopable(autoStraight);
	    	}
    	
    	//Center Gear Auto
	    	else if(autoChooser.getSelected() == "3"){    		
	    		//AutoLooper.addLoopable(UCenterGear);
	    		AutoLooper.addLoopable(centerGear);
	    	}
    	
    	//Left Gear Auto
	    	else if(autoChooser.getSelected() == "4"){
	    		if(sideSelect.getSelected() == "1"){
	    			angleLeftGear.setSide(1);
	    		}else if(sideSelect.getSelected() == "2"){
	    			angleLeftGear.setSide(-1);
	    		}
	    		
	    		AutoLooper.addLoopable(angleLeftGear);
	    	}
    	
    	//Right Gear Auto
	    	else if(autoChooser.getSelected() == "5"){
	    		if(sideSelect.getSelected() == "1"){
	    			angleRightGear.setSide(1);
	    		}else if(sideSelect.getSelected() == "2"){
	    			angleRightGear.setSide(1);
	    		}
	    		
	    		AutoLooper.addLoopable(angleRightGear);
	    	}
    	*/
    	//Setup Drivetrain to auto setup
    		myDrive.autoSetupMotors();
    		AutoLooper.addLoopable(centerGear);
    		//AutoLooper.addLoopable(UCenterGear);
    		//AutoLooper.addLoopable(autoStraight);
    		//AutoLooper.addLoopable(sideGearAuto);
    		
    	//Auto Looper Setup
    		AutoLooper.start();
    		AutoLooper.update();
    }

    /**
     * This function is called periodically during autonomous
     */
    public void autonomousPeriodic(){
    	//Values That Need To Be Updated Periodically
    		myDrive.outputToSmartDashboard();
    		myDrive.resetSpartanBoard();
    }
    
    public void teleopInit(){
    	//Setup Drivetrain For Teleop
    		myDrive.teleopSetupMotors();
    		myDrive.teleopMotorsFull();
    		
    	//Start Control Loops
    		DriveLooper.start();
	    	SSLooper.start();
	    	DriveLooper.update();
	    	SSLooper.update();
    }

    /**
     * This function is called periodically during operator control
     */
    public void teleopPeriodic(){
    	//Values That Need To Be Updated Periodically
    		myDrive.resetSpartanBoard();
    }
    
    /**
     * This function is called periodically during test mode
     */
    public void testPeriodic(){
    
    }
    
    public void disabledInit(){
    	//Stop All Loopers
	    	AutoLooper.stop();
	    	DriveLooper.stop();
	    	SSLooper.stop();
    }
}