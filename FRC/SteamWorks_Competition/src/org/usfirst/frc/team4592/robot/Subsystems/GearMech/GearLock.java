package org.usfirst.frc.team4592.robot.Subsystems.GearMech;

import org.usfirst.frc.team4592.robot.Constants;
import org.usfirst.frc.team4592.robot.Hardware;
import org.usfirst.frc.team4592.robot.Lib.SubsystemFramework;
import org.usfirst.frc.team4592.robot.Util.doubleSolenoid;

public class GearLock extends SubsystemFramework{
	//Hardware
	private doubleSolenoid gearLockPiston;
	
	//Gear Lock State
	private GearLockState state = GearLockState.Lock;
	
	//Gear Lock Constructor
	public GearLock(doubleSolenoid gearLockPiston){
		this.gearLockPiston = gearLockPiston;
	}
	
	//Gear Lock States
	//Unlock Allows Gear To Move Freely
	//Lock Doesn't Allow Gear To Move
	public enum GearLockState{
		Unlock, Lock;
	}
	
	//Auto Control
	public void GearLock_Unlock(){
		gearLockPiston.open();
	}
	
	public void GearLock_Lock(){
		gearLockPiston.close();
	}
	
	//Telop Control	
	@Override
	public void update() {
		GearLockState newState = state;
		
		switch(state){	
			case Unlock:
				//Opens Gear Lock Piston
				gearLockPiston.open();
				
				//Checks For Button Press To Lock The System
				if(Hardware.driverPad.getRawAxis(Constants.GEARLOCK) <= 0.2){
					newState = GearLockState.Lock;
				}
				
				break;
			
			case Lock:
				//Closes Gear Lock Piston
				gearLockPiston.close();
				
				//Checks For Button Press To Unlock The System
				if(Hardware.driverPad.getRawAxis(Constants.GEARLOCK) > 0.2){
					newState = GearLockState.Unlock;
				}
				
				break;
				
			default:
				newState = GearLockState.Lock;
				break;
		}
		
		if(newState != state){
			state = newState;
		}
		
	}

	@Override
	public void outputToSmartDashboard() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setupSensors() {
		// TODO Auto-generated method stub
		
	}

}