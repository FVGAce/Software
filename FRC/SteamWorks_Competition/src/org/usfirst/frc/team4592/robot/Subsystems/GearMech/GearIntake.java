package org.usfirst.frc.team4592.robot.Subsystems.GearMech;

import org.usfirst.frc.team4592.robot.Constants;
import org.usfirst.frc.team4592.robot.Hardware;
import org.usfirst.frc.team4592.robot.Lib.SubsystemFramework;

import edu.wpi.first.wpilibj.VictorSP;

public class GearIntake extends SubsystemFramework{
	//Hardware
	private VictorSP gearIntakeMotor;
	
	//Gear Intake State
	private GearIntakeState state = GearIntakeState.Off;
	
	//Gear Intake Constructor
	public GearIntake(VictorSP gearIntakeMotor){
		this.gearIntakeMotor = gearIntakeMotor;
	}
	
	//Gear Intake States
	//Off Tells VictorSP To Provide 0 Power
	//Intake Tells VictorSP To Provide Power To Intake Gear
	//Reverse Tells VictorSP To Provide Power To Spit Gear
	public enum GearIntakeState{
		Off, Intake, Reverse;
	}
	
	//Auto Control
	public void GearIntake_Off(){
		gearIntakeMotor.set(0);
	}
	
	public void GearIntake_Intake(){
		gearIntakeMotor.set(-1);
	}
	
	public void GearIntake_Reverse(){
		gearIntakeMotor.set(1);
	}
	
	//Telop Control
	@Override
	public void update(){
		GearIntakeState newState = state;
		
		switch(state){
			case Off:
				//Tells Motor Controller To Give 0 Power
				gearIntakeMotor.set(0);
				
				//Checks For Button Presses To Switch TO Other States
				if(Hardware.driverPad.getRawAxis(Constants.GEARINTAKE_INTAKE) > 0.2){
					newState = GearIntakeState.Intake;
				}else if(Hardware.driverPad.getRawButton(Constants.GEARINTAKE_REVERSE)){
					newState = GearIntakeState.Reverse;
				}
				
				break;
			
			case Intake:
				//Tells Motor Controller To Give 60% Power
				gearIntakeMotor.set(0.6);
				
				//Checks That Intake Button Isn't Pressed To Shut Off Intake
				if(Hardware.driverPad.getRawAxis(Constants.GEARINTAKE_INTAKE) <= 0.2){
					newState = GearIntakeState.Off;
				}
				
				break;
			
			case Reverse:
				//Tells Motor Controller To Give -100% Power
				gearIntakeMotor.set(-1);
				
				//Checks That Spit Button Isn't Pressed To Shut Off Intake
				if(!Hardware.driverPad.getRawButton(Constants.GEARINTAKE_REVERSE)){
					newState = GearIntakeState.Off;
				}
				
				break;
			
			default:
				newState = GearIntakeState.Off;
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
