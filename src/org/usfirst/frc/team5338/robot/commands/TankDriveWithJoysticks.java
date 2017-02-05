package org.usfirst.frc.team5338.robot.commands;

import org.usfirst.frc.team5338.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;

/**
 * Have the robot drive tank style using the two Joysticks until interrupted.
 */
public class TankDriveWithJoysticks extends Command
{
	public TankDriveWithJoysticks()
	{
		requires(Robot.drivetrain);
	}
	// Called repeatedly when this Command is scheduled to run
	@Override
	protected void execute()
	{
		Robot.drivetrain.drive(Robot.oi.getJoystick1(), Robot.oi.getJoystick2());
	}
	// Make this return true when this Command no longer needs to run execute()
	@Override
	protected boolean isFinished()
	{
		return false; // Runs until interrupted
	}
	// Called once after isFinished returns true
	@Override
	protected void end() {
		Robot.drivetrain.drive(0, 0);
	}
}
