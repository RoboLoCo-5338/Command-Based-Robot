package org.usfirst.frc.team5338.robot.subsystems;

import org.usfirst.frc.team5338.robot.Robot;
import org.usfirst.frc.team5338.robot.commands.ArcadeDriveWithJoysticks;
import org.usfirst.frc.team5338.robot.commands.HandleBalls;

import com.ctre.CANTalon;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.Relay;
import edu.wpi.first.wpilibj.command.Subsystem;

/**
 *
 */
public class BallHandler extends Subsystem
{

	private final CANTalon TOP = new CANTalon(6);
    private final CANTalon BOTTOM = new CANTalon(5);

    public BallHandler()
	{
		super();
	}
    public void initDefaultCommand() 
    {
    	setDefaultCommand(new HandleBalls());
    }
    public void handleBalls(Joystick joy)
	{
		if(joy.getRawButton(7) && joy.getRawButton(8))
		{
			Robot.jetsonReset.set(Relay.Value.kOn);
		}
		if(!(joy.getRawButton(7) || joy.getRawButton(8)))
		{
			Robot.jetsonReset.set(Relay.Value.kOff);
		}
	}
    public void handleBalls(double top, double bottm)
	{
	}
}

