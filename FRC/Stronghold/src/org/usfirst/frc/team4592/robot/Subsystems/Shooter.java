package org.usfirst.frc.team4592.robot.Subsystems;


import org.usfirst.frc.team4592.robot.Lib.Loopable;
import org.usfirst.frc.team4592.robot.Subsystems.Intake.IntakeStates;
//import org.usfirst.frc.team4592.robot.Util.doubleSolenoid;
import org.usfirst.frc.team4592.robot.Constants;
import org.usfirst.frc.team4592.robot.Hardware;
import edu.wpi.first.wpilibj.*;

public class Shooter implements Loopable{
	public static double Rightperiod;
	public static double Leftperiod;
	public static double LeftRPM = 0;
	public static double RightRPM = 0;
	public VictorSP LeftShooterWheels;
	public VictorSP RightShooterWheels;
	public CANTalon shooterPosition;
	public Solenoid shooterShoot;
	public Solenoid shooterShoot2;
	public DigitalInput Shooter_Zero;
	public DigitalInput shooterSwitch;
	public Counter hallEffectLeft;
	public Counter hallEffectRight;
	public static double error;
	public static double error_sum;
	public static double  goal = 0;
	public static double speed_goal = 0;
	public static double output = 0;
	public static double angle = 0;
	public static ShooterStates state = ShooterStates.POSITION1;
	public static ShooterStates prevState;
	
	public void shooterRPMs(){
		if (Hardware.hallEffectShooterLeft.getStopped()== true ) {
			Leftperiod = 0;
			LeftRPM = 0; 
		}	else if (Hardware.hallEffectShooterLeft.getStopped()== false ){
			Leftperiod = Hardware.hallEffectShooterLeft.getPeriod();
			LeftRPM = 30/Leftperiod;
		}
			if (Hardware.hallEffectShooterRight.getStopped()== true){
			Rightperiod = 0;
			RightRPM = 0; 
		}	 else if (Hardware.hallEffectShooterRight.getStopped()== false){
			Rightperiod = Hardware.hallEffectShooterRight.getPeriod();
			RightRPM = 30/Rightperiod;
		}
	}
	
	public void  bangBang(double goal){
		Shooter.speed_goal = goal;
		//Max
		if (Shooter.speed_goal == 0){
			LeftShooterWheels.set(0);
			RightShooterWheels.set(0);
		}
		if( Shooter.speed_goal < 2900 && Shooter.speed_goal >0){
		 if (LeftRPM < Shooter.speed_goal && RightRPM < Shooter.speed_goal){
			LeftShooterWheels.set(-1);
			RightShooterWheels.set(-1);
		} else if (LeftRPM < Shooter.speed_goal && RightRPM >= Shooter.speed_goal) {
			LeftShooterWheels.set(-1);
			RightShooterWheels.set(-0.25);
		} else if (LeftRPM >= Shooter.speed_goal && RightRPM < Shooter.speed_goal){
			LeftShooterWheels.set(-0.25);
			RightShooterWheels.set(-1);
		} else if (LeftRPM >= Shooter.speed_goal && RightRPM >= Shooter.speed_goal){
			LeftShooterWheels.set(-0.25);
			RightShooterWheels.set(-0.25);
		}
		}
		if( Shooter.speed_goal >= 2900){
			 if (LeftRPM < Shooter.speed_goal && RightRPM < Shooter.speed_goal){
				LeftShooterWheels.set(-1);
				RightShooterWheels.set(-1);
			} else if (LeftRPM < Shooter.speed_goal && RightRPM >= Shooter.speed_goal) {
				LeftShooterWheels.set(-1);
				RightShooterWheels.set(-0.45);
			} else if (LeftRPM >= Shooter.speed_goal && RightRPM < Shooter.speed_goal){
				LeftShooterWheels.set(-0.45);
				RightShooterWheels.set(-1);
			} else if (LeftRPM >= Shooter.speed_goal && RightRPM >= Shooter.speed_goal){
				LeftShooterWheels.set(-0.45);
				RightShooterWheels.set(-0.45);
			}
			}
		}
		
	
/*	private void shooterReady(){
		if (Math.abs(goal - LeftRPM) <= 100 && Math.abs(goal - RightRPM) <= 100){
			shooterLED.set(true);
		} else if (Math.abs(goal - LeftRPM) > 100 && Math.abs(goal - RightRPM) > 100) {
			shooterLED.set(false);
		}
	}*/
	
	/*DOWN place the shooter down
	 *UP place the shooter up for scoring
	 *INTAKE reverse wheels and place it down to intake
	 *SHOOT shoots the ball
	 *LOWBAR moves the shooter down at a proportional rate to the robot going up 
	 */
	public enum ShooterStates{
		POSITION1, POSITION2, POSITION3, POSITION4, INTAKE, SHOOT, CLOSE, SLOWRPM, MIDRPM, FASTRPM, FWD, STOP, LOWSHOOT;
	}
	
	public Shooter(VictorSP LeftShooterWheels, VictorSP RightShooterWheels,
				   CANTalon shooterPosition, Solenoid shooterShoot, Solenoid shooterShoot2, 
				   DigitalInput Shooter_Zero, DigitalInput shooterSwitch, 
				   Counter hallEffectLeft, Counter hallEffectRight){
		this.LeftShooterWheels = LeftShooterWheels;
		this.RightShooterWheels = RightShooterWheels;
		this.shooterPosition = shooterPosition;
		this.Shooter_Zero = Shooter_Zero;
		this.shooterShoot = shooterShoot;
		this.shooterShoot2 = shooterShoot2;
		this.shooterSwitch = shooterSwitch;
		//this.shooterLED = shooterLED;
		this.hallEffectLeft = hallEffectLeft;
		this.hallEffectRight = hallEffectRight;
	}
	
	@Override
	public void update(){
		shooterRPMs();
		if(!Shooter_Zero.get()){
			shooterPosition.setPosition(0);
		}
		angle = ((shooterPosition.getPosition()*(360/3.14)));

		error_sum = error + error;
		ShooterStates newState = state;	
		System.out.println("Dude");
		switch (state){
		case POSITION1:
			shooterPosition.reverseOutput(false);
			shooterPosition.reverseSensor(false);
			goal = 1;
			error = goal - angle;
			output = (Constants.SHOOTER_Kp * error)  ;
			if (output > 1){
				output = 1;
			}
			
			shooterPosition.set(-1*output);
			
			//shooterReady();
			bangBang(0);
			
			//drive shoot
			if(Hardware.drivePad.getRawButton(Constants.SHOOTER_Trigger) /*&& Hardware.shooterSwitch.get()*/){
				prevState = ShooterStates.POSITION1;
				newState = ShooterStates.SHOOT;
			}
			
			//low goal shoot
			else if(Hardware.drivePad.getRawButton(4)){
				prevState = ShooterStates.POSITION1;
				newState = ShooterStates.LOWSHOOT;
			}
			
			//intaking
			else if(Hardware.operatorPad.getRawButton(Constants.INTAKE_START)){
				prevState = ShooterStates.POSITION1;
				newState = ShooterStates.INTAKE;
			}
			
			//stage 2
			else if(Hardware.operatorPad.getRawButton(Constants.SHOOTER_STAGE2)&& ((Intake.state == IntakeStates.DOWN) || (Intake.state == IntakeStates.OP))){
				newState = ShooterStates.POSITION2;
			}
			
			//stage 3
			else if(Hardware.operatorPad.getRawButton(Constants.SHOOTER_STAGE3)&& ((Intake.state == IntakeStates.DOWN) || (Intake.state == IntakeStates.OP))){
				newState = ShooterStates.POSITION3;
			}
			
			//stage 4
			else if(Hardware.operatorPad.getRawButton(Constants.SHOOTER_STAGE4)&& ((Intake.state == IntakeStates.DOWN) || (Intake.state == IntakeStates.OP))){
				newState = ShooterStates.POSITION4;
			}
			
			//low speed
			else if(Hardware.operatorPad.getRawButton(Constants.SPEED_LOW)){
				prevState = ShooterStates.POSITION1;
				newState = ShooterStates.SLOWRPM;
			}
			
			//mid speed
			else if(Hardware.operatorPad.getRawButton(Constants.SPEED_MED)){
				prevState = ShooterStates.POSITION1;
				newState = ShooterStates.MIDRPM;
			}
			
			//fast speed
			else if (Hardware.operatorPad.getRawButton(Constants.SPEED_HIGH)){
				prevState = ShooterStates.POSITION1;
				newState = ShooterStates.FASTRPM;
			}
			
			//stop driver
			else if(Hardware.drivePad.getRawButton(Constants.SHOOTER_STOP)){
				prevState = ShooterStates.POSITION1;
				newState = ShooterStates.STOP;
			}else if(Hardware.operatorPad.getRawButton(Constants.INTAKE_SHOOT)){
				prevState = ShooterStates.POSITION1;
				newState = ShooterStates.FWD;
			}
	break;
		
		case POSITION2:
		goal = 2.5;
		error = goal - angle;
		output = (Constants.SHOOTER_Kp * error) ;
		if (output > 1){
			output = 1;
		}
		
		
		shooterPosition.set(-1*output);
		//	shooterReady();
		bangBang(0);
		
			//drive shoot
			if(Hardware.drivePad.getRawButton(Constants.SHOOTER_Trigger) /*&& Hardware.shooterSwitch.get()*/){
				prevState = ShooterStates.POSITION2;
				newState = ShooterStates.SHOOT;
			}
			//
			else if(Hardware.drivePad.getRawButton(4)){
				prevState = ShooterStates.POSITION2;
				newState = ShooterStates.LOWSHOOT;
			}else if(Hardware.operatorPad.getRawButton(Constants.INTAKE_START)){
				prevState = ShooterStates.POSITION2;
				newState = ShooterStates.INTAKE;
			}else if(Hardware.operatorPad.getRawButton(Constants.SHOOTER_STAGE1)&& /*Intake.state == IntakeStates.DOWN*/ ((Intake.state == IntakeStates.DOWN) || (Intake.state == IntakeStates.OP))){
				newState = ShooterStates.POSITION1;
			}else if(Hardware.operatorPad.getRawButton(Constants.SHOOTER_STAGE3)&& /*Intake.state == IntakeStates.DOWN)*/ ((Intake.state == IntakeStates.DOWN) || (Intake.state == IntakeStates.OP))){
				newState = ShooterStates.POSITION3;
			}else if(Hardware.operatorPad.getRawButton(Constants.SHOOTER_STAGE4)&& /*Intake.state == IntakeStates.DOWN*/ ((Intake.state == IntakeStates.DOWN) || (Intake.state == IntakeStates.OP))){
				newState = ShooterStates.POSITION4;
			}else if (Hardware.operatorPad.getRawButton(Constants.SPEED_LOW)){
				prevState = ShooterStates.POSITION2;
				newState = ShooterStates.SLOWRPM;
			}else if(Hardware.operatorPad.getRawButton(Constants.SPEED_MED)){
				prevState = ShooterStates.POSITION2;
				newState = ShooterStates.MIDRPM;
			}else if (Hardware.operatorPad.getRawButton(Constants.SPEED_HIGH)){
				prevState = ShooterStates.POSITION2;
				newState = ShooterStates.FASTRPM;
			}else if(Hardware.drivePad.getRawButton(Constants.SHOOTER_STOP)){
				prevState = ShooterStates.POSITION2;
				newState = ShooterStates.STOP;
			}else if(Hardware.operatorPad.getRawButton(Constants.INTAKE_SHOOT)){
				prevState = ShooterStates.POSITION2;
				newState = ShooterStates.FWD;
			}
	break;
	
		case POSITION3:
			goal = 18.5;
			error = goal - angle;
			output = (Constants.SHOOTER_Kp * error);
			if (output > 1){
				output = 1;
			}
			
			
			shooterPosition.set(-1*output);
			
			//shooterReady();
			bangBang(0);
			
			if(Hardware.drivePad.getRawButton(Constants.SHOOTER_Trigger) /*&& Hardware.shooterSwitch.get()*/){
				prevState = ShooterStates.POSITION3;
				newState = ShooterStates.SHOOT;
			}else if(Hardware.drivePad.getRawButton(4)){
				prevState = ShooterStates.POSITION3;
				newState = ShooterStates.LOWSHOOT;
			}else if(Hardware.operatorPad.getRawButton(Constants.INTAKE_START)){
				prevState = ShooterStates.POSITION3;
				newState = ShooterStates.INTAKE;
			}else if(Hardware.operatorPad.getRawButton(Constants.SHOOTER_STAGE1) && /*Intake.state == IntakeStates.DOWN*/ ((Intake.state == IntakeStates.DOWN) || (Intake.state == IntakeStates.OP))){
				newState = ShooterStates.POSITION1;
			}else if(Hardware.operatorPad.getRawButton(Constants.SHOOTER_STAGE2)&& /*Intake.state == IntakeStates.DOWN*/ ((Intake.state == IntakeStates.DOWN) || (Intake.state == IntakeStates.OP))){
				newState = ShooterStates.POSITION2;
			}else if(Hardware.operatorPad.getRawButton(Constants.SHOOTER_STAGE4)&& /*Intake.state == IntakeStates.DOWN*/ ((Intake.state == IntakeStates.DOWN) || (Intake.state == IntakeStates.OP))){
				newState = ShooterStates.POSITION4;
			}else if (Hardware.operatorPad.getRawButton(Constants.SPEED_LOW)){
				prevState = ShooterStates.POSITION3;
				newState = ShooterStates.SLOWRPM;
			}else if(Hardware.operatorPad.getRawButton(Constants.SPEED_MED)){
				prevState = ShooterStates.POSITION3;
				newState = ShooterStates.MIDRPM;
			}else if(Hardware.operatorPad.getRawButton(Constants.SPEED_HIGH)){
				prevState = ShooterStates.POSITION3;
				newState = ShooterStates.FASTRPM;
			}else if(Hardware.drivePad.getRawButton(Constants.SHOOTER_STOP)){
				prevState = ShooterStates.POSITION3;
				newState = ShooterStates.STOP;
			}else if(Hardware.operatorPad.getRawButton(Constants.INTAKE_SHOOT)){
				prevState = ShooterStates.POSITION3;
				newState = ShooterStates.FWD;
			}
	break;
	
		case POSITION4:
			goal = 75;
			error = goal - angle;
			output = (Constants.SHOOTER_Kp * error) ;
			if (output > 1){
				output = 1;
			}
			
			shooterPosition.set(-1*output);
			
			//shooterReady();
			bangBang(0);	
				if(Hardware.drivePad.getRawButton(Constants.SHOOTER_Trigger) /*&& Hardware.shooterSwitch.get()*/){
					prevState = ShooterStates.POSITION4;
					newState = ShooterStates.SHOOT;
				}else if(Hardware.drivePad.getRawButton(4)){
					prevState = ShooterStates.POSITION4;
					newState = ShooterStates.LOWSHOOT;
				}else if(Hardware.operatorPad.getRawButton(Constants.INTAKE_START)){
					prevState = ShooterStates.POSITION4;
					newState = ShooterStates.INTAKE;
				}else if(Hardware.operatorPad.getRawButton(Constants.SHOOTER_STAGE1)&& /*Intake.state == IntakeStates.DOWN*/ ((Intake.state == IntakeStates.DOWN) || (Intake.state == IntakeStates.OP))){
					newState = ShooterStates.POSITION1;
				}else if(Hardware.operatorPad.getRawButton(Constants.SHOOTER_STAGE2)&& /*Intake.state == IntakeStates.DOWN*/ ((Intake.state == IntakeStates.DOWN) || (Intake.state == IntakeStates.OP))){
					newState = ShooterStates.POSITION2;
				}else if(Hardware.operatorPad.getRawButton(Constants.SHOOTER_STAGE3)&& /*Intake.state == IntakeStates.DOWN*/ ((Intake.state == IntakeStates.DOWN) || (Intake.state == IntakeStates.OP))){
					newState = ShooterStates.POSITION3;
				}else if (Hardware.operatorPad.getRawButton(Constants.SPEED_LOW)){
					prevState = ShooterStates.POSITION4;
					newState = ShooterStates.SLOWRPM;
				}else if(Hardware.operatorPad.getRawButton(Constants.SPEED_MED)){
					prevState = ShooterStates.POSITION4;
					newState = ShooterStates.MIDRPM;
				}else if (Hardware.operatorPad.getRawButton(Constants.SPEED_HIGH)){
					prevState = ShooterStates.POSITION4;
					newState = ShooterStates.FASTRPM;
				}else if(Hardware.drivePad.getRawButton(Constants.SHOOTER_STOP)){
					prevState = ShooterStates.POSITION4;
					newState = ShooterStates.STOP;
				}else if(Hardware.operatorPad.getRawButton(Constants.INTAKE_SHOOT)){
					prevState = ShooterStates.POSITION4;
					newState = ShooterStates.FWD;
				}
	break;
		//Max
		case INTAKE:
			error = goal - angle;
			output = (Constants.SHOOTER_Kp * error);
			if (output > 1){
				output = 1;
			}
			
			LeftShooterWheels.set(1);
			RightShooterWheels.set(1);
			shooterPosition.set(-1*output);
			
				if(Hardware.drivePad.getRawButton(Constants.SHOOTER_Trigger)/*&& Hardware.shooterSwitch.get()*/){
					newState = ShooterStates.SHOOT;
				}else if(Hardware.operatorPad.getRawButton(Constants.SHOOTER_STAGE1) && /*Intake.state == IntakeStates.DOWN*/ ((Intake.state == IntakeStates.DOWN) || (Intake.state == IntakeStates.OP))){
					newState = ShooterStates.POSITION1;
				}else if(Hardware.operatorPad.getRawButton(Constants.SHOOTER_STAGE2)&& /*Intake.state == IntakeStates.DOWN*/ ((Intake.state == IntakeStates.DOWN) || (Intake.state == IntakeStates.OP))){
					newState = ShooterStates.POSITION2;
				}else if(Hardware.operatorPad.getRawButton(Constants.SHOOTER_STAGE3)&& /*Intake.state == IntakeStates.DOWN*/ ((Intake.state == IntakeStates.DOWN) || (Intake.state == IntakeStates.OP))){
					newState = ShooterStates.POSITION3;
				}else if(Hardware.operatorPad.getRawButton(Constants.SHOOTER_STAGE4)&& /*Intake.state == IntakeStates.DOWN*/ ((Intake.state == IntakeStates.DOWN) || (Intake.state == IntakeStates.OP))){
					newState = ShooterStates.POSITION4;
				}else if (Hardware.operatorPad.getRawButton(Constants.SPEED_LOW)){
					newState = ShooterStates.SLOWRPM;
				}else if(Hardware.drivePad.getRawButton(Constants.SHOOTER_STOP)){
					newState = ShooterStates.STOP;
				}else if(Hardware.operatorPad.getRawButton(Constants.SPEED_HIGH)){
					newState = ShooterStates.FASTRPM;
				}else if(Hardware.operatorPad.getRawButton(Constants.SPEED_MED)){
					newState = ShooterStates.MIDRPM;
				}
	break;
	
		//add so we can shot into low goal, change to momentary
		case LOWSHOOT:
			error = goal - angle;
			output = (Constants.SHOOTER_Kp * error);
			if (output > 1){
				output = 1;
			}
			
			LeftShooterWheels.set(-1);
			RightShooterWheels.set(-1);
				
			shooterShoot.set(true);
			shooterShoot2.set(false);
			
			if(Hardware.drivePad.getRawButton(Constants.SHOOTER_Trigger)/*&& Hardware.shooterSwitch.get()*/){
				newState = ShooterStates.SHOOT;
			}else if(Hardware.operatorPad.getRawButton(Constants.SHOOTER_STAGE1) && /*Intake.state == IntakeStates.DOWN*/ ((Intake.state == IntakeStates.DOWN) || (Intake.state == IntakeStates.OP))){
				newState = ShooterStates.POSITION1;
			}else if(Hardware.operatorPad.getRawButton(Constants.SHOOTER_STAGE2)&& /*Intake.state == IntakeStates.DOWN*/ ((Intake.state == IntakeStates.DOWN) || (Intake.state == IntakeStates.OP))){
				newState = ShooterStates.POSITION2;
			}else if(Hardware.operatorPad.getRawButton(Constants.SHOOTER_STAGE3)&& /*Intake.state == IntakeStates.DOWN*/ ((Intake.state == IntakeStates.DOWN) || (Intake.state == IntakeStates.OP))){
				newState = ShooterStates.POSITION3;
			}else if(Hardware.operatorPad.getRawButton(Constants.SHOOTER_STAGE4)&& /*Intake.state == IntakeStates.DOWN*/ ((Intake.state == IntakeStates.DOWN) || (Intake.state == IntakeStates.OP))){
				newState = ShooterStates.POSITION4;
			}else if(Hardware.operatorPad.getRawButton(Constants.SPEED_LOW)){
				newState = ShooterStates.SLOWRPM;
			}else if(Hardware.operatorPad.getRawButton(Constants.SPEED_MED)){
				newState = ShooterStates.MIDRPM;
			}else if(Hardware.operatorPad.getRawButton(Constants.SPEED_HIGH)){
				newState = ShooterStates.FASTRPM;
			}else if(Hardware.drivePad.getRawButton(Constants.SHOOTER_STOP)){
				newState = ShooterStates.STOP;
			}
			
	break;
		case FWD:
			
 			LeftShooterWheels.set(-1);
 			RightShooterWheels.set(-1);
			
			
	break;
			
		case SLOWRPM:
			error = goal - angle;
			output = (Constants.SHOOTER_Kp * error);
			if (output > 1){
				output = 1;
			}
			shooterPosition.set(-1*output);
			
			bangBang(1500);
			//shooterReady();
			
			if(Hardware.drivePad.getRawButton(Constants.SHOOTER_Trigger) /*&& Hardware.shooterSwitch.get()*/){
				newState = ShooterStates.SHOOT;
			}else if(Hardware.operatorPad.getRawButton(Constants.SHOOTER_STAGE1) && /*Intake.state == IntakeStates.DOWN*/ ((Intake.state == IntakeStates.DOWN) || (Intake.state == IntakeStates.OP))){
				newState = ShooterStates.POSITION1;
			}else if(Hardware.operatorPad.getRawButton(Constants.SHOOTER_STAGE2)&& /*Intake.state == IntakeStates.DOWN*/ ((Intake.state == IntakeStates.DOWN) || (Intake.state == IntakeStates.OP))){
				newState = ShooterStates.POSITION2;
			}else if(Hardware.operatorPad.getRawButton(Constants.SHOOTER_STAGE3)&& /*Intake.state == IntakeStates.DOWN*/ ((Intake.state == IntakeStates.DOWN) || (Intake.state == IntakeStates.OP))){
				newState = ShooterStates.POSITION3;
			}else if(Hardware.operatorPad.getRawButton(Constants.SHOOTER_STAGE4)&& /*Intake.state == IntakeStates.DOWN*/ ((Intake.state == IntakeStates.DOWN) || (Intake.state == IntakeStates.OP))){
				newState = ShooterStates.POSITION4;
			}else if(Hardware.operatorPad.getRawButton(Constants.SPEED_MED)){
				newState = ShooterStates.MIDRPM;
			}else if (Hardware.operatorPad.getRawButton(Constants.SPEED_HIGH)){
				newState = ShooterStates.FASTRPM;
			}else if (Hardware.drivePad.getRawButton(Constants.SHOOTER_STOP)){
			newState = ShooterStates.STOP;
			}
			
	break;
	
		//will be old fast rpm
		case MIDRPM:
			error = goal - angle;
			output = (Constants.SHOOTER_Kp * error);
			if (output > 1){
				output = 1;
			}
			shooterPosition.set(-1*output);
			bangBang(2700);
			//shooterReady();
			
			if(Hardware.drivePad.getRawButton(Constants.SHOOTER_Trigger)){
				newState = ShooterStates.SHOOT;
			}else if(Hardware.operatorPad.getRawButton(Constants.SHOOTER_STAGE1) && /*Intake.state == IntakeStates.DOWN*/ ((Intake.state == IntakeStates.DOWN) || (Intake.state == IntakeStates.OP))){
				newState = ShooterStates.POSITION1;
			}else if(Hardware.operatorPad.getRawButton(Constants.SHOOTER_STAGE2)&& /*Intake.state == IntakeStates.DOWN*/ ((Intake.state == IntakeStates.DOWN) || (Intake.state == IntakeStates.OP))){
				newState = ShooterStates.POSITION2;
			}else if(Hardware.operatorPad.getRawButton(Constants.SHOOTER_STAGE3)&& /*Intake.state == IntakeStates.DOWN*/ ((Intake.state == IntakeStates.DOWN) || (Intake.state == IntakeStates.OP))){
				newState = ShooterStates.POSITION3;
			}else if(Hardware.operatorPad.getRawButton(Constants.SHOOTER_STAGE4)&& /*Intake.state == IntakeStates.DOWN*/ ((Intake.state == IntakeStates.DOWN) || (Intake.state == IntakeStates.OP))){
				newState = ShooterStates.POSITION4;
			}else if(Hardware.operatorPad.getRawButton(Constants.SPEED_LOW)){
				newState = ShooterStates.SLOWRPM;
			}else if(Hardware.operatorPad.getRawButton(Constants.SPEED_HIGH)){
				newState = ShooterStates.FASTRPM;
			}else if (Hardware.drivePad.getRawButton(Constants.SHOOTER_STOP)){
				newState = ShooterStates.STOP;
			}
	break;
	
		//will become are spybot shooter rpm and sweet spot
		case FASTRPM:
			error = goal - angle;
			output = (Constants.SHOOTER_Kp * error);
			if (output > 1){
				output = 1;
			}
			shooterPosition.set(-1*output);
			bangBang(2930);
			//shooterReady();
			
			if(Hardware.drivePad.getRawButton(Constants.SHOOTER_Trigger) /*&& Hardware.shooterSwitch.get()*/){
				newState = ShooterStates.SHOOT;
			}else if(Hardware.operatorPad.getRawButton(Constants.INTAKE_SHOOT)){
				newState = ShooterStates.LOWSHOOT;
			}else if(Hardware.operatorPad.getRawButton(Constants.SHOOTER_STAGE1) && /*Intake.state == IntakeStates.DOWN*/ ((Intake.state == IntakeStates.DOWN) || (Intake.state == IntakeStates.OP))){
				newState = ShooterStates.POSITION1;
			}else if(Hardware.operatorPad.getRawButton(Constants.SHOOTER_STAGE2)&& /*Intake.state == IntakeStates.DOWN*/ ((Intake.state == IntakeStates.DOWN) || (Intake.state == IntakeStates.OP))){
				newState = ShooterStates.POSITION2;
			}else if(Hardware.operatorPad.getRawButton(Constants.SHOOTER_STAGE3)&& /*Intake.state == IntakeStates.DOWN*/ ((Intake.state == IntakeStates.DOWN) || (Intake.state == IntakeStates.OP))){
				newState = ShooterStates.POSITION3;
			}else if(Hardware.operatorPad.getRawButton(Constants.SHOOTER_STAGE4)&& /*Intake.state == IntakeStates.DOWN*/ ((Intake.state == IntakeStates.DOWN) || (Intake.state == IntakeStates.OP))){
				newState = ShooterStates.POSITION4;
			}else if (Hardware.operatorPad.getRawButton(Constants.SPEED_LOW)){
				newState = ShooterStates.SLOWRPM;
			}else if(Hardware.operatorPad.getRawButton(Constants.SPEED_MED)){
				newState = ShooterStates.MIDRPM;
			}else if (Hardware.drivePad.getRawButton(Constants.SHOOTER_STOP)){
				newState = ShooterStates.STOP;
			}
	break;
		case SHOOT:
			error = goal - angle;
			output = (Constants.SHOOTER_Kp * error);
			if (output > 1){
				output = 1;
			}
			bangBang(speed_goal);
			shooterPosition.set(-1*output);
			shooterShoot.set(true);
			shooterShoot2.set(false);
						
				if(Hardware.drivePad.getRawButton(Constants.SHOOTER_CLOSE)/*!Hardware.shooterSwitch.get()*/){
					newState = ShooterStates.CLOSE;
				}else if (Hardware.drivePad.getRawButton(Constants.SHOOTER_STOP)){
					newState = ShooterStates.STOP;
				}
	break;
	
		case CLOSE:
			error = goal - angle;
			output = (Constants.SHOOTER_Kp * error);
			if (output > 1){
				output = 1;
			}
			
			bangBang(0);
			shooterShoot.set(false);
			shooterShoot2.set(true);
			newState = prevState;
			
			/*if(Hardware.drivePad.getRawButton(Constants.SHOOTER_Trigger) /*&& Hardware.shooterSwitch.get()){
				newState = ShooterStates.SHOOT;
			}else if(Hardware.operatorPad.getRawButton(Constants.INTAKE_START)){
				newState = ShooterStates.INTAKE;
			}else if(Hardware.operatorPad.getRawButton(Constants.SHOOTER_STAGE1) && Intake.state == IntakeStates.DOWN){
				newState = ShooterStates.POSITION1;
			}else if(Hardware.operatorPad.getRawButton(Constants.SHOOTER_STAGE2)&& Intake.state == IntakeStates.DOWN){
				newState = ShooterStates.POSITION2;
			}else if(Hardware.operatorPad.getRawButton(Constants.SHOOTER_STAGE3)&& Intake.state == IntakeStates.DOWN){
				newState = ShooterStates.POSITION3;
			}else if(Hardware.operatorPad.getRawButton(Constants.SHOOTER_STAGE4)&& Intake.state == IntakeStates.DOWN){
				newState = ShooterStates.POSITION4;
			}else if (Hardware.operatorPad.getRawButton(Constants.SPEED_LOW)){
				newState = ShooterStates.SLOWRPM;
			} else if (Hardware.drivePad.getRawButton(Constants.SHOOTER_STOP)){
				newState = ShooterStates.STOP;
			}else if (Hardware.operatorPad.getRawButton(Constants.SPEED_HIGH)){
				newState = ShooterStates.FASTRPM;
			}*/
	break;
			
		case STOP:
			error = goal - angle;
			output = (Constants.SHOOTER_Kp * error);
			if (output > 1){
				output = 1;
			}
			
			bangBang(0);		
			newState = prevState;
			
		/*if(Hardware.drivePad.getRawButton(Constants.SHOOTER_Trigger) /*&& Hardware.shooterSwitch.get()){
				newState = ShooterStates.SHOOT;
			}else if(Hardware.operatorPad.getRawButton(Constants.INTAKE_START)){
				newState = ShooterStates.INTAKE;
			}else if(Hardware.operatorPad.getRawButton(Constants.SHOOTER_STAGE1) && Intake.state == IntakeStates.DOWN){
				newState = ShooterStates.POSITION1;
			}else if(Hardware.operatorPad.getRawButton(Constants.SHOOTER_STAGE2)&& Intake.state == IntakeStates.DOWN){
				newState = ShooterStates.POSITION2;
			}else if(Hardware.operatorPad.getRawButton(Constants.SHOOTER_STAGE3)&& Intake.state == IntakeStates.DOWN){
				newState = ShooterStates.POSITION3;
			}else if(Hardware.operatorPad.getRawButton(Constants.SHOOTER_STAGE4)&& Intake.state == IntakeStates.DOWN){
				newState = ShooterStates.POSITION4;
			}else if(Hardware.operatorPad.getRawButton(Constants.SPEED_LOW)){
				newState = ShooterStates.SLOWRPM;
			}*/
	break;
	
		default:
			newState = ShooterStates.POSITION1;
	break;
		}
		
		if(newState != state) {
			state = newState;
		}
	}
}