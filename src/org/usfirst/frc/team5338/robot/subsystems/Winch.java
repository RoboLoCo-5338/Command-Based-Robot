package org.usfirst.frc.team5338.robot.subsystems;

import org.usfirst.frc.team5338.robot.commands.LiftRobot;

import com.ctre.CANTalon;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.command.Subsystem;

/**
 *
 */
public class Winch extends Subsystem
{
	private final CANTalon LIFT = new CANTalon(7);

    public Winch()
	{
		super();
		LIFT.enable();
	}
    public void initDefaultCommand() 
    {
    	setDefaultCommand(new LiftRobot());
    }
    public void liftRobot(Joystick joy)
	{
		if(joy.getRawButton(11))
		{
			LIFT.set(-1.0);
			return;
		}
		if(joy.getRawButton(12))
		{
			LIFT.set(1.0);
		}
		LIFT.set(0.0);
	}
    public void liftRobot(double value)
	{
    	LIFT.set(value);
	}
}

