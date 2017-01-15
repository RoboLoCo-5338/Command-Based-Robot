package org.usfirst.frc.team5338.robot.commands;

import org.usfirst.frc.team5338.robot.Robot;

import edu.wpi.first.wpilibj.GenericHID.RumbleType;
import edu.wpi.first.wpilibj.command.TimedCommand;

/**
 *
 */
public class PowerSettingMin extends TimedCommand
{

    public PowerSettingMin()
    {
    	super(1);
    	requires(Robot.drivetrain);
    }

    // Called just before this Command runs the first time
    protected void initialize()
    {
    	Robot.drivetrain.DRIVE.setMaxOutput(0.5);
    	Robot.oi.getJoystick().setRumble(RumbleType.kRightRumble, 1);
    }


    // Called once after isFinished returns true
    protected void end()
    {
    	Robot.oi.getJoystick().setRumble(RumbleType.kRightRumble, 0);
    }
}
