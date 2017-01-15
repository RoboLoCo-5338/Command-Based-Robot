package org.usfirst.frc.team5338.robot.commands;

import org.usfirst.frc.team5338.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class PowerSync extends Command
{
    public PowerSync() {
    	requires(Robot.drivetrain);
    }

    // Called just before this Command runs the first time
    protected void initialize()
    {
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute()
    {
    	Robot.drivetrain.DRIVE.setMaxOutput(1 - ((1 + Robot.oi.getJoystick().getRawAxis(2)) / 2)); //CHANGED FOR QDRIVERSTATION, 2 should be 3
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return false;
    }

    // Called once after isFinished returns true
    protected void end() {
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    }
}
