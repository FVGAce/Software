package org.usfirst.frc.team4592.robot.Lib;

import org.usfirst.frc.team4592.robot.Lib.Loop.Loopable;

import edu.wpi.first.wpilibj.DriverStation;

public abstract class AutoFramework implements Loopable{
	public int counter = 0;
	public String gameData;
	
	public abstract void outputToSmartDashboard();
	public void getGameData() {
		gameData = DriverStation.getInstance().getGameSpecificMessage(); 
	}
}
