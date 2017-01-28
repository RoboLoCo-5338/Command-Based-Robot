package org.usfirst.frc.team5338.robot;

import edu.wpi.first.wpilibj.Joystick;

/**
 * This class is the glue that binds the controls on the physical operator
 * interface to the commands and command groups that allow control of the robot.
 */
public class OI {
	private final Joystick joy1 = new Joystick(0);
	private final Joystick joy2 = new Joystick(1);
	public OI()
	{
	}
	public Joystick getJoystick1()
	{
		return joy1;
	}
	public Joystick getJoystick2()
	{
		return joy2;
	}
}
