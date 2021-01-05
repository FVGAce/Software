package org.usfirst.frc.team4592.robot;

/* This file is used to hold constant robot values.
 * DO NOT DEFINE MOTOR PWM OR PID VALUES OUTSIDE OF THIS FILE!!
 */

public class Constants {
	//Drivetrain CAN Values
		public static final int RIGHT_MOTOR_MASTER_CAN = 11;
		public static final int RIGHT_MOTOR_SLAVE_CAN = 10;
		public static final int LEFT_MOTOR_MASTER_CAN = 5;
		public static final int LEFT_MOTOR_SLAVE_CAN = 6;
		
	//Drivetrain Pnuematics
		public static final int SHIFTER_OPEN = 1;
		public static final int SHIFTER_CLOSE = 0;
	
	//Gear Mech PWM Values
		public static final int GEAR_INTAKE_MOTOR = 6;
		
	//Gear Mech Pnuematics
		public static final int FLIPPER_PISTON_OPEN = 2;
		public static final int FLIPPER_PISTON_CLOSE = 3;
		public static final int GEAR_LOCK_OPEN = 5;
		public static final int GEAR_LOCK_CLOSE = 4;
			
	//Climber PWM Values
		public static final int RIGHT_CLIMBER_MOTOR_PWM = 8;	
		public static final int LEFT_CLIMBER_MOTOR_PWM = 7;		
	
	//Stick USB Values
		public static final int DRIVE_USB_PORT = 0;
		public static final int OPERATOR_USB_PORT = 1;
	
	//Driver Buttons
		//Gear Delivery Bumpers
			public static final int GEARINTAKE_INTAKE = 3;
			public static final int GEARINTAKE_REVERSE = 3;
		//Gear Delivery Buttons
			public static final int GEARLOCK = 2;
			public static final int FLIPPERPOSITION_INTAKE = 5;
			public static final int FLIPPERPOSITION_PLACE = 6;
		//Drivetrain Buttons
			public static final int DRIVETRAIN_LOWGEAR = 2;
			public static final int DRIVETRAIN_HIGHGEAR = 1;
		//Climber Buttons
			public static final int CLIMB = 8;
			public static final int HOOK_ON_TO_ROPE = 4;
	
	//Drivetrain PI Gains
		public static final double Drive_ANGLE_Kp = 0.06;
		public static final double Drive_ANGLE_Ki = 0.002;
		//needs to be fixed
		public static final double Average_Ticks_Per_Meter = 3.110508;
		public static final double Drive_Kf = 0;
		public static final double Drive_Kp = 0.7;
		public static final double Drive_Ki = 0;
		public static final double Drive_Kd = 3.5;
}