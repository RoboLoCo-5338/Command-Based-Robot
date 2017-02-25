package org.usfirst.frc.team5338.robot.commands;

import org.usfirst.frc.team5338.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class LiftRobot extends Command
{
	public LiftRobot()
    {
		requires(Robot.winch);
	}
 // Called repeatedly when this Command is scheduled to run
 	@Override
 	protected void execute()
 	{
 		Robot.winch.liftRobot(Robot.oi.getJoystick());
 	}
 	// Make this return true when this Command no longer needs to run execute()
 	@Override
 	protected boolean isFinished()
 	{
 		return false; // Runs until interrupted
 	}
 	// Called once after isFinished returns true
 	@Override
 	protected void end()
 	{
 		Robot.winch.liftRobot(0);
 	}
}
