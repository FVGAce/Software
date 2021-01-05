package org.usfirst.frc.team4592.robot.AutonomousModes;

import org.usfirst.frc.team4592.robot.Lib.Loopable;
import org.usfirst.frc.team4592.robot.Subsystems.Drivetrain;
import org.usfirst.frc.team4592.robot.Subsystems.GearMech.FlipperPosition;
import org.usfirst.frc.team4592.robot.Subsystems.GearMech.GearLock;

public class AngleRightGear implements Loopable{
	private Drivetrain myDrive;
	private FlipperPosition flipperPosition;
	private GearLock gearLock;
	private int counter = 0;
	private int side = 1;
	
	public AngleRightGear(Drivetrain myDrive, FlipperPosition flipperPosition, GearLock gearLock){
		this.myDrive = myDrive;
		this.flipperPosition = flipperPosition;
		this.gearLock = gearLock;
	}
	
	public void setSide(int side){
		this.side = side;
	}
	
	@Override
	public void update() {
		if(counter >= 0 && counter <= 1050){
			flipperPosition.FlipperPosition_Place();
			gearLock.GearLock_Lock();
			
			if(counter >= 0 && counter <= 590){
				myDrive.autoDrive(-2.2);
			}else if(counter >= 600 && counter <= 760){
				myDrive.teleopSetupMotors();
				myDrive.autoTurn(side*-45.92);
			}else if(counter >= 770 && counter <= 1050){
				myDrive.autoSetupMotors();
				myDrive.zeroEncoders();
				myDrive.autoDrive(-1);
			}
		}else if(counter >= 1060 && counter <= 1160){
			gearLock.GearLock_Unlock();
		}else if(counter >= 1170){
			myDrive.auto_90_power();
			myDrive.autoDrive(-0.2);
		}
		
		counter++;
		System.out.println(counter);
		System.out.println(myDrive.get_GoalAngle());
	}

}
