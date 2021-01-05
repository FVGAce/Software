package org.usfirst.frc.team4592.robot.AutonomousModes;

import org.usfirst.frc.team4592.robot.Lib.Loopable;
import org.usfirst.frc.team4592.robot.Subsystems.Intake;
import org.usfirst.frc.team4592.robot.Subsystems.Shooter;
import org.usfirst.frc.team4592.robot.Subsystems.Intake.IntakeStates;
import org.usfirst.frc.team4592.robot.Subsystems.Shooter.ShooterStates;
import org.usfirst.frc.team4592.robot.*;

public class TestAuto implements Loopable {
	public static int i = 0;
	public static double Drive_AngleGoal = 0;
	public static double angleOutput = 0;
	public static double angleError;
	static TestAutoStates autoSelected = TestAutoStates.CHEZ;
	
	public TestAuto() {
		
	}
	
	public static void autoMode(){
		if(SmartDashBoard.autoSelected == 1){
			autoSelected = TestAutoStates.NOTHING;
		}else if(SmartDashBoard.autoSelected == 2){
			autoSelected = TestAutoStates.SPYBOT; 
		}else if (SmartDashBoard.autoSelected == 3){
			autoSelected = TestAutoStates.OBSTACLE;
		}else if(SmartDashBoard.autoSelected == 4){
			autoSelected = TestAutoStates.LOWBAR;
		}else if (SmartDashBoard.autoSelected == 5){
			autoSelected = TestAutoStates.TURN;
		}else if(SmartDashBoard.autoSelected == 6){
			autoSelected = TestAutoStates.OBSTACLE180;
		}
	}
	
	public enum TestAutoStates{
		SPYBOT, CHEZ, LOWBAR, TURN, OBSTACLE, NOTHING, OBSTACLE180;
	}

	@Override
	public void update() {
		autoMode();
		
   switch(autoSelected){
		case SPYBOT:
			i++;
			if (i <= 9){
			Intake.state = IntakeStates.UP;
			}
			if (i > 10){
			Intake.state = IntakeStates.DOWN;
			}
			Shooter.state = ShooterStates.POSITION1;
			if (i < 250){
			Shooter.state = ShooterStates.FASTRPM;
			}
			
			if(i >= 250 && i <= 320) {
				Shooter.state = ShooterStates.SHOOT;
			} else if (i >= 321){
				Shooter.prevState = ShooterStates.POSITION1;
				Shooter.state = ShooterStates.CLOSE;
			}
			
	break;
	
		//this case doesn't exist, it is just a programming joke (We were sleepy)
		case CHEZ:
			i++;
			if (i<=400){
				Robot.myRobot.drive(-0.5, 0);
			} else if (i>=401){
				Robot.myRobot.drive(0, 0);	
			}
	break;
		
		case LOWBAR:
			  Drive_AngleGoal = 0;
			  angleError = (Drive_AngleGoal - Hardware.SpartanBoard.getAngle());
			  angleOutput = (Constants.Drive_Kp * angleError);
			  
			  i++;
			   if (i <= 99){
			   Intake.state = IntakeStates.UP; //Make sure the intake is up
			   Shooter.state = ShooterStates.POSITION1;
			   Robot.myRobot.drive(-0.25, -angleOutput);
			   }
			   
			   if (i >= 100 && i<=149){
			   Intake.state = IntakeStates.DOWN;
			   Shooter.state = ShooterStates.POSITION1; //Keep shooter upeeeeee+
			   Robot.myRobot.drive(-0.5, -angleOutput);
			 
			   }
			   if (i >= 150 && i<=249){
				   Shooter.state = ShooterStates.POSITION4; //Drop the shooter
				   Robot.myRobot.drive(-0.5, -angleOutput);   
			   }
			   if (i >= 250 && i <= 799){
			   Robot.myRobot.drive(-0.8, -angleOutput);
			   }
			   else if (i >= 800){ 
			   Robot.myRobot.drive(0, 0);
			   }
	break;	
		case OBSTACLE:
			  Drive_AngleGoal = 0;
			  angleError = (Drive_AngleGoal - Hardware.SpartanBoard.getAngle());
			  angleOutput = (Constants.Drive_Kp * angleError);
			  
			  i++;
			
			   if (i <= 749){
			   Intake.state = IntakeStates.UP;
			   Shooter.state = ShooterStates.POSITION1;
			   Robot.myRobot.drive(.9, angleOutput);
			   }
			   else if (i >= 750){ 
			   Robot.myRobot.drive(0, 0);
			   }
	break;
		case TURN:
			i++;
			  Drive_AngleGoal = 50;
			  angleError = (Drive_AngleGoal - Hardware.SpartanBoard.getAngle());
			  angleOutput = (Constants.Drive_Kp * angleError);
			  if (angleOutput > 1){
				  angleOutput = 1;
			  }
			  
			  
			   if (i <= 499){
			   Intake.state = IntakeStates.UP; //Make sure the intake is up
			   Shooter.state = ShooterStates.POSITION1;
			   Robot.myRobot.arcadeDrive(0, -angleOutput);
			   }
			   
			   if ( i >= 500){
			   Robot.myRobot.drive(0, 0);
			   }
	break;
	
		case NOTHING:
		
	break;
	
		case OBSTACLE180:
			Drive_AngleGoal = 0;
			  angleError = (Drive_AngleGoal - Hardware.SpartanBoard.getAngle());
			  angleOutput = (Constants.Drive_Kp * angleError);
			  
			  i++;
			
			   if (i <= 749){
			   Intake.state = IntakeStates.UP;
			   Shooter.state = ShooterStates.POSITION1;
			   Robot.myRobot.drive(.8, angleOutput);
			   }else if (i >= 750 && i < 950){
			   Robot.myRobot.drive(0, 0);
			   }else if(i >= 950 && i < 1151){
			   Robot.myRobot.drive(-.25, -angleOutput);
			   }else if(i >= 1151 && i < 1351){
			   Robot.myRobot.drive(-.5, -angleOutput);
			   }else if(i >= 1351 && i < 1751){
			   Robot.myRobot.drive(-.8, -angleOutput);
			   }
	break;
	
		default:
			autoSelected = TestAutoStates.NOTHING;	
	break;
		}	
	}
}
