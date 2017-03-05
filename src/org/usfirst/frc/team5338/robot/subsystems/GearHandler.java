package org.usfirst.frc.team5338.robot.subsystems;

import org.usfirst.frc.team5338.robot.OI;
import org.usfirst.frc.team5338.robot.commands.HandleBalls;

import com.ctre.CANTalon;

import edu.wpi.first.wpilibj.Compressor;
import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.command.Subsystem;

/**
 *
 */
public class GearHandler extends Subsystem
{

	private final Compressor COMPRESSOR = new Compressor(0);
	private final DoubleSolenoid DOOR = new DoubleSolenoid(1, 2); 

    public GearHandler()
	{
		super();
		COMPRESSOR.setClosedLoopControl(true);
	}

    public void initDefaultCommand()
    {
    	setDefaultCommand(new HandleGears());
    }
    public void handleGears(OI oi)
	{
    	Joystick joyL = oi.getJoystick(0);
		if(joyL.getRawButton(4))
		{
			DOOR.set(DoubleSolenoid.Value.kForward);
			return;
		}
		else
			DOOR.set(DoubleSolenoid.Value.kReverse);
	}
    public void stopGears()
    {
    	DOOR.set(DoubleSolenoid.Value.kOff);
    }
}

