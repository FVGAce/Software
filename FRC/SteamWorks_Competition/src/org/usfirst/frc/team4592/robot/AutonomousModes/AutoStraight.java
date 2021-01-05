package org.usfirst.frc.team4592.robot.AutonomousModes;

import org.usfirst.frc.team4592.robot.Lib.Loopable;
import org.usfirst.frc.team4592.robot.Subsystems.Drivetrain;

public class AutoStraight implements Loopable{
	private Drivetrain myDrive;
	
	public AutoStraight(Drivetrain myDrive){
		this.myDrive = myDrive;
	}
	@Override
	public void update() {
		myDrive.auto_90_power();
		myDrive.autoDrive(-5);
	}

}
