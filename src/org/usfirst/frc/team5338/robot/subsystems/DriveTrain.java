package org.usfirst.frc.team5338.robot.subsystems;

import org.usfirst.frc.team5338.robot.Robot;
import org.usfirst.frc.team5338.robot.commands.ArcadeDriveWithJoysticks;

import com.ctre.CANTalon;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.Relay;
import edu.wpi.first.wpilibj.RobotDrive;
import edu.wpi.first.wpilibj.command.Subsystem;

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
		setDefaultCommand(new ArcadeDriveWithJoysticks());
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
		DRIVE.arcadeDrive(throttle * forward, -throttle * rotation, true);
	}
	/**
	 * @param joy
	 *            The XBOX style joystick to use to drive arcade style.
	 */
	public void drive(Joystick joy)
	{
		if(joy.getRawButton(7) && joy.getRawButton(8))
		{
			Robot.jetsonReset.set(Relay.Value.kOn);
		}
		if(!(joy.getRawButton(7) || joy.getRawButton(8)))
		{
			Robot.jetsonReset.set(Relay.Value.kOff);
		}
		throttle = (1 - (joy.getRawAxis(3))) / 2;
		double forward = throttle * -joystickDeadZone(joy.getRawAxis(1));
		double rotation = 0.75 * throttle * -joystickDeadZone(joy.getRawAxis(0)) + 0.25 * throttle * -joystickDeadZone(joy.getRawAxis(2));
	    DRIVE.arcadeDrive(forward, rotation, true);
	}
	public double joystickDeadZone(double value)
	{
		if (value > 0.01 || value < -0.01)
		{
		 return (value - 0.01)/0.99;
		}
		else if (value < -0.01)
		{
		 return (value + 0.01)/0.99;
		}
		return 0.0;
	}
	public double getThrottle()
	{
		return throttle;
	}
}
