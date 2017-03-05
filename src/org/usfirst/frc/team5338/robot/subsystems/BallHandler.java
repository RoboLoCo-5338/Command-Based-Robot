package org.usfirst.frc.team5338.robot.subsystems;

import org.usfirst.frc.team5338.robot.OI;
import org.usfirst.frc.team5338.robot.commands.HandleBalls;

import com.ctre.CANTalon;

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
    public void handleBalls(OI oi)
	{
    	
    	switch(oi.ballState)
    	{
    	case LOWER:
			TOP.set(-0.50);
			BOTTOM.set(0.75);
			break;
		case UPPER:
			TOP.set(0.75);
			BOTTOM.set(-0.75);
			break;
		case OUT:
			TOP.set(0.75);
			BOTTOM.set(0.75);
			break;
		default:
			TOP.set(0.0);
			BOTTOM.set(0.0);
    	}
	}
    public void handleBalls(double top, double bottom)
	{
    	TOP.set(top);
		BOTTOM.set(bottom);
	}
}

