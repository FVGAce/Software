package org.usfirst.frc.team4592.robot;



/* This file is used to hold constant robot values.
 * DO NOT DEFINE MOTOR PWM OR PID VALUES OUTSIDE OF THIS FILE!!
 */

public class Constants {
	//Drivetrain PWM Values
	public static final int RIGHT_MOTOR_PWM = 0; // 0 is equal to 1 
	public static final int LEFT_MOTOR_PWM = 4;
		
	//Intake PWM Values
	public static final int INTAKE_MOTOR_PWM = 2; // 0 is equal to 1
	public static final int INTAKE_MOTOR_PWM2 = 3;
	public static final int INTAKE_POSITION_PWM = 6;
		
	//Intake CAN Value
	public static final int INTAKE_MOTOR_CAN = 0;
	public static final int SHOOTER_MOTOR_CAN = 1;
	
	//Shooter PWM Values
	public static final int LEFT_SHOOTER_WHEEL_PWM = 7; // 0 is equal to 1
	public static final int RIGHT_SHOOTER_WHEEL_PWM = 8;
	
	//Vision
	public static final int Camera = 1;
	public static final int LED1 = 4;
	public static final int LED2 = 5;
	public static final int LED3 = 6;
	public static final double CAMERA_GOAL = 258.901;
	public static final double CAMERA_FOCAL = 416.938111;
		
	//Digital Inputs
	public static final int SHOOTER_LIMIT_SWITCH = 6;
	public static final int WHEEL_ENCODER1 = 10;
	public static final int WHEEL_ENCODER2 = 11;
	public static final int WHEEL_ENCODER3 = 12;
	public static final int WHEEl_ENCODER4 = 13;
	public static final int SHOOTER_HALL_EFFECT_LEFT = 4;
	public static final int SHOOTER_HALL_EFFECT_RIGHT = 3;
	public static final int INTAKE_SWITCH = 5;
	public static final int SHOOTER_ZERO = 0;
	
	//Pnuematic Values
	public static final int SHOOTER_SHOOT_PISTON_PUSH = 2; // 0 is equal to 1
	public static final int SHOOTER_SHOOT_PISTON_PULL = 3;
	 
		
	//Stick USB Values
	public static final int DRIVE_USB_PORT = 0; // 0 is equal to 1 
	public static final int OPERATOR_USB_PORT = 1;
		 
	//Operator Buttons
	public static final int INTAKE_UP = 16;
	public static final int INTAKE_DOWN = 19;
	public static final int INTAKE_OP = 13;
	public static final int INTAKE_HIGHER = 17;
	public static final int INTAKE_START = 18;
	public static final int INTAKE_SHOOT = 3;
	public static final int SHOOTER_STAGE1 = 1;
	public static final int SHOOTER_STAGE2 = 4;
	public static final int SHOOTER_STAGE3 = 5;
	public static final int SHOOTER_STAGE4 = 8;
	public static final int SPEED_LOW = 14;
	public static final int SPEED_MED = 2;
	public static final int SPEED_HIGH = 6;
	public static final int OBSTACLE_PISTON_OUT = 12;
	public static final int OBSTACLE_PISTON_IN = 20;
	
	
	//Driver Buttons
	public static final int SHOOTER_Trigger = 6;
	public static final int SHOOTER_STOP = 5;
	public static final int SHOOTER_CLOSE = 1;
	public static final int VISION_BUTTON = 8;
	
	//not being used
	public static final int CLIMBER = 10;
	
		
	// PI GAINS
	public static final double INTAKE_Kp = 0.0125;
	//public static double INTAKE_Kp = 0.001; 
	public static final double INTAKE_Ki = 0.003;
	//public static double INTAKE_Ki = 0;
	public static final double Drive_Kp = 0.0675;
	public static final double Drive_Ki = 0.002;
    public static final double SHOOTER_Kp = 0.0375;
    public static final double SHOOTER_RPM_KP = 0.07;
    public static final double SHOOTER_Ki = 0.005;
    public static final double VISION_INITIAL_KP = -.0028;
    public static final double VISION_GREATER_KP = -0.0155;
    public static final double VISION_GREATEST_KP = -0.035;
    public static final double VISION_kI = -0.00225;
	 
	 //RPM
	 public static final double TICKS_PER_REVOULTION = 2.0;
		
	//Sensor Values
	 
}
