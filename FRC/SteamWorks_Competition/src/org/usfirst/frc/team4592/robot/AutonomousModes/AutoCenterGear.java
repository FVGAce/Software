package org.usfirst.frc.team4592.robot.AutonomousModes;

import org.usfirst.frc.team4592.robot.Lib.Loopable;
import org.usfirst.frc.team4592.robot.Subsystems.Drivetrain;
import org.usfirst.frc.team4592.robot.Subsystems.GearMech.FlipperPosition;
import org.usfirst.frc.team4592.robot.Subsystems.GearMech.GearLock;

public class AutoCenterGear implements Loopable{
	private Drivetrain myDrive;
	private FlipperPosition flipperPosition;
	private GearLock gearLock;
	private int counter = 0;
	
	public AutoCenterGear(Drivetrain myDrive, FlipperPosition flipperPosition, GearLock gearLock){
		this.myDrive = myDrive;
		this.flipperPosition = flipperPosition;
		this.gearLock = gearLock;
	}
	
	@Override
	public void update(){
		if(counter >= 0 && counter <= 640){
			flipperPosition.FlipperPosition_Place();;
			gearLock.GearLock_Lock();
			
			if(counter >= 0 && counter <= 200){
				myDrive.autoDrive(-0.73);
			}
			if(counter >= 220 && counter <= 420){
				myDrive.autoDrive(-1.46);
			}else if(counter >= 440 && counter <= 640){
				myDrive.autoDrive(-2.2);
			}
		}else if(counter >= 650 && counter <= 750){
			gearLock.GearLock_Unlock();
		}else if(counter >= 760){
			myDrive.auto_90_power();
			myDrive.autoDrive(-0.2);
		}
		
		counter++;
		System.out.println(counter);
		System.out.println(myDrive.get_GoalAngle());
	}
}