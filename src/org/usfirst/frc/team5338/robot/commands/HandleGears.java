package org.usfirst.frc.team5338.robot.commands;

import org.usfirst.frc.team5338.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class HandleGears extends Command
{

    public HandleGears()
    {
		requires(Robot.gearhandler);
	}
    // Called repeatedly when this Command is scheduled to run
    protected void execute()
    {
    	Robot.gearhandler.handleGears(Robot.oi);
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished()
    {
        return false;
    }

    // Called once after isFinished returns true
    protected void end()
    {
    	Robot.gearhandler.stopGears();
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    }
}
