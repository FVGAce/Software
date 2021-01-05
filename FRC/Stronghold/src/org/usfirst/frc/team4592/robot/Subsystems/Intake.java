package org.usfirst.frc.team4592.robot.Subsystems;

import org.usfirst.frc.team4592.robot.Lib.Loopable;
import org.usfirst.frc.team4592.robot.Subsystems.Shooter.ShooterStates;

import edu.wpi.first.wpilibj.*;
import org.usfirst.frc.team4592.robot.Constants;
import org.usfirst.frc.team4592.robot.Hardware;

public class Intake implements Loopable{
	public CANTalon intakeController;
	public VictorSP intakePosition;
	public Talon intakeMotor;
	public Talon intakeMotor2;
	public DigitalInput intakeSwitch;
	public static float goal;
	public static double error;
	public static double error_sum;
	double output;
	public static IntakeStates state = IntakeStates.UP;
	IntakeStates prevState;
		
	/*Up is to put the intake up
	 *down is to put the intake down
	 *OP is the obstacle position
	 *start is to start intaking(Start Rollers)
	 *stop is to stop intaking(Stop Rollers) 
	 */
	public enum IntakeStates{
		UP, DOWN, OP, HIGHER, START, STOP, SHOOT, FWD;
	}
	
	public Intake(CANTalon intakeController, VictorSP intakePosition, Talon intakeMotor, Talon intakeMotor2, DigitalInput intakeSwitch){
		this.intakeController = intakeController;
		this.intakePosition = intakePosition;
		this.intakeMotor = intakeMotor;
		this.intakeMotor2 = intakeMotor2;
		this.intakeSwitch = intakeSwitch;
	}
	
	@Override
	public void update(){
		if (!intakeSwitch.get()){
			intakeController.setPosition(0);
		}
		IntakeStates newState = state;
	  	switch (state){
	  	case UP:	  		
	  		intakeController.reverseSensor(true);
			intakeController.reverseOutput(true);
			goal = 5;
			error = goal - intakeController.getPosition()*(360/3.14);
			output = Constants.INTAKE_Kp * error + (Constants.INTAKE_Ki * error_sum);
			
			if(output > 1){
			output = 1;
			}
			
	  	intakeController.set(-1*output);
	  	intakePosition.set(-1*output);
	  		
	  		intakeMotor.set(0);
	  		intakeMotor2.set(0);
	  		
	  			if(Hardware.operatorPad.getRawButton(Constants.INTAKE_DOWN)){
	  				newState = IntakeStates.DOWN;
	  			}else if(Hardware.operatorPad.getRawButton(Constants.INTAKE_START)){
	  				prevState = IntakeStates.UP;
	  				newState = IntakeStates.START;
	  			}else if(Hardware.operatorPad.getRawButton(Constants.INTAKE_OP)/*&& Shooter.state == ShooterStates.POSITION1*/){
	  				newState = IntakeStates.OP;
	  			}else if(Hardware.operatorPad.getRawButton(Constants.INTAKE_HIGHER)&& Shooter.state == ShooterStates.POSITION1){
	  				newState = IntakeStates.HIGHER;
	  			}else if(Hardware.drivePad.getRawButton(4)){
	  				prevState = IntakeStates.UP;
	  				newState = IntakeStates.SHOOT;
	  			}else if (Hardware.operatorPad.getRawButton(Constants.INTAKE_SHOOT)){
	  				prevState = IntakeStates.UP;
	  				newState = IntakeStates.FWD;
	  			}
	  			
	 break;
	 
	  	case DOWN:
	  		goal = 92;
			error = goal - intakeController.getPosition()*(360/3.14);
			output = Constants.INTAKE_Kp * error + (Constants.INTAKE_Ki * error_sum);
			
			if(output >1){
			output = 1;
			}
			
	  		intakeController.set(-1*output);
	  		intakePosition.set(-1*output);
	  			if(Hardware.operatorPad.getRawButton(Constants.INTAKE_UP)&& Shooter.state == ShooterStates.POSITION1){
	  				newState = IntakeStates.UP;
	  			}else if(Hardware.operatorPad.getRawButton(Constants.INTAKE_START)){
	  				prevState = IntakeStates.DOWN;
	  				newState = IntakeStates.START;
	  			}else if(Hardware.operatorPad.getRawButton(Constants.INTAKE_OP)/*&& Shooter.state == ShooterStates.POSITION1*/){
	  				newState = IntakeStates.OP;
	  			}else if(Hardware.operatorPad.getRawButton(Constants.INTAKE_HIGHER)&& Shooter.state == ShooterStates.POSITION1){
	  				newState = IntakeStates.HIGHER;
	  			}else if(Hardware.drivePad.getRawButton(4)){
	  				prevState = IntakeStates.DOWN;
	  				newState = IntakeStates.SHOOT;
	  			}else if (Hardware.operatorPad.getRawButton(Constants.INTAKE_SHOOT)){
	  				prevState = IntakeStates.DOWN;
	  				newState = IntakeStates.FWD;
	  			}
	 break;
	 	
	 //Max
	 	case OP:
	  		goal = 110;
			error = goal - intakeController.getPosition()*(360/3.14);
			output = Constants.INTAKE_Kp * error + (Constants.INTAKE_Ki * error_sum);
			
			if(output > 1){
			output = 1;
			}
			
	  		intakeController.set(-1*output);
	  		intakePosition.set(-1*output);
	  			if(Hardware.operatorPad.getRawButton(Constants.INTAKE_UP)&& Shooter.state == ShooterStates.POSITION1){
	  				newState = IntakeStates.UP;
	  			}else if(Hardware.operatorPad.getRawButton(Constants.INTAKE_START)){
	  				prevState = IntakeStates.OP;
	  				newState = IntakeStates.START;
	  			}else if (Hardware.operatorPad.getRawButton(Constants.INTAKE_DOWN)){
	  				newState = IntakeStates.DOWN;
	  			}else if(Hardware.operatorPad.getRawButton(Constants.INTAKE_HIGHER)&& Shooter.state == ShooterStates.POSITION1){
	  				newState = IntakeStates.HIGHER;
	  			}else if(Hardware.drivePad.getRawButton(4)){
	  				prevState = IntakeStates.OP;
	  				newState = IntakeStates.SHOOT;
	  			}else if (Hardware.operatorPad.getRawButton(Constants.INTAKE_SHOOT)){
	  				prevState = IntakeStates.OP;
	  				newState = IntakeStates.FWD;
	  			}
	 break;
	 
	 	case HIGHER:
	  		goal = 35;
			error = goal - intakeController.getPosition()*(360/3.14);
			output = Constants.INTAKE_Kp * error + (Constants.INTAKE_Ki * error_sum);
			
			if(output > 1){
			output = 1;
			}
			
	  		intakeController.set(-1*output);
	  		intakePosition.set(-1*output);
	  			if(Hardware.operatorPad.getRawButton(Constants.INTAKE_UP)&& Shooter.state == ShooterStates.POSITION1){
	  				newState = IntakeStates.UP;
	  			}else if(Hardware.operatorPad.getRawButton(Constants.INTAKE_START)){
	  				prevState = IntakeStates.HIGHER;
	  				newState = IntakeStates.START;
	  			}else if (Hardware.operatorPad.getRawButton(Constants.INTAKE_DOWN)){
	  				newState = IntakeStates.DOWN; 
	  			}else if (Hardware.operatorPad.getRawButton(Constants.INTAKE_OP)/*&& Shooter.state == ShooterStates.POSITION1*/){
	  				newState = IntakeStates.OP;
	  			}else if(Hardware.drivePad.getRawButton(4)){
	  				prevState = IntakeStates.HIGHER;
	  				newState = IntakeStates.SHOOT;
	  			}else if (Hardware.operatorPad.getRawButton(Constants.INTAKE_SHOOT)){
	  				prevState = IntakeStates.HIGHER;
	  				newState = IntakeStates.FWD;
	  			}
	 break;
	 	case FWD:
	 		while(Hardware.operatorPad.getRawButton(Constants.INTAKE_SHOOT)){
	 			intakeMotor.set(1);
	 			intakeMotor2.set(-1);
	 			Shooter.state = ShooterStates.FWD;		}
	 		newState = IntakeStates.STOP;
	 
	 
	 	//change to momentary button
	  	case START:
	  		while(Hardware.operatorPad.getRawButton(Constants.INTAKE_START)){
	  			intakeMotor.set(-1);
	  			intakeMotor2.set(1);
	  			Shooter.state = ShooterStates.INTAKE;
	  		0}
	  		
	  		newState = IntakeStates.STOP;
	  		
	  			/*if(Hardware.operatorPad.getRawButton(Constants.INTAKE_START)){
	  				newState = IntakeStates.STOP;
	  			}else if(Hardware.operatorPad.getRawButton(Constants.INTAKE_UP)&& Shooter.state == ShooterStates.POSITION1){
	  				newState = IntakeStates.UP;
	  			}else if(Hardware.operatorPad.getRawButton(Constants.INTAKE_DOWN)){
	  				newState = IntakeStates.DOWN;
	  			}*/
	 break;
	 	  			
	  	case STOP:
	  		intakeMotor.set(0);
	  		intakeMotor2.set(0);
	  		Shooter.state = ShooterStates.CLOSE;
	  		newState = prevState;
	  		
	  		//if(Hardware.operatorPad.getRawButton(Constants.INTAKE_UP)&& Shooter.state == ShooterStates.POSITION1){
	  			//newState = IntakeStates.UP;
	  		//}else if(Hardware.operatorPad.getRawButton(Constants.INTAKE_START)){
	  			//newState = IntakeStates.START;
	  		//}
	  break;
	  
	  	//Change to momentary button
	  	case SHOOT:
	  		while(Hardware.drivePad.getRawButton(4)){  		
	  			intakeMotor.set(1);
	  			intakeMotor2.set(-1);
	  			
	  			Shooter.state = ShooterStates.LOWSHOOT;
	  		}
	  		
	  		newState =IntakeStates.STOP;
	  		
	  	    //if(Hardware.operatorPad.getRawButton(Constants.INTAKE_DOWN)){
  				//newState = IntakeStates.DOWN;
  			//}else if(Hardware.drivePad.getRawButton(Constants.SHOOTER_CLOSE)){
  				//newState = IntakeStates.STOP;
  			//}
	  break;
	  
	  	default:
	  		newState = IntakeStates.UP;
	  break;
	  	}
	  	
	  	if(newState != state) {
	  		state = newState;
		}
	}
}