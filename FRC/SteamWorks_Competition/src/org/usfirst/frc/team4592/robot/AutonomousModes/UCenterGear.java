package org.usfirst.frc.team4592.robot.AutonomousModes;

import org.usfirst.frc.team4592.robot.Lib.Loopable;
import org.usfirst.frc.team4592.robot.Subsystems.Drivetrain;
//import org.usfirst.frc.team4592.robot.Subsystems.GearDelivery;
import org.usfirst.frc.team4592.robot.Subsystems.GearMech.FlipperPosition;
import org.usfirst.frc.team4592.robot.Subsystems.GearMech.GearLock;

public class UCenterGear implements Loopable{
	private Drivetrain myDrive;
	private FlipperPosition flipperPosition;
	private GearLock gearLock;
	private int counter = 0;
	private int side = 1;
	
	public UCenterGear(Drivetrain myDrive, FlipperPosition flipperPosition, GearLock gearLock){
		this.myDrive = myDrive;
		this.flipperPosition = flipperPosition;
		this.gearLock = gearLock;
	}
	
	public void setSide(int side){
		this.side = side;
	}
	
	@Override
	public void update(){
		if(counter >= 0 && counter < 450){
			flipperPosition.FlipperPosition_Place();;
			
			gearLock.GearLock_Lock();
			myDrive.autoDrive(-1.75);
			
			//if(counter >= 220 && counter < 500){
				//flipperPosition.FlipperPosition_Place();
			//}
		}else if(counter >= 460 && counter < 590){
			gearLock.GearLock_Lock();
			myDrive.autoDrive(-2.2);
		}else if(counter >= 600 && counter <= 700){
			gearLock.GearLock_Unlock();
		}else if(counter >= 750 && counter <= 950){
			myDrive.auto_90_power();
			myDrive.autoDrive(-0.2);
		/*}else if(counter >= 980 && counter <= 1140){
			myDrive.teleopSetupMotors();
			myDrive.autoTurn(side*90);
		}else if(counter >= 1150 && counter <= 1160){
			myDrive.autoSetupMotors();
			myDrive.zeroEncoders();
		}else if(counter >= 1170 && counter <= 1560){
			myDrive.autoDrive(-2.5);
		}else if(counter >= 1580 && counter <= 1850){
			myDrive.teleopSetupMotors();
			myDrive.autoTurn(side*0);
		}else if(counter >= 1860 && counter <= 1870){
			myDrive.autoSetupMotors();
			myDrive.zeroEncoders();
		}else if(counter >= 1880){
			myDrive.autoDrive(-5);&*/
		}
		
		counter++;
		System.out.println(counter);
		System.out.println(myDrive.get_GoalAngle());
	}
}