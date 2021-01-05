package org.usfirst.frc.team4592.robot.Subsystems;

import org.usfirst.frc.team4592.robot.Lib.Loopable;
import edu.wpi.first.wpilibj.*;
import org.usfirst.frc.team4592.robot.Constants;
import org.usfirst.frc.team4592.robot.Hardware;

@SuppressWarnings("unused")
public class Climber implements Loopable{
	public Solenoid CLeftStage1;
	public Solenoid CLeftStage2;
	public Solenoid CRightStage1;
	public Solenoid CRightStage2;
	ClimberStates state;
	
	public enum ClimberStates{
		UP, DOWN;
	}
	
	public Climber(Solenoid CLeftStage1, Solenoid CLeftStage2, Solenoid CRightStage1, Solenoid CRightStage2){
		this.CLeftStage1 = CLeftStage1;
		this.CLeftStage2 = CLeftStage2;
		this.CRightStage1 = CRightStage1;
		this.CRightStage2 = CRightStage2;
	}
	
	@Override
	public void update(){
		ClimberStates newState = state;
		switch (state){
		case UP:
			CLeftStage1.set(true);
			CLeftStage2.set(true);
			CRightStage1.set(true);
			CRightStage2.set(true);
			
			if(Hardware.operatorPad.getRawButton(Constants.CLIMBER)){
				newState = ClimberStates.DOWN;
			}
	break;
		
		case DOWN:
			CLeftStage1.set(false);
			CLeftStage2.set(false);
			CRightStage1.set(false);
			CRightStage2.set(false);
			
			if(Hardware.operatorPad.getRawButton(Constants.CLIMBER)){
				newState = ClimberStates.UP;
			}
	
	break;
	
		default:
			newState = ClimberStates.DOWN;
	break;
		}
	}
}
