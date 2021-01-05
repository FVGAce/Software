package org.usfirst.frc.team4592.robot.Subsystems.GearMech;

import org.usfirst.frc.team4592.robot.Constants;
import org.usfirst.frc.team4592.robot.Hardware;
import org.usfirst.frc.team4592.robot.Lib.SubsystemFramework;
import org.usfirst.frc.team4592.robot.Util.doubleSolenoid;

public class FlipperPosition extends SubsystemFramework{
	//Hardware
	private doubleSolenoid flipperPiston;
	
	//Flipper State
	private FlipperPositionState state = FlipperPositionState.Intake;
	
	//Flipper Constructor 
	public FlipperPosition(doubleSolenoid flipperPiston){
		this.flipperPiston = flipperPiston;
	}
	
	//Flipper States
	//Intake State Puts Flipper Into Intaking Position (Angled Position)
	//Place State Puts Flipper Into Placeing Posiotn (Vertical Position) 
	public enum FlipperPositionState{
		Intake, Place;
	}
	
	//Auto Control
	public void FlipperPosition_Intake(){
		flipperPiston.close();
	}
	
	public void FlipperPosition_Place(){
		flipperPiston.open();
	}
	
	//Telop Control
	@Override
	public void update(){
		FlipperPositionState newState = state;
		
		switch(state){
			case Intake:
				//Tells Flipper Piston To Closed State
				flipperPiston.close();
				
				//Checks For Button Press To Switch To Place State
				if(Hardware.driverPad.getRawButton(Constants.FLIPPERPOSITION_PLACE)){
					newState = FlipperPositionState.Place;
				}
				
				break;
				
			case Place:
				//Tells Flipper Piston To Open State
				flipperPiston.open();
				
				//Checks For Button Press To Switch To Intake State
				if(Hardware.driverPad.getRawButton(Constants.FLIPPERPOSITION_INTAKE)){
					newState = FlipperPositionState.Intake;
				}
				
				break;
			
			default:
				newState = FlipperPositionState.Intake;
				break;
		}
		
		if(newState != state){
			state = newState;
		}
		
	}

	@Override
	public void outputToSmartDashboard(){
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setupSensors(){
		// TODO Auto-generated method stub
		
	}

}
