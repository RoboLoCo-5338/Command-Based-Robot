package org.usfirst.frc.team5338.robot;

import edu.wpi.first.wpilibj.Joystick;

public class OI {
	private final Joystick joy0 = new Joystick(0);
	private final Joystick joy1 = new Joystick(1);
	
	public BallState ballState; 
	enum BallState
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
			return joy0;
		else if(n == 1)
			return joy1;
		else
			return null;
	}
}
