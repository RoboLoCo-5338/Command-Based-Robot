package org.usfirst.frc.team5338.robot.commands;

import edu.wpi.first.wpilibj.command.CommandGroup;

import com.ctre.CANTalon;
import com.ctre.CANTalon.TalonControlMode;

import edu.wpi.first.wpilibj.RobotDrive;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 * The main autonomous command to pickup and deliver the soda to the box.
 */
public class Autonomous extends CommandGroup
{
	//Base line is 7ft and 9(1/4)in from alliance wall
	String placement = (SmartDashboard.getString("Placement =", ""));
		
	double fdist = 0;
	double bdist = 0;
	double theta = 0;
	double fdist2 = 0;
	public Autonomous() {
		//Maybe implement a switch to allow multiple placements of robot
		switch(placement)
		{
			case "left":
				addSequential(new MoveF(fdist));
				addSequential(new MoveB(bdist));
				addSequential(new TurnL(theta));
				addSequential(new MoveF(fdist2));
				addSequential(new Gear());
				break;
			case "center":
				addSequential(new MoveF(fdist));
				addSequential(new Gear());
				addSequential(new MoveB(bdist));
				addSequential(new TurnL(theta));
				addSequential(new MoveF(fdist2));
				addSequential(new TurnR(theta));
				addSequential(new MoveF(fdist2));
				break;
			case "right":
				addSequential(new MoveF(fdist));
				addSequential(new MoveB(bdist));
				addSequential(new TurnR(theta));
				addSequential(new MoveF(fdist2));
				addSequential(new Gear());
				break;
			default:
				break;
		}
	}
	    public void changeTalonModeToPosition(CANTalon talon)
	    {
	    	talon.changeControlMode(TalonControlMode.Position);
	    	//TODO
	    }
	    public void changeTalonModeToVoltage(CANTalon talon)
	    {
	    	talon.changeControlMode(TalonControlMode.PercentVbus);
	    	//TODO
	    }
	}