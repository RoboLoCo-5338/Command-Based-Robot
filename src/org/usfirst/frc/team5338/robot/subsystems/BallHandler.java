package org.usfirst.frc.team5338.robot.subsystems;

import org.usfirst.frc.team5338.robot.commands.HandleBalls;

import com.ctre.CANTalon;

import edu.wpi.first.wpilibj.Joystick;
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
		TOP.enable();
		BOTTOM.enable();
	}
    public void initDefaultCommand() 
    {
    	setDefaultCommand(new HandleBalls());
    }
    public void handleBalls(Joystick joy)
	{
		if(joy.getRawButton(5))
		{
			TOP.set(1.0);
			return;
		}
		if(joy.getRawButton(3))
		{
			TOP.set(-0.75);
			BOTTOM.set(1.0);
			return;
		}
		if(joy.getRawButton(4))
		{
			TOP.set(1.0);
			BOTTOM.set(1.0);
			return;
		}
		TOP.set(0.0);
		BOTTOM.set(0.0);
	}
    public void handleBalls(double top, double bottom)
	{
    	TOP.set(top);
		BOTTOM.set(bottom);
	}
}

