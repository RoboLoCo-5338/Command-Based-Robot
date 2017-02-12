package org.usfirst.frc.team5338.robot.subsystems;

import org.usfirst.frc.team5338.robot.commands.TankDriveWithJoysticks;

import com.ctre.CANTalon;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.RobotDrive;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

import org.usfirst.frc.team5338.robot.*;

/**
 * The DriveTrain subsystem incorporates the sensors and actuators attached to
 * the robots chassis. These include four drive motors.
 */
public class DriveTrain extends Subsystem
{
	private final CANTalon DRIVEL1 = new CANTalon(4);
    private final CANTalon DRIVEL2 = new CANTalon(3);
    private final CANTalon DRIVER1 = new CANTalon(2);
    private final CANTalon DRIVER2 = new CANTalon(1);
    
	public final RobotDrive DRIVE = new RobotDrive(DRIVEL1, DRIVEL2, DRIVER1, DRIVER2);
	
	private double throttle = 0.5;

	public DriveTrain()
	{
		super();
	}
	/**
	 * When no other command is running let the operator drive around using the
	 * twin joysticks.
	 */
	@Override
	public void initDefaultCommand()
	{
		setDefaultCommand(new TankDriveWithJoysticks());
	}
	/**
	 * Arcade style driving for the DriveTrain.
	 * 
	 * @param left
	 *            Speed in range [-1,1]
	 * @param right
	 *            Speed in range [-1,1]
	 */
	public void drive(double forward, double rotation)
	{
		throttle = 0.0;
		DRIVE.tankDrive(throttle * forward, throttle * rotation, false);
	}
	/**
	 * @param joy
	 *            The XBOX style joystick to use to drive arcade style.
	 */
	public void drive(Joystick joy1)
	{
		throttle = (1-(joy1.getRawAxis(2)))/2;
		if(joy1.getRawButton(3))
		{
			int x = (int)Robot.outputSnapshot.x;
			if(Math.abs(x)<10)
				;
			else
				DRIVE.arcadeDrive(0, -1* throttle * x/1280.0 + (x>0?-.2:.2), false);
		}
		else
			DRIVE.arcadeDrive(throttle * joystickDeadZone(joy1.getRawAxis(1)), -throttle * joystickDeadZone(joy1.getRawAxis(0)), false);
	}
	public double joystickDeadZone(double value)
	{
		if (value > 0.05 || value < -0.05)
		{
		 return (value - 0.05)/0.95;
		}
		else if (value < -0.05)
		{
		 return (value + 0.05)/0.95;
		}
		return 0.0;
	}
	public double getThrottle()
	{
		return throttle;
	}
}
