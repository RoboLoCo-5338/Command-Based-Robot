/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.usfirst.frc.team5338.robot.commands;

import edu.wpi.first.wpilibj.command.Command;

import org.usfirst.frc.team5338.robot.Robot;

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
<<<<<<< HEAD:src/org/usfirst/frc/team5338/robot/commands/TankDriveWithJoysticks.java
		Robot.drivetrain.drive(Robot.oi.getJoystick1(), Robot.oi.getJoystick2());
=======
		//Robot.drivetrain.DRIVE.setMaxOutput(1 - ((1 + Robot.oi.getJoystick().getRawAxis(3)) / 2));
		Robot.drivetrain.drive(Robot.oi.getJoystick());
>>>>>>> master:src/org/usfirst/frc/team5338/robot/commands/MecanumDriveWithJoystick.java
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
