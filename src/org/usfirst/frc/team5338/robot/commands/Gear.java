package org.usfirst.frc.team5338.robot.commands;

import edu.wpi.first.wpilibj.*;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.Timer;
import org.usfirst.frc.team5338.robot.Robot;
import java.lang.Math;

public class Gear extends Command {
	
	double dps = 1; //degrees per second at max speed
	double timefordist = 0;
	public Gear(){
		requires(Robot.drivetrain);
		requires(Robot.table);
		setTimeout(12);
	}
	
	protected void intitalize(){
	}
	
	protected double angle(){
		return Math.atan(Robot.table.getValue("x")/1108.5) * (180/Math.PI);
	}
	
	protected void execute(){
		while(angle() > 5){
			Robot.drivetrain.tank(-0.5, 0.5);
			Timer.delay(.5);
		}
		while(angle() < -5){
			Robot.drivetrain.tank(0.5, -0.5);
		}
		while(Robot.table.getValue("width") != null){
			Robot.drivetrain.tank(0.5, 0.5);
			Timer.delay(.5);
		}
		Robot.drivetrain.tank(-0.5, -0.5);
		Timer.delay(2);
		end();
	}
	
	protected boolean isFinished(){
		return isTimedOut();
	}
	
	protected void end(){
		Robot.drivetrain.tank(0, 0);
	}
	
	protected void interrupted(){
		end();
	}
}
