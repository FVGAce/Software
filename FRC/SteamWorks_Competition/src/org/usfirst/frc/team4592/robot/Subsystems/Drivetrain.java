package org.usfirst.frc.team4592.robot.Subsystems;

import org.usfirst.frc.team4592.robot.Constants;
import org.usfirst.frc.team4592.robot.Hardware;
import org.usfirst.frc.team4592.robot.Lib.SubsystemFramework;
import org.usfirst.frc.team4592.robot.Util.PID;
import org.usfirst.frc.team4592.robot.Util.PixyCam;
import org.usfirst.frc.team4592.robot.Util.doubleSolenoid;

import com.ctre.CANTalon;
import com.ctre.CANTalon.FeedbackDevice;
import com.ctre.CANTalon.TalonControlMode;

import edu.wpi.first.wpilibj.ADXRS450_Gyro;
import edu.wpi.first.wpilibj.RobotDrive;
import edu.wpi.first.wpilibj.VictorSP;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

@SuppressWarnings("unused")
public class Drivetrain extends SubsystemFramework{
	//Hardware
	private RobotDrive myRobot;
	private CANTalon rightMasterMotor;
	private CANTalon rightSlaveMotor;
	private CANTalon leftMasterMotor;
	private CANTalon leftSlaveMotor;
	private doubleSolenoid shifter;
	private ADXRS450_Gyro SpartanBoard;
	private double Average_Ticks_Per_Meter;
	
	//PID
	private PID Drive_Angle_PI;
	private PID Drive_PI;
	private double Drive_F = 0;
	private double Drive_P = 0;
	private double Drive_I = 0;
	private double Drive_D = 0;
	
	//Constants
	private double goal_Ticks;
	private double goal_Angle = 0;
	private double goal_Ticks_Error;
	private double goal_Angle_Error;
	
	//Drivetrain State
	private DrivetrainStates state = DrivetrainStates.LowGear;
	
	//VictorSP and Talon SRX Drivetrain Constructor
	//No PID
	public Drivetrain(VictorSP rightMotor, CANTalon rightMasterMotor,
					VictorSP leftMotor, CANTalon leftMasterMotor, doubleSolenoid shifter,
					ADXRS450_Gyro SpartanBoard){
		//Setup Drivetrain
		myRobot = new RobotDrive(leftMotor, leftMasterMotor, rightMotor, rightMasterMotor);
		
		//Talon SRX
		this.rightMasterMotor = rightMasterMotor;
		this.leftMasterMotor = leftMasterMotor;
		
		//Shifter Pistons
		this.shifter = shifter;
		
		//Spartan Board(GYRO)
		this.SpartanBoard = SpartanBoard;
	}
	
	//Talon SRX Drivetrain Constructor
	//NO PID
	public Drivetrain(CANTalon rightMasterMotor, CANTalon rightSlaveMotor,
					CANTalon leftMasterMotor, CANTalon leftSlaveMotor, doubleSolenoid shifter,
					ADXRS450_Gyro SpartanBoard){
		//Drivetrain
		myRobot = new RobotDrive(leftMasterMotor, rightMasterMotor);
		
		//Talon SRX
		this.rightMasterMotor = rightMasterMotor;
		this.rightSlaveMotor = rightSlaveMotor;
		this.leftMasterMotor = leftMasterMotor;
		this.leftSlaveMotor = leftSlaveMotor;
		
		//Shifter Piston
		this.shifter = shifter;
		
		//Spartan Board(GYRO)
		this.SpartanBoard = SpartanBoard;
	}
	
	//Talon SRX Drivtrain Constructor
	//PID
	public Drivetrain(CANTalon rightMasterMotor, CANTalon rightSlaveMotor,
				CANTalon leftMasterMotor, CANTalon leftSlaveMotor, doubleSolenoid shifter, 
				ADXRS450_Gyro SpartanBoard,	double Average_RPM_Per_Meter, 
				double Drive_Angle_Kp, double Drive_Angle_Ki, double Drive_Kf, double Drive_Kp, double Drive_Ki, double Drive_Kd){
		//Drivetrain
		myRobot = new RobotDrive(leftMasterMotor, rightMasterMotor);
		
		//Talon SRX
		this.rightMasterMotor = rightMasterMotor;
		this.rightSlaveMotor = rightSlaveMotor;
		this.leftMasterMotor = leftMasterMotor;
		this.leftSlaveMotor = leftSlaveMotor;
		
		//Shifter
		this.shifter = shifter;
		
		//Spartan Board (GYRO)
		this.SpartanBoard = SpartanBoard;
		
		//Constants
		this.Average_Ticks_Per_Meter = Average_RPM_Per_Meter; 
		this.Drive_F = Drive_Kf;
		this.Drive_P = Drive_Kp;
		this.Drive_I = Drive_Ki;
		this.Drive_D = Drive_Kd;
		
		//Setup PID
		this.Drive_Angle_PI = new PID(Drive_Angle_Kp, Drive_Angle_Ki);
	}
	
	/*public Drivetrain(CANTalon rightMasterMotor, CANTalon rightSlaveMotor,
			CANTalon leftMasterMotor, CANTalon leftSlaveMotor, doubleSolenoid shifter, 
			ADXRS450_Gyro SpartanBoard,	double Average_Ticks_Per_Meter, 
			double Drive_Angle_Kp, double Drive_Angle_Ki, double Drive_Kp, double Drive_Ki){
		//Drivetrain
		myRobot = new RobotDrive(leftMasterMotor, rightMasterMotor);
				
		//Talon SRX
		this.rightMasterMotor = rightMasterMotor;
		this.rightSlaveMotor = rightSlaveMotor;
		this.leftMasterMotor = leftMasterMotor;
		this.leftSlaveMotor = leftSlaveMotor;
				
		//Shifter
		this.shifter = shifter;
				
		//Spartan Board (GYRO)
		this.SpartanBoard = SpartanBoard;
				
		//Constants
		this.Average_Ticks_Per_Meter = Average_Ticks_Per_Meter;
		
		//Setup PID
		this.Drive_Angle_PI = new PID(Drive_Angle_Kp, Drive_Angle_Ki);
		this.Drive_PI = new PID(Drive_Kp, Drive_Ki);
	}*/
	
	//Drivetrain States
	//Low Gear State Puts Robot In Lowest Speed Best For Pushing
	//High Gear State Puts Robot In Highest Speed Best For Long Distance Travel
	public enum DrivetrainStates{
		LowGear, HighGear;
	}
	
	//Method Is Called By Auto Modes To Tell The Robot How Far To Drive
	public void autoDrive(double amtToDrive){
		shifter.close();
		
		goal_Ticks = (amtToDrive * Average_Ticks_Per_Meter);
		
		rightMasterMotor.set(goal_Ticks);
		leftMasterMotor.set(-1*goal_Ticks);
	}
	
	/*public void autoDriveStraight(double amtToDrive){
		shifter.close();
		
		goal_Ticks = (amtToDrive * Average_Ticks_Per_Meter);
		goal_Ticks_Error = (goal_Ticks - getPosition());
		goal_Angle_Error
	}*/
	
	//Methods Are Called By Auto Modes To Tell The Robot To Turn To A Certain Degree
	public void autoTurn(int wantedDegree){		
		goal_Angle_Error = wantedDegree + (SpartanBoard.getAngle());
		
		System.out.println(goal_Angle_Error);
		
		myRobot.arcadeDrive(0, Drive_Angle_PI.getOutputP(goal_Angle_Error));
	}
	
	public void autoTurn(double wantedDegree){		
		goal_Angle_Error = wantedDegree + SpartanBoard.getAngle();
		
		System.out.println(goal_Angle_Error);
		
		myRobot.arcadeDrive(0, Drive_Angle_PI.getOutputP(goal_Angle_Error));
	}
	
	public double getPosition(){
		return ((-1*rightMasterMotor.getPosition() + leftMasterMotor.getPosition()) / 2);
	}
	
	//Get Motor Positions
	public double getRightPosition(){
		return rightMasterMotor.getPosition();
	}
	
	public double getLeftPosition(){
		return leftMasterMotor.getPosition();
	}
	
	//Get Goal Constants
	public double get_GoalTicks(){
		return goal_Ticks;
	}
	
	public double get_GoalAngle(){
		return goal_Angle_Error;
	}
	
	public double get_Angle(){
		return SpartanBoard.getAngle();
	}
	
	//Teleop Control
	@Override
	public void update(){
		DrivetrainStates newState = state;
		
		switch(state){
			case LowGear:
				//Shifts Piston To Low Gear Position
				shifter.close();
				
				//Joystick Control
				myRobot.arcadeDrive(Hardware.driverPad.getRawAxis(1)*-1, (Hardware.driverPad.getRawAxis(4))*-1, false);
				
				//Checks For Button Press To Switch To High Gear State
				if(Hardware.driverPad.getRawButton(Constants.DRIVETRAIN_HIGHGEAR)){
					newState = DrivetrainStates.HighGear;
				}
				break;
	
			case HighGear:
				//Shifts Piston To High Gear Position
				shifter.open();
				
				//Joystick Control
				myRobot.arcadeDrive(Hardware.driverPad.getRawAxis(1)*-1, (Hardware.driverPad.getRawAxis(4))*-1, false);
				
				//Checks For Button Press To Switch To Low Gear State
				if(Hardware.driverPad.getRawButton(Constants.DRIVETRAIN_LOWGEAR)){
					newState = DrivetrainStates.LowGear;
				}
				break;
	
			default:
				newState = DrivetrainStates.LowGear;
				break;
		}
		
		if(newState != state){
			state = newState;
		}
		
		//Calls Values That Need To Be Updated Periodically
		outputToSmartDashboard();
	}
	
	//Output Values To SmartDashboard
	@Override
	public void outputToSmartDashboard(){
		//Robot Angle
		SmartDashboard.putNumber("Angle", SpartanBoard.getAngle());
		
		//Robot Position
		SmartDashboard.putNumber("Right Position", rightMasterMotor.getPosition());
		SmartDashboard.putNumber("Left Position", leftMasterMotor.getPosition());
		System.out.println("Right Position: " + rightMasterMotor.getPosition());
		System.out.println("Left Position: " + leftMasterMotor.getPosition());
	}
	
	//Reset Spartan Board 
	//When Current Robot Angle Is
	//Outside (-360, 360)
	public void resetSpartanBoard(){
		if(SpartanBoard.getAngle() >= 360){
			SpartanBoard.reset();
		}else if(SpartanBoard.getAngle() <= -360){
			SpartanBoard.reset();
		}
	}
	
	//Setup Motors For Position Control
	public void autoSetupMotors(){
		rightMasterMotor.changeControlMode(TalonControlMode.Position);
		leftMasterMotor.changeControlMode(TalonControlMode.Position);
	}
	
	//Setup Motros For RobotDrive Control
	public void teleopSetupMotors(){
		rightMasterMotor.changeControlMode(TalonControlMode.PercentVbus);
		leftMasterMotor.changeControlMode(TalonControlMode.PercentVbus);
	}
	
	public void teleopMotorsFull(){
		//Set Right Motors To 90%
				rightMasterMotor.configNominalOutputVoltage(+0f, -0f);
				rightMasterMotor.configPeakOutputVoltage(+12f, -12f);
				
				//Set Left Motors To 90%
				leftMasterMotor.configNominalOutputVoltage(+0f, -0f);
				leftMasterMotor.configPeakOutputVoltage(+12f, -12f);
	}
	
	//Set Encoders Location To Zero
	public void zeroEncoders(){
		rightMasterMotor.setEncPosition(0);
		leftMasterMotor.setEncPosition(0);
	}
	
	//Set Spartan Board To Zero
	public void zeroSpartanBoard(){
		SpartanBoard.reset();
	}
	
	//Setup Talon SRX To 90% Power
	public void auto_90_power(){
		//Set Right Motors To 90%
		rightMasterMotor.configNominalOutputVoltage(+0f, -0f);
		rightMasterMotor.configPeakOutputVoltage(+10.8f, -10.8f);
		
		//Set Left Motors To 90%
		leftMasterMotor.configNominalOutputVoltage(+0f, -0f);
		leftMasterMotor.configPeakOutputVoltage(+10.8f, -10.8f);
	}

	//Setup Sensors
	@Override
	public void setupSensors() {
		//Reset Spartan Board
		SpartanBoard.reset();
		
		//Setup Master Encoders
		rightMasterMotor.setEncPosition(0);
		leftMasterMotor.setEncPosition(0);
		
		rightMasterMotor.setFeedbackDevice(FeedbackDevice.CtreMagEncoder_Relative);
		leftMasterMotor.setFeedbackDevice(FeedbackDevice.CtreMagEncoder_Relative);
		
		rightMasterMotor.reverseSensor(true);
		leftMasterMotor.reverseSensor(true);
		
		//Setup Master Slave relationship		
		rightSlaveMotor.changeControlMode(CANTalon.TalonControlMode.Follower);
		rightSlaveMotor.set(rightMasterMotor.getDeviceID());
		leftSlaveMotor.changeControlMode(CANTalon.TalonControlMode.Follower);
		leftSlaveMotor.set(leftMasterMotor.getDeviceID());
		
		//Setup Right Power To 50% Power
		rightMasterMotor.configNominalOutputVoltage(+0f, -0f);
		rightMasterMotor.configPeakOutputVoltage(+6f, -6f);
		
		//Setup Talon SRX PID
		rightMasterMotor.setAllowableClosedLoopErr(0);
		rightMasterMotor.setProfile(0);
		rightMasterMotor.setF(Drive_F);
		rightMasterMotor.setP(Drive_P);
		rightMasterMotor.setI(Drive_I);
		rightMasterMotor.setD(Drive_D);
		
		//Setup Left Power To 50% Power
		leftMasterMotor.configNominalOutputVoltage(+0f, -0f);
		leftMasterMotor.configPeakOutputVoltage(+6f, -6f);
		
		//Setup Talon SRX PID
		leftMasterMotor.setAllowableClosedLoopErr(0);
		leftMasterMotor.setProfile(0);
		leftMasterMotor.setF(Drive_F);
		leftMasterMotor.setP(Drive_P);
		leftMasterMotor.setI(Drive_I);
		leftMasterMotor.setD(Drive_D);
	}
}