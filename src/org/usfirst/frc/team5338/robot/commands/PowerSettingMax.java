package org.usfirst.frc.team5338.robot.commands;

import org.usfirst.frc.team5338.robot.Robot;

import edu.wpi.first.wpilibj.GenericHID.RumbleType;
import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class PowerSettingMax extends Command
{

    public PowerSettingMax() {
    	requires(Robot.drivetrain);
    }

    // Called just before this Command runs the first time
    protected void initialize()
    {
    	Robot.drivetrain.DRIVE.setMaxOutput(0.9);
    	Robot.oi.getJoystick().setRumble(RumbleType.kLeftRumble, 1);
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() 
    {	
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() 
    {
        return true;
    }

    // Called once after isFinished returns true
    protected void end()
    {
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted()
    {
    }
}
