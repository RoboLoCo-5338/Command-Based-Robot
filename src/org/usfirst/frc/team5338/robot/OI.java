package org.usfirst.frc.team5338.robot;

import edu.wpi.first.wpilibj.Joystick;

/**
 * This class is the glue that binds the controls on the physical operator
 * interface to the commands and command groups that allow control of the robot.
 */
public class OI {
	private final Joystick joy0 = new Joystick(0);
	private final Joystick joy1 = new Joystick(1);
	public String ballState = "";
	public OI()
	{
	}
	public Joystick getJoystick(int n)
	{
		if(n == 0)
			return joy0;
		else
			return joy1;
	}
}
