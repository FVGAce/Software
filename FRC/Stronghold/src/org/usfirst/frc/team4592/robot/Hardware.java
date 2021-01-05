package org.usfirst.frc.team4592.robot;

//import org.usfirst.frc.team4592.robot.Util.doubleSolenoid;

import edu.wpi.first.wpilibj.*;

//This file holds all the decelerations of hardware for the robot

public class Hardware {
	//Drivetrain Motors
	public static final VictorSP rightMotor = 
			new VictorSP(Constants.RIGHT_MOTOR_PWM);
	public static final VictorSP leftMotor = 
			new VictorSP(Constants.LEFT_MOTOR_PWM);
	
	//Intake Motors
	public static final Talon intakeMotor = 
			new Talon(Constants.INTAKE_MOTOR_PWM);
	public static final Talon intakeMotor2 =
			new Talon(Constants.INTAKE_MOTOR_PWM2);
	public static final VictorSP intakePosition = 
			new VictorSP(Constants.INTAKE_POSITION_PWM);
	public static final CANTalon intakeController = 
			new CANTalon(0);
	
	//Shooter Motors
	public static final VictorSP LeftShooterWheels = 
			new VictorSP(Constants.LEFT_SHOOTER_WHEEL_PWM);
	public static final VictorSP RightShooterWheels = 
			new VictorSP(Constants.RIGHT_SHOOTER_WHEEL_PWM);
	public static final CANTalon shooterPosition =
			new CANTalon (Constants.SHOOTER_MOTOR_CAN);
	
	//Vision
	public static final AnalogInput PixyCam = 
			new AnalogInput(Constants.Camera);
	public static final Solenoid Led1 =
			new Solenoid(Constants.LED1);
	public static final Solenoid Led2 =
			new Solenoid(Constants.LED2);
	public static final Solenoid Led3 = 
			new Solenoid(Constants.LED3);
	
	//Digital Inputs
	public static final DigitalInput shooterSwitch = 
			new DigitalInput(Constants.SHOOTER_LIMIT_SWITCH);
	public static final Encoder driveEncoder = 
			new Encoder(Constants.WHEEL_ENCODER1, Constants.WHEEL_ENCODER2, 
						false, Encoder.EncodingType.k4X);
	public static final Encoder leftDriveEncoder =
			new Encoder (Constants.WHEEL_ENCODER3, Constants.WHEEl_ENCODER4,
						 false, Encoder.EncodingType.k4X);
	public static final Counter hallEffectShooterLeft = 
			new Counter(Constants.SHOOTER_HALL_EFFECT_LEFT);
	public static final Counter hallEffectShooterRight = 
			new Counter(Constants.SHOOTER_HALL_EFFECT_RIGHT);
	public static final DigitalInput intakeSwitch =
			new DigitalInput (Constants.INTAKE_SWITCH);
	public static final DigitalInput Shooter_zero =
			new DigitalInput (Constants.SHOOTER_ZERO);
	
	// Pnuematics
	public static final Solenoid shooterShoot = 
			new Solenoid(Constants.SHOOTER_SHOOT_PISTON_PUSH);
	public static final Solenoid shooterShoot2 =
			new Solenoid (Constants.SHOOTER_SHOOT_PISTON_PULL);
	
	//Sticks
	public static final Joystick drivePad = 
			new Joystick(Constants.DRIVE_USB_PORT);
	public static final Joystick operatorPad = 
			new Joystick(Constants.OPERATOR_USB_PORT);
	//Sensors
	public static final ADXRS450_Gyro SpartanBoard=
			new ADXRS450_Gyro();
	
}