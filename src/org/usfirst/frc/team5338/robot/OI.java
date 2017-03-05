package org.usfirst.frc.team5338.robot;

import edu.wpi.first.wpilibj.Joystick;

public class OI {
	private final Joystick joyL = new Joystick(0);
	private final Joystick joyR = new Joystick(1);
	
	public BallState ballState; 
	public enum BallState
	{
		OFF,
		UPPER, //top intake
		LOWER, //ground intake
		OUT
	}
									
	public OI()
	{
		ballState = OFF;
	}
	
	public Joystick getJoystick(int n)
	{
		if(n == 0)
			return joyL;
		else if(n == 1)
			return joyR;
		else
			return null;
	}
	
	public enum Button
	{
		OFF, UPPER, LOWER, OUT,
		
		SLOW, STRAIGHT, REVERSE, FORWARD,
		
		GEAR, JETSONRESET
	}
	
	public boolean get(Button button)
	{
		switch(button)
		{
		case OFF: 			return joyR.getRawButton(0);
		case UPPER: 		return joyR.getRawButton(0);
		case LOWER: 		return joyR.getRawButton(0);
		case OUT: 			return joyR.getRawButton(0);
		
		case SLOW: 			return joyL.getRawButton(1);
		case STRAIGHT:		return joyR.getRawButton(0);
		case REVERSE: 		return joyR.getRawButton(0);
		case FORWARD: 		return joyR.getRawButton(0);
		
		case GEAR: 			return joyR.getRawButton(0);
		case JETSONRESET:	return joyR.getRawButton(0);
		
		case default: return false;
		}
	}
	
	private double joystickDeadZone(double value)
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
	
	public double getLeft()
	{
		return joystickDeadZone(joyL.getRawAxis(1));
	}
	
	public double getRight()
	{
		return joystickDeadZone(joyR.getRawAxis(1));
	}
}
