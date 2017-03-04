package org.usfirst.frc.team5338.robot.subsystems;

import org.usfirst.frc.team5338.robot.OI;
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
    public void liftRobot(OI oi)
	{
		Joystick joyR = oi.getJoystick(1);
		if(joyR.getRawButton(4))
		{
			LIFT.set(-0.95);
			return;
		}
		LIFT.set(0.0);
	}
    public void liftRobot(double value)
	{
    	LIFT.set(value);
	}
}

