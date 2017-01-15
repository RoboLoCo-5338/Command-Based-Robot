package org.usfirst.frc.team5338.robot;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.buttons.JoystickButton;

import org.usfirst.frc.team5338.robot.commands.*;

/**
 * This class is the glue that binds the controls on the physical operator
 * interface to the commands and command groups that allow control of the robot.
 */
public class OI {
	private final Joystick joy = new Joystick(0);
	private JoystickButton Button_5;
	private JoystickButton Button_6;
	public OI()
	{
		Button_5= new JoystickButton(joy, 5);
		Button_6= new JoystickButton(joy, 6);
		
		Button_5.whenPressed(new PowerSettingMax());
		Button_6.whenPressed(new PowerSettingMin());
	}

	public Joystick getJoystick() {
		return joy;
	}
}
