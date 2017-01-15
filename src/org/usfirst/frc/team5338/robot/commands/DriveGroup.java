package org.usfirst.frc.team5338.robot.commands;

import org.usfirst.frc.team5338.robot.Robot;

import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 *
 */
public class DriveGroup extends CommandGroup
{

    public DriveGroup()
    {
    	requires(Robot.drivetrain);
    	addParallel(new PowerSync());
    	addParallel(new ArcadeDriveWithJoystick());
    }
}
