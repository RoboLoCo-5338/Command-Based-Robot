package org.usfirst.frc.team5338.robot.commands;

import org.usfirst.frc.team5338.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class HandleBalls extends Command
{
    public HandleBalls()
    {
		requires(Robot.ballhandler);
	}
 // Called repeatedly when this Command is scheduled to run
 	protected void execute()
 	{
 		Robot.ballhandler.handleBalls(Robot.oi);
 	}
 	// Make this return true when this Command no longer needs to run execute()
 	protected boolean isFinished()
 	{
 		return false; // Runs until interrupted
 	}
 	// Called once after isFinished returns true
 	protected void end()
 	{
 		Robot.ballhandler.handleBalls(0, 0);
 	}
}
