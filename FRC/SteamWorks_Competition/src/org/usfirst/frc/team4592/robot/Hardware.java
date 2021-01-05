package org.usfirst.frc.team4592.robot;

import org.usfirst.frc.team4592.robot.Subsystems.Drivetrain.DrivetrainStates;
import org.usfirst.frc.team4592.robot.Util.doubleSolenoid;

import com.ctre.CANTalon;

import edu.wpi.first.wpilibj.ADXRS450_Gyro;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.VictorSP;

//This file holds all initialization for hardware and buttons

public class Hardware {
	//Drivetrain Motors
	public static final CANTalon rightMasterMotor =
			new CANTalon(Constants.RIGHT_MOTOR_MASTER_CAN);
	public static final CANTalon rightSlaveMotor =
			new CANTalon(Constants.RIGHT_MOTOR_SLAVE_CAN);
	public static final CANTalon leftMasterMotor =
			new CANTalon(Constants.LEFT_MOTOR_MASTER_CAN);
	public static final CANTalon leftSlaveMotor =
			new CANTalon(Constants.LEFT_MOTOR_SLAVE_CAN);
	
	//Drivetrain Pnuematics
	public static final doubleSolenoid shifter =
			new doubleSolenoid(Constants.SHIFTER_OPEN, Constants.SHIFTER_CLOSE);
	
	//Gear Mech Pnuematics
	public static final doubleSolenoid flipperPosition =
			new doubleSolenoid(Constants.FLIPPER_PISTON_OPEN, Constants.FLIPPER_PISTON_CLOSE);
	public static final doubleSolenoid gearLock =
			new doubleSolenoid(Constants.GEAR_LOCK_OPEN, Constants.GEAR_LOCK_CLOSE);
	
	//Gear Mech Motors
	public static final VictorSP gearIntakeMotor =
			new VictorSP(Constants.GEAR_INTAKE_MOTOR);
	//Climber
	public static final VictorSP rightClimberMotor =
			new VictorSP(Constants.RIGHT_CLIMBER_MOTOR_PWM);
	public static final VictorSP leftClimberMotor =
			new VictorSP(Constants.LEFT_CLIMBER_MOTOR_PWM);
	
	//Gyro
	public static final ADXRS450_Gyro SpartanBoard =
			new ADXRS450_Gyro();
	
	//Sticks
	public static final Joystick driverPad = 
			new Joystick(Constants.DRIVE_USB_PORT);
	public static final Joystick operatorPad = 
			new Joystick(Constants.OPERATOR_USB_PORT);
}